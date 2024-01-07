package com.fiap.pedidos.interfaces.repositories;

import com.fiap.pedidos.utils.enums.StatusPagamento;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "consulta-pagamento", url = "http://localhost:4000")
public interface PagamentoRepository {
    @PostMapping("/consulta-pagamento")
    StatusPagamento consultarPagamento();
}
