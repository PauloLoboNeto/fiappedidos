package com.fiap.pedidos.interfaces.repositories;

import com.fiap.pedidos.utils.enums.StatusPagamento;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "consulta-pagamento", url = "${pagamentos.service.url}")
public interface PagamentoRepository {
    @GetMapping("/{id}")
    StatusPagamento consultarPagamento(@PathVariable("idPedido")UUID idPedido);
}