package com.fiap.pedidos.interfaces.repositories;

import com.fiap.pedidos.gateways.entities.PedidoEntity;
import com.fiap.pedidos.utils.enums.StatusPedido;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, UUID> {

    @Query("SELECT p FROM PedidoEntity p WHERE id_cliente = ?1")
    List<PedidoEntity> findByIdCliente(UUID idCliente);

    List<PedidoEntity> findByStatusPedido(StatusPedido statusPedido);

    @Query("SELECT p FROM PedidoEntity p WHERE id_cliente = ?1 AND id_status LIKE ?2")
    List<PedidoEntity> findByIdClienteAndStatusPedido(UUID idCliente, String statusPedido);

    List<PedidoEntity> listagemOrdenadaPorStatusExcluindoFinalizados(Pageable pageable);
}
