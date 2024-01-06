package com.fiap.pedidos.controllers;

import com.fiap.pedidos.adapters.PedidoDTO;
import com.fiap.pedidos.adapters.PedidoProdutoDTO;
import com.fiap.pedidos.controllers.requestValidations.PedidoProdutoRequest;
import com.fiap.pedidos.controllers.requestValidations.PedidoRequest;
import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.interfaces.usecases.IClienteUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoProdutoUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.utils.enums.StatusPagamento;
import com.fiap.pedidos.utils.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        if (request.getIdCliente() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Cliente> cliente = clienteUseCasePort.buscarPorId(request.getIdCliente()); // TODO chamar serviço de clientes
        if (!cliente.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pedido pedido = this.pedidoUseCasePort.cadastrar(request.from(request, cliente.get()));
        if (pedido != null) {
            return new ResponseEntity<>(PedidoDTO.from(pedido), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/pedido/{id}")
    public ResponseEntity<PedidoProdutoDTO> adicionarProdutoAoPedido(
            @PathVariable UUID idPedido, @RequestBody @NotNull PedidoProdutoRequest request) {
        PedidoProduto pedidoProduto = request.from(request);
        pedidoProduto.setPedidoId(idPedido);
        PedidoProduto addedPedidoProduto = pedidoProdutoUseCasePort.adicionarPedidoProduto(pedidoProduto);
        if (addedPedidoProduto != null) {
            PedidoProdutoDTO pedidoProdutoDTO = PedidoProdutoDTO.from(addedPedidoProduto);
            return new ResponseEntity<>(pedidoProdutoDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //remover item do pedido
    @DeleteMapping("/pedido/{idPedido}/produto/{id}")
    public ResponseEntity<PedidoDTO> excluirProdutoDoPedido(@PathVariable UUID idPedido, @PathVariable UUID id) {
        pedidoProdutoUseCasePort.deletarPedidoProduto(id);
        Optional<Pedido> pedidoOptional = pedidoUseCasePort.buscarPorId(idPedido);
        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            PedidoDTO pedidoDTO = PedidoDTO.from(pedido);
            return new ResponseEntity<>(pedidoDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //buscar pedido por id
    @GetMapping("/pedidos/{id}")
    public ResponseEntity<PedidoDTO> buscarPedidoPorId(@PathVariable UUID id) {
        return pedidoUseCasePort.buscarPorId(id)
                .map(pedido -> new ResponseEntity<>(PedidoDTO.from(pedido), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/pedido/{idPedido}/status/{status}")
    public ResponseEntity<PedidoDTO> atualizarStatusDoPedido(
            @PathVariable UUID idPedido, @PathVariable StatusPedido status) {
        PedidoDTO pedidoDTO = PedidoDTO.from(pedidoUseCasePort.atualizarStatus(status, idPedido));
        return ResponseEntity.ok().body(pedidoDTO);
    }

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
        PedidoDTO pedidoDTO = PedidoDTO.from(pedidoUseCasePort.atualizarStatusPagamento(idPedido));
        return ResponseEntity.ok().body(pedidoDTO);
    }
}
