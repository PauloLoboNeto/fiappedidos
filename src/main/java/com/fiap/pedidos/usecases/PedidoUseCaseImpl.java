package com.fiap.pedidos.usecases;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.exceptions.entities.PedidoNaoEncontradoException;
import com.fiap.pedidos.exceptions.entities.PedidoOperacaoNaoSuportadaException;
import com.fiap.pedidos.exceptions.entities.PedidoPagamentoErroException;
import com.fiap.pedidos.interfaces.gateways.IPedidoProdutoRepositoryPort;
import com.fiap.pedidos.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.utils.enums.StatusPagamento;
import com.fiap.pedidos.utils.enums.StatusPedido;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@RequiredArgsConstructor
public class PedidoUseCaseImpl implements IPedidoUseCasePort {

    private final IPedidoRepositoryPort pedidoRepositoryPort;
    private final IPedidoProdutoRepositoryPort pedidoProdutoRepositoryPort;

    @Override
    public Pedido cadastrar(Pedido pedido) {
        UUID idCliente = pedido.getCliente().getId();
        List<Pedido> pedidosAtivos = buscarPedidosPorClienteEStatus(idCliente, StatusPedido.A);
        if (pedidosAtivos.isEmpty()) {
            return pedidoRepositoryPort.cadastrar(pedido);
        } else {
            pedidosAtivos.sort(Comparator.comparing(Pedido::getDataInclusao).reversed());
            return pedidosAtivos.get(0);
        }
    }
    @Override
    public Pedido atualizar(Pedido pedido) {
        Pedido existingPedido = checkPedidoStatus(pedido.getIdPedido());
        existingPedido.setProdutos(pedido.getProdutos());
        existingPedido.setValorPedido(pedido.getValorPedido());
        existingPedido.setStatusPedido(pedido.getStatusPedido());
        existingPedido.setDataAtualizacao(new Date());
        return pedidoRepositoryPort.atualizar(existingPedido);
    }

    @Override
    public Pedido atualizarStatus(StatusPedido status, UUID idPedido) throws PedidoNaoEncontradoException {
        Optional<Pedido> pedido = buscarPorId(idPedido);
        if (pedido.isEmpty()) {
            throw new PedidoNaoEncontradoException();
        }
        pedido.get().setStatusPedido(status);
        pedido.get().setDataAtualizacao(new Date());
        return pedidoRepositoryPort.atualizar(pedido.get());
    }

    @Override
    public Pedido atualizarStatusPagamento(UUID idPedido) throws PedidoNaoEncontradoException {
        Optional<Pedido> pedido = buscarPorId(idPedido);
        if (pedido.isEmpty()) {
            throw new PedidoNaoEncontradoException();
        }
        return this.atualizarPedidoPagamento(pedido.get());
    }

    private Pedido atualizarPedidoPagamento(Pedido pedido) {
        StatusPagamento status = StatusPagamento.APROVADO; //TODO chamar api de pagamento

        pedido.setStatusPagamento(status);

        switch (pedido.getStatusPagamento()) {
            case APROVADO -> {
                pedido.setStatusPedido(StatusPedido.R);
                pedido.setDataAtualizacao(new Date());
                //filaUseCasePort.inserirPedidoNaFila(pedido); TODO chamar api de fila
            }
            case RECUSADO -> {
                pedido.setStatusPedido(StatusPedido.F);
                pedido.setDataAtualizacao(new Date());
            }
        }
        return pedido;
    }

    @Override
    public void remover(UUID id) {
        pedidoRepositoryPort.remover(id);
    }

    @Override
    public Optional<Pedido> buscarPorId(UUID id) {
        return pedidoRepositoryPort.buscarPorId(id);
    }

    @Override
    public List<Pedido> buscarPedidosPorClienteEStatus(UUID idCliente, StatusPedido statusPedido) {
        return pedidoRepositoryPort.buscarPedidosPorClienteEStatus(idCliente, statusPedido);
    }

    @Override
    public Pedido checkout(UUID id) {
        Pedido pedido = checkPedidoStatus(id);
        BigDecimal valorTotal = pedido.getProdutos().stream()
                .map(PedidoProduto::getValorProduto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setValorPedido(valorTotal);
        pedido.setDataAtualizacao(new Date());
        pedidoRepositoryPort.atualizar(pedido);
        return pedido;
    }

    private Pedido checkPedidoStatus(UUID idPedido) {
        Optional<Pedido> optionalPedido = pedidoRepositoryPort.buscarPorId(idPedido);
        if (optionalPedido.isPresent()) {
            Pedido existingPedido = optionalPedido.get();
            if (existingPedido.getStatusPedido() == StatusPedido.A) {
                return existingPedido;
            } else {
                throw new PedidoOperacaoNaoSuportadaException("Pedido não está aberto para edição.");
            }
        } else {
            throw new PedidoNaoEncontradoException("Pedido não encontrado.");
        }
    }

    @Override
    public List<Pedido> buscarPedidosPorStatus(StatusPedido statusPedido) {
        return pedidoRepositoryPort.buscarPedidosPorStatus(statusPedido);
    }

}
