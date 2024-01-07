package com.fiap.pedidos.interfaces.usecases;


import com.fiap.pedidos.entities.PedidoProduto;

import java.util.Optional;
import java.util.UUID;

public interface IPedidoProdutoUseCasePort {

    void adicionarItemNoPedido(PedidoProduto pedidoProduto);
    void  removerItemDoPedido(PedidoProduto pedidoProduto);
}
