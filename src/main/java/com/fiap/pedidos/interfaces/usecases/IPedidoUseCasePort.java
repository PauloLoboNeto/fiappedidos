package com.fiap.pedidos.interfaces.usecases;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.utils.enums.StatusPedido;
import com.fiap.pedidos.utils.enums.TipoAtualizacao;

import java.util.Optional;
import java.util.UUID;

public interface IPedidoUseCasePort {
    Pedido iniciarPedido(Pedido pedido);

    Optional<Pedido> buscarPorId(UUID idPedido);

//    List<Pedido> buscarTodos(int pageNumber, int pageSize);

    Pedido atualizarPedido(UUID idPedido, TipoAtualizacao tipoAtualizacao, Pedido pedido, StatusPedido statusPedido);

    Pedido atualizarPedido(Pedido pedido);
}
