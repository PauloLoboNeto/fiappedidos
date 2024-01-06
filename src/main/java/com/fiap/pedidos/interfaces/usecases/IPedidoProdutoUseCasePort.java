package com.fiap.pedidos.interfaces.usecases;


import com.fiap.pedidos.entities.PedidoProduto;

import java.util.Optional;
import java.util.UUID;

public interface IPedidoProdutoUseCasePort {

    Optional<PedidoProduto> buscarPorId(UUID id);
    PedidoProduto adicionarPedidoProduto(PedidoProduto pedidoProduto);
    void  deletarPedidoProduto(UUID idPedidoProduto);
}
