CREATE OR REPLACE VIEW v_dash_vendas_categoria AS
SELECT
    ROW_NUMBER() OVER (ORDER BY C.id, MIN(P.created_at)) AS id,
    C.id AS categoria_id,
    C.titulo AS nome_categoria,
    SUM(PP.preco_unitario * PP.qtd_produto) AS valor_total_vendido,
    COUNT(DISTINCT PP.pedido_id) AS quantidade_pedidos,
    DATE(P.created_at) AS data_referencia
FROM produto_pedido PP
         JOIN pedido P ON PP.pedido_id = P.id
         JOIN produto PR ON PP.produto_id = PR.id
         JOIN tema_produto T ON PR.tema_produto_id = T.id
         JOIN categoria_tema C ON T.categoria_tema_id = C.id
GROUP BY C.id, C.titulo, DATE(P.created_at);

CREATE OR REPLACE VIEW v_dash_despesas_categoria AS
SELECT
    ROW_NUMBER() OVER (ORDER BY CM.id, MIN(M.created_at)) AS id,
    CM.id AS categoria_id,
    CM.descricao AS nome_categoria,
    SUM(M.valor) AS valor_total,
    DATE(M.created_at) AS data_referencia
FROM movimentacao M
         JOIN categoria_movimentacao CM ON M.categoria_movimentacao_id = CM.id
WHERE M.tipo = 'DESPESA'
GROUP BY CM.id, CM.descricao, DATE(M.created_at);

CREATE OR REPLACE VIEW v_dash_kpi_mes AS
WITH DadosMensais AS (
    SELECT
        DATE_FORMAT(M.created_at, '%Y-%m-01') AS MES_REFERENCIA_FORMATADA,
        M.tipo AS TIPO_MOVIMENTACAO,
        SUM(M.valor) AS VALOR_MES
    FROM movimentacao M
             JOIN categoria_movimentacao CM ON M.categoria_movimentacao_id = CM.id
    GROUP BY 1, 2
),
     ValoresComPeriodoAnterior AS (
         SELECT
             MES_REFERENCIA_FORMATADA,
             TIPO_MOVIMENTACAO,
             VALOR_MES,
             LAG(VALOR_MES, 1, 0.0) OVER (
                 PARTITION BY TIPO_MOVIMENTACAO
                 ORDER BY MES_REFERENCIA_FORMATADA
                 ) AS VALOR_MES_ANTERIOR
         FROM DadosMensais
     )
SELECT
    ROW_NUMBER() OVER (ORDER BY MES_REFERENCIA_FORMATADA, TIPO_MOVIMENTACAO) AS id,
    T.TIPO_MOVIMENTACAO AS tipo_movimentacao,
    T.VALOR_MES AS valor_mes_atual,
    T.VALOR_MES_ANTERIOR AS valor_mes_anterior,
    CASE
        WHEN T.VALOR_MES_ANTERIOR = 0 THEN
            CASE WHEN T.VALOR_MES > 0 THEN 1.0 ELSE 0.0 END
        ELSE
            (T.VALOR_MES - T.VALOR_MES_ANTERIOR) / T.VALOR_MES_ANTERIOR
        END AS percentual_variacao,
    STR_TO_DATE(T.MES_REFERENCIA_FORMATADA, '%Y-%m-%d') AS mes_referencia
FROM ValoresComPeriodoAnterior T;