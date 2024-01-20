package com.fiap.pedidos;

import com.fiap.pedidos.entities.*;
import com.fiap.pedidos.exceptions.entities.CpfExistenteException;
import com.fiap.pedidos.interfaces.usecases.IClienteUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IProdutoUseCasePort;
import com.fiap.pedidos.utils.enums.TipoProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableAsync
@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class PedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(PedidosApplication.class, args);
	}
}
