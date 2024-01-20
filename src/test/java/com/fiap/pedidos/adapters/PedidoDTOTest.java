package com.fiap.pedidos.adapters;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.helpers.Helper;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PedidoDTOTest {

    @Nested
    class ConverterParaPedidoDto {

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Converter Pedido para DTO")
        void converterPedidoParaDto() {
            Pedido pedido = Helper.gerarPedidoComClienteEProdutos();

            PedidoDTO pedidoDTO = PedidoDTO.from(pedido);

            assertThat(pedidoDTO).isNotNull();
            assertThat(pedidoDTO.getIdPedido()).isEqualTo(pedido.getIdPedido());
            assertThat(pedidoDTO.getIdCliente()).isEqualTo(pedido.getCliente().getId());
            assertThat(pedidoDTO.getProdutos()).isNotNull();
            assertThat(pedidoDTO.getStatusPedido()).isEqualTo(pedido.getStatusPedido());
            assertThat(pedidoDTO.getValorPedido()).isEqualTo(pedido.getValorPedido());
            assertThat(pedidoDTO.getDataInclusao()).isEqualTo(pedido.getDataInclusao());
            assertThat(pedidoDTO.getDataAtualizacao()).isEqualTo(pedido.getDataAtualizacao());
        }
    }
}
