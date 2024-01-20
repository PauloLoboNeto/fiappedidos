package com.fiap.pedidos.facade;

import com.fiap.pedidos.interfaces.gateways.*;
import com.fiap.pedidos.interfaces.usecases.IClienteUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoProdutoUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IProdutoUseCasePort;
import com.fiap.pedidos.usecases.ClienteUseCaseImpl;
import com.fiap.pedidos.usecases.PedidoProdutoUseCaseImpl;
import com.fiap.pedidos.usecases.PedidoUseCaseImpl;
import com.fiap.pedidos.usecases.ProdutoUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
