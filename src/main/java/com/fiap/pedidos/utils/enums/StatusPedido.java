package com.fiap.pedidos.utils.enums;

public enum StatusPedido {
    A("Aberto"),
    R("Pagamento Recebido"),
    E("Em preparação"),
    C("Concluído"),
    F("Finalizado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
