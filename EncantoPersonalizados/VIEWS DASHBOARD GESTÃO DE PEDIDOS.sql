-- VIEWS PROJETO DASHBOARD DE PEDIDOS

-- CREATE OR REPLACE VIEW vw_pedidos_criados_entregues_filtros AS


-- VIEW PARA TIPOS DE PEDIDO 
select * from pedido;
select * from pedido_status_pedido;
select * from status_pedido;
SELECT * FROM vw_tipo_pedido;
SELECT * FROM produto;
SELECT * FROM produto_pedido;





select * from usuario;
select * from item_produto;
select * from produto_pedido;



-- view analitico por pedido 

SELECT 
	DISTINCT p.id,
    c.nome AS cliente,
    (
		SELECT 
        COUNT(*) 
        FROM produto_pedido AS prod_ped
        WHERE prod_ped.pedido_id = p.id
    ) AS qtd_produtos,
    sp.status AS status_do_pedido,
    DATEDIFF(
			psp.created_at,
            p.created_at
        ) AS lead_time,
	u.name AS funcionario
    FROM pedido AS p
    JOIN usuario u 
    ON u.id = p.usuario_id
    JOIN cliente c
    ON c.id = p.cliente_id
    JOIN pedido_status_pedido psp
    ON psp.pedido_id = p.id 
    JOIN status_pedido sp
    ON sp.id = psp.status_id
    WHERE p.ativo =1 
    AND psp.status_atual = 1
    ORDER BY p.id ASC;
    


-- view pedidos concluidos por funcionario
SELECT 
	u.name AS funcionario,
    COUNT(DISTINCT p.id) AS qtd_pedidos
    FROM pedido AS p
    JOIN usuario u
    ON u.id = p.usuario_id
    JOIN pedido_status_pedido psp 
    ON psp.pedido_id = p.id 
    JOIN status_pedido sp
    ON sp.id = psp.status_id 
    JOIN vw_tipo_pedido AS tp
	ON tp.id = p.id 
	WHERE psp.status_atual = 1 
    AND p.ativo = 1
    AND sp.status = 'Finalizado'
    GROUP BY funcionario
    ORDER BY qtd_pedidos DESC;


-- view lt por funcionario 
CREATE OR REPLACE VIEW vw_leadtime_funcionario AS 
SELECT 
	u.name AS funcionario,
    AVG(
		DATEDIFF(
			psp.created_at,
            p.created_at
        ) 
    ) AS lead_time
    FROM pedido AS p
    JOIN usuario u
    ON u.id = p.usuario_id
    JOIN pedido_status_pedido psp 
    ON psp.pedido_id = p.id 
    JOIN status_pedido sp
    ON sp.id = psp.status_id 
    JOIN vw_tipo_pedido AS tp
	ON tp.id = p.id 
	WHERE psp.status_atual = 1 
    AND p.ativo = 1
    AND sp.status = 'Finalizado'
    GROUP BY funcionario
    ORDER BY funcionario DESC;




-- view de pedidos com retrabalho por mes 
CREATE OR REPLACE VIEW vw_retrabalho_quantidade_mes AS 
SELECT 	
	DATE_FORMAT(p.created_at, '%Y-%m') as mes,
    COUNT(*) as quantidade_pedidos
    FROM pedido AS p
    JOIN vw_tipo_pedido tp
    ON tp.id = p.id
    WHERE tp.tipo_pedido = 'Retrabalho'
    AND p.ativo =1 
    GROUP BY mes
    ORDER BY mes DESC;



-- TEMPO LEAD TIME POR ETAPA
CREATE OR REPLACE VIEW vw_leadtime_etapa AS 
SELECT 
	sp.status as etapa,
    AVG(
		DATEDIFF(
			psp.updated_at,
            psp.created_at
        ) 
    ) AS lead_time
    FROM pedido AS p
    JOIN pedido_status_pedido psp 
    ON psp.pedido_id = p.id 
    JOIN status_pedido sp
    ON sp.id = psp.status_id 
    JOIN vw_tipo_pedido AS tp
	ON tp.id = p.id 
    AND p.ativo = 1
    GROUP BY etapa
    ORDER BY lead_time DESC;


-- GRAFICO LEAD TIME MENSAL 
CREATE OR REPLACE VIEW vw_leadtime_mensal AS 
SELECT 
	DATE_FORMAT(p.created_at, '%Y-%m') AS mes,
    AVG(
		DATEDIFF(
			psp.created_at,
            p.created_at
        ) 
    ) AS lead_time
    FROM pedido AS p
    JOIN pedido_status_pedido psp 
    ON psp.pedido_id = p.id 
    JOIN status_pedido sp
    ON sp.id = psp.status_id 
    JOIN vw_tipo_pedido AS tp
	ON tp.id = p.id 
	WHERE psp.status_atual = 1 
    AND p.ativo = 1
    AND sp.status = 'Finalizado'
    GROUP BY mes
    ORDER BY mes DESC;




-- GRAFICO TRAZER PEDIDOS CRIADOS X ENTREGUES NO MES 
SELECT 
	DATE_FORMAT(p.created_at, '%Y-%m') AS mes,
	COUNT(*) AS pedidos_criados,
    SUM(
		CASE 
			WHEN 
            (
				SELECT p1.id FROM pedido AS p1
                JOIN pedido_status_pedido AS psp1
                ON psp1.pedido_id = p1.id
                JOIN status_pedido sp1
                ON sp1.id = psp1.status_id
                WHERE psp1.status_atual = 1
                AND p.id = p1.id
                AND sp1.status = 'Finalizado'
                
            ) = p.id
            THEN 1
            ELSE 0
		END  
        
    
     ) AS pedidos_finalizados
     FROM pedido AS p
     JOIN vw_tipo_pedido AS tp
     ON tp.id = p.id 
     WHERE p.ativo = 1
     AND tp.tipo_pedido = 'Atrasado'
     GROUP BY mes
     ORDER BY mes DESC;
    
    



-- CREATE VIEW FILTRO DE (PRODUTO)
CREATE OR REPLACE VIEW  vw_filtro_produto_item AS
SELECT p.id, pp.produto_id, prod.item_produto_id as qtd_prod FROM produto AS p
LEFT JOIN produto_pedido pp
ON pp.pedido_id = p.id 
LEFT JOIN produto prod
ON pp.produto_id = prod.id
WHERE p.ativo = 1
ORDER BY qtd_prod DESC;
 

-- CREATE DA VIEW TIPO DE PEDIDO

CREATE OR REPLACE VIEW vw_tipo_pedido AS 
SELECT 
	p.id,
    p.origem,
    p.observacoes,
	sp.status,
    CASE 
		WHEN 
			p.data_limite <= now()
		AND sp.status NOT IN ('Cancelado', 'Entregue')
        THEN 'Atrasado'
	 
		WHEN 
			p.id IN (
		SELECT p1.id
        FROM pedido AS p1
        JOIN pedido_status_pedido psp1
		ON psp1.pedido_id = p1.id
		JOIN status_pedido sp1
		ON sp1.id = psp1.status_id
        WHERE (
			p1.id IN (
				SELECT psp2.pedido_id FROM 
                pedido_status_pedido AS psp2
                JOIN status_pedido sp2
                ON sp2.id = psp2.status_id
                WHERE sp2.status = 'Entregue'
            ) AND (
            psp1.status_atual = 1 AND sp1.status NOT IN  ('Entregue', 'Cancelado') )
        )
    
    )
		THEN 'Retrabalho'
        
        ELSE 'Normal'
        END AS tipo_pedido
    
FROM pedido AS p
JOIN pedido_status_pedido psp
ON psp.pedido_id = p.id
JOIN status_pedido sp
ON sp.id = psp.status_id

WHERE (
	p.ativo = 1
)

GROUP BY p.id,
    p.origem,
    p.observacoes,
	sp.status,
	tipo_pedido
;

SELECT * FROM vw_tipo_pedido
WHERE tipo_pedido = 'Retrabalho';




SELECT * FROM pedido AS p
JOIN pedido_status_pedido psp
ON psp.pedido_id = p.id
JOIN status_pedido sp
ON sp.id = psp.status_id 
WHERE p.id = 300;
