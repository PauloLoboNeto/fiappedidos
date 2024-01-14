package com.fiap.pedidos.interfaces.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "insere-fila", url = "${fila.service.url}")
public interface FilaRepository {
    @PostMapping("/clientes/{idCliente}/pedidos/{idPedido}")
    void inserePedidoNaFila(@PathVariable(name = "idPedido") UUID idPedido,
                            @PathVariable(name = "idCliente") UUID idCliente);
}
