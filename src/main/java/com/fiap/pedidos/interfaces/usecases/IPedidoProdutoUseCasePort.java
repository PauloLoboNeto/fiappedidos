package com.fiap.pedidos.interfaces.usecases;


import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;

import java.util.Optional;
import java.util.UUID;

public interface IPedidoProdutoUseCasePort {

    Pedido adicionarItemNoPedido(PedidoProduto pedidoProduto);
    Pedido removerItemDoPedido(PedidoProduto pedidoProduto);
}
