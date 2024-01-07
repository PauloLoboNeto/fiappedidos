package com.fiap.pedidos.usecases;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.exceptions.entities.PedidoNaoEncontradoException;
import com.fiap.pedidos.exceptions.entities.PedidoOperacaoNaoSuportadaException;
import com.fiap.pedidos.exceptions.entities.PedidoProdutoNaoEncontradoException;
import com.fiap.pedidos.exceptions.entities.ProdutoNaoEncontradoException;
import com.fiap.pedidos.interfaces.gateways.IPedidoProdutoRepositoryPort;
import com.fiap.pedidos.interfaces.usecases.IPedidoProdutoUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IProdutoUseCasePort;
import com.fiap.pedidos.utils.enums.StatusPedido;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
public class PedidoProdutoUseCaseImpl implements IPedidoProdutoUseCasePort {

    private final IPedidoProdutoRepositoryPort pedidoProdutoRepositoryPort;
    private final IPedidoUseCasePort pedidoUseCasePort;
    private final IProdutoUseCasePort produtoUseCasePort;

    @Override
    public Pedido adicionarItemNoPedido(PedidoProduto pedidoProduto) {
        Optional<PedidoProduto> optionalPedidoProduto = pedidoProdutoRepositoryPort.buscarPorId(pedidoProduto.getId());
        validarPedidoProduto(optionalPedidoProduto);

        Optional<Pedido> optionalPedido = pedidoUseCasePort.buscarPorId(pedidoProduto.getPedidoId());
        validarPedido(optionalPedido);

        Optional<Produto> optionalProduto = produtoUseCasePort.buscarPorId(pedidoProduto.getProdutoId());
        validarProduto(optionalProduto);

        Pedido pedido = optionalPedido.get();
        Produto produto = optionalProduto.get();

        pedidoProdutoRepositoryPort.adicionarPedidoProduto(pedido, produto, pedidoProduto);

        pedido.setDataAtualizacao(new Date());
        pedido.setValorPedido(
                pedido.getValorPedido()
                        .add(produto.getValorProduto().getValorProduto())
        );

       return pedidoUseCasePort.atualizarPedido(pedido);
    }

    @Override
    public Pedido removerItemDoPedido(PedidoProduto pedidoProduto) {
        Optional<PedidoProduto> optionalPedidoProduto = pedidoProdutoRepositoryPort.buscarPorId(pedidoProduto.getId());
        validarPedidoProduto(optionalPedidoProduto);

        Optional<Pedido> optionalPedido = pedidoUseCasePort.buscarPorId(pedidoProduto.getPedidoId());
        validarPedido(optionalPedido);

        Optional<Produto> optionalProduto = produtoUseCasePort.buscarPorId(pedidoProduto.getProdutoId());
        validarProduto(optionalProduto);

        pedidoProdutoRepositoryPort.excluirPedidoProduto(pedidoProduto.getId());

        Pedido pedido = optionalPedido.get();
        Produto produto = optionalProduto.get();

        var novoValorPedido = pedido.getValorPedido()
                .subtract(produto.getValorProduto().getValorProduto());

        pedido.setValorPedido(novoValorPedido);
        pedido.setDataAtualizacao(new Date());

        return pedidoUseCasePort.atualizarPedido(pedido);
    }

    private void validarPedido(Optional<Pedido> optionalPedido) {

        if (optionalPedido.isEmpty()) {
            throw new PedidoNaoEncontradoException("Pedido não encontrado.");
        }

        if (optionalPedido.get().getStatusPedido() != StatusPedido.A) {
            throw new PedidoOperacaoNaoSuportadaException("Pedido não está aberto para edição.");
        }
    }

    private void validarProduto(Optional<Produto> optionalProduto) {
        if (optionalProduto.isEmpty()) {
            throw new ProdutoNaoEncontradoException("Produto não encontrado.");
        }
    }

    private void validarPedidoProduto(Optional<PedidoProduto> optionalPedidoProduto) {
        if (optionalPedidoProduto.isEmpty()) {
            throw new PedidoProdutoNaoEncontradoException("Pedido Produto não encontrado.");
        }
    }
}
