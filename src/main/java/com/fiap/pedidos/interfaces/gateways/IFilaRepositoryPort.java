package com.fiap.pedidos.interfaces.gateways;

import com.fiap.pedidos.utils.enums.StatusPagamento;

import java.util.UUID;

public interface IFilaRepositoryPort {
    void inserePedidoNaFila(UUID idPedido, UUID idCliente);
}
