package com.fiap.pedidos.sqslisteners;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.sqslisteners.request.AtualizaPedidoFila;
import com.fiap.pedidos.utils.enums.TipoAtualizacao;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.config.SqsListenerConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.util.MimeType;

@Configuration
@RequiredArgsConstructor
public class AtualizaPedidoListener {

    private final IPedidoUseCasePort pedidoUseCasePort;

    @SqsListener(queueNames = "${queue.update.pedido}")
    public void listenStatusUpdate(AtualizaPedidoFila message) {
        if(message.getTipoAtualizacao().equals(TipoAtualizacao.P)){
            this.pedidoUseCasePort.atualizarPedido(
                    message.getIdPedido(),
                    TipoAtualizacao.P,
                    null,
                    null);
        } else if(message.getTipoAtualizacao().equals(TipoAtualizacao.F)) {
            this.pedidoUseCasePort.atualizarPedido(
                    message.getIdPedido(),
                    TipoAtualizacao.F,
                    null,
                    message.getStatusPedido());
        }
    }
}
