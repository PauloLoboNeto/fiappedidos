package com.fiap.pedidos.gateways;

import com.fiap.pedidos.interfaces.gateways.IPagamentoRepositoryPort;
import com.fiap.pedidos.interfaces.repositories.PagamentoRepository;
import com.fiap.pedidos.utils.enums.StatusPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PagamentoRepositoryAdapter implements IPagamentoRepositoryPort {

    private final PagamentoRepository pagamentoRepository;
//    @Override
//    public StatusPagamento consultaPagamento(UUID idPedido) {
//        return this.pagamentoRepository.consultarPagamento();
//    }
//
    @Override
    public StatusPagamento consultaPagamento(UUID idPedido) {
        return StatusPagamento.APROVADO;
    }
}
