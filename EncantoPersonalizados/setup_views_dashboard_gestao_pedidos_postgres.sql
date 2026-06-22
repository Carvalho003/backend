-- PostgreSQL version of setup_views_dashboard_gestao_pedidos
-- Converted from MySQL syntax

-- ============================================================
--  Dashboard de Gestão de Pedidos — todas as views
--
--  Execute este script para criar/recriar as views do módulo.
--  Garante a ordem correta de criação (vw_tipo_pedido primeiro).
--
--  Após rodar este script, rode dados_teste_dashboard_postgres.sql
--  para popular o banco com dados de teste.
-- ============================================================

-- PostgreSQL: drop views and tables
DROP VIEW IF EXISTS vw_pedidos_sem_atualizacao CASCADE;
DROP VIEW IF EXISTS vw_carga_trabalho CASCADE;
DROP VIEW IF EXISTS vw_pedidos_mes CASCADE;
DROP VIEW IF EXISTS vw_filtro_produto_item CASCADE;
DROP VIEW IF EXISTS vw_leadtime_mensal CASCADE;
DROP VIEW IF EXISTS vw_leadtime_etapa CASCADE;
DROP VIEW IF EXISTS vw_retrabalho_quantidade_mes CASCADE;
DROP VIEW IF EXISTS vw_leadtime_funcionario CASCADE;
DROP VIEW IF EXISTS vw_tipo_pedido CASCADE;

-- ─────────────────────────────────────────────────────────────
--  1. BASE — deve existir antes das demais
--     Classifica cada pedido como Atrasado / Retrabalho / Normal
-- ─────────────────────────────────────────────────────────────
CREATE OR REPLACE VIEW vw_tipo_pedido AS
SELECT
    p.id,
    p.origem,
    p.observacoes,
    sp.status,
    sp.status_role,
    CASE
        WHEN (p.data_limite IS NOT NULL AND p.data_limite <= NOW()
              AND (sp.status_role IS NULL OR sp.status_role NOT IN ('CANCELADO', 'ENTREGUE')))
            THEN 'Atrasado'
        WHEN (
            EXISTS (
                SELECT 1
                FROM pedido AS p1
                JOIN pedido_status_pedido psp1 ON psp1.pedido_id = p1.id
                JOIN status_pedido sp1 ON sp1.id = psp1.status_id
                WHERE p1.id = p.id
                  AND psp1.status_atual = true
                  AND (sp1.status_role IS NULL OR sp1.status_role NOT IN ('ENTREGUE', 'CANCELADO'))
                  AND p1.id IN (
                      SELECT psp2.pedido_id
                      FROM pedido_status_pedido AS psp2
                      JOIN status_pedido sp2 ON sp2.id = psp2.status_id
                      WHERE sp2.status_role = 'ENTREGUE'
                  )
            )
        ) THEN 'Retrabalho'
        ELSE 'Normal'
    END AS tipo_pedido
FROM pedido AS p
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
JOIN status_pedido sp ON sp.id = psp.status_id
WHERE p.ativo = true
  AND psp.status_atual = true
GROUP BY p.id, p.origem, p.observacoes, sp.status, sp.status_role;

-- ─────────────────────────────────────────────────────────────
--  2. Lead time médio por funcionário (pedidos Finalizados)
--     Inclui total_pedidos para o gráfico de concluídos
-- ─────────────────────────────────────────────────────────────
CREATE OR REPLACE VIEW vw_leadtime_funcionario AS
SELECT
    u.name                                       AS funcionario,
    AVG(EXTRACT(DAY FROM (psp.created_at - p.created_at)))  AS lead_time,
    COUNT(p.id)                                  AS total_pedidos
FROM pedido AS p
JOIN usuario u ON u.id = p.usuario_id
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
JOIN status_pedido sp ON sp.id = psp.status_id
JOIN vw_tipo_pedido tp ON tp.id = p.id
WHERE psp.status_atual = true
  AND p.ativo = true
  AND sp.status_role = 'FINALIZADO'
GROUP BY u.name;

-- ─────────────────────────────────────────────────────────────
--  3. Quantidade de pedidos de Retrabalho por mês
-- ─────────────────────────────────────────────────────────────
CREATE OR REPLACE VIEW vw_retrabalho_quantidade_mes AS
SELECT
    to_char(p.created_at, 'YYYY-MM') AS mes,
    COUNT(*)                           AS quantidade_pedidos
FROM pedido AS p
JOIN vw_tipo_pedido tp ON tp.id = p.id
WHERE tp.tipo_pedido = 'Retrabalho'
  AND p.ativo = true
GROUP BY to_char(p.created_at, 'YYYY-MM')
ORDER BY mes DESC;

-- ─────────────────────────────────────────────────────────────
--  4. Lead time médio por etapa
-- ─────────────────────────────────────────────────────────────
CREATE OR REPLACE VIEW vw_leadtime_etapa AS
SELECT
    sp.status                                        AS etapa,
    AVG(EXTRACT(DAY FROM (psp.updated_at - psp.created_at)))   AS lead_time
FROM pedido AS p
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
JOIN status_pedido sp ON sp.id = psp.status_id
JOIN vw_tipo_pedido tp ON tp.id = p.id
WHERE p.ativo = true
GROUP BY sp.status;

-- ─────────────────────────────────────────────────────────────
--  5. Lead time médio mensal (pedidos Finalizados)
-- ─────────────────────────────────────────────────────────────
CREATE OR REPLACE VIEW vw_leadtime_mensal AS
SELECT
    to_char(p.created_at, 'YYYY-MM')           AS mes,
    AVG(EXTRACT(DAY FROM (psp.created_at - p.created_at)))  AS lead_time
FROM pedido AS p
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
JOIN status_pedido sp ON sp.id = psp.status_id
JOIN vw_tipo_pedido tp ON tp.id = p.id
WHERE psp.status_atual = true
  AND p.ativo = true
  AND sp.status_role = 'FINALIZADO'
GROUP BY to_char(p.created_at, 'YYYY-MM')
ORDER BY mes DESC;

-- ─────────────────────────────────────────────────────────────
--  6. Produtos mais pedidos (independente das demais)
-- ─────────────────────────────────────────────────────────────
CREATE OR REPLACE VIEW vw_filtro_produto_item AS
SELECT
    p.id                               AS id,
    pp.produto_id                      AS produto_id,
    COALESCE(prod.item_produto_id, 0)  AS qtd_prod
FROM pedido AS p
LEFT JOIN produto_pedido pp ON pp.pedido_id = p.id
LEFT JOIN produto prod ON pp.produto_id = prod.id
WHERE p.ativo = true;

-- ─────────────────────────────────────────────────────────────
--  7. Pedidos criados vs entregues por mês
-- ─────────────────────────────────────────────────────────────
CREATE OR REPLACE VIEW vw_pedidos_mes AS
SELECT
    to_char(p.created_at, 'YYYY-MM')                                                 AS mes,
    COUNT(p.id)                                                                        AS total_criados,
    SUM(CASE WHEN sp.status_role = 'ENTREGUE' AND psp.status_atual = true THEN 1 ELSE 0 END)  AS total_entregues
FROM pedido p
LEFT JOIN pedido_status_pedido psp ON psp.pedido_id = p.id AND psp.status_atual = true
LEFT JOIN status_pedido sp ON sp.id = psp.status_id
WHERE p.ativo = true
GROUP BY to_char(p.created_at, 'YYYY-MM')
ORDER BY mes;

-- ─────────────────────────────────────────────────────────────
--  8. Carga de trabalho atual por funcionário
--     (pedidos em andamento — excluindo Entregue/Cancelado/Finalizado)
-- ─────────────────────────────────────────────────────────────
CREATE OR REPLACE VIEW vw_carga_trabalho AS
SELECT
    u.name       AS funcionario,
    COUNT(p.id)  AS em_andamento
FROM pedido p
JOIN usuario u ON u.id = p.usuario_id
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id AND psp.status_atual = true
JOIN status_pedido sp ON sp.id = psp.status_id
WHERE p.ativo = true
  AND (sp.status_role IS NULL OR sp.status_role NOT IN ('ENTREGUE', 'CANCELADO', 'FINALIZADO'))
GROUP BY u.name;

-- ─────────────────────────────────────────────────────────────
--  9. Pedidos sem atualização há 3+ dias
--     (estado atual do sistema, independente do filtro de data)
-- ─────────────────────────────────────────────────────────────
CREATE OR REPLACE VIEW vw_pedidos_sem_atualizacao AS
SELECT
    p.id,
    c.nome                          AS cliente,
    sp.status,
    EXTRACT(DAY FROM (NOW() - psp.created_at))::integer AS dias_parado,
    u.name                          AS responsavel
FROM pedido p
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id AND psp.status_atual = true
JOIN status_pedido sp ON sp.id = psp.status_id
JOIN cliente c ON c.id = p.cliente_id
JOIN usuario u ON u.id = p.usuario_id
WHERE p.ativo = true
  AND (sp.status_role IS NULL OR sp.status_role NOT IN ('ENTREGUE', 'CANCELADO', 'FINALIZADO'))
  AND EXTRACT(DAY FROM (NOW() - psp.created_at)) >= 3
ORDER BY dias_parado DESC;

-- ─────────────────────────────────────────────────────────────
--  Verificação
-- ─────────────────────────────────────────────────────────────
-- SELECT status, tipo_pedido, COUNT(*) AS qtd     FROM vw_tipo_pedido              GROUP BY status, tipo_pedido;
-- SELECT funcionario, ROUND(lead_time::numeric,1), total_pedidos FROM vw_leadtime_funcionario ORDER BY funcionario;
-- SELECT mes, ROUND(lead_time::numeric,1)                   FROM vw_leadtime_mensal          ORDER BY mes;
-- SELECT etapa, ROUND(lead_time::numeric,1)                 FROM vw_leadtime_etapa           ORDER BY lead_time DESC;
-- SELECT mes, quantidade_pedidos                   FROM vw_retrabalho_quantidade_mes ORDER BY mes;
-- SELECT mes, total_criados, total_entregues       FROM vw_pedidos_mes              ORDER BY mes;
-- SELECT funcionario, em_andamento                 FROM vw_carga_trabalho           ORDER BY em_andamento DESC;
-- SELECT id, cliente, status, dias_parado          FROM vw_pedidos_sem_atualizacao;

