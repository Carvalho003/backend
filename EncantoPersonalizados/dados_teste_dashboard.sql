USE encanto_personalizados;

-- ============================================================
--  DADOS DE TESTE — Dashboard de Gestão de Pedidos
--  Período coberto: Jan – Abr 2025
--
--  Senha de todos os usuários: password
--
--  ATENÇÃO: apaga e recria dados nas tabelas abaixo.
--  Certifique-se de que as views do arquivo
--  "VIEWS DASHBOARD GESTÃO DE PEDIDOS.sql" já foram criadas
--  (vw_tipo_pedido deve existir antes das demais).
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE produto_pedido;
TRUNCATE TABLE pedido_status_pedido;
TRUNCATE TABLE pedido;
TRUNCATE TABLE produto;
TRUNCATE TABLE tema_produto;
TRUNCATE TABLE item_produto;
TRUNCATE TABLE categoria_tema;
TRUNCATE TABLE status_pedido;
TRUNCATE TABLE usuario;
TRUNCATE TABLE cliente;
SET FOREIGN_KEY_CHECKS = 1;

-- ─────────────────────────────────────────────────────────────
--  STATUS DE PEDIDO
--  'Finalizado', 'Entregue' e 'Cancelado' são classificados
--  por status_role para as views de dashboard.
-- ─────────────────────────────────────────────────────────────
INSERT INTO status_pedido (id, status, cor, ordem_kanban, status_role, ativo, created_at, updated_at) VALUES
(1, 'Análise',    '#F59E0B', 1, NULL, 1, NOW(), NOW()),
(2, 'Produção',   '#3B82F6', 2, NULL, 1, NOW(), NOW()),
(3, 'Qualidade',  '#8B5CF6', 3, NULL, 1, NOW(), NOW()),
(4, 'Finalizado', '#10B981', 4, 'FINALIZADO', 1, NOW(), NOW()),
(5, 'Entregue',   '#059669', 5, 'ENTREGUE', 1, NOW(), NOW()),
(6, 'Cancelado',  '#EF4444', 6, 'CANCELADO', 1, NOW(), NOW());

-- ─────────────────────────────────────────────────────────────
--  USUÁRIOS
--  Hash BCrypt para a senha "password"
--  (padrão de testes Spring Security)
-- ─────────────────────────────────────────────────────────────
INSERT INTO usuario (id, name, email, password, cpf, data_nasc, cargo, status, foto, created_at, updated_at) VALUES
(1, 'Carlos Silva',   'carlos@encanto.com',  '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '111.111.111-11', '1990-03-15', 'Administrador', 1, NULL, NOW(), NOW()),
(2, 'Ana Costa',      'ana@encanto.com',     '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '222.222.222-22', '1992-07-22', 'Manufatura',    1, NULL, NOW(), NOW()),
(3, 'João Santos',    'joao@encanto.com',    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '333.333.333-33', '1988-11-05', 'Manufatura',    1, NULL, NOW(), NOW()),
(4, 'Maria Oliveira', 'maria@encanto.com',   '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '444.444.444-44', '1995-01-30', 'Manufatura',    1, NULL, NOW(), NOW()),
(5, 'Pedro Lima',     'pedro@encanto.com',   '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '555.555.555-55', '1985-09-12', 'Social Media',  1, NULL, NOW(), NOW());

-- ─────────────────────────────────────────────────────────────
--  CLIENTES
-- ─────────────────────────────────────────────────────────────
INSERT INTO cliente (id, nome, telefone, created_at, updated_at) VALUES
(1, 'Tech Solutions Ltda',    '(11) 99999-0001', NOW(), NOW()),
(2, 'Indústria ABC',          '(21) 99999-0002', NOW(), NOW()),
(3, 'Comercial XYZ',          '(31) 99999-0003', NOW(), NOW()),
(4, 'Distribuidora Nacional', '(41) 99999-0004', NOW(), NOW()),
(5, 'Varejo Express',         '(51) 99999-0005', NOW(), NOW()),
(6, 'Magazine Central',       '(61) 99999-0006', NOW(), NOW()),
(7, 'Loja da Esquina',        '(71) 99999-0007', NOW(), NOW()),
(8, 'Confecções do Norte',    '(81) 99999-0008', NOW(), NOW());

-- ─────────────────────────────────────────────────────────────
--  PRODUTO
-- ─────────────────────────────────────────────────────────────
INSERT INTO categoria_tema (id, titulo, ativo) VALUES
(1, 'Festas',      1),
(2, 'Corporativo', 1),
(3, 'Casamentos',  1);

INSERT INTO item_produto (id, descricao, preco_venda, custo_producao, prazo_producao, largura, altura, peso, comprimento, material, descricao_padrao, preco_promocional, ativo, created_at, updated_at) VALUES
(1, 'Caneca Personalizada',    45.00, 12.00, 3,  8.5,  9.5, 0.35,   0.0, 'Cerâmica',  'Caneca de cerâmica 325ml com impressão UV',         39.90, 1, NOW(), NOW()),
(2, 'Camiseta Estampada',      65.00, 20.00, 5,  0.0,  0.0, 0.25,   0.0, 'Algodão',   'Camiseta 100% algodão com estampa personalizada',   55.00, 1, NOW(), NOW()),
(3, 'Almofada Personalizada',  89.00, 25.00, 4, 40.0, 40.0, 0.60,  40.0, 'Poliéster', 'Almofada 40x40cm com capa sublimada',               75.00, 1, NOW(), NOW()),
(4, 'Toalha Bordada',         120.00, 35.00, 7, 70.0,  0.0, 0.80, 140.0, 'Algodão',   'Toalha de banho 70x140cm com bordado personalizado',100.00, 1, NOW(), NOW());

INSERT INTO tema_produto (id, descricao, categoria_tema_id, ativo, created_at, updated_at) VALUES
(1, 'Aniversário',    1, 1, NOW(), NOW()),
(2, 'Festa Infantil', 1, 1, NOW(), NOW()),
(3, 'Corporativo',    2, 1, NOW(), NOW()),
(4, 'Casamento',      3, 1, NOW(), NOW()),
(5, 'Formatura',      1, 1, NOW(), NOW());

INSERT INTO produto (id, titulo, descricao, tema_produto_id, item_produto_id, ativo, created_at, updated_at) VALUES
(1, 'Caneca Aniversário',   'Caneca de cerâmica temática de aniversário',  1, 1, 1, NOW(), NOW()),
(2, 'Caneca Infantil',      'Caneca temática para festa infantil',          2, 1, 1, NOW(), NOW()),
(3, 'Camiseta Corporativa', 'Camiseta com logo corporativo bordado',        3, 2, 1, NOW(), NOW()),
(4, 'Camiseta Casamento',   'Camiseta temática para padrinhos',             4, 2, 1, NOW(), NOW()),
(5, 'Almofada Aniversário', 'Almofada sublimada com foto de aniversário',   1, 3, 1, NOW(), NOW()),
(6, 'Almofada Formatura',   'Almofada de lembrança de formatura',           5, 3, 1, NOW(), NOW()),
(7, 'Toalha Corporativa',   'Toalha bordada com logo da empresa',           3, 4, 1, NOW(), NOW()),
(8, 'Toalha Casamento',     'Toalha bordada com nomes e data do casamento', 4, 4, 1, NOW(), NOW());

-- ─────────────────────────────────────────────────────────────
--  PEDIDOS
--
--  IDs  1-12 → Finalizados   (alimentam vw_leadtime_funcionario
--                              e vw_leadtime_mensal)
--  IDs 13-17 → Atrasados     (data_limite no passado, status ativo)
--  IDs 18-21 → Retrabalho    (tiveram Entregue, voltaram para produção)
--  IDs 22-27 → Em andamento  (Normal)
--  IDs 28-31 → Entregues
--  IDs 32-33 → Cancelados
--
--  data_limite = NULL para tudo exceto Atrasados.
--  Todos os dados são de 2025 (passado); sem NULL, a view
--  classificaria Finalizado e Normal como Atrasado também.
-- ─────────────────────────────────────────────────────────────

-- FINALIZADOS
INSERT INTO pedido (id, observacoes, created_at, updated_at, data_limite, usuario_id, cliente_id, origem, preco_total, peso_total, ativo) VALUES
-- Janeiro — Carlos 9d, Ana 11d, Pedro 11d
( 1, 'Canecas para aniversário corporativo', '2025-01-05 09:00:00', NOW(), NULL, 1, 1, 'WhatsApp',  450.00,  3.50, 1),
( 2, 'Camisetas evento de integração',       '2025-01-08 10:00:00', NOW(), NULL, 2, 2, 'Instagram', 650.00,  2.50, 1),
( 3, 'Toalhas bordadas para brinde',         '2025-01-12 08:30:00', NOW(), NULL, 5, 3, 'WhatsApp',  600.00,  4.00, 1),
-- Fevereiro — Carlos 8d, Ana 11d, Maria 7d
( 4, 'Canecas corporativas kit 8un',         '2025-02-03 09:00:00', NOW(), NULL, 1, 4, 'Site',      360.00,  2.80, 1),
( 5, 'Almofadas kit aniversário',            '2025-02-10 11:00:00', NOW(), NULL, 2, 5, 'WhatsApp',  890.00,  6.00, 1),
( 6, 'Camisetas de formatura turma 2025',    '2025-02-15 08:00:00', NOW(), NULL, 4, 6, 'Instagram', 780.00,  3.00, 1),
-- Março — João 8d, Carlos 9d, Maria 7d
( 7, 'Kit canecas festa infantil',           '2025-03-02 09:30:00', NOW(), NULL, 3, 7, 'WhatsApp',  540.00,  4.20, 1),
( 8, 'Toalhas bordadas casamento Silva',     '2025-03-10 10:00:00', NOW(), NULL, 1, 8, 'Site',      960.00,  6.40, 1),
( 9, 'Almofadas corporativas 8un',           '2025-03-20 09:00:00', NOW(), NULL, 4, 1, 'WhatsApp',  712.00,  4.80, 1),
-- Abril — João 8d, Ana 11d, Pedro 11d
(10, 'Camisetas aniversário 8un',            '2025-04-01 08:00:00', NOW(), NULL, 3, 2, 'Instagram', 520.00,  2.00, 1),
(11, 'Canecas casamento Costa',              '2025-04-05 09:00:00', NOW(), NULL, 2, 3, 'WhatsApp',  270.00,  2.10, 1),
(12, 'Kit almofadas formatura 12un',         '2025-04-10 10:30:00', NOW(), NULL, 5, 4, 'Site',     1068.00,  7.20, 1);

-- ATRASADOS (data_limite vencida, status ativo)
INSERT INTO pedido (id, observacoes, created_at, updated_at, data_limite, usuario_id, cliente_id, origem, preco_total, peso_total, ativo) VALUES
(13, 'Canecas corp — prazo vencido',       '2025-02-01 09:00:00', NOW(), '2025-02-15 23:59:59', 1, 5, 'WhatsApp',  180.00, 1.40, 1),
(14, 'Camisetas — prazo vencido',          '2025-03-01 09:00:00', NOW(), '2025-03-10 23:59:59', 2, 6, 'Site',      260.00, 1.00, 1),
(15, 'Almofadas — prazo vencido',          '2025-03-05 09:00:00', NOW(), '2025-03-20 23:59:59', 3, 7, 'Instagram', 356.00, 2.40, 1),
(16, 'Toalhas — prazo vencido',            '2025-04-01 09:00:00', NOW(), '2025-04-10 23:59:59', 1, 8, 'WhatsApp',  480.00, 3.20, 1),
(17, 'Canecas — prazo vencido',            '2025-04-05 09:00:00', NOW(), '2025-04-12 23:59:59', 4, 1, 'Site',       90.00, 0.70, 1);

-- RETRABALHO (voltaram após Entregue)
INSERT INTO pedido (id, observacoes, created_at, updated_at, data_limite, usuario_id, cliente_id, origem, preco_total, peso_total, ativo) VALUES
(18, 'Retrabalho — caneca com defeito de impressão',  '2025-01-20 09:00:00', NOW(), NULL, 1, 2, 'WhatsApp',  135.00, 1.05, 1),
(19, 'Retrabalho — camiseta com cor errada',          '2025-02-18 09:00:00', NOW(), NULL, 2, 3, 'Instagram', 195.00, 0.75, 1),
(20, 'Retrabalho — almofada com costura solta',       '2025-03-12 09:00:00', NOW(), NULL, 3, 4, 'WhatsApp',  267.00, 1.80, 1),
(21, 'Retrabalho — toalha com bordado incorreto',     '2025-04-08 09:00:00', NOW(), NULL, 5, 5, 'Site',      240.00, 1.60, 1);

-- EM ANDAMENTO (Normal)
INSERT INTO pedido (id, observacoes, created_at, updated_at, data_limite, usuario_id, cliente_id, origem, preco_total, peso_total, ativo) VALUES
(22, 'Canecas aniversário — em análise',       '2025-02-20 09:00:00', NOW(), NULL, 3, 6, 'WhatsApp',  225.00, 1.75, 1),
(23, 'Camisetas formatura — em produção',      '2025-03-15 09:00:00', NOW(), NULL, 1, 7, 'Site',      390.00, 1.50, 1),
(24, 'Almofadas casamento — qualidade',        '2025-03-22 09:00:00', NOW(), NULL, 4, 8, 'Instagram', 534.00, 3.60, 1),
(25, 'Toalhas corporativas — em análise',      '2025-04-02 09:00:00', NOW(), NULL, 2, 1, 'WhatsApp',  720.00, 4.80, 1),
(26, 'Canecas infantil — em produção',         '2025-04-07 09:00:00', NOW(), NULL, 3, 2, 'Site',      270.00, 2.10, 1),
(27, 'Camisetas aniversário — qualidade',      '2025-04-12 09:00:00', NOW(), NULL, 5, 3, 'Instagram', 195.00, 0.75, 1);

-- ENTREGUES
INSERT INTO pedido (id, observacoes, created_at, updated_at, data_limite, usuario_id, cliente_id, origem, preco_total, peso_total, ativo) VALUES
(28, 'Canecas entregues — janeiro',  '2025-01-25 09:00:00', NOW(), NULL, 2, 4, 'WhatsApp',  180.00, 1.40, 1),
(29, 'Almofadas entregues — fev',    '2025-02-25 09:00:00', NOW(), NULL, 4, 5, 'Site',      356.00, 2.40, 1),
(30, 'Camisetas entregues — mar',    '2025-03-28 09:00:00', NOW(), NULL, 1, 6, 'Instagram', 260.00, 1.00, 1),
(31, 'Toalhas entregues — abr',      '2025-04-15 09:00:00', NOW(), NULL, 3, 7, 'WhatsApp',  480.00, 3.20, 1);

-- CANCELADOS
INSERT INTO pedido (id, observacoes, created_at, updated_at, data_limite, usuario_id, cliente_id, origem, preco_total, peso_total, ativo) VALUES
(32, 'Cancelado — cliente desistiu do pedido', '2025-02-12 09:00:00', NOW(), NULL, 4, 8, 'WhatsApp', 65.00, 0.25, 1),
(33, 'Cancelado — pagamento não confirmado',   '2025-03-08 09:00:00', NOW(), NULL, 5, 1, 'Site',     89.00, 0.60, 1);

-- ─────────────────────────────────────────────────────────────
--  PEDIDO_STATUS_PEDIDO
--
--  Lead time dos Finalizados = DATEDIFF(psp.created_at, pedido.created_at)
--  Carlos (~9d), Ana (~11d), João (~8d), Maria (~7d), Pedro (~11d)
--
--  Lead time por etapa = DATEDIFF(psp.updated_at, psp.created_at)
--  Análise ~2d, Produção ~5d, Qualidade ~1d, Finalizado ~1d
-- ─────────────────────────────────────────────────────────────

-- FINALIZADOS (status_id=4, status_atual=1)
INSERT INTO pedido_status_pedido (id, pedido_id, status_id, status_atual, created_at, updated_at) VALUES
-- Pedido 1: Carlos, Jan, criado 01-05, finalizado 01-14 → 9d
( 1,  1, 4, 1, '2025-01-14 17:00:00', '2025-01-15 10:00:00'),
-- Pedido 2: Ana, Jan, criado 01-08, finalizado 01-19 → 11d
( 2,  2, 4, 1, '2025-01-19 17:00:00', '2025-01-20 10:00:00'),
-- Pedido 3: Pedro, Jan, criado 01-12, finalizado 01-23 → 11d
( 3,  3, 4, 1, '2025-01-23 17:00:00', '2025-01-24 10:00:00'),
-- Pedido 4: Carlos, Fev, criado 02-03, finalizado 02-11 → 8d
( 4,  4, 4, 1, '2025-02-11 17:00:00', '2025-02-12 10:00:00'),
-- Pedido 5: Ana, Fev, criado 02-10, finalizado 02-21 → 11d
( 5,  5, 4, 1, '2025-02-21 17:00:00', '2025-02-22 10:00:00'),
-- Pedido 6: Maria, Fev, criado 02-15, finalizado 02-22 → 7d
( 6,  6, 4, 1, '2025-02-22 17:00:00', '2025-02-23 10:00:00'),
-- Pedido 7: João, Mar, criado 03-02, finalizado 03-10 → 8d
( 7,  7, 4, 1, '2025-03-10 17:00:00', '2025-03-11 10:00:00'),
-- Pedido 8: Carlos, Mar, criado 03-10, finalizado 03-19 → 9d
( 8,  8, 4, 1, '2025-03-19 17:00:00', '2025-03-20 10:00:00'),
-- Pedido 9: Maria, Mar, criado 03-20, finalizado 03-27 → 7d
( 9,  9, 4, 1, '2025-03-27 17:00:00', '2025-03-28 10:00:00'),
-- Pedido 10: João, Abr, criado 04-01, finalizado 04-09 → 8d
(10, 10, 4, 1, '2025-04-09 17:00:00', '2025-04-10 10:00:00'),
-- Pedido 11: Ana, Abr, criado 04-05, finalizado 04-16 → 11d
(11, 11, 4, 1, '2025-04-16 17:00:00', '2025-04-17 10:00:00'),
-- Pedido 12: Pedro, Abr, criado 04-10, finalizado 04-21 → 11d
(12, 12, 4, 1, '2025-04-21 17:00:00', '2025-04-22 10:00:00');

-- ATRASADOS (status ativo, status_atual=1)
-- updated_at - created_at gera o lead time da etapa
INSERT INTO pedido_status_pedido (id, pedido_id, status_id, status_atual, created_at, updated_at) VALUES
(13, 13, 1, 1, '2025-02-01 10:00:00', '2025-02-03 10:00:00'),  -- Análise 2d
(14, 14, 2, 1, '2025-03-01 10:00:00', '2025-03-06 10:00:00'),  -- Produção 5d
(15, 15, 1, 1, '2025-03-05 10:00:00', '2025-03-07 10:00:00'),  -- Análise 2d
(16, 16, 2, 1, '2025-04-01 10:00:00', '2025-04-06 10:00:00'),  -- Produção 5d
(17, 17, 1, 1, '2025-04-05 10:00:00', '2025-04-07 10:00:00');  -- Análise 2d

-- RETRABALHO
-- Par de registros por pedido:
--   registro 1: Entregue histórico (status_atual=0)
--   registro 2: status atual ativo (status_atual=1)
-- A view vw_tipo_pedido detecta Retrabalho pelo EXISTS que verifica
-- se o pedido já teve status Entregue E tem status atual fora de
-- (Entregue, Cancelado).
INSERT INTO pedido_status_pedido (id, pedido_id, status_id, status_atual, created_at, updated_at) VALUES
-- Pedido 18 (Jan): Entregue → voltou para Análise
(18, 18, 5, 0, '2025-01-28 10:00:00', '2025-02-03 10:00:00'),
(19, 18, 1, 1, '2025-02-03 10:00:00', '2025-02-05 10:00:00'),
-- Pedido 19 (Fev): Entregue → voltou para Produção
(20, 19, 5, 0, '2025-02-26 10:00:00', '2025-03-04 10:00:00'),
(21, 19, 2, 1, '2025-03-04 10:00:00', '2025-03-09 10:00:00'),
-- Pedido 20 (Mar): Entregue → voltou para Análise
(22, 20, 5, 0, '2025-03-20 10:00:00', '2025-03-28 10:00:00'),
(23, 20, 1, 1, '2025-03-28 10:00:00', '2025-03-30 10:00:00'),
-- Pedido 21 (Abr): Entregue → voltou para Produção
(24, 21, 5, 0, '2025-04-16 10:00:00', '2025-04-20 10:00:00'),
(25, 21, 2, 1, '2025-04-20 10:00:00', '2025-04-22 10:00:00');

-- EM ANDAMENTO (Normal)
INSERT INTO pedido_status_pedido (id, pedido_id, status_id, status_atual, created_at, updated_at) VALUES
(26, 22, 1, 1, '2025-02-20 10:00:00', '2025-02-22 10:00:00'),  -- Análise 2d
(27, 23, 2, 1, '2025-03-15 10:00:00', '2025-03-20 10:00:00'),  -- Produção 5d
(28, 24, 3, 1, '2025-03-22 10:00:00', '2025-03-23 10:00:00'),  -- Qualidade 1d
(29, 25, 1, 1, '2025-04-02 10:00:00', '2025-04-04 10:00:00'),  -- Análise 2d
(30, 26, 2, 1, '2025-04-07 10:00:00', '2025-04-12 10:00:00'),  -- Produção 5d
(31, 27, 3, 1, '2025-04-12 10:00:00', '2025-04-13 10:00:00');  -- Qualidade 1d

-- ENTREGUES (status_id=5, status_atual=1)
INSERT INTO pedido_status_pedido (id, pedido_id, status_id, status_atual, created_at, updated_at) VALUES
(32, 28, 5, 1, '2025-02-03 10:00:00', '2025-02-04 10:00:00'),
(33, 29, 5, 1, '2025-03-05 10:00:00', '2025-03-06 10:00:00'),
(34, 30, 5, 1, '2025-04-05 10:00:00', '2025-04-06 10:00:00'),
(35, 31, 5, 1, '2025-04-22 10:00:00', '2025-04-23 10:00:00');

-- CANCELADOS (status_id=6, status_atual=1)
INSERT INTO pedido_status_pedido (id, pedido_id, status_id, status_atual, created_at, updated_at) VALUES
(36, 32, 6, 1, '2025-02-12 10:00:00', '2025-02-12 11:00:00'),
(37, 33, 6, 1, '2025-03-08 10:00:00', '2025-03-08 11:00:00');

-- ─────────────────────────────────────────────────────────────
--  PRODUTO_PEDIDO — um item por pedido
-- ─────────────────────────────────────────────────────────────
INSERT INTO produto_pedido (id, preco_unitario, qtd_produto, preco_total, peso_unitario, peso_total, pedido_id, produto_id, created_at, updated_at) VALUES
-- Finalizados
( 1,  45.00, 10,  450.00, 0.35,  3.50,  1, 1, NOW(), NOW()),
( 2,  65.00, 10,  650.00, 0.25,  2.50,  2, 3, NOW(), NOW()),
( 3, 120.00,  5,  600.00, 0.80,  4.00,  3, 8, NOW(), NOW()),
( 4,  45.00,  8,  360.00, 0.35,  2.80,  4, 2, NOW(), NOW()),
( 5,  89.00, 10,  890.00, 0.60,  6.00,  5, 5, NOW(), NOW()),
( 6,  65.00, 12,  780.00, 0.25,  3.00,  6, 4, NOW(), NOW()),
( 7,  45.00, 12,  540.00, 0.35,  4.20,  7, 2, NOW(), NOW()),
( 8, 120.00,  8,  960.00, 0.80,  6.40,  8, 8, NOW(), NOW()),
( 9,  89.00,  8,  712.00, 0.60,  4.80,  9, 6, NOW(), NOW()),
(10,  65.00,  8,  520.00, 0.25,  2.00, 10, 4, NOW(), NOW()),
(11,  45.00,  6,  270.00, 0.35,  2.10, 11, 1, NOW(), NOW()),
(12,  89.00, 12, 1068.00, 0.60,  7.20, 12, 6, NOW(), NOW()),
-- Atrasados
(13,  45.00,  4,  180.00, 0.35,  1.40, 13, 1, NOW(), NOW()),
(14,  65.00,  4,  260.00, 0.25,  1.00, 14, 3, NOW(), NOW()),
(15,  89.00,  4,  356.00, 0.60,  2.40, 15, 5, NOW(), NOW()),
(16, 120.00,  4,  480.00, 0.80,  3.20, 16, 7, NOW(), NOW()),
(17,  45.00,  2,   90.00, 0.35,  0.70, 17, 2, NOW(), NOW()),
-- Retrabalho
(18,  45.00,  3,  135.00, 0.35,  1.05, 18, 1, NOW(), NOW()),
(19,  65.00,  3,  195.00, 0.25,  0.75, 19, 3, NOW(), NOW()),
(20,  89.00,  3,  267.00, 0.60,  1.80, 20, 5, NOW(), NOW()),
(21, 120.00,  2,  240.00, 0.80,  1.60, 21, 7, NOW(), NOW()),
-- Em andamento
(22,  45.00,  5,  225.00, 0.35,  1.75, 22, 2, NOW(), NOW()),
(23,  65.00,  6,  390.00, 0.25,  1.50, 23, 4, NOW(), NOW()),
(24,  89.00,  6,  534.00, 0.60,  3.60, 24, 6, NOW(), NOW()),
(25, 120.00,  6,  720.00, 0.80,  4.80, 25, 7, NOW(), NOW()),
(26,  45.00,  6,  270.00, 0.35,  2.10, 26, 2, NOW(), NOW()),
(27,  65.00,  3,  195.00, 0.25,  0.75, 27, 4, NOW(), NOW()),
-- Entregues
(28,  45.00,  4,  180.00, 0.35,  1.40, 28, 1, NOW(), NOW()),
(29,  89.00,  4,  356.00, 0.60,  2.40, 29, 5, NOW(), NOW()),
(30,  65.00,  4,  260.00, 0.25,  1.00, 30, 3, NOW(), NOW()),
(31, 120.00,  4,  480.00, 0.80,  3.20, 31, 8, NOW(), NOW()),
-- Cancelados
(32,  65.00,  1,   65.00, 0.25,  0.25, 32, 3, NOW(), NOW()),
(33,  89.00,  1,   89.00, 0.60,  0.60, 33, 5, NOW(), NOW());

-- ─────────────────────────────────────────────────────────────
--  VERIFICAÇÃO — rode após o INSERT para confirmar os dados
-- ─────────────────────────────────────────────────────────────
-- SELECT funcionario, ROUND(lead_time,1) AS lead_time_dias FROM vw_leadtime_funcionario ORDER BY funcionario;
-- SELECT mes, ROUND(lead_time,1) AS lead_time_dias        FROM vw_leadtime_mensal        ORDER BY mes;
-- SELECT etapa, ROUND(lead_time,1) AS lead_time_dias      FROM vw_leadtime_etapa         ORDER BY lead_time DESC;
-- SELECT mes, quantidade_pedidos                           FROM vw_retrabalho_quantidade_mes ORDER BY mes;
-- SELECT status, tipo_pedido, COUNT(*) AS qtd             FROM vw_tipo_pedido GROUP BY status, tipo_pedido ORDER BY tipo_pedido;
