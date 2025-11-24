CREATE OR REPLACE VIEW V_DASH_KPI_MES AS
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
    ROW_NUMBER() OVER (ORDER BY MES_REFERENCIA_FORMATADA, TIPO_MOVIMENTACAO) AS ID,

    T.TIPO_MOVIMENTACAO AS TIPO_MOVIMENTACAO,
    T.VALOR_MES AS VALOR_MES_ATUAL,
    T.VALOR_MES_ANTERIOR AS VALOR_MES_ANTERIOR,
    CASE
        WHEN T.VALOR_MES_ANTERIOR = 0 THEN
            CASE
                WHEN T.VALOR_MES > 0 THEN 1.0
                ELSE 0.0
                END
        ELSE
            (T.VALOR_MES - T.VALOR_MES_ANTERIOR) / T.VALOR_MES_ANTERIOR
        END AS PERCENTUAL_VARIACAO,

    STR_TO_DATE(T.MES_REFERENCIA_FORMATADA, '%Y-%m-%d') AS MES_REFERENCIA
FROM ValoresComPeriodoAnterior T;

CREATE OR REPLACE VIEW V_DASH_VENDAS_CATEGORIA AS
SELECT
    C.id AS ID,
    C.titulo AS NOME_CATEGORIA,
    SUM(PP.preco_unitario * PP.qtd_produto) AS VALOR_TOTAL_VENDIDO,
    COUNT(DISTINCT PP.pedido_id) AS QUANTIDADE_PEDIDOS
FROM produto_pedido PP
         JOIN pedido P ON PP.pedido_id = P.id
         JOIN produto PR ON PP.produto_id = PR.id
         JOIN tema_produto T ON PR.tema_produto_id = T.id
         JOIN categoria_tema C ON T.categoria_tema_id = C.id
GROUP BY C.id, C.titulo
ORDER BY VALOR_TOTAL_VENDIDO DESC;

CREATE OR REPLACE VIEW V_DASH_DESPESAS_CATEGORIA AS
SELECT
    CM.id AS ID,
    CM.descricao AS NOME_CATEGORIA,
    SUM(M.valor) AS VALOR_TOTAL
FROM movimentacao M
         JOIN categoria_movimentacao CM ON M.categoria_movimentacao_id = CM.id
WHERE M.tipo = 'DESPESA'
GROUP BY CM.id, CM.descricao
ORDER BY VALOR_TOTAL DESC;