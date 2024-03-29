package com.fiap.pedidos.entities;

import com.fiap.pedidos.exceptions.entities.ValorProdutoInvalidoException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public final class ValorProduto {
    private BigDecimal valorProduto;

    public ValorProduto(BigDecimal valorProduto) {
        if (Objects.isNull(valorProduto)) {
            throw new ValorProdutoInvalidoException();
        }
        this.valorProduto = valorProduto.setScale(2);
    }
}
