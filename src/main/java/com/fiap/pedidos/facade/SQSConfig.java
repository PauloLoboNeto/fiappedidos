package com.fiap.pedidos.facade;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.QueueNotFoundStrategy;
import io.awspring.cloud.sqs.listener.acknowledgement.AcknowledgementOrdering;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.cloud.sqs.operations.TemplateAcknowledgementMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.time.Duration;

@Configuration
public class SQSConfig {

    @Value("${queue.fila.pedido}")
    private String nomeDaFila;

    @Bean
    public SqsTemplate sqsTemplate() {
        return SqsTemplate.builder()
                .configure(options -> options
                        .acknowledgementMode(TemplateAcknowledgementMode.MANUAL)
                        .defaultQueue(nomeDaFila)
                        .queueNotFoundStrategy(QueueNotFoundStrategy.FAIL))
                        .build();
    }

    @SqsListener(queueNames = "${queue.fila.pedido}", pollTimeoutSeconds = "50")
    public void listen(String message) {
        System.out.println(message);
    }
}