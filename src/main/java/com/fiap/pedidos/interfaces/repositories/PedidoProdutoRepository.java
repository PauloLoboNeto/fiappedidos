package com.fiap.pedidos.interfaces.repositories;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.gateways.entities.PedidoProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoProdutoRepository extends JpaRepository<PedidoProdutoEntity, UUID> {

}
