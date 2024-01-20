package com.fiap.pedidos.gateways;

import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.gateways.entities.PedidoEntity;
import com.fiap.pedidos.gateways.entities.PedidoProdutoEntity;
import com.fiap.pedidos.helpers.Helper;
import com.fiap.pedidos.interfaces.gateways.IPedidoProdutoRepositoryPort;
import com.fiap.pedidos.interfaces.repositories.PedidoProdutoRepository;
import com.fiap.pedidos.interfaces.repositories.PedidoRepository;
import com.fiap.pedidos.interfaces.repositories.ProdutoRepository;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoProdutoRepositoryAdapterTest {

    private IPedidoProdutoRepositoryPort pedidoProdutoRepositoryPort;

    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private PedidoProdutoRepository pedidoProdutoRepository;
    @Mock
    private ProdutoRepository produtoRepository;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        pedidoProdutoRepositoryPort = new PedidoProdutoRepositoryAdapter(
                pedidoProdutoRepository,
                pedidoRepository,
                produtoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class CadastrarPedidoProduto {

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar PedidoProduto")
        void deveCadastrarPedidoProduto() {
            var uuidPedido = UUID.randomUUID();
            var uuidProduto = UUID.randomUUID();

            var pedido = Helper.gerarPedidoComCliente();
            pedido.setIdPedido(uuidPedido);

            var produto = Helper.gerarProdutoLanche();
            produto.setIdProduto(uuidProduto);

            var pedidoProduto = Helper.gerarPedidoProduto();
            pedidoProduto.setPedidoId(UUID.randomUUID());

            var pedidoProdutoEntity = Helper.gerarPedidoProdutoEntity();
            pedidoProdutoEntity.setId(UUID.randomUUID());

            when(pedidoProdutoRepository.save(any(PedidoProdutoEntity.class))).thenReturn(pedidoProdutoEntity);

            var pedidoProdutoSalvo = pedidoProdutoRepositoryPort
                    .adicionarPedidoProduto(pedido, produto, pedidoProduto);

            assertThat(pedidoProdutoSalvo).isNotNull();
            assertThat(pedidoProdutoSalvo).isInstanceOf(PedidoProduto.class);
            assertThat(pedidoProdutoSalvo.getPedidoId()).isEqualTo(pedidoProdutoEntity.getPedido().getIdPedido());
            assertThat(pedidoProdutoSalvo.getProdutoId()).isEqualTo(pedidoProdutoEntity.getProduto().getIdProduto());

            assertThat(pedidoProdutoSalvo.getProdutoId()).isInstanceOf(UUID.class);
            assertThat(pedidoProdutoSalvo.getProdutoId()).isInstanceOf(UUID.class);

            verify(pedidoProdutoRepository, times(1)).save(any(PedidoProdutoEntity.class));
        }
    }

    @Nested
    class BuscarPedidoProduto {

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Buscar PedidoProduto")
        void deveBuscarPedidoProduto() {
            var pedido = Helper.gerarPedidoComCliente();
            var produto = Helper.gerarProdutoLanche();
            var pedidoProdutoEntity = Helper.gerarPedidoProdutoEntity();
            var uuid = UUID.randomUUID();
            pedidoProdutoEntity.setId(uuid);

            when(pedidoProdutoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pedidoProdutoEntity));

            var pedidoProdutoSalvo = pedidoProdutoRepositoryPort.buscarPorId(uuid);

            assertThat(pedidoProdutoSalvo.isEmpty()).isFalse();
            assertThat(pedidoProdutoSalvo.get()).isInstanceOf(PedidoProduto.class);

            assertThat(pedidoProdutoSalvo.get().getPedidoId()).isEqualTo(pedidoProdutoEntity.getPedido().getIdPedido());
            assertThat(pedidoProdutoSalvo.get().getProdutoId()).isEqualTo(pedidoProdutoEntity.getProduto().getIdProduto());

            verify(pedidoProdutoRepository, times(1)).findById(any(UUID.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Remover PedidoProduto")
        void deveRemoverPedidoProduto() {
            var uuidPedido = UUID.randomUUID();
            var uuidProduto = UUID.randomUUID();
            var uuidPedidoProduto = UUID.randomUUID();
            var uuidPedidoEntity = UUID.randomUUID();

            var pedido = Helper.gerarPedidoComClienteEProdutos();
            pedido.setIdPedido(uuidPedido);

            var produto = Helper.gerarProdutoLanche();
            produto.setIdProduto(uuidProduto);

            var pedidoEntity = new PedidoEntity().from(pedido, false);

            var pedidoProdutoEntity = Helper.gerarPedidoProdutoEntity();
            pedidoProdutoEntity.setId(uuidPedidoProduto);

            pedidoEntity.setProdutos(List.of(pedidoProdutoEntity));
            pedidoEntity.setIdPedido(uuidPedidoEntity);

            when(pedidoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pedidoEntity));
            doNothing().when(pedidoProdutoRepository)
                    .deleteByIdPedidoAndIdProduto(any(UUID.class), any(UUID.class));

            pedidoProdutoRepositoryPort.excluirPedidoProduto(uuidPedido, uuidProduto);

            verify(pedidoProdutoRepository, times(1))
                    .deleteByIdPedidoAndIdProduto(any(UUID.class), any(UUID.class));

            verify(pedidoRepository, times(1)).findById(any(UUID.class));
        }
    }
}