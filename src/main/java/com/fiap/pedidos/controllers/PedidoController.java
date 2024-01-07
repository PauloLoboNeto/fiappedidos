package com.fiap.pedidos.controllers;

import com.fiap.pedidos.adapters.PedidoDTO;
import com.fiap.pedidos.controllers.requestValidations.PedidoProdutoRequest;
import com.fiap.pedidos.controllers.requestValidations.PedidoRequest;
import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.interfaces.usecases.IClienteUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoProdutoUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.utils.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tech-challenge")
@RequiredArgsConstructor
public class PedidoController {

    private final IPedidoUseCasePort pedidoUseCasePort;
    private final IPedidoProdutoUseCasePort pedidoProdutoUseCasePort;
    private final IClienteUseCasePort clienteUseCasePort;

    @PostMapping("/pedido")
    public ResponseEntity<PedidoDTO> iniciarPedido(@RequestBody @NotNull PedidoRequest request) {
        Optional<Cliente> cliente = clienteUseCasePort.buscarPorId(request.getIdCliente());

        if (cliente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Pedido pedido = this.pedidoUseCasePort.cadastrar(request.from(request, cliente.get()));

        return Objects.nonNull(pedido) ?
                new ResponseEntity<>(PedidoDTO.from(pedido), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/pedido/{id}")
    public ResponseEntity<PedidoDTO> adicionarItem(
            @PathVariable UUID idPedido, @RequestBody @NotNull PedidoProdutoRequest request) {

        //todo validar produto antes de incluir ao pedido

        PedidoProduto pedidoProduto = request.from(request, idPedido);

        pedidoProdutoUseCasePort.adicionarItemNoPedido(pedidoProduto);

        Optional<Pedido> pedido = this.pedidoUseCasePort.buscarPorId(pedidoProduto.getPedidoId());

        return pedido
                .map(value ->  new ResponseEntity<>(PedidoDTO.from(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    //remover item do pedido
    @DeleteMapping("/pedido/{id}")
    public ResponseEntity<PedidoDTO> removerItem(
            @PathVariable UUID idPedido,
            @RequestBody @NotNull PedidoProdutoRequest request) {

        PedidoProduto pedidoProduto = request.from(request, idPedido);
        pedidoProduto.setPedidoId(idPedido);

        pedidoProdutoUseCasePort.removerItemDoPedido(pedidoProduto);

        Optional<Pedido> pedidoOptional = pedidoUseCasePort.buscarPorId(pedidoProduto.getPedidoId());

        return pedidoOptional
                .map(pedido -> new ResponseEntity<>(PedidoDTO.from(pedido), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    //Utilizado pelo app de fila ao atualizar fila
    @PutMapping("/pedido/{idPedido}/status/{status}")
    public ResponseEntity<PedidoDTO> atualizarStatusDoPedido(
            @PathVariable UUID idPedido,
            @PathVariable StatusPedido status) {

        PedidoDTO pedidoDTO = PedidoDTO.from(pedidoUseCasePort.atualizarPedidoFila(idPedido, status));
        return ResponseEntity.ok().body(pedidoDTO);
    }

    //Utilizado pelo app de pagamentos ao atualizar status do pagamento
    @PostMapping("/pedido/checkout/{idPedido}")
    public ResponseEntity<PedidoDTO> checkout(@PathVariable UUID idPedido) {
        return new ResponseEntity<>(PedidoDTO.from(pedidoUseCasePort.checkout(idPedido)), HttpStatus.OK);
    }


    @GetMapping("/pedidos")
    public ResponseEntity<List<PedidoDTO>> buscarTodos(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "100") int pageSize) {
        List<Pedido> pedidos = pedidoUseCasePort.buscarPedidosPorStatus(pageNumber, pageSize);
        List<PedidoDTO> pedidoDTOs = pedidos.stream()
                .map(PedidoDTO::from)
                .collect(Collectors.toList());
        return new ResponseEntity<>(pedidoDTOs, HttpStatus.OK);
    }

    @PutMapping("/pedido/{idPedido}/webhook")
    public ResponseEntity<PedidoDTO> atualizarStatusDoPagamentoDoPedido(
            @PathVariable UUID idPedido) {
        PedidoDTO pedidoDTO = PedidoDTO.from(pedidoUseCasePort.atualizarPedidoPagamento(idPedido));
        return ResponseEntity.ok().body(pedidoDTO);
    }
}
