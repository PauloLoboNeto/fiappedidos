package com.fiap.pedidos.interfaces.usecases;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.exceptions.entities.PedidoNaoEncontradoException;
import com.fiap.pedidos.utils.enums.StatusPagamento;
import com.fiap.pedidos.utils.enums.StatusPedido;
import com.fiap.pedidos.utils.enums.TipoAtualizacao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPedidoUseCasePort {
    Pedido cadastrar(Pedido pedido);

    Pedido atualizarPedido(Pedido pedido, TipoAtualizacao tipoAtualizacao)  throws PedidoNaoEncontradoException;

    void remover(UUID idPedido);
    Optional<Pedido> buscarPorId(UUID idPedido);

    List<Pedido> buscarPedidosPorStatus(StatusPedido statusPedido);

    Pedido checkout(UUID idPedido);

    Pedido atualizarPedidoPagamento(UUID idPedido);

    Pedido atualizarPedidoFila(UUID idPedido, StatusPedido statusPedido);

    Pedido atualizarPedidoDefault(Pedido pedido);

}
