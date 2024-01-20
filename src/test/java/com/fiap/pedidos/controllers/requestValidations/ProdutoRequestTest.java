package com.fiap.pedidos.controllers.requestValidations;

import com.fiap.pedidos.entities.DescricaoProduto;
import com.fiap.pedidos.entities.NomeProduto;
import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.entities.ValorProduto;
import com.fiap.pedidos.utils.enums.TipoProduto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProdutoRequestTest {

    @Test
    @DisplayName("Deve criar um Produto a partir de um ProdutoRequest válido")
    void deveCriarProdutoAPartirDeProdutoRequestValido() {
        ProdutoRequest produtoRequest = new ProdutoRequest();
        produtoRequest.setNome("Nome do Produto");
        produtoRequest.setDescricao("Descrição do Produto");
        produtoRequest.setTipo(TipoProduto.BEBIDA.getCodigo());
        produtoRequest.setValor(BigDecimal.TEN);

        Produto produto = produtoRequest.from(produtoRequest);

        assertThat(produto).isNotNull();
        assertThat(produto.getNomeProduto()).isEqualTo(new NomeProduto("Nome do Produto"));
        assertThat(produto.getDescricaoProduto()).isEqualTo(new DescricaoProduto("Descrição do Produto"));
        assertThat(produto.getTipoProduto()).isEqualTo(TipoProduto.BEBIDA);
        assertThat(produto.getValorProduto()).isEqualTo(new ValorProduto(BigDecimal.TEN));
    }

}
