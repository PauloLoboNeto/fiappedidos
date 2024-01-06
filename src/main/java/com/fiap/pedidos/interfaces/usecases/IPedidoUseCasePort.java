package com.fiap.pedidos.interfaces.usecases;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.exceptions.entities.PedidoNaoEncontradoException;
import com.fiap.pedidos.utils.enums.StatusPagamento;
import com.fiap.pedidos.utils.enums.StatusPedido;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPedidoUseCasePort {
    Pedido cadastrar(Pedido pedido);
    Pedido atualizar(Pedido pedido);
    Pedido atualizarStatus(StatusPedido status, UUID idPedido) throws PedidoNaoEncontradoException;
    Pedido atualizarStatusPagamento(UUID idPedido) throws PedidoNaoEncontradoException;
    void remover(UUID idPedido);
    Optional<Pedido> buscarPorId(UUID idPedido);
    List<Pedido> buscarPedidosPorClienteEStatus(UUID idCliente, StatusPedido statusPedido);

    List<Pedido> buscarPedidosPorStatus(StatusPedido statusPedido);

    Pedido checkout(UUID idPedido);
}
