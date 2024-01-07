package com.fiap.pedidos.interfaces.repositories;

import com.fiap.pedidos.gateways.entities.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, UUID> {

    @Query(value = "SELECT p FROM PedidoEntity p WHERE id_cliente = ?1 AND id_status LIKE ?2",
    nativeQuery = true)
    Optional<List<PedidoEntity>> findByIdClienteAndStatusPedido(UUID idCliente, String statusPedido);

    @Query(value = "SELECT p FROM PedidoEntity p WHERE p.idStatus NOT LIKE 'F' ORDER BY CASE p.idStatus WHEN 'C' THEN 1 WHEN 'E' THEN 2 WHEN 'R' THEN 3 WHEN 'A' THEN 4 END ASC, p.dtHInclusao ASC",
            nativeQuery = true)
    Optional<List<PedidoEntity>> listagemOrdenadaPorStatusExcluindoFinalizados(Pageable pageable);
}
