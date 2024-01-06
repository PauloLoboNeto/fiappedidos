package com.fiap.pedidos.controllers.requestValidations;

import com.fiap.pedidos.entities.PedidoProduto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProdutoRequest {

    @NotNull(message = "id pedido não pode estar vazio")
    private UUID idPedido;  // changed from id to idPedido
    @NotNull(message = "id produto não pode estar vazio")
    private UUID idPedidoProduto;
    @NotNull(message = "valor não pode ser nulo")
    private BigDecimal valorProduto;
    private String observacaoProduto;

    public PedidoProduto from(PedidoProdutoRequest request) {
        return PedidoProduto.builder()
                .id(request.getIdPedido())  // changed from getId() to getIdPedido()
                .produtoId(request.getIdPedidoProduto())
                .valorProduto(request.getValorProduto())
                .observacaoProduto(request.getObservacaoProduto())
                .build();
    }
}
