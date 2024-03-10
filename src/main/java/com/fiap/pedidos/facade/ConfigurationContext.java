package com.fiap.pedidos.facade;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.pedidos.interfaces.gateways.*;
import com.fiap.pedidos.interfaces.usecases.IClienteUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoProdutoUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IProdutoUseCasePort;
import com.fiap.pedidos.usecases.ClienteUseCaseImpl;
import com.fiap.pedidos.usecases.PedidoProdutoUseCaseImpl;
import com.fiap.pedidos.usecases.PedidoUseCaseImpl;
import com.fiap.pedidos.usecases.ProdutoUseCaseImpl;
import io.awspring.cloud.sqs.config.SqsListenerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.util.MimeType;

@Configuration
public class ConfigurationContext {

    @Bean
    public IProdutoUseCasePort produtoUseCasePort(IProdutoRepositoryPort produtoRepositoryPort) {
        return new ProdutoUseCaseImpl(produtoRepositoryPort);
    }

    @Bean
    public IPedidoUseCasePort pedidoUseCasePort(
            IPedidoProdutoRepositoryPort pedidoProdutoRepositoryPort,
            IPedidoRepositoryPort pedidoRepositoryPort,
            IPagamentoRepositoryPort pagamentoRepositoryPort,
            IFilaRepositoryPort filaRepositoryPort) {
        return new PedidoUseCaseImpl(pedidoProdutoRepositoryPort, pedidoRepositoryPort, pagamentoRepositoryPort, filaRepositoryPort);
    }

    @Bean
    public IPedidoProdutoUseCasePort pedidoProdutoUseCasePort(IPedidoRepositoryPort pedidoRepositoryPort,
                                                              IPedidoProdutoRepositoryPort pedidoProdutoRepositoryPort,
                                                              IProdutoRepositoryPort produtoRepositoryPort) {
        return new PedidoProdutoUseCaseImpl(pedidoProdutoRepositoryPort, pedidoRepositoryPort, produtoRepositoryPort);
    }

    @Bean
    public IClienteUseCasePort clienteUseCasePort(IClienteRepositoryPort clienteRepositoryPort) {
        return new ClienteUseCaseImpl(clienteRepositoryPort);
    }

    @Primary
    @Bean
    public ObjectMapper om() {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        return om;
    }

    @Bean
    public SqsListenerConfigurer configurer(ObjectMapper objectMapper) {
        var defaultConverter = new MappingJackson2MessageConverter(
                new MimeType("application", "json"),
                new MimeType("application", "*+json"),
                new MimeType("text", "plain")
        );
        defaultConverter.setObjectMapper(objectMapper);
        return registrar -> {
            registrar.manageMessageConverters(converters -> {
                converters.clear();
                converters.add(defaultConverter);
            });
        };
    }

}
