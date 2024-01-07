package com.fiap.pedidos.usecases;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.exceptions.entities.PedidoNaoEncontradoException;
import com.fiap.pedidos.exceptions.entities.PedidoOperacaoNaoSuportadaException;
import com.fiap.pedidos.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.utils.enums.StatusPagamento;
import com.fiap.pedidos.utils.enums.StatusPedido;
import com.fiap.pedidos.utils.enums.TipoAtualizacao;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class PedidoUseCaseImpl implements IPedidoUseCasePort {

    private final IPedidoRepositoryPort pedidoRepositoryPort;

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
    public void remover(UUID id) {
        pedidoRepositoryPort.remover(id);
    }

    @Override
    public Pedido atualizarPedidoPagamento(UUID idPedido) {
        Optional<Pedido> pedidoOptional = buscarPorId(idPedido);

        if (pedidoOptional.isEmpty()) {
            throw new PedidoNaoEncontradoException();
        }
        return this.atualizarPedido(pedidoOptional.get(), TipoAtualizacao.P);
    }

    @Override
    public Pedido atualizarPedidoFila(UUID idPedido, StatusPedido statusPedido) {
        Optional<Pedido> pedidoOptional = buscarPorId(idPedido);

        if (pedidoOptional.isEmpty()) {
            throw new PedidoNaoEncontradoException();
        }
        return this.atualizarPedido(pedidoOptional.get(), TipoAtualizacao.F);
    }

    @Override
    public Pedido atualizarPedidoDefault(Pedido pedido) {
        Optional<Pedido> pedidoOptional = buscarPorId(pedido.getIdPedido());

        if (pedidoOptional.isEmpty()) {
            throw new PedidoNaoEncontradoException();
        }
        return this.atualizarPedido(pedidoOptional.get(), TipoAtualizacao.D);
    }


    public Pedido atualizarPedido(Pedido pedido, TipoAtualizacao tipoAtualizacao) {
        Pedido existingPedido = checkPedidoStatus(pedido.getIdPedido());

        if(tipoAtualizacao == TipoAtualizacao.F) {
            existingPedido.setStatusPedido(pedido.getStatusPedido());
        }

        if(tipoAtualizacao == TipoAtualizacao.P ){
            StatusPagamento status = StatusPagamento.APROVADO; //TODO por enquanto o retorno está mockado,
            // chamar api de pagamento quando tiver pronto

            switch (pedido.getStatusPagamento()) {
                case APROVADO -> {
                    pedido.setStatusPedido(StatusPedido.R);
                    pedido.setStatusPagamento(status);

                    //filaUseCasePort.inserirPedidoNaFila(pedido); TODO chamar api de fila
                }
                case RECUSADO -> {
                    pedido.setStatusPedido(StatusPedido.F);
                    pedido.setStatusPagamento(status);
                }
            }
        }

        existingPedido.setProdutos(pedido.getProdutos());
        existingPedido.setValorPedido(pedido.getValorPedido());
        existingPedido.setDataAtualizacao(new Date());

        return pedidoRepositoryPort.atualizarPedido(existingPedido);
    }


    @Override
    public Optional<Pedido> buscarPorId(UUID id) {
        return pedidoRepositoryPort.buscarPorId(id);
    }

    @Override
    public Pedido checkout(UUID id) {
        Pedido pedido = checkPedidoStatus(id);
        pedido.setDataAtualizacao(new Date());
        pedidoRepositoryPort.atualizarPedido(pedido);
        return pedido;
    }

    private Pedido checkPedidoStatus(UUID idPedido) {
        Optional<Pedido> optionalPedido = pedidoRepositoryPort.buscarPorId(idPedido);

        if (optionalPedido.isEmpty()) {
            throw new PedidoNaoEncontradoException("Pedido não encontrado.");
        }

        if (optionalPedido.get().getStatusPedido() != StatusPedido.A) {
            throw new PedidoOperacaoNaoSuportadaException("Pedido não está aberto para edição.");
        }

        return optionalPedido.get();
    }

    @Override
    public List<Pedido> buscarPedidosPorStatus(StatusPedido statusPedido) {
        return pedidoRepositoryPort.buscarPedidosPorStatus(statusPedido);
    }
}
