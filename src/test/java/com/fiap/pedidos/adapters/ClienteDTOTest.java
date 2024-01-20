package com.fiap.pedidos.adapters;

import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.helpers.Helper;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ClienteDTOTest {

    @Nested
    class ConverterParaClienteDto {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Converter Cliente para DTO")
        void converterClienteParaDto() {
            Cliente cliente = Helper.gerarClienteComTodosDados();

            ClienteDTO clienteDTO = new ClienteDTO().from(cliente);


            assertThat(clienteDTO).isNotNull();
            assertThat(clienteDTO.getId()).isEqualTo(cliente.getId());
            assertThat(clienteDTO.getNome()).isEqualTo(cliente.getNome().getNome());
            assertThat(clienteDTO.getCpf()).isEqualTo(cliente.getCpf().getCpf());
            assertThat(clienteDTO.getEmail()).isEqualTo(cliente.getEmail().getEmail());
        }
    }
}

