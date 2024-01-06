package com.fiap.pedidos.exceptions.entities;

public class PedidoPagamentoErroException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PedidoPagamentoErroException() {
        super("Ocorreu um erro na finalização do pedido");
    }

    public PedidoPagamentoErroException(String msg) {
        super(msg);
    }
}
