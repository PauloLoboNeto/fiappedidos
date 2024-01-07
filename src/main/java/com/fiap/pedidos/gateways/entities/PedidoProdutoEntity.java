package com.fiap.pedidos.gateways.entities;

import com.fiap.pedidos.entities.NomeProduto;
import com.fiap.pedidos.entities.PedidoProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pedido_produtos")
public class PedidoProdutoEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private PedidoEntity pedido;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private ProdutoEntity produto;

    public static PedidoProduto to(PedidoProdutoEntity pedidoProdutoEntity) {
        return PedidoProduto.builder()
                .id(pedidoProdutoEntity.getId())
                .pedidoId(pedidoProdutoEntity.getPedido().getIdPedido())
                .produtoId(pedidoProdutoEntity.getProduto().getIdProduto())
                .build();
    }

    public static PedidoProdutoEntity from(PedidoProduto pedidoProduto, PedidoEntity pedidoEntity, ProdutoEntity produtoEntity) {
        return PedidoProdutoEntity.builder()
                .id(pedidoProduto.getId())
                .pedido(pedidoEntity)
                .produto(produtoEntity)
                .build();
    }
}
