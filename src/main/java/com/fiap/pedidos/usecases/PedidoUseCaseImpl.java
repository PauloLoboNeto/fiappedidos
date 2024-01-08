package com.fiap.pedidos.usecases;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.exceptions.entities.PedidoNaoEncontradoException;
import com.fiap.pedidos.exceptions.entities.PedidoOperacaoNaoSuportadaException;
import com.fiap.pedidos.interfaces.gateways.IPagamentoRepositoryPort;
import com.fiap.pedidos.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.utils.enums.StatusPagamento;
import com.fiap.pedidos.utils.enums.StatusPedido;
import com.fiap.pedidos.utils.enums.TipoAtualizacao;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@RequiredArgsConstructor
public class PedidoUseCaseImpl implements IPedidoUseCasePort {

    private final IPedidoRepositoryPort pedidoRepositoryPort;
    private final IPagamentoRepositoryPort pagamentoRepositoryPort;

    @Override
    public Pedido iniciarPedido(Pedido pedido) {
        UUID idCliente = pedido.getCliente().getId();

        List<Pedido> pedidosAtivos = this.pedidoRepositoryPort
                .buscarPedidosPorClienteEStatus(idCliente, StatusPedido.A);

        if (pedidosAtivos.isEmpty()) {
            pedido.setValorPedido(new BigDecimal("0.0"));
            return pedidoRepositoryPort.cadastrar(pedido);
        }

        pedidosAtivos.sort(Comparator.comparing(Pedido::getDataInclusao).reversed());
        return pedidosAtivos.get(0);
    }

    @Override
    public Pedido atualizarPedido(
            UUID idPedido,
            TipoAtualizacao tipoAtualizacao,
            Pedido pedidoRequest,
            StatusPedido statusPedido) {

        Optional<Pedido> pedidoOptional = buscarPorId(idPedido);

        if (pedidoOptional.isEmpty()) {
            throw new PedidoNaoEncontradoException();
        }

        Pedido pedidoExistente = pedidoOptional.get();

        switch (tipoAtualizacao){
            case F -> pedidoExistente.setStatusPedido(statusPedido);
            case C -> {
                if (pedidoExistente.getStatusPedido() != StatusPedido.A) {
                    throw new PedidoOperacaoNaoSuportadaException("Pedido não está aberto para edição.");
                }
                pedidoExistente.setStatusPagamento(StatusPagamento.PENDENTE);
            }
            case I -> {
                if (pedidoExistente.getStatusPedido() != StatusPedido.A) {
                    throw new PedidoOperacaoNaoSuportadaException("Pedido não está aberto para edição.");
                }
                pedidoExistente.setProdutos(pedidoRequest.getProdutos());
                pedidoExistente.setValorPedido(pedidoRequest.getValorPedido());
                pedidoExistente.setStatusPedido(pedidoRequest.getStatusPedido());
                pedidoExistente.setDataAtualizacao(new Date());
            }
            case P -> this.atualizarStatusPagamento(pedidoExistente);
        }

        return this.atualizarPedido(pedidoExistente);
    }

    private void atualizarStatusPagamento(Pedido pedido) {
        StatusPagamento status = this.pagamentoRepositoryPort.consultaPagamento(pedido.getIdPedido());

        switch (status) {
            case APROVADO -> {
                pedido.setStatusPedido(StatusPedido.R);
                pedido.setStatusPagamento(status);
                //filaUseCasePort.inserirPedidoNaFila(pedido); TODO chamar api de fila
            }
            case RECUSADO -> {
                pedido.setStatusPedido(StatusPedido.F);
                pedido.setStatusPagamento(StatusPagamento.RECUSADO);
            }
            case PENDENTE -> {
                pedido.setStatusPedido(StatusPedido.A);
                pedido.setStatusPagamento(StatusPagamento.PENDENTE);
            }
        }
    }

    @Override
    public Pedido atualizarPedido(Pedido pedido) {
        return pedidoRepositoryPort.atualizarPedido(pedido);
    }

    @Override
    public Optional<Pedido> buscarPorId(UUID id) {
        return pedidoRepositoryPort.buscarPorId(id);
    }

    @Override
    public List<Pedido> buscarTodos(int pageNumber, int pageSize) {
        return pedidoRepositoryPort.buscarTodos(pageNumber, pageSize);
    }
}
