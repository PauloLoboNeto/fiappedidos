package com.fiap.pedidos.facade;

import com.fiap.pedidos.helpers.Helper;
import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.utils.enums.TipoAtualizacao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.annotation.Async;

import java.util.UUID;

import static org.mockito.Mockito.*;

class ServiceAsyncProcessWebhookTest {

    @InjectMocks
    private ServiceAsyncProcessWebhook serviceAsyncProcessWebhook;

    @Mock
    private IPedidoUseCasePort pedidoUseCasePort;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        serviceAsyncProcessWebhook = new ServiceAsyncProcessWebhook(pedidoUseCasePort);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    @DisplayName("Deve processar o webhook e chamar o método de atualizar pedido")
    void deveProcessarWebhookEAtualizarPedido() {
        UUID idPedido = UUID.randomUUID();

        when(pedidoUseCasePort.atualizarPedido(
                any(UUID.class),
                any(TipoAtualizacao.class),
                any(),
                any()
        )).thenReturn(Helper.gerarPedidoComCliente());

        serviceAsyncProcessWebhook.processarWebhook(idPedido);

        verify(pedidoUseCasePort, times(1))
                .atualizarPedido(eq(idPedido), eq(TipoAtualizacao.P),
                        isNull(),
                        isNull());
    }
}
