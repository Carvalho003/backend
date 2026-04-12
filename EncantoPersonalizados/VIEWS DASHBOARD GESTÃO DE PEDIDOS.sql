-- VIEWS PARA DASHBOARD DE GESTÃO DE PEDIDOS
-- Este arquivo contém CREATE OR REPLACE VIEW para as views utilizadas pelo módulo de dashboard de pedidos.
-- Ajuste os nomes das colunas se o esquema do banco for diferente.

-- VIEW: lead time médio por funcionário (apenas pedidos finalizados)
CREATE OR REPLACE VIEW vw_leadtime_funcionario AS
SELECT
    u.name AS funcionario,
    AVG(DATEDIFF(psp.created_at, p.created_at)) AS lead_time
FROM pedido AS p
JOIN usuario u ON u.id = p.usuario_id
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
JOIN status_pedido sp ON sp.id = psp.status_id
JOIN vw_tipo_pedido tp ON tp.id = p.id
WHERE psp.status_atual = 1
  AND p.ativo = 1
  AND sp.status = 'Finalizado'
GROUP BY u.name;

-- VIEW: quantidade de pedidos de retrabalho por mês
CREATE OR REPLACE VIEW vw_retrabalho_quantidade_mes AS
SELECT
    DATE_FORMAT(p.created_at, '%Y-%m') AS mes,
    COUNT(*) AS quantidade_pedidos
FROM pedido AS p
JOIN vw_tipo_pedido tp ON tp.id = p.id
WHERE tp.tipo_pedido = 'Retrabalho'
  AND p.ativo = 1
GROUP BY mes
ORDER BY mes DESC;

-- VIEW: lead time médio por etapa (tempo médio entre criação e atualização do status)
CREATE OR REPLACE VIEW vw_leadtime_etapa AS
SELECT
    sp.status AS etapa,
    AVG(DATEDIFF(psp.updated_at, psp.created_at)) AS lead_time
FROM pedido AS p
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
JOIN status_pedido sp ON sp.id = psp.status_id
JOIN vw_tipo_pedido tp ON tp.id = p.id
WHERE p.ativo = 1
GROUP BY sp.status;

-- VIEW: lead time médio mensal (apenas pedidos finalizados)
CREATE OR REPLACE VIEW vw_leadtime_mensal AS
SELECT
    DATE_FORMAT(p.created_at, '%Y-%m') AS mes,
    AVG(DATEDIFF(psp.created_at, p.created_at)) AS lead_time
FROM pedido AS p
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
JOIN status_pedido sp ON sp.id = psp.status_id
JOIN vw_tipo_pedido tp ON tp.id = p.id
WHERE psp.status_atual = 1
  AND p.ativo = 1
  AND sp.status = 'Finalizado'
GROUP BY mes
ORDER BY mes DESC;

-- VIEW: filtro de produto por item (produtos mais pedidos)
CREATE OR REPLACE VIEW vw_filtro_produto_item AS
SELECT
    p.id AS id,
    pp.produto_id AS produto_id,
    COALESCE(prod.item_produto_id, 0) AS qtd_prod
FROM pedido AS p
LEFT JOIN produto_pedido pp ON pp.pedido_id = p.id
LEFT JOIN produto prod ON pp.produto_id = prod.id
WHERE p.ativo = 1;

-- VIEW: tipo de pedido (Atrasado / Retrabalho / Normal)
CREATE OR REPLACE VIEW vw_tipo_pedido AS
SELECT
    p.id,
    p.origem,
    p.observacoes,
    sp.status,
    CASE
        WHEN (p.data_limite IS NOT NULL AND p.data_limite <= NOW()
              AND sp.status NOT IN ('Cancelado', 'Entregue'))
            THEN 'Atrasado'
        WHEN (
            EXISTS (
                SELECT 1
                FROM pedido AS p1
                JOIN pedido_status_pedido psp1 ON psp1.pedido_id = p1.id
                JOIN status_pedido sp1 ON sp1.id = psp1.status_id
                WHERE p1.id = p.id
                  AND psp1.status_atual = 1
                  AND sp1.status NOT IN ('Entregue', 'Cancelado')
                  AND p1.id IN (
                      SELECT psp2.pedido_id
                      FROM pedido_status_pedido AS psp2
                      JOIN status_pedido sp2 ON sp2.id = psp2.status_id
                      WHERE sp2.status = 'Entregue'
                  )
            )
        ) THEN 'Retrabalho'
        ELSE 'Normal'
    END AS tipo_pedido
FROM pedido AS p
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
JOIN status_pedido sp ON sp.id = psp.status_id
WHERE p.ativo = 1
GROUP BY p.id, p.origem, p.observacoes, sp.status;

-- FIM DO ARQUIVO
