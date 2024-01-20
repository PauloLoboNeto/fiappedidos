package com.fiap.pedidos.adapters;

import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.helpers.Helper;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProdutoDTOTest {

    @Nested
    class ConverterParaProdutoDto {

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Converter Produto para DTO")
        void converterProdutoParaDto() {
            Produto produto = Helper.gerarProdutoLanche();

            ProdutoDTO produtoDTO = ProdutoDTO.from(produto);

            assertThat(produtoDTO).isNotNull();
            assertThat(produtoDTO.getId()).isEqualTo(produto.getIdProduto());
            assertThat(produtoDTO.getNome()).isEqualTo(produto.getNomeProduto().getNome());
            assertThat(produtoDTO.getDescricao()).isEqualTo(produto.getDescricaoProduto().getDescricao());
            assertThat(produtoDTO.getTipo()).isEqualTo(produto.getTipoProduto().name());
            assertThat(produtoDTO.getValor()).isEqualTo(produto.getValorProduto().getValorProduto());
        }
    }
}