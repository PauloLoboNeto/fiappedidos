package com.fiap.pedidos.interfaces.gateways;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPedidoProdutoRepositoryPort {

    List<PedidoProduto> buscarTodos();
    Optional<PedidoProduto> buscarPorId(UUID id);
    List<PedidoProduto> buscarPorPedido(Pedido pedido);
    PedidoProduto adicionarPedidoProduto(PedidoProduto pedidoProduto);
    PedidoProduto editarPedidoProduto(PedidoProduto pedidoProduto);
    void excluirPedidoProduto(UUID idProduto);
}
