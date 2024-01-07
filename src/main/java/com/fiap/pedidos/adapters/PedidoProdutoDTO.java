package com.fiap.pedidos.adapters;

import com.fiap.pedidos.entities.PedidoProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoProdutoDTO {
    private UUID id;
    private UUID idProduto;

    public static PedidoProdutoDTO from(PedidoProduto pedidoProduto) {
        return PedidoProdutoDTO.builder()
                .id(pedidoProduto.getId())
                .idProduto(pedidoProduto.getProdutoId())
                .build();
    }
}
