USE encanto_personalizados;

-- ============================================================
--  Dashboard de Gestão de Pedidos — todas as views
--
--  Execute este script para criar/recriar as views do módulo.
--  Garante a ordem correta de criação (vw_tipo_pedido primeiro).
--
--  Após rodar este script, rode dados_teste_dashboard.sql
--  para popular o banco com dados de teste.
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS vw_tipo_pedido;
DROP TABLE IF EXISTS vw_leadtime_funcionario;
DROP TABLE IF EXISTS vw_retrabalho_quantidade_mes;
DROP TABLE IF EXISTS vw_leadtime_etapa;
DROP TABLE IF EXISTS vw_leadtime_mensal;
DROP TABLE IF EXISTS vw_filtro_produto_item;
DROP TABLE IF EXISTS vw_pedidos_mes;
DROP TABLE IF EXISTS vw_carga_trabalho;
DROP TABLE IF EXISTS vw_pedidos_sem_atualizacao;

DROP VIEW IF EXISTS vw_tipo_pedido;
DROP VIEW IF EXISTS vw_leadtime_funcionario;
DROP VIEW IF EXISTS vw_retrabalho_quantidade_mes;
DROP VIEW IF EXISTS vw_leadtime_etapa;
DROP VIEW IF EXISTS vw_leadtime_mensal;
DROP VIEW IF EXISTS vw_filtro_produto_item;
DROP VIEW IF EXISTS vw_pedidos_mes;
DROP VIEW IF EXISTS vw_carga_trabalho;
DROP VIEW IF EXISTS vw_pedidos_sem_atualizacao;

SET FOREIGN_KEY_CHECKS = 1;

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
  AND psp.status_atual = 1
GROUP BY p.id, p.origem, p.observacoes, sp.status;

-- ─────────────────────────────────────────────────────────────
--  2. Lead time médio por funcionário (pedidos Finalizados)
--     Inclui total_pedidos para o gráfico de concluídos
-- ─────────────────────────────────────────────────────────────
CREATE OR REPLACE VIEW vw_leadtime_funcionario AS
SELECT
    u.name                                       AS funcionario,
    AVG(DATEDIFF(psp.created_at, p.created_at))  AS lead_time,
    COUNT(p.id)                                  AS total_pedidos
FROM pedido AS p
JOIN usuario u ON u.id = p.usuario_id
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
JOIN status_pedido sp ON sp.id = psp.status_id
JOIN vw_tipo_pedido tp ON tp.id = p.id
WHERE psp.status_atual = 1
  AND p.ativo = 1
  AND sp.status = 'Finalizado'
GROUP BY u.name;

-- ─────────────────────────────────────────────────────────────
--  3. Quantidade de pedidos de Retrabalho por mês
-- ─────────────────────────────────────────────────────────────
CREATE OR REPLACE VIEW vw_retrabalho_quantidade_mes AS
SELECT
    DATE_FORMAT(p.created_at, '%Y-%m') AS mes,
    COUNT(*)                           AS quantidade_pedidos
FROM pedido AS p
JOIN vw_tipo_pedido tp ON tp.id = p.id
WHERE tp.tipo_pedido = 'Retrabalho'
  AND p.ativo = 1
GROUP BY mes
ORDER BY mes DESC;

-- ─────────────────────────────────────────────────────────────
--  4. Lead time médio por etapa
-- ─────────────────────────────────────────────────────────────
CREATE OR REPLACE VIEW vw_leadtime_etapa AS
SELECT
    sp.status                                        AS etapa,
    AVG(DATEDIFF(psp.updated_at, psp.created_at))   AS lead_time
FROM pedido AS p
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
JOIN status_pedido sp ON sp.id = psp.status_id
JOIN vw_tipo_pedido tp ON tp.id = p.id
WHERE p.ativo = 1
GROUP BY sp.status;

-- ─────────────────────────────────────────────────────────────
--  5. Lead time médio mensal (pedidos Finalizados)
-- ─────────────────────────────────────────────────────────────
CREATE OR REPLACE VIEW vw_leadtime_mensal AS
SELECT
    DATE_FORMAT(p.created_at, '%Y-%m')           AS mes,
    AVG(DATEDIFF(psp.created_at, p.created_at))  AS lead_time
FROM pedido AS p
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
JOIN status_pedido sp ON sp.id = psp.status_id
JOIN vw_tipo_pedido tp ON tp.id = p.id
WHERE psp.status_atual = 1
  AND p.ativo = 1
  AND sp.status = 'Finalizado'
GROUP BY mes
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
WHERE p.ativo = 1;

-- ─────────────────────────────────────────────────────────────
--  7. Pedidos criados vs entregues por mês
-- ─────────────────────────────────────────────────────────────
CREATE OR REPLACE VIEW vw_pedidos_mes AS
SELECT
    DATE_FORMAT(p.created_at, '%Y-%m')                                                 AS mes,
    COUNT(p.id)                                                                        AS total_criados,
    SUM(CASE WHEN sp.status = 'Entregue' AND psp.status_atual = 1 THEN 1 ELSE 0 END)  AS total_entregues
FROM pedido p
LEFT JOIN pedido_status_pedido psp ON psp.pedido_id = p.id AND psp.status_atual = 1
LEFT JOIN status_pedido sp ON sp.id = psp.status_id
WHERE p.ativo = 1
GROUP BY DATE_FORMAT(p.created_at, '%Y-%m')
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
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id AND psp.status_atual = 1
JOIN status_pedido sp ON sp.id = psp.status_id
WHERE p.ativo = 1
  AND sp.status NOT IN ('Entregue', 'Cancelado', 'Finalizado')
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
    DATEDIFF(NOW(), psp.created_at) AS dias_parado,
    u.name                          AS responsavel
FROM pedido p
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id AND psp.status_atual = 1
JOIN status_pedido sp ON sp.id = psp.status_id
JOIN cliente c ON c.id = p.cliente_id
JOIN usuario u ON u.id = p.usuario_id
WHERE p.ativo = 1
  AND sp.status NOT IN ('Entregue', 'Cancelado', 'Finalizado')
  AND DATEDIFF(NOW(), psp.created_at) >= 3
ORDER BY dias_parado DESC;

-- ─────────────────────────────────────────────────────────────
--  Verificação
-- ─────────────────────────────────────────────────────────────
-- SELECT status, tipo_pedido, COUNT(*) AS qtd     FROM vw_tipo_pedido              GROUP BY status, tipo_pedido;
-- SELECT funcionario, ROUND(lead_time,1), total_pedidos FROM vw_leadtime_funcionario ORDER BY funcionario;
-- SELECT mes, ROUND(lead_time,1)                   FROM vw_leadtime_mensal          ORDER BY mes;
-- SELECT etapa, ROUND(lead_time,1)                 FROM vw_leadtime_etapa           ORDER BY lead_time DESC;
-- SELECT mes, quantidade_pedidos                   FROM vw_retrabalho_quantidade_mes ORDER BY mes;
-- SELECT mes, total_criados, total_entregues       FROM vw_pedidos_mes              ORDER BY mes;
-- SELECT funcionario, em_andamento                 FROM vw_carga_trabalho           ORDER BY em_andamento DESC;
-- SELECT id, cliente, status, dias_parado          FROM vw_pedidos_sem_atualizacao;
