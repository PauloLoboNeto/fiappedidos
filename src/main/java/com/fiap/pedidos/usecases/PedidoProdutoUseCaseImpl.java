package com.fiap.pedidos.usecases;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.exceptions.entities.PedidoNaoEncontradoException;
import com.fiap.pedidos.exceptions.entities.PedidoOperacaoNaoSuportadaException;
import com.fiap.pedidos.interfaces.gateways.IPedidoProdutoRepositoryPort;
import com.fiap.pedidos.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.pedidos.interfaces.usecases.IPedidoProdutoUseCasePort;
import com.fiap.pedidos.utils.enums.StatusPedido;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PedidoProdutoUseCaseImpl implements IPedidoProdutoUseCasePort {

    private final IPedidoRepositoryPort pedidoRepositoryPort;
    private final IPedidoProdutoRepositoryPort pedidoProdutoRepositoryPort;

    @Override
    public Optional<PedidoProduto> buscarPorId(UUID id) {
        return pedidoProdutoRepositoryPort.buscarPorId(id);
    }

    @Override
    public List<PedidoProduto> buscarPorPedido(Pedido pedido) {
        return pedidoProdutoRepositoryPort.buscarPorPedido(pedido);
    }

    @Override
    public PedidoProduto adicionarPedidoProduto(PedidoProduto pedidoProduto) {
        checkPedidoStatus(pedidoProduto.getPedidoId());
        return pedidoProdutoRepositoryPort.adicionarPedidoProduto(pedidoProduto);
    }
    @Override
    public PedidoProduto editarPedidoProduto(PedidoProduto pedidoProduto) {
        checkPedidoStatus(pedidoProduto.getPedidoId());
        return pedidoProdutoRepositoryPort.editarPedidoProduto(pedidoProduto);
    }

    @Override
    public void deletarPedidoProduto(UUID id) {
        Optional<PedidoProduto> pedidoProdutoOptional = pedidoProdutoRepositoryPort.buscarPorId(id);
        if (pedidoProdutoOptional.isPresent()) {
            PedidoProduto pedidoProduto = pedidoProdutoOptional.get();
            pedidoProdutoRepositoryPort.excluirPedidoProduto(pedidoProduto.getId());

            Optional<Pedido> pedidoOptional = pedidoRepositoryPort.buscarPorId(pedidoProduto.getPedidoId());
            if (pedidoOptional.isPresent()) {
                Pedido pedido = pedidoOptional.get();
                pedido.setDataAtualizacao(new Date());
                pedidoRepositoryPort.atualizar(pedido);
                pedidoRepositoryPort.atualizar(pedido);
            }
        }
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

}
