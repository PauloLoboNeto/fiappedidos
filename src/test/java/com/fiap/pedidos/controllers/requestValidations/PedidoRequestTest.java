package com.fiap.pedidos.controllers.requestValidations;
import com.fiap.pedidos.controllers.requestValidations.PedidoRequest;
import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Pedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PedidoRequestTest {

    @Test
    @DisplayName("Deve criar um Pedido a partir de um PedidoRequest válido")
    void deveCriarPedidoAPartirDePedidoRequestValido() {
        PedidoRequest pedidoRequest = new PedidoRequest();
        UUID idCliente = UUID.randomUUID();
        pedidoRequest.setIdCliente(idCliente);

        Cliente cliente = mock(Cliente.class);
        Pedido pedido = pedidoRequest.from(pedidoRequest, cliente);
        assertThat(pedido).isNotNull();
        assertThat(pedido.getCliente()).isEqualTo(cliente);
    }

    @Test
    @DisplayName("Deve criar um Pedido a partir de um PedidoRequest com idCliente nulo")
    void deveCriarPedidoAPartirDePedidoRequestComIdClienteNulo() {
        PedidoRequest pedidoRequest = new PedidoRequest();
        pedidoRequest.setIdCliente(null);

        Cliente cliente = mock(Cliente.class);
        Pedido pedido = pedidoRequest.from(pedidoRequest, cliente);

        assertThat(pedido).isNotNull();
        assertThat(pedido.getCliente()).isEqualTo(cliente);
    }
}