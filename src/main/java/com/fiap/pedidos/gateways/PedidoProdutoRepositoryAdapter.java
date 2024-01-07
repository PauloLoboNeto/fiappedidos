package com.fiap.pedidos.gateways;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.exceptions.entities.PedidoNaoEncontradoException;
import com.fiap.pedidos.exceptions.entities.PedidoOperacaoNaoSuportadaException;
import com.fiap.pedidos.exceptions.entities.PedidoProdutoNaoEncontradoException;
import com.fiap.pedidos.gateways.entities.PedidoEntity;
import com.fiap.pedidos.gateways.entities.PedidoProdutoEntity;
import com.fiap.pedidos.gateways.entities.ProdutoEntity;
import com.fiap.pedidos.interfaces.gateways.IPedidoProdutoRepositoryPort;
import com.fiap.pedidos.interfaces.repositories.PedidoProdutoRepository;
import com.fiap.pedidos.interfaces.repositories.PedidoRepository;
import com.fiap.pedidos.interfaces.repositories.ProdutoRepository;
import com.fiap.pedidos.utils.enums.StatusPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoProdutoRepositoryAdapter implements IPedidoProdutoRepositoryPort {

    private final PedidoProdutoRepository pedidoProdutoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PedidoProduto> buscarTodos() {
        return this.pedidoProdutoRepository.findAll().stream()
                .map(PedidoProdutoEntity::to)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PedidoProduto> buscarPorId(UUID id) {
        return this.pedidoProdutoRepository.findById(id)
                .map(PedidoProdutoEntity::to);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoProduto> buscarPorPedido(Pedido pedido) {
        PedidoEntity pedidoEntity = this.pedidoRepository.findById(pedido.getIdPedido())
                .orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado, id: " + pedido.getIdPedido()));

        return this.pedidoProdutoRepository.findByPedido(pedido)
                .stream()
                .map(PedidoProdutoEntity::to)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PedidoProduto adicionarPedidoProduto(Pedido pedido, Produto produto, PedidoProduto pedidoProduto) {

        ProdutoEntity existProdutoEntity = new ProdutoEntity().from(produto, false);
        PedidoEntity pedidoEntity = new PedidoEntity().from(pedido, false);

        PedidoProdutoEntity pedidoProdutoEntity = PedidoProdutoEntity.builder()
                .id(pedidoProduto.getId())
                .pedido(pedidoEntity)
                .produto(existProdutoEntity)
                .build();

        return PedidoProdutoEntity.to(this.pedidoProdutoRepository.save(pedidoProdutoEntity));
    }

    @Override
    @Transactional
    public void excluirPedidoProduto(UUID id) {
        Optional<PedidoProdutoEntity> pedidoProdutoOptional = pedidoProdutoRepository.findById(id);

        if (pedidoProdutoOptional.isPresent()) {
            Optional<PedidoEntity> pedidoOptional = pedidoRepository
                    .findById(pedidoProdutoOptional.get().getPedido().getIdPedido());

            if(pedidoOptional.isEmpty()){
                throw new PedidoNaoEncontradoException();
            }

            if(pedidoOptional.get().getStatusPedido() == StatusPedido.A) {
                throw new PedidoOperacaoNaoSuportadaException("Pedido status is not A, id: " + pedidoOptional.get().getIdPedido());
            }

            pedidoProdutoRepository.deleteById(id);
        }
    }
}
