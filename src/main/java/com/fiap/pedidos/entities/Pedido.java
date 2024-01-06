package com.fiap.pedidos.entities;

import com.fiap.pedidos.utils.enums.StatusPagamento;
import com.fiap.pedidos.utils.enums.StatusPedido;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    private UUID idPedido;
    private Cliente cliente;
    private List<PedidoProduto> produtos;
    private StatusPedido statusPedido;
    private StatusPagamento statusPagamento;
    private BigDecimal valorPedido;
    private Date dataInclusao;
    private Date dataAtualizacao;

}
