package school.sptech.EncantoPersonalizados.infrastructure.dto.produtosEmUmPedido;

import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.core.domain.Pedido;
import school.sptech.EncantoPersonalizados.core.domain.Produto;
import school.sptech.EncantoPersonalizados.core.domain.ProdutoPedido;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProdutosPedidoMapperTest {

    @Test
    void toEntity_mapsFields() {
        // ProdutosPedidoRequestDto requires (idProduto, idPedido, quantidade)
        ProdutosPedidoRequestDto dto = new ProdutosPedidoRequestDto(2, 3, 4);
        var entity = ProdutosPedidoMapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(4, entity.getQtdProduto());
    }

    @Test
    void toDto_mapsFields() {
        ProdutoPedido pp = new ProdutoPedido();
        pp.setId(10);
        Produto p = new Produto(); p.setId(2);
        Pedido ped = new Pedido(); ped.setId(3);
        pp.setProduto(p);
        pp.setPedido(ped);
        pp.setQtdProduto(7);
        pp.setPesoTotal(100.0);
        pp.setPesoUnitario(14.0);
        pp.setPrecoUnitario(5.0);
        pp.setPrecoTotal(35.0);
        pp.setCreatedAt(LocalDateTime.now());

        var dto = ProdutosPedidoMapper.toDto(pp);
        assertNotNull(dto);
        assertEquals(10, dto.id());
        // ProdutosPedidoResponseDto accessors are idProduto() and idPedido()
        assertEquals(2, dto.idProduto());
        assertEquals(3, dto.idPedido());
        assertEquals(7, dto.quantidade());
    }
}
