package com.fiap.pedidos.gateways;

import com.fiap.pedidos.interfaces.gateways.IFilaRepositoryPort;
import com.fiap.pedidos.interfaces.repositories.FilaRepository;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FilaRepositoryAdapterTest {

    private IFilaRepositoryPort filaRepositoryPortAdapter;

    @Mock
    private FilaRepository filaRepository;

    AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
//        filaRepositoryPortAdapter = new FilaRepositoryAdapter();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested class InserirPedidoNaFila {

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Inserir pedido na fila")
        void deveInserirPedidoNaFila() {
            doNothing().when(filaRepository).inserePedidoNaFila(any(UUID.class), any(UUID.class));
            filaRepositoryPortAdapter.inserePedidoNaFila(UUID.randomUUID(), UUID.randomUUID());
            verify(filaRepository, times(1))
                    .inserePedidoNaFila(any(UUID.class), any(UUID.class));
        }
    }
}
