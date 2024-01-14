package com.fiap.pedidos.facade;

import com.fiap.pedidos.interfaces.facade.IServiceAsyncProcessWebhook;
import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.utils.enums.TipoAtualizacao;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceAsyncProcessWebhook implements IServiceAsyncProcessWebhook {

    private final IPedidoUseCasePort pedidoUseCasePort;

    @Override
    @Async
    public void processarWebhook(UUID idPedido) {
        this.pedidoUseCasePort.atualizarPedido(idPedido, TipoAtualizacao.P, null, null);
    }
}
