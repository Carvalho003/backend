USE encanto_personalizados;

INSERT IGNORE INTO categoria_movimentacao (id, descricao, status) VALUES
(1, 'Venda', 21),
(2, 'Compra de Insumos', 21),
(3, 'Despesa Operacional', 1);

INSERT IGNORE INTO categoria_tema (id, ativo, titulo) VALUES
(1, 1, 'Aniversário'),
(2, 1, 'Casamento'),
(3, 1, 'Geek'),
(4, 1, 'Motivacional'),
(5, 1, 'Corporativo'),
(6, 1, 'Dia das Mães'),
(7, 1, 'Dia dos Pais'),
(8, 1, 'Natal'),
(9, 1, 'Acadêmico'),
(10, 1, 'Pet'),
(11, 1, 'series');

INSERT IGNORE INTO cliente (id, nome, telefone, ativo, created_at, updated_at) VALUES
(1, 'Mariana Souza', '11987650001', true, NOW(), NOW()),
(2, 'Rafael Almeida', '11987650002', true, NOW(), NOW()),
(3, 'Camila Torres', '11987650003', true, NOW(), NOW()),
(4, 'Felipe Martins', '11987650004', true,NOW(), NOW()),
(5, 'Juliana Ribeiro', '11987650005', true, NOW(), NOW()),
(6, 'Thiago Costa', '11987650006', true, NOW(), NOW()),
(7, 'Larissa Nogueira', '11987650007', true, NOW(), NOW()),
(8, 'Bruno Carvalho', '11987650008', true, NOW(), NOW()),
(9, 'Aline Ferreira', '11987650009', true, NOW(), NOW()),
(10, 'Gustavo Lima', '11987650010', true, NOW(), NOW()),
(11, 'Patrícia Gomes', '11987650011', true, NOW(), NOW()),
(12, 'Vinícius Barros', '11987650012', true, NOW(), NOW()),
(13, 'Beatriz Menezes', '11987650013', true, NOW(), NOW()),
(14, 'Renato Pires', '11987650014', true, NOW(), NOW()),
(15, 'Fernanda Oliveira', '11987650015', true, NOW(), NOW()),
(16, 'Lucas Azevedo', '11987650016', true, NOW(), NOW()),
(17, 'Natália Freitas', '11987650017', true, NOW(), NOW()),
(18, 'Eduardo Rocha', '11987650018', true, NOW(), NOW()),
(19, 'Carolina Duarte', '11987650019', true, NOW(), NOW()),
(20, 'André Moura', '11987650020', true, NOW(), NOW()),
(21, 'Paula Siqueira', '11987650021', true, NOW(), NOW()),
(22, 'Leonardo Cunha', '11987650022', true, NOW(), NOW()),
(23, 'Vanessa Batista', '11987650023', true, NOW(), NOW()),
(24, 'Rodrigo Teles', '11987650024', true, NOW(), NOW()),
(25, 'Isabela Cardoso', '11987650025', true, NOW(), NOW()),
(26, 'Henrique Farias', '11987650026', true, NOW(), NOW()),
(27, 'Sabrina Teixeira', '11987650027', true, NOW(), NOW()),
(28, 'Márcio Vieira', '11987650028', true, NOW(), NOW()),
(29, 'Tatiane Lopes', '11987650029', true, NOW(), NOW()),
(30, 'Daniela Campos', '11987650030', true, NOW(), NOW());

INSERT IGNORE INTO contraparte (id, nome, descricao, segmento, tipo_contrato, status, created_at, updated_at) VALUES
(1, 'Sublimix Insumos', 'Fornecedor de canecas, squeezes e papelaria personalizada', 'Insumos', 'Recorrente', 1, NOW(), NOW()),
(2, 'Mercado Livre', 'Marketplace com comissão por venda', 'Marketplace', 'Comissão', 1, NOW(), NOW()),
(3, 'Shopee', 'Marketplace com comissão por venda', 'Marketplace', 'Comissão', 1, NOW(), NOW()),
(4, 'Meta Ads Brasil', 'Campanhas patrocinadas nas redes sociais', 'Marketing', 'Mensal', 1, NOW(), NOW()),
(5, 'Flash Entregas', 'Operação logística para envios locais e regionais', 'Logística', 'Sob Demanda', 1, NOW(), NOW()),
(6, 'Papel & Arte Cadernos', 'Fornecedor de cadernos, capas e encadernação', 'Insumos', 'Recorrente', 1, NOW(), NOW());

INSERT IGNORE INTO item_produto (id, descricao, material, altura, comprimento, largura, peso, custo_producao, preco_promocional, preco_venda, prazo_producao, ativo, created_at, updated_at) VALUES
(1, 'Caneca Branca 325ml', 'Cerâmica', 9.5, 8.0, 8.0, 320, 8.5, 24.9, 29.9, 2, 1, NOW(), NOW()),
(2, 'Caneca Mágica 325ml', 'Cerâmica Termossensível', 9.5, 8.0, 8.0, 340, 14.0, 34.9, 44.9, 3, 1, NOW(), NOW()),
(3, 'Camiseta Básica Personalizada', 'Algodão', 70.0, 50.0, 1.0, 200, 22.0, 44.9, 59.9, 3, 1, NOW(), NOW()),
(4, 'Camiseta Premium Personalizada', 'Algodão Penteado', 72.0, 52.0, 1.0, 240, 28.0, 54.9, 69.9, 4, 1, NOW(), NOW()),
(5, 'Caderno A5 Personalizado', 'Papel/Cartão', 21.0, 15.0, 2.0, 450, 11.5, 29.9, 39.9, 2, 1, NOW(), NOW()),
(6, 'Caderno Universitário Personalizado', 'Papel/Cartão', 27.5, 20.0, 3.0, 750, 18.0, 39.9, 54.9, 3, 1, NOW(), NOW()),
(7, 'Squeeze Personalizada 500ml', 'Alumínio', 22.0, 7.0, 7.0, 180, 13.0, 29.9, 39.9, 2, 1, NOW(), NOW()),
(8, 'Mousepad Personalizado', 'Neoprene', 0.3, 24.0, 20.0, 150, 7.0, 19.9, 27.9, 2, 1, NOW(), NOW()),
(9, 'Almofada Personalizada', 'Poliéster', 10.0, 40.0, 40.0, 500, 16.0, 34.9, 49.9, 3, 1, NOW(), NOW()),
(10, 'Ecobag Personalizada', 'Algodão Cru', 35.0, 30.0, 5.0, 120, 9.5, 24.9, 34.9, 2, 1, NOW(), NOW());

INSERT IGNORE INTO status_pedido (id, status, cor, ordem_kanban, status_role, ativo, created_at, updated_at) VALUES
(1, 'A Fazer', '#FFE5D9', 1, NULL, 1, NOW(), NOW()),
(2, 'Em Produção', '#F4ACB7', 2, NULL, 1, NOW(), NOW()),
(3, 'Finalizado', '#EAD2AC', 3, 'FINALIZADO', 1, NOW(), NOW()),
(4, 'Entregue', '#D8E2DC', 4, 'ENTREGUE', 1, NOW(), NOW()),
(5, 'Cancelado', '#E2ECE9', 5, 'CANCELADO', 1, NOW(), NOW());

UPDATE status_pedido SET status_role = 'FINALIZADO' WHERE id = 3 AND status_role IS NULL;
UPDATE status_pedido SET status_role = 'ENTREGUE' WHERE id = 4 AND status_role IS NULL;
UPDATE status_pedido SET status_role = 'CANCELADO' WHERE LOWER(status) = 'cancelado' AND status_role IS NULL;

INSERT IGNORE INTO usuario (id, name, email, password, cpf, cargo, status, created_at, updated_at) VALUES
(1, 'Diogo Yudi', 'diogo@encantopersonalizados.com.br', '$2a$10$VaiOzlGn2y7rvrEURrDoteehExkYbTE7/7OV5XInOkR1TD4yXOWTy', '11111111111', 'Administrador', 1, NOW(), NOW()),
(2, 'Hugo Carvalho', 'hugo@encantopersonalizados.com.br', '$2a$10$lETSHiEmLnQ4UmzHK.16ouVSgY8jJpzBBJC0oovns2kAzVe0wfIQW', '22222222222', 'Administrador', 1, NOW(), NOW()),
(3, 'Israel Coaquira', 'israel@encantopersonalizados.com.br', '$2a$10$9qP.j9Kfcp8eRSvs4gu8UOwAfYCrHDyLtp2OpZAeW2evriQ3Ww9mG', '33333333333', 'Administrador', 1, NOW(), NOW()),
(4, 'Matteo Garcia', 'matteo@encantopersonalizados.com.br', '$2a$10$vSanEk7dcQ3XO4QODI2X8.3gmvp7mH5861QxypdBQKLORaUWPLXqS', '44444444444', 'Administrador', 1, NOW(), NOW()),
(5, 'Nicoly Teixeira', 'nicoly@encantopersonalizados.com.br', '$2a$10$JTP3ehbG28PhzBDI66f2VeZ4ixiT.yUqfvQEdAEKo34s6RfPDWPO.', '55555555555', 'Administrador', 1, NOW(), NOW()),
(6, 'Zaqueu', 'zaqueu@encantopersonalizados.com.br', '$2a$10$KUagJdySjLwepVZ7p.Uzr.OO6sbsQ/j2n.fWzCrz47.G2Twm/SCrO', '66666666666', 'Administrador', 1, NOW(), NOW());

UPDATE usuario SET foto = '/uploads/usuarios/1/diogo.png' WHERE id = 1;
UPDATE usuario SET foto = '/uploads/usuarios/2/hugo.png' WHERE id = 2;
UPDATE usuario SET foto = '/uploads/usuarios/3/israel.jpg' WHERE id = 3;
UPDATE usuario SET foto = '/uploads/usuarios/4/matteo.png' WHERE id = 4;
UPDATE usuario SET foto = '/uploads/usuarios/5/nicoly.png' WHERE id = 5;
UPDATE usuario SET foto = '/uploads/usuarios/6/zeca.png' WHERE id = 6;

INSERT IGNORE INTO endereco_cliente (id, cep, logradouro, num_logradouro, complemento, bairro, cidade, municipio, estado, uf, ativo, created_at, updated_at, cliente_id) VALUES
(1, '01310-100', 'Avenida Paulista', '103', NULL, 'Bela Vista', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 21),
(2, '05407-002', 'Rua dos Pinheiros', '106', NULL, 'Pinheiros', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 22),
(3, '04538-132', 'Avenida Brigadeiro Faria Lima', '109', NULL, 'Itaim Bibi', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 3),
(4, '04101-300', 'Rua Vergueiro', '112', 'Apto 5', 'Vila Mariana', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 4),
(5, '05010-000', 'Rua Clélia', '115', NULL, 'Lapa', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 5),
(6, '03310-000', 'Rua Serra de Japi', '118', NULL, 'Tatuapé', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 6),
(7, '03164-000', 'Rua da Mooca', '121', NULL, 'Mooca', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 7),
(8, '04094-050', 'Avenida Jabaquara', '124', 'Apto 9', 'Saúde', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 8),
(9, '04707-000', 'Rua Barão do Triunfo', '127', NULL, 'Brooklin', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 9),
(10, '02310-000', 'Avenida Nova Cantareira', '130', NULL, 'Tucuruvi', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 10),
(11, '01310-100', 'Avenida Paulista', '133', NULL, 'Bela Vista', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 11),
(12, '05407-002', 'Rua dos Pinheiros', '136', 'Apto 1', 'Pinheiros', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 12),
(13, '04538-132', 'Avenida Brigadeiro Faria Lima', '139', NULL, 'Itaim Bibi', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 13),
(14, '04101-300', 'Rua Vergueiro', '142', NULL, 'Vila Mariana', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 14),
(15, '05010-000', 'Rua Clélia', '145', NULL, 'Lapa', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 15),
(16, '03310-000', 'Rua Serra de Japi', '148', 'Apto 5', 'Tatuapé', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 16),
(17, '03164-000', 'Rua da Mooca', '151', NULL, 'Mooca', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 17),
(18, '04094-050', 'Avenida Jabaquara', '154', NULL, 'Saúde', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 18),
(19, '04707-000', 'Rua Barão do Triunfo', '157', NULL, 'Brooklin', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 19),
(20, '02310-000', 'Avenida Nova Cantareira', '160', 'Apto 9', 'Tucuruvi', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 20),
(21, '01310-100', 'Avenida Paulista', '163', NULL, 'Bela Vista', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 21),
(22, '05407-002', 'Rua dos Pinheiros', '166', NULL, 'Pinheiros', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 22),
(23, '04538-132', 'Avenida Brigadeiro Faria Lima', '169', NULL, 'Itaim Bibi', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 23),
(24, '04101-300', 'Rua Vergueiro', '172', 'Apto 1', 'Vila Mariana', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 24),
(25, '05010-000', 'Rua Clélia', '175', NULL, 'Lapa', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 25),
(26, '03310-000', 'Rua Serra de Japi', '178', NULL, 'Tatuapé', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 26),
(27, '03164-000', 'Rua da Mooca', '181', NULL, 'Mooca', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 27),
(28, '04094-050', 'Avenida Jabaquara', '184', 'Apto 5', 'Saúde', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 28),
(29, '04707-000', 'Rua Barão do Triunfo', '187', NULL, 'Brooklin', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 29),
(30, '02310-000', 'Avenida Nova Cantareira', '190', NULL, 'Tucuruvi', 'São Paulo', 'São Paulo', 'São Paulo', 'SP', 1, NOW(), NOW(), 30);

INSERT IGNORE INTO tema_produto (id, descricao, ativo, created_at, updated_at, categoria_tema_id) VALUES
(1, 'Coleção Festa Colorida', 1, NOW(), NOW(), 21),
(2, 'Linha Casamento Minimalista', 1, NOW(), NOW(), 22),
(3, 'Galáxia Geek', 1, NOW(), NOW(), 3),
(4, 'Frases que Inspiram', 1, NOW(), NOW(), 4),
(5, 'Linha Marca Própria', 1, NOW(), NOW(), 5),
(6, 'Mãe, Meu Maior Amor', 1, NOW(), NOW(), 6),
(7, 'Pai, Meu Herói', 1, NOW(), NOW(), 7),
(8, 'Natal Afetivo', 1, NOW(), NOW(), 8),
(9, 'Vida Acadêmica', 1, NOW(), NOW(), 9),
(10, 'Pet em Casa', 1, NOW(), NOW(), 10),
(11, 'Breaking Bad', 1, NOW(), NOW(), 11);

INSERT IGNORE INTO produto (id, titulo, descricao, ativo, created_at, updated_at, item_produto_id, tema_produto_id) VALUES
(1, 'Caneca Festa Colorida', 'Caneca com nome e idade personalizada', 1, NOW(), NOW(), 1, 21),
(2, 'Caneca Casamento Minimalista', 'Caneca de casal com iniciais', 1, NOW(), NOW(), 1, 22),
(3, 'Caneca Galáxia Gamer', 'Caneca temática geek com arte espacial', 1, NOW(), NOW(), 1, 3),
(4, 'Caneca Frase do Dia', 'Caneca com mensagem motivacional', 1, NOW(), NOW(), 1, 4),
(5, 'Caneca Identidade Visual', 'Caneca corporativa com logo da empresa', 1, NOW(), NOW(), 1, 5),
(6, 'Caneca Melhor Mãe', 'Caneca afetiva para Dia das Mães', 1, NOW(), NOW(), 1, 6),
(7, 'Caneca Melhor Pai', 'Caneca afetiva para Dia dos Pais', 1, NOW(), NOW(), 1, 7),
(8, 'Caneca Natal Ilustrado', 'Caneca temática de Natal', 1, NOW(), NOW(), 1, 8),
(9, 'Caneca Professora Nota 10', 'Caneca para professores e formandos', 1, NOW(), NOW(), 1, 9),
(10, 'Caneca Meu Pet Favorito', 'Caneca com nome e ilustração do pet', 1, NOW(), NOW(), 1, 10),
(11, 'Caneca Mágica Geek', 'Caneca mágica com revelação de estampa', 1, NOW(), NOW(), 2, 3),
(12, 'Camiseta Gamer Edition', 'Camiseta básica com estampa geek', 1, NOW(), NOW(), 3, 3),
(13, 'Camiseta Frase Positiva', 'Camiseta com mensagem motivacional', 1, NOW(), NOW(), 3, 4),
(14, 'Camiseta Premium Corporativa', 'Camiseta premium para ações promocionais', 1, NOW(), NOW(), 4, 5),
(15, 'Caderno A5 Inspirador', 'Caderno com frase e capa personalizada', 1, NOW(), NOW(), 5, 4),
(16, 'Caderno Universitário Acadêmico', 'Caderno universitário com nome do aluno', 1, NOW(), NOW(), 6, 9),
(17, 'Squeeze Empresa em Movimento', 'Squeeze com identidade visual da marca', 1, NOW(), NOW(), 7, 5),
(18, 'Mousepad Mundo Nerd', 'Mousepad com arte geek', 1, NOW(), NOW(), 8, 3),
(19, 'Almofada Pet Lovers', 'Almofada com tema pet', 1, NOW(), NOW(), 9, 10),
(20, 'Ecobag Evento Criativo', 'Ecobag para brindes e eventos', 1, NOW(), NOW(), 10, 5),
(21, 'Camiseta Los Pollos Hermanos', 'Camiseta personalizada inspirada no tema Breaking Bad', 1, NOW(), NOW(), 3, 11),
(22, 'Caderno Breaking Bad', 'Caderno personalizado inspirado no tema Breaking Bad', 1, NOW(), NOW(), 5, 11);

INSERT IGNORE INTO foto_produto (id, foto, produto_id, created_at, updated_at) VALUES
(1, '/uploads/produtos/1/canecaFestaColorida.jpeg', 1, NOW(), NOW()),
(2, '/uploads/produtos/2/CanecaCasamento.jpeg', 2, NOW(), NOW()),
(3, '/uploads/produtos/3/canecaGalaxiaGamer.jpeg', 3, NOW(), NOW()),
(4, '/uploads/produtos/4/canecaFraseDoDia.jpeg', 4, NOW(), NOW()),
(5, '/uploads/produtos/5/canecaIdentidadeVisual.jpeg', 5, NOW(), NOW()),
(6, '/uploads/produtos/6/canecaMelhorMae.jpeg', 6, NOW(), NOW()),
(7, '/uploads/produtos/7/canecaMelhorPai.jpeg', 7, NOW(), NOW()),
(8, '/uploads/produtos/8/canecaNatal.jpeg', 8, NOW(), NOW()),
(9, '/uploads/produtos/9/canecaProfessoraNota10.jpeg', 9, NOW(), NOW()),
(10, '/uploads/produtos/10/canecaPetFavorito.jpeg', 10, NOW(), NOW()),
(11, '/uploads/produtos/11/canecaMagicaGeek.jpeg', 11, NOW(), NOW()),
(12, '/uploads/produtos/12/camisetaGamerEdition.jpeg', 12, NOW(), NOW()),
(13, '/uploads/produtos/13/camiseta_frase_positiva.jpeg', 13, NOW(), NOW()),
(14, '/uploads/produtos/14/CamisetaPremiumCorporativa.png', 14, NOW(), NOW()),
(15, '/uploads/produtos/15/CadernoA5.webp', 15, NOW(), NOW()),
(16, '/uploads/produtos/16/CadernoUniversitario.webp', 16, NOW(), NOW()),
(17, '/uploads/produtos/17/SqueezeEmpresa.webp', 17, NOW(), NOW()),
(18, '/uploads/produtos/18/MousepadNerd.jfif', 18, NOW(), NOW()),
(19, '/uploads/produtos/19/AlmofadaPetLovers.webp', 19, NOW(), NOW()),
(20, '/uploads/produtos/20/ecobag.png', 20, NOW(), NOW()),
(21, '/uploads/produtos/21/caneca_breaking_bad.jpeg', 21, NOW(), NOW());

INSERT IGNORE INTO pedido (id, origem, observacoes, preco_total, peso_total, data_limite, ativo, created_at, updated_at, cliente_id, usuario_id) VALUES
(1, 'Instagram', 'Pedido com embalagem especial para presente', 29.9, 0.32, DATE_SUB(NOW(), INTERVAL 3 DAY), 1, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), 1, 22),
(2, 'Mercado Livre', 'Personalização com frase enviada por WhatsApp', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 9 DAY), 1, DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY), 2, 22),
(3, 'Shopee', 'Pedido com tema Breaking Bad personalizado', 99.8, 1.09, DATE_SUB(NOW(), INTERVAL 15 DAY), 1, DATE_SUB(NOW(), INTERVAL 21 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY), 3, 4),
(4, 'WhatsApp', 'Entrega combinada via motoboy', 74.8, 0.66, DATE_SUB(NOW(), INTERVAL 21 DAY), 1, DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY), 4, 22),
(5, 'Instagram', 'Pedido com tema Breaking Bad personalizado', 144.7, 0.9, DATE_SUB(NOW(), INTERVAL 27 DAY), 1, DATE_SUB(NOW(), INTERVAL 35 DAY), DATE_SUB(NOW(), INTERVAL 35 DAY), 5, 22),
(6, 'Site', 'Pedido com tema Breaking Bad personalizado', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 33 DAY), 1, DATE_SUB(NOW(), INTERVAL 42 DAY), NOW(), 6, 4),
(7, 'Mercado Livre', 'Produto será utilizado em casamento', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 39 DAY), 1, DATE_SUB(NOW(), INTERVAL 49 DAY), DATE_SUB(NOW(), INTERVAL 49 DAY), 7, 22),
(8, 'Shopee', 'Pedido liberado após confirmação de pagamento', 159.6, 0.92, DATE_SUB(NOW(), INTERVAL 53 DAY), 1, DATE_SUB(NOW(), INTERVAL 56 DAY), DATE_SUB(NOW(), INTERVAL 56 DAY), 8, 2),
(9, 'Instagram', 'Pedido com tema Breaking Bad personalizado', 84.8, 0.79, DATE_SUB(NOW(), INTERVAL 59 DAY), 1, DATE_SUB(NOW(), INTERVAL 63 DAY), DATE_SUB(NOW(), INTERVAL 63 DAY), 9, 4),
(10, 'WhatsApp', 'Pedido com tema Breaking Bad personalizado', 164.6, 1.34, DATE_SUB(NOW(), INTERVAL 65 DAY), 1, DATE_SUB(NOW(), INTERVAL 70 DAY), DATE_SUB(NOW(), INTERVAL 70 DAY), 10, 2),
(11, 'Site', 'Produto destinado a presente de formatura', 29.9, 0.32, DATE_SUB(NOW(), INTERVAL 71 DAY), 1, DATE_SUB(NOW(), INTERVAL 77 DAY), DATE_SUB(NOW(), INTERVAL 77 DAY), 11, 2),
(12, 'Instagram', 'Pedido com tema Breaking Bad personalizado', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 77 DAY), 1, DATE_SUB(NOW(), INTERVAL 84 DAY), NOW(), 12, 4),
(13, 'Site', 'Cliente pediu alteração nas cores da arte', 72.8, 0.49, DATE_ADD(NOW(), INTERVAL 8 DAY), 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 13, 2),
(14, 'Shopee', 'Produto será utilizado em casamento', 89.8, 0.68, DATE_ADD(NOW(), INTERVAL 2 DAY), 1, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), 14, 2),
(15, 'Mercado Livre', 'Pedido com tema Breaking Bad personalizado', 134.6, 1.3, DATE_SUB(NOW(), INTERVAL 4 DAY), 1, DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY), 15, 4),
(16, 'Instagram', 'Cliente aprovou mockup sem alterações', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 18 DAY), 1, DATE_SUB(NOW(), INTERVAL 21 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY), 16, 2),
(17, 'Mercado Livre', 'Pedido com embalagem especial para presente', 29.9, 0.32, DATE_SUB(NOW(), INTERVAL 24 DAY), 1, DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY), 17, 2),
(18, 'Instagram', 'Pedido com tema Breaking Bad personalizado', 124.7, 0.7, DATE_SUB(NOW(), INTERVAL 30 DAY), 1, DATE_SUB(NOW(), INTERVAL 35 DAY), NOW(), 18, 4),
(19, 'Shopee', 'Cliente pediu urgência na produção', 89.8, 0.68, DATE_SUB(NOW(), INTERVAL 36 DAY), 1, DATE_SUB(NOW(), INTERVAL 42 DAY), DATE_SUB(NOW(), INTERVAL 42 DAY), 19, 2),
(20, 'WhatsApp', 'Pedido com tema Breaking Bad personalizado', 119.7, 1, DATE_SUB(NOW(), INTERVAL 42 DAY), 1, DATE_SUB(NOW(), INTERVAL 49 DAY), DATE_SUB(NOW(), INTERVAL 49 DAY), 20, 2),
(21, 'Mercado Livre', 'Arte revisada pelo setor de criação', 34.9, 0.12, DATE_SUB(NOW(), INTERVAL 48 DAY), 1, DATE_SUB(NOW(), INTERVAL 56 DAY), DATE_SUB(NOW(), INTERVAL 56 DAY), 21, 4),
(22, 'Shopee', 'Cliente pediu alteração nas cores da arte', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 54 DAY), 1, DATE_SUB(NOW(), INTERVAL 63 DAY), DATE_SUB(NOW(), INTERVAL 63 DAY), 22, 2),
(23, 'Site', 'Cliente pediu urgência na produção', 149.6, 1.32, DATE_SUB(NOW(), INTERVAL 60 DAY), 1, DATE_SUB(NOW(), INTERVAL 70 DAY), DATE_SUB(NOW(), INTERVAL 70 DAY), 23, 2),
(24, 'Instagram', 'Pedido com tema Breaking Bad personalizado', 119.7, 1, DATE_SUB(NOW(), INTERVAL 74 DAY), 1, DATE_SUB(NOW(), INTERVAL 77 DAY), NOW(), 24, 4),
(25, 'WhatsApp', 'Pedido com tema Breaking Bad personalizado', 169.6, 1.04, DATE_SUB(NOW(), INTERVAL 80 DAY), 1, DATE_SUB(NOW(), INTERVAL 84 DAY), DATE_SUB(NOW(), INTERVAL 84 DAY), 25, 2),
(26, 'Site', 'Pedido com embalagem especial para presente', 89.8, 0.68, DATE_ADD(NOW(), INTERVAL 5 DAY), 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 26, 2),
(27, 'Mercado Livre', 'Pedido para evento interno da empresa', 59.8, 0.64, DATE_SUB(NOW(), INTERVAL 1 DAY), 1, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), 27, 4),
(28, 'Site', 'Personalização com frase enviada por WhatsApp', 104.8, 0.54, DATE_SUB(NOW(), INTERVAL 7 DAY), 1, DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY), 28, 2),
(29, 'Mercado Livre', 'Pedido liberado após confirmação de pagamento', 74.8, 0.66, DATE_SUB(NOW(), INTERVAL 13 DAY), 1, DATE_SUB(NOW(), INTERVAL 21 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY), 29, 2),
(30, 'Site', 'Pedido com tema Breaking Bad personalizado', 119.7, 1, DATE_SUB(NOW(), INTERVAL 19 DAY), 1, DATE_SUB(NOW(), INTERVAL 28 DAY), NOW(), 30, 4),
(31, 'WhatsApp', 'Cliente aprovou mockup sem alterações', 89.8, 0.68, DATE_SUB(NOW(), INTERVAL 25 DAY), 1, DATE_SUB(NOW(), INTERVAL 35 DAY), DATE_SUB(NOW(), INTERVAL 35 DAY), 1, 2),
(32, 'Site', 'Cliente pediu alteração no tamanho da estampa', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 39 DAY), 1, DATE_SUB(NOW(), INTERVAL 42 DAY), DATE_SUB(NOW(), INTERVAL 42 DAY), 2, 2),
(33, 'Mercado Livre', 'Pedido com tema Breaking Bad personalizado', 119.7, 1, DATE_SUB(NOW(), INTERVAL 45 DAY), 1, DATE_SUB(NOW(), INTERVAL 49 DAY), DATE_SUB(NOW(), INTERVAL 49 DAY), 3, 4),
(34, 'Shopee', 'Pedido com embalagem especial para presente', 74.8, 0.66, DATE_SUB(NOW(), INTERVAL 51 DAY), 1, DATE_SUB(NOW(), INTERVAL 56 DAY), DATE_SUB(NOW(), INTERVAL 56 DAY), 4, 2),
(35, 'Instagram', 'Pedido com tema Breaking Bad personalizado', 184.6, 2.16, DATE_SUB(NOW(), INTERVAL 57 DAY), 1, DATE_SUB(NOW(), INTERVAL 63 DAY), DATE_SUB(NOW(), INTERVAL 63 DAY), 5, 2),
(36, 'Mercado Livre', 'Pedido com tema Breaking Bad personalizado', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 63 DAY), 1, DATE_SUB(NOW(), INTERVAL 70 DAY), NOW(), 6, 4),
(37, 'WhatsApp', 'Entrega programada para feira escolar', 39.9, 0.18, DATE_SUB(NOW(), INTERVAL 69 DAY), 1, DATE_SUB(NOW(), INTERVAL 77 DAY), DATE_SUB(NOW(), INTERVAL 77 DAY), 7, 2),
(38, 'Shopee', 'Cliente pediu acabamento fosco', 114.8, 0.58, DATE_SUB(NOW(), INTERVAL 75 DAY), 1, DATE_SUB(NOW(), INTERVAL 84 DAY), DATE_SUB(NOW(), INTERVAL 84 DAY), 8, 2),
(39, 'Mercado Livre', 'Pedido com tema Breaking Bad personalizado', 72.8, 0.49, DATE_ADD(NOW(), INTERVAL 10 DAY), 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 9, 4),
(40, 'Instagram', 'Pedido com tema Breaking Bad personalizado', 119.7, 1, DATE_SUB(NOW(), INTERVAL 4 DAY), 1, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), 10, 2),
(41, 'Shopee', 'Cliente solicitou embalagem para presente', 59.9, 0.2, DATE_SUB(NOW(), INTERVAL 10 DAY), 1, DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY), 11, 2),
(42, 'Site', 'Pedido com tema Breaking Bad personalizado', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 16 DAY), 1, DATE_SUB(NOW(), INTERVAL 21 DAY), NOW(), 12, 4),
(43, 'Instagram', 'Cliente pediu alteração no tamanho da estampa', 84.8, 0.52, DATE_SUB(NOW(), INTERVAL 22 DAY), 1, DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY), 13, 2),
(44, 'Mercado Livre', 'Personalização com frase enviada por WhatsApp', 89.8, 0.68, DATE_SUB(NOW(), INTERVAL 28 DAY), 1, DATE_SUB(NOW(), INTERVAL 35 DAY), DATE_SUB(NOW(), INTERVAL 35 DAY), 14, 2),
(45, 'WhatsApp', 'Pedido com tema Breaking Bad personalizado', 164.6, 1.34, DATE_SUB(NOW(), INTERVAL 34 DAY), 1, DATE_SUB(NOW(), INTERVAL 42 DAY), DATE_SUB(NOW(), INTERVAL 42 DAY), 15, 4),
(46, 'Instagram', 'Pedido com personalização minimalista', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 40 DAY), 1, DATE_SUB(NOW(), INTERVAL 49 DAY), DATE_SUB(NOW(), INTERVAL 49 DAY), 16, 2),
(47, 'WhatsApp', 'Pedido com tema pet e nome personalizado', 49.9, 0.5, DATE_SUB(NOW(), INTERVAL 46 DAY), 1, DATE_SUB(NOW(), INTERVAL 56 DAY), DATE_SUB(NOW(), INTERVAL 56 DAY), 17, 2),
(48, 'Shopee', 'Pedido com tema Breaking Bad personalizado', 104.7, 0.98, DATE_SUB(NOW(), INTERVAL 60 DAY), 1, DATE_SUB(NOW(), INTERVAL 63 DAY), NOW(), 18, 4),
(49, 'WhatsApp', 'Arte revisada pelo setor de criação', 119.7, 1, DATE_SUB(NOW(), INTERVAL 66 DAY), 1, DATE_SUB(NOW(), INTERVAL 70 DAY), DATE_SUB(NOW(), INTERVAL 70 DAY), 19, 2),
(50, 'Site', 'Pedido com tema Breaking Bad personalizado', 164.6, 1.34, DATE_SUB(NOW(), INTERVAL 72 DAY), 1, DATE_SUB(NOW(), INTERVAL 77 DAY), DATE_SUB(NOW(), INTERVAL 77 DAY), 20, 2),
(51, 'Shopee', 'Cliente pediu ajuste de nome na arte', 29.9, 0.32, DATE_SUB(NOW(), INTERVAL 78 DAY), 1, DATE_SUB(NOW(), INTERVAL 84 DAY), DATE_SUB(NOW(), INTERVAL 84 DAY), 21, 4),
(52, 'Instagram', 'Pedido com personalização minimalista', 44.9, 0.34, DATE_ADD(NOW(), INTERVAL 7 DAY), 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 22, 2),
(53, 'Mercado Livre', 'Entrega para aniversário no fim de semana', 74.8, 0.66, DATE_ADD(NOW(), INTERVAL 1 DAY), 1, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), 23, 2),
(54, 'WhatsApp', 'Pedido com tema Breaking Bad personalizado', 74.8, 0.66, DATE_SUB(NOW(), INTERVAL 5 DAY), 1, DATE_SUB(NOW(), INTERVAL 14 DAY), NOW(), 24, 4),
(55, 'Instagram', 'Pedido com tema Breaking Bad personalizado', 149.6, 1.32, DATE_SUB(NOW(), INTERVAL 11 DAY), 1, DATE_SUB(NOW(), INTERVAL 21 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY), 25, 2),
(56, 'Mercado Livre', 'Pedido para evento interno da empresa', 89.8, 0.68, DATE_SUB(NOW(), INTERVAL 25 DAY), 1, DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY), 26, 2),
(57, 'Instagram', 'Personalização com frase enviada por WhatsApp', 27.9, 0.15, DATE_SUB(NOW(), INTERVAL 31 DAY), 1, DATE_SUB(NOW(), INTERVAL 35 DAY), DATE_SUB(NOW(), INTERVAL 35 DAY), 27, 4),
(58, 'Shopee', 'Produto destinado a presente de formatura', 189.6, 1.68, DATE_SUB(NOW(), INTERVAL 37 DAY), 1, DATE_SUB(NOW(), INTERVAL 42 DAY), DATE_SUB(NOW(), INTERVAL 42 DAY), 28, 2),
(59, 'Instagram', 'Pedido com embalagem especial para presente', 99.8, 1.09, DATE_SUB(NOW(), INTERVAL 43 DAY), 1, DATE_SUB(NOW(), INTERVAL 49 DAY), DATE_SUB(NOW(), INTERVAL 49 DAY), 29, 2),
(60, 'WhatsApp', 'Pedido com tema Breaking Bad personalizado', 189.6, 1.77, DATE_SUB(NOW(), INTERVAL 49 DAY), 1, DATE_SUB(NOW(), INTERVAL 56 DAY), NOW(), 30, 4),
(61, 'Shopee', 'Produto destinado a presente de formatura', 59.8, 0.64, DATE_SUB(NOW(), INTERVAL 55 DAY), 1, DATE_SUB(NOW(), INTERVAL 63 DAY), DATE_SUB(NOW(), INTERVAL 63 DAY), 1, 2),
(62, 'Mercado Livre', 'Cliente pediu ajuste de nome na arte', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 61 DAY), 1, DATE_SUB(NOW(), INTERVAL 70 DAY), DATE_SUB(NOW(), INTERVAL 70 DAY), 2, 2),
(63, 'Shopee', 'Pedido com tema Breaking Bad personalizado', 119.7, 1, DATE_SUB(NOW(), INTERVAL 67 DAY), 1, DATE_SUB(NOW(), INTERVAL 77 DAY), DATE_SUB(NOW(), INTERVAL 77 DAY), 3, 4),
(64, 'Site', 'Pedido com identidade visual da empresa', 104.8, 0.54, DATE_SUB(NOW(), INTERVAL 81 DAY), 1, DATE_SUB(NOW(), INTERVAL 84 DAY), DATE_SUB(NOW(), INTERVAL 84 DAY), 4, 2),
(65, 'Mercado Livre', 'Pedido com tema Breaking Bad personalizado', 144.7, 0.9, DATE_ADD(NOW(), INTERVAL 4 DAY), 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 5, 2),
(66, 'Shopee', 'Pedido com tema Breaking Bad personalizado', 89.8, 0.68, DATE_SUB(NOW(), INTERVAL 2 DAY), 1, DATE_SUB(NOW(), INTERVAL 7 DAY), NOW(), 6, 4),
(67, 'WhatsApp', 'Pedido com embalagem especial para presente', 29.9, 0.32, DATE_SUB(NOW(), INTERVAL 8 DAY), 1, DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY), 7, 2),
(68, 'Mercado Livre', 'Cliente pediu alteração no tamanho da estampa', 89.8, 0.68, DATE_SUB(NOW(), INTERVAL 14 DAY), 1, DATE_SUB(NOW(), INTERVAL 21 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY), 8, 2),
(69, 'WhatsApp', 'Pedido com tema Breaking Bad personalizado', 104.7, 0.98, DATE_SUB(NOW(), INTERVAL 20 DAY), 1, DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY), 9, 4),
(70, 'Site', 'Pedido com tema Breaking Bad personalizado', 169.6, 1.14, DATE_SUB(NOW(), INTERVAL 26 DAY), 1, DATE_SUB(NOW(), INTERVAL 35 DAY), DATE_SUB(NOW(), INTERVAL 35 DAY), 10, 2),
(71, 'Shopee', 'Entrega para aniversário no fim de semana', 39.9, 0.45, DATE_SUB(NOW(), INTERVAL 32 DAY), 1, DATE_SUB(NOW(), INTERVAL 42 DAY), DATE_SUB(NOW(), INTERVAL 42 DAY), 11, 2),
(72, 'Site', 'Pedido com tema Breaking Bad personalizado', 89.8, 0.68, DATE_SUB(NOW(), INTERVAL 46 DAY), 1, DATE_SUB(NOW(), INTERVAL 49 DAY), NOW(), 12, 4),
(73, 'Shopee', 'Cliente pediu alteração nas cores da arte', 114.8, 0.58, DATE_SUB(NOW(), INTERVAL 52 DAY), 1, DATE_SUB(NOW(), INTERVAL 56 DAY), DATE_SUB(NOW(), INTERVAL 56 DAY), 13, 2),
(74, 'Mercado Livre', 'Pedido com identidade visual da empresa', 74.8, 0.66, DATE_SUB(NOW(), INTERVAL 58 DAY), 1, DATE_SUB(NOW(), INTERVAL 63 DAY), DATE_SUB(NOW(), INTERVAL 63 DAY), 14, 2),
(75, 'Instagram', 'Pedido com tema Breaking Bad personalizado', 114.7, 1.11, DATE_SUB(NOW(), INTERVAL 64 DAY), 1, DATE_SUB(NOW(), INTERVAL 70 DAY), DATE_SUB(NOW(), INTERVAL 70 DAY), 15, 4),
(76, 'Mercado Livre', 'Cliente pediu alteração nas cores da arte', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 70 DAY), 1, DATE_SUB(NOW(), INTERVAL 77 DAY), DATE_SUB(NOW(), INTERVAL 77 DAY), 16, 2),
(77, 'Site', 'Pedido para lembrança de evento corporativo', 29.9, 0.32, DATE_SUB(NOW(), INTERVAL 76 DAY), 1, DATE_SUB(NOW(), INTERVAL 84 DAY), DATE_SUB(NOW(), INTERVAL 84 DAY), 17, 2),
(78, 'WhatsApp', 'Pedido com tema Breaking Bad personalizado', 72.8, 0.49, DATE_ADD(NOW(), INTERVAL 9 DAY), 1, DATE_SUB(NOW(), INTERVAL 0 DAY), NOW(), 18, 4),
(79, 'Site', 'Cliente enviou referência de arte pelo Instagram', 74.8, 0.66, DATE_ADD(NOW(), INTERVAL 3 DAY), 1, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), 19, 2),
(80, 'Instagram', 'Pedido com tema Breaking Bad personalizado', 134.7, 1.02, DATE_SUB(NOW(), INTERVAL 11 DAY), 1, DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY), 20, 2),
(81, 'Mercado Livre', 'Pedido com tema pet e nome personalizado', 54.9, 0.75, DATE_SUB(NOW(), INTERVAL 17 DAY), 1, DATE_SUB(NOW(), INTERVAL 21 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY), 21, 4),
(82, 'WhatsApp', 'Cliente enviou referência de arte pelo Instagram', 89.8, 0.68, DATE_SUB(NOW(), INTERVAL 23 DAY), 1, DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY), 22, 2),
(83, 'Mercado Livre', 'Entrega programada para feira escolar', 74.8, 0.66, DATE_SUB(NOW(), INTERVAL 29 DAY), 1, DATE_SUB(NOW(), INTERVAL 35 DAY), DATE_SUB(NOW(), INTERVAL 35 DAY), 23, 2),
(84, 'Instagram', 'Pedido com tema Breaking Bad personalizado', 74.8, 0.66, DATE_SUB(NOW(), INTERVAL 35 DAY), 1, DATE_SUB(NOW(), INTERVAL 42 DAY), NOW(), 24, 4),
(85, 'Mercado Livre', 'Pedido com tema Breaking Bad personalizado', 234.5, 0.98, DATE_SUB(NOW(), INTERVAL 41 DAY), 1, DATE_SUB(NOW(), INTERVAL 49 DAY), DATE_SUB(NOW(), INTERVAL 49 DAY), 25, 2),
(86, 'Site', 'Pedido para lembrança de evento corporativo', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 47 DAY), 1, DATE_SUB(NOW(), INTERVAL 56 DAY), DATE_SUB(NOW(), INTERVAL 56 DAY), 26, 2),
(87, 'WhatsApp', 'Cliente pediu revisão ortográfica da frase', 79.8, 0.9, DATE_SUB(NOW(), INTERVAL 53 DAY), 1, DATE_SUB(NOW(), INTERVAL 63 DAY), DATE_SUB(NOW(), INTERVAL 63 DAY), 27, 4),
(88, 'Instagram', 'Cliente pediu alteração no tamanho da estampa', 229.6, 1.16, DATE_SUB(NOW(), INTERVAL 67 DAY), 1, DATE_SUB(NOW(), INTERVAL 70 DAY), DATE_SUB(NOW(), INTERVAL 70 DAY), 28, 2),
(89, 'Mercado Livre', 'Pedido com tema pet e nome personalizado', 154.7, 1.84, DATE_SUB(NOW(), INTERVAL 73 DAY), 1, DATE_SUB(NOW(), INTERVAL 77 DAY), DATE_SUB(NOW(), INTERVAL 77 DAY), 29, 2),
(90, 'Shopee', 'Pedido com tema Breaking Bad personalizado', 119.7, 1, DATE_SUB(NOW(), INTERVAL 79 DAY), 1, DATE_SUB(NOW(), INTERVAL 84 DAY), NOW(), 30, 4),
(91, 'Site', 'Pedido liberado após confirmação de pagamento', 59.8, 0.64, DATE_ADD(NOW(), INTERVAL 6 DAY), 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 1, 2),
(92, 'Mercado Livre', 'Pedido para lembrança de evento corporativo', 44.9, 0.34, NOW(), 1, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), 2, 2),
(93, 'Site', 'Pedido com tema Breaking Bad personalizado', 134.7, 1.02, DATE_SUB(NOW(), INTERVAL 6 DAY), 1, DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY), 3, 4),
(94, 'Shopee', 'Pedido com personalização minimalista', 119.7, 1, DATE_SUB(NOW(), INTERVAL 12 DAY), 1, DATE_SUB(NOW(), INTERVAL 21 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY), 4, 2),
(95, 'Mercado Livre', 'Pedido com tema Breaking Bad personalizado', 129.7, 1.41, DATE_SUB(NOW(), INTERVAL 18 DAY), 1, DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY), 5, 2),
(96, 'Shopee', 'Pedido com tema Breaking Bad personalizado', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 32 DAY), 1, DATE_SUB(NOW(), INTERVAL 35 DAY), NOW(), 6, 4),
(97, 'Site', 'Cliente pediu revisão ortográfica da frase', 39.9, 0.18, DATE_SUB(NOW(), INTERVAL 38 DAY), 1, DATE_SUB(NOW(), INTERVAL 42 DAY), DATE_SUB(NOW(), INTERVAL 42 DAY), 7, 2),
(98, 'WhatsApp', 'Produto destinado a presente de formatura', 74.8, 0.66, DATE_SUB(NOW(), INTERVAL 44 DAY), 1, DATE_SUB(NOW(), INTERVAL 49 DAY), DATE_SUB(NOW(), INTERVAL 49 DAY), 8, 2),
(99, 'Mercado Livre', 'Pedido com tema Breaking Bad personalizado', 104.7, 0.98, DATE_SUB(NOW(), INTERVAL 50 DAY), 1, DATE_SUB(NOW(), INTERVAL 56 DAY), DATE_SUB(NOW(), INTERVAL 56 DAY), 9, 4),
(100, 'Site', 'Pedido com tema Breaking Bad personalizado', 209.5, 1.68, DATE_SUB(NOW(), INTERVAL 56 DAY), 1, DATE_SUB(NOW(), INTERVAL 63 DAY), DATE_SUB(NOW(), INTERVAL 63 DAY), 10, 2),
(101, 'WhatsApp', 'Pedido com tema pet e nome personalizado', 29.9, 0.32, DATE_SUB(NOW(), INTERVAL 62 DAY), 1, DATE_SUB(NOW(), INTERVAL 70 DAY), DATE_SUB(NOW(), INTERVAL 70 DAY), 11, 2),
(102, 'Instagram', 'Pedido com tema Breaking Bad personalizado', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 68 DAY), 1, DATE_SUB(NOW(), INTERVAL 77 DAY), NOW(), 12, 4),
(103, 'Site', 'Entrega para aniversário no fim de semana', 74.8, 0.66, DATE_SUB(NOW(), INTERVAL 74 DAY), 1, DATE_SUB(NOW(), INTERVAL 84 DAY), DATE_SUB(NOW(), INTERVAL 84 DAY), 13, 2),
(104, 'Instagram', 'Cliente pediu urgência na produção', 104.7, 0.98, DATE_ADD(NOW(), INTERVAL 3 DAY), 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 14, 2),
(105, 'Site', 'Pedido com tema Breaking Bad personalizado', 119.7, 1, DATE_SUB(NOW(), INTERVAL 3 DAY), 1, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), 15, 4),
(106, 'Shopee', 'Entrega para aniversário no fim de semana', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 9 DAY), 1, DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY), 16, 2),
(107, 'WhatsApp', 'Pedido com embalagem especial para presente', 99.8, 1, DATE_SUB(NOW(), INTERVAL 15 DAY), 1, DATE_SUB(NOW(), INTERVAL 21 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY), 17, 2),
(108, 'Instagram', 'Pedido com tema Breaking Bad personalizado', 84.8, 0.52, DATE_SUB(NOW(), INTERVAL 21 DAY), 1, DATE_SUB(NOW(), INTERVAL 28 DAY), NOW(), 18, 4),
(109, 'Mercado Livre', 'Entrega para aniversário no fim de semana', 74.8, 0.66, DATE_SUB(NOW(), INTERVAL 27 DAY), 1, DATE_SUB(NOW(), INTERVAL 35 DAY), DATE_SUB(NOW(), INTERVAL 35 DAY), 19, 2),
(110, 'Site', 'Pedido com tema Breaking Bad personalizado', 134.7, 1.02, DATE_SUB(NOW(), INTERVAL 33 DAY), 1, DATE_SUB(NOW(), INTERVAL 42 DAY), DATE_SUB(NOW(), INTERVAL 42 DAY), 20, 2),
(111, 'WhatsApp', 'Pedido com identidade visual da empresa', 29.9, 0.32, DATE_SUB(NOW(), INTERVAL 39 DAY), 1, DATE_SUB(NOW(), INTERVAL 49 DAY), DATE_SUB(NOW(), INTERVAL 49 DAY), 21, 4),
(112, 'Mercado Livre', 'Pedido liberado após confirmação de pagamento', 44.9, 0.34, DATE_SUB(NOW(), INTERVAL 53 DAY), 1, DATE_SUB(NOW(), INTERVAL 56 DAY), DATE_SUB(NOW(), INTERVAL 56 DAY), 22, 2),
(113, 'Instagram', 'Produto destinado a presente de formatura', 74.8, 0.66, DATE_SUB(NOW(), INTERVAL 59 DAY), 1, DATE_SUB(NOW(), INTERVAL 63 DAY), DATE_SUB(NOW(), INTERVAL 63 DAY), 23, 2),
(114, 'Mercado Livre', 'Pedido com tema Breaking Bad personalizado', 84.8, 0.79, DATE_SUB(NOW(), INTERVAL 65 DAY), 1, DATE_SUB(NOW(), INTERVAL 70 DAY), NOW(), 24, 4),
(115, 'Instagram', 'Pedido com tema Breaking Bad personalizado', 114.7, 1.11, DATE_SUB(NOW(), INTERVAL 71 DAY), 1, DATE_SUB(NOW(), INTERVAL 77 DAY), DATE_SUB(NOW(), INTERVAL 77 DAY), 25, 2),
(116, 'Mercado Livre', 'Cliente pediu ajuste de nome na arte', 89.8, 0.68, DATE_SUB(NOW(), INTERVAL 77 DAY), 1, DATE_SUB(NOW(), INTERVAL 84 DAY), DATE_SUB(NOW(), INTERVAL 84 DAY), 26, 2),
(117, 'Instagram', 'Pedido com tema Breaking Bad personalizado', 89.8, 0.68, DATE_ADD(NOW(), INTERVAL 8 DAY), 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 27, 4),
(118, 'Site', 'Cliente pediu acabamento fosco', 74.8, 0.66, DATE_ADD(NOW(), INTERVAL 2 DAY), 1, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), 28, 2),
(119, 'Mercado Livre', 'Cliente pediu revisão ortográfica da frase', 104.7, 0.98, DATE_SUB(NOW(), INTERVAL 4 DAY), 1, DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY), 29, 2),
(120, 'WhatsApp', 'Pedido com tema Breaking Bad personalizado', 119.7, 1, DATE_SUB(NOW(), INTERVAL 18 DAY), 1, DATE_SUB(NOW(), INTERVAL 21 DAY), NOW(), 30, 4);

INSERT IGNORE INTO pedido_status_pedido (id, status_atual, created_at, updated_at, pedido_id, status_id) VALUES
(1, 1, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 1, 4),
(2, 1, DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), 2, 4),
(3, 1, DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 13 DAY), 3, 4),
(4, 1, DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 19 DAY), 4, 4),
(5, 1, DATE_SUB(NOW(), INTERVAL 31 DAY), DATE_SUB(NOW(), INTERVAL 30 DAY), 5, 4),
(6, 1, DATE_SUB(NOW(), INTERVAL 37 DAY), DATE_SUB(NOW(), INTERVAL 36 DAY), 6, 4),
(7, 1, DATE_SUB(NOW(), INTERVAL 43 DAY), DATE_SUB(NOW(), INTERVAL 42 DAY), 7, 4),
(8, 1, DATE_SUB(NOW(), INTERVAL 49 DAY), DATE_SUB(NOW(), INTERVAL 48 DAY), 8, 4),
(9, 1, DATE_SUB(NOW(), INTERVAL 55 DAY), DATE_SUB(NOW(), INTERVAL 54 DAY), 9, 4),
(10, 1, DATE_SUB(NOW(), INTERVAL 66 DAY), DATE_SUB(NOW(), INTERVAL 65 DAY), 10, 4),
(11, 1, DATE_SUB(NOW(), INTERVAL 72 DAY), DATE_SUB(NOW(), INTERVAL 71 DAY), 11, 4),
(12, 1, DATE_SUB(NOW(), INTERVAL 78 DAY), DATE_SUB(NOW(), INTERVAL 77 DAY), 12, 4),
(13, 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 13, 4),
(14, 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 14, 4),
(15, 1, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY), 15, 4),
(16, 1, DATE_SUB(NOW(), INTERVAL 16 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY), 16, 4),
(17, 1, DATE_SUB(NOW(), INTERVAL 22 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY), 17, 4),
(18, 1, DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_SUB(NOW(), INTERVAL 27 DAY), 18, 4),
(19, 1, DATE_SUB(NOW(), INTERVAL 34 DAY), DATE_SUB(NOW(), INTERVAL 33 DAY), 19, 4),
(20, 1, DATE_SUB(NOW(), INTERVAL 45 DAY), DATE_SUB(NOW(), INTERVAL 44 DAY), 20, 4),
(21, 1, DATE_SUB(NOW(), INTERVAL 51 DAY), DATE_SUB(NOW(), INTERVAL 50 DAY), 21, 4),
(22, 1, DATE_SUB(NOW(), INTERVAL 57 DAY), DATE_SUB(NOW(), INTERVAL 56 DAY), 22, 4),
(23, 1, DATE_SUB(NOW(), INTERVAL 63 DAY), DATE_SUB(NOW(), INTERVAL 62 DAY), 23, 4),
(24, 1, DATE_SUB(NOW(), INTERVAL 69 DAY), DATE_SUB(NOW(), INTERVAL 68 DAY), 24, 4),
(25, 1, DATE_SUB(NOW(), INTERVAL 80 DAY), DATE_SUB(NOW(), INTERVAL 79 DAY), 25, 4),
(26, 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 26, 4),
(27, 1, DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 27, 4),
(28, 1, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY), 28, 4),
(29, 1, DATE_SUB(NOW(), INTERVAL 13 DAY), DATE_SUB(NOW(), INTERVAL 12 DAY), 29, 4),
(30, 1, DATE_SUB(NOW(), INTERVAL 24 DAY), DATE_SUB(NOW(), INTERVAL 23 DAY), 30, 4),
(31, 1, DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 29 DAY), 31, 4),
(32, 0, DATE_SUB(NOW(), INTERVAL 36 DAY), DATE_SUB(NOW(), INTERVAL 35 DAY), 32, 4),
(33, 1, DATE_SUB(NOW(), INTERVAL 39 DAY), DATE_SUB(NOW(), INTERVAL 38 DAY), 32, 4),
(34, 1, DATE_SUB(NOW(), INTERVAL 42 DAY), DATE_SUB(NOW(), INTERVAL 41 DAY), 33, 4),
(35, 1, DATE_SUB(NOW(), INTERVAL 48 DAY), DATE_SUB(NOW(), INTERVAL 47 DAY), 34, 4),
(36, 1, DATE_SUB(NOW(), INTERVAL 59 DAY), DATE_SUB(NOW(), INTERVAL 58 DAY), 35, 4),
(37, 1, DATE_SUB(NOW(), INTERVAL 65 DAY), DATE_SUB(NOW(), INTERVAL 64 DAY), 36, 4),
(38, 1, DATE_SUB(NOW(), INTERVAL 71 DAY), DATE_SUB(NOW(), INTERVAL 70 DAY), 37, 4),
(39, 1, DATE_SUB(NOW(), INTERVAL 77 DAY), DATE_SUB(NOW(), INTERVAL 76 DAY), 38, 4),
(40, 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 39, 4),
(41, 1, DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), 40, 4),
(42, 0, DATE_SUB(NOW(), INTERVAL 9 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY), 41, 4),
(43, 1, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY), 41, 4),
(44, 1, DATE_SUB(NOW(), INTERVAL 16 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY), 42, 4),
(45, 1, DATE_SUB(NOW(), INTERVAL 22 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY), 43, 4),
(46, 1, DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_SUB(NOW(), INTERVAL 27 DAY), 44, 4),
(47, 1, DATE_SUB(NOW(), INTERVAL 34 DAY), DATE_SUB(NOW(), INTERVAL 33 DAY), 45, 4),
(48, 1, DATE_SUB(NOW(), INTERVAL 40 DAY), DATE_SUB(NOW(), INTERVAL 39 DAY), 46, 4),
(49, 1, DATE_SUB(NOW(), INTERVAL 46 DAY), DATE_SUB(NOW(), INTERVAL 45 DAY), 47, 4),
(50, 1, DATE_SUB(NOW(), INTERVAL 60 DAY), DATE_SUB(NOW(), INTERVAL 59 DAY), 48, 4),
(51, 1, DATE_SUB(NOW(), INTERVAL 66 DAY), DATE_SUB(NOW(), INTERVAL 65 DAY), 49, 4),
(52, 1, DATE_SUB(NOW(), INTERVAL 72 DAY), DATE_SUB(NOW(), INTERVAL 71 DAY), 50, 4),
(53, 1, DATE_SUB(NOW(), INTERVAL 78 DAY), DATE_SUB(NOW(), INTERVAL 77 DAY), 51, 4),
(54, 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 52, 3),
(55, 1, DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 53, 3),
(56, 1, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 4 DAY), 54, 4),
(57, 1, DATE_SUB(NOW(), INTERVAL 11 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), 55, 4),
(58, 1, DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 24 DAY), 56, 4),
(59, 1, DATE_SUB(NOW(), INTERVAL 31 DAY), DATE_SUB(NOW(), INTERVAL 30 DAY), 57, 4),
(60, 1, DATE_SUB(NOW(), INTERVAL 37 DAY), DATE_SUB(NOW(), INTERVAL 36 DAY), 58, 4),
(61, 0, DATE_SUB(NOW(), INTERVAL 41 DAY), DATE_SUB(NOW(), INTERVAL 40 DAY), 59, 4),
(62, 1, DATE_SUB(NOW(), INTERVAL 43 DAY), DATE_SUB(NOW(), INTERVAL 42 DAY), 59, 4),
(63, 1, DATE_SUB(NOW(), INTERVAL 49 DAY), DATE_SUB(NOW(), INTERVAL 48 DAY), 60, 4),
(64, 1, DATE_SUB(NOW(), INTERVAL 55 DAY), DATE_SUB(NOW(), INTERVAL 54 DAY), 61, 4),
(65, 1, DATE_SUB(NOW(), INTERVAL 61 DAY), DATE_SUB(NOW(), INTERVAL 60 DAY), 62, 4),
(66, 0, DATE_SUB(NOW(), INTERVAL 70 DAY), DATE_SUB(NOW(), INTERVAL 69 DAY), 63, 4),
(67, 1, DATE_SUB(NOW(), INTERVAL 67 DAY), DATE_SUB(NOW(), INTERVAL 66 DAY), 63, 4),
(68, 1, DATE_SUB(NOW(), INTERVAL 81 DAY), DATE_SUB(NOW(), INTERVAL 80 DAY), 64, 4),
(69, 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 65, 3),
(70, 1, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 66, 4),
(71, 1, DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), 67, 4),
(72, 1, DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 13 DAY), 68, 4),
(73, 1, DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 19 DAY), 69, 4),
(74, 1, DATE_SUB(NOW(), INTERVAL 26 DAY), DATE_SUB(NOW(), INTERVAL 25 DAY), 70, 4),
(75, 1, DATE_SUB(NOW(), INTERVAL 32 DAY), DATE_SUB(NOW(), INTERVAL 31 DAY), 71, 4),
(76, 1, DATE_SUB(NOW(), INTERVAL 46 DAY), DATE_SUB(NOW(), INTERVAL 45 DAY), 72, 4),
(77, 1, DATE_SUB(NOW(), INTERVAL 52 DAY), DATE_SUB(NOW(), INTERVAL 51 DAY), 73, 4),
(78, 1, DATE_SUB(NOW(), INTERVAL 58 DAY), DATE_SUB(NOW(), INTERVAL 57 DAY), 74, 4),
(79, 1, DATE_SUB(NOW(), INTERVAL 64 DAY), DATE_SUB(NOW(), INTERVAL 63 DAY), 75, 4),
(80, 1, DATE_SUB(NOW(), INTERVAL 70 DAY), DATE_SUB(NOW(), INTERVAL 69 DAY), 76, 4),
(81, 1, DATE_SUB(NOW(), INTERVAL 76 DAY), DATE_SUB(NOW(), INTERVAL 75 DAY), 77, 4),
(82, 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 78, 2),
(83, 1, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY), 79, 2),
(84, 1, DATE_SUB(NOW(), INTERVAL 11 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), 80, 4),
(85, 0, DATE_SUB(NOW(), INTERVAL 16 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY), 81, 4),
(86, 1, DATE_SUB(NOW(), INTERVAL 17 DAY), DATE_SUB(NOW(), INTERVAL 16 DAY), 81, 4),
(87, 1, DATE_SUB(NOW(), INTERVAL 23 DAY), DATE_SUB(NOW(), INTERVAL 22 DAY), 82, 4),
(88, 1, DATE_SUB(NOW(), INTERVAL 29 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY), 83, 4),
(89, 1, DATE_SUB(NOW(), INTERVAL 35 DAY), DATE_SUB(NOW(), INTERVAL 34 DAY), 84, 4),
(90, 1, DATE_SUB(NOW(), INTERVAL 41 DAY), DATE_SUB(NOW(), INTERVAL 40 DAY), 85, 4),
(91, 0, DATE_SUB(NOW(), INTERVAL 51 DAY), DATE_SUB(NOW(), INTERVAL 50 DAY), 86, 4),
(92, 1, DATE_SUB(NOW(), INTERVAL 47 DAY), DATE_SUB(NOW(), INTERVAL 46 DAY), 86, 4),
(93, 1, DATE_SUB(NOW(), INTERVAL 53 DAY), DATE_SUB(NOW(), INTERVAL 52 DAY), 87, 4),
(94, 1, DATE_SUB(NOW(), INTERVAL 67 DAY), DATE_SUB(NOW(), INTERVAL 66 DAY), 88, 4),
(95, 1, DATE_SUB(NOW(), INTERVAL 73 DAY), DATE_SUB(NOW(), INTERVAL 72 DAY), 89, 4),
(96, 1, DATE_SUB(NOW(), INTERVAL 79 DAY), DATE_SUB(NOW(), INTERVAL 78 DAY), 90, 4),
(97, 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 91, 2),
(98, 1, DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), 92, 2),
(99, 1, DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY), 93, 4),
(100, 1, DATE_SUB(NOW(), INTERVAL 12 DAY), DATE_SUB(NOW(), INTERVAL 11 DAY), 94, 4),
(101, 1, DATE_SUB(NOW(), INTERVAL 18 DAY), DATE_SUB(NOW(), INTERVAL 17 DAY), 95, 4),
(102, 1, DATE_SUB(NOW(), INTERVAL 32 DAY), DATE_SUB(NOW(), INTERVAL 31 DAY), 96, 4),
(103, 1, DATE_SUB(NOW(), INTERVAL 38 DAY), DATE_SUB(NOW(), INTERVAL 37 DAY), 97, 4),
(104, 1, DATE_SUB(NOW(), INTERVAL 44 DAY), DATE_SUB(NOW(), INTERVAL 43 DAY), 98, 4),
(105, 1, DATE_SUB(NOW(), INTERVAL 50 DAY), DATE_SUB(NOW(), INTERVAL 49 DAY), 99, 4),
(106, 1, DATE_SUB(NOW(), INTERVAL 56 DAY), DATE_SUB(NOW(), INTERVAL 55 DAY), 100, 4),
(107, 1, DATE_SUB(NOW(), INTERVAL 62 DAY), DATE_SUB(NOW(), INTERVAL 61 DAY), 101, 4),
(108, 1, DATE_SUB(NOW(), INTERVAL 68 DAY), DATE_SUB(NOW(), INTERVAL 67 DAY), 102, 4),
(109, 1, DATE_SUB(NOW(), INTERVAL 74 DAY), DATE_SUB(NOW(), INTERVAL 73 DAY), 103, 4),
(110, 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 104, 2),
(111, 1, DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), 105, 4),
(112, 1, DATE_SUB(NOW(), INTERVAL 9 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY), 106, 4),
(113, 1, DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY), 107, 4),
(114, 1, DATE_SUB(NOW(), INTERVAL 21 DAY), DATE_SUB(NOW(), INTERVAL 20 DAY), 108, 4),
(115, 1, DATE_SUB(NOW(), INTERVAL 27 DAY), DATE_SUB(NOW(), INTERVAL 26 DAY), 109, 4),
(116, 1, DATE_SUB(NOW(), INTERVAL 33 DAY), DATE_SUB(NOW(), INTERVAL 32 DAY), 110, 4),
(117, 1, DATE_SUB(NOW(), INTERVAL 39 DAY), DATE_SUB(NOW(), INTERVAL 38 DAY), 111, 4),
(118, 1, DATE_SUB(NOW(), INTERVAL 53 DAY), DATE_SUB(NOW(), INTERVAL 52 DAY), 112, 4),
(119, 1, DATE_SUB(NOW(), INTERVAL 59 DAY), DATE_SUB(NOW(), INTERVAL 58 DAY), 113, 4),
(120, 1, DATE_SUB(NOW(), INTERVAL 65 DAY), DATE_SUB(NOW(), INTERVAL 64 DAY), 114, 4),
(121, 1, DATE_SUB(NOW(), INTERVAL 71 DAY), DATE_SUB(NOW(), INTERVAL 70 DAY), 115, 4),
(122, 1, DATE_SUB(NOW(), INTERVAL 77 DAY), DATE_SUB(NOW(), INTERVAL 76 DAY), 116, 4),
(123, 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 117, 1),
(124, 1, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY), 118, 1),
(125, 1, DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY), 119, 4),
(126, 1, DATE_SUB(NOW(), INTERVAL 18 DAY), DATE_SUB(NOW(), INTERVAL 17 DAY), 120, 4);

INSERT IGNORE INTO produto_pedido (id, qtd_produto, preco_unitario, preco_total, peso_unitario, peso_total, created_at, updated_at, pedido_id, produto_id) VALUES
(1, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 119 DAY), DATE_SUB(NOW(), INTERVAL 119 DAY), 1, 13),
(2, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 118 DAY), DATE_SUB(NOW(), INTERVAL 118 DAY), 2, 22),
(3, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 117 DAY), DATE_SUB(NOW(), INTERVAL 117 DAY), 3, 2),
(4, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 117 DAY), DATE_SUB(NOW(), INTERVAL 117 DAY), 3, 15),
(5, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 116 DAY), DATE_SUB(NOW(), INTERVAL 116 DAY), 4, 12),
(6, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 116 DAY), DATE_SUB(NOW(), INTERVAL 116 DAY), 4, 10),
(7, 1, 49.90, 49.90, 0.50, 0.50, DATE_SUB(NOW(), INTERVAL 115 DAY), DATE_SUB(NOW(), INTERVAL 115 DAY), 5, 19),
(8, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 115 DAY), DATE_SUB(NOW(), INTERVAL 115 DAY), 5, 6),
(9, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 115 DAY), DATE_SUB(NOW(), INTERVAL 115 DAY), 5, 7),
(10, 1, 54.90, 54.90, 0.75, 0.75, DATE_SUB(NOW(), INTERVAL 114 DAY), DATE_SUB(NOW(), INTERVAL 114 DAY), 6, 16),
(11, 1, 27.90, 27.90, 0.15, 0.15, DATE_SUB(NOW(), INTERVAL 113 DAY), DATE_SUB(NOW(), INTERVAL 113 DAY), 7, 18),
(12, 2, 59.90, 119.80, 0.20, 0.40, DATE_SUB(NOW(), INTERVAL 112 DAY), DATE_SUB(NOW(), INTERVAL 112 DAY), 8, 21),
(13, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 112 DAY), DATE_SUB(NOW(), INTERVAL 112 DAY), 8, 3),
(14, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 111 DAY), DATE_SUB(NOW(), INTERVAL 111 DAY), 9, 12),
(15, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 111 DAY), DATE_SUB(NOW(), INTERVAL 111 DAY), 9, 8),
(16, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 110 DAY), DATE_SUB(NOW(), INTERVAL 110 DAY), 10, 9),
(17, 1, 69.90, 69.90, 0.24, 0.24, DATE_SUB(NOW(), INTERVAL 110 DAY), DATE_SUB(NOW(), INTERVAL 110 DAY), 10, 14),
(18, 2, 59.90, 119.80, 0.20, 0.40, DATE_SUB(NOW(), INTERVAL 110 DAY), DATE_SUB(NOW(), INTERVAL 110 DAY), 10, 21),
(19, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 109 DAY), DATE_SUB(NOW(), INTERVAL 109 DAY), 11, 9),
(20, 1, 27.90, 27.90, 0.15, 0.15, DATE_SUB(NOW(), INTERVAL 108 DAY), DATE_SUB(NOW(), INTERVAL 108 DAY), 12, 18),
(21, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 107 DAY), DATE_SUB(NOW(), INTERVAL 107 DAY), 13, 5),
(22, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 107 DAY), DATE_SUB(NOW(), INTERVAL 107 DAY), 13, 10),
(23, 1, 39.90, 39.90, 0.18, 0.18, DATE_SUB(NOW(), INTERVAL 106 DAY), DATE_SUB(NOW(), INTERVAL 106 DAY), 14, 17),
(24, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 106 DAY), DATE_SUB(NOW(), INTERVAL 106 DAY), 14, 22),
(25, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 105 DAY), DATE_SUB(NOW(), INTERVAL 105 DAY), 15, 15),
(26, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 105 DAY), DATE_SUB(NOW(), INTERVAL 105 DAY), 15, 2),
(27, 2, 44.90, 89.80, 0.34, 0.68, DATE_SUB(NOW(), INTERVAL 105 DAY), DATE_SUB(NOW(), INTERVAL 105 DAY), 15, 11),
(28, 1, 49.90, 49.90, 0.50, 0.50, DATE_SUB(NOW(), INTERVAL 104 DAY), DATE_SUB(NOW(), INTERVAL 104 DAY), 16, 19),
(29, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 103 DAY), DATE_SUB(NOW(), INTERVAL 103 DAY), 17, 4),
(30, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 102 DAY), DATE_SUB(NOW(), INTERVAL 102 DAY), 18, 3),
(31, 2, 59.90, 119.80, 0.20, 0.40, DATE_SUB(NOW(), INTERVAL 102 DAY), DATE_SUB(NOW(), INTERVAL 102 DAY), 18, 21),
(32, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 101 DAY), DATE_SUB(NOW(), INTERVAL 101 DAY), 19, 8),
(33, 1, 39.90, 39.90, 0.18, 0.18, DATE_SUB(NOW(), INTERVAL 101 DAY), DATE_SUB(NOW(), INTERVAL 101 DAY), 19, 17),
(34, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 100 DAY), DATE_SUB(NOW(), INTERVAL 100 DAY), 20, 1),
(35, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 100 DAY), DATE_SUB(NOW(), INTERVAL 100 DAY), 20, 13),
(36, 1, 44.90, 44.90, 0.34, 0.34, DATE_SUB(NOW(), INTERVAL 100 DAY), DATE_SUB(NOW(), INTERVAL 100 DAY), 20, 11),
(37, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 99 DAY), DATE_SUB(NOW(), INTERVAL 99 DAY), 21, 5),
(38, 1, 69.90, 69.90, 0.24, 0.24, DATE_SUB(NOW(), INTERVAL 98 DAY), DATE_SUB(NOW(), INTERVAL 98 DAY), 22, 14),
(39, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 97 DAY), DATE_SUB(NOW(), INTERVAL 97 DAY), 23, 1),
(40, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 97 DAY), DATE_SUB(NOW(), INTERVAL 97 DAY), 23, 2),
(41, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 96 DAY), DATE_SUB(NOW(), INTERVAL 96 DAY), 24, 15),
(42, 2, 59.90, 119.80, 0.20, 0.40, DATE_SUB(NOW(), INTERVAL 96 DAY), DATE_SUB(NOW(), INTERVAL 96 DAY), 24, 12),
(43, 2, 44.90, 89.80, 0.34, 0.68, DATE_SUB(NOW(), INTERVAL 95 DAY), DATE_SUB(NOW(), INTERVAL 95 DAY), 25, 11),
(44, 1, 34.90, 34.90, 0.12, 0.12, DATE_SUB(NOW(), INTERVAL 95 DAY), DATE_SUB(NOW(), INTERVAL 95 DAY), 25, 20),
(45, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 95 DAY), DATE_SUB(NOW(), INTERVAL 95 DAY), 25, 7),
(46, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 94 DAY), DATE_SUB(NOW(), INTERVAL 94 DAY), 26, 7),
(47, 2, 54.90, 109.80, 0.75, 1.50, DATE_SUB(NOW(), INTERVAL 93 DAY), DATE_SUB(NOW(), INTERVAL 93 DAY), 27, 16),
(48, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 92 DAY), DATE_SUB(NOW(), INTERVAL 92 DAY), 28, 5),
(49, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 92 DAY), DATE_SUB(NOW(), INTERVAL 92 DAY), 28, 21),
(50, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 91 DAY), DATE_SUB(NOW(), INTERVAL 91 DAY), 29, 4),
(51, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 91 DAY), DATE_SUB(NOW(), INTERVAL 91 DAY), 29, 13),
(52, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 90 DAY), DATE_SUB(NOW(), INTERVAL 90 DAY), 30, 8),
(53, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 90 DAY), DATE_SUB(NOW(), INTERVAL 90 DAY), 30, 9),
(54, 1, 69.90, 69.90, 0.24, 0.24, DATE_SUB(NOW(), INTERVAL 90 DAY), DATE_SUB(NOW(), INTERVAL 90 DAY), 30, 14),
(55, 2, 59.90, 119.80, 0.20, 0.40, DATE_SUB(NOW(), INTERVAL 89 DAY), DATE_SUB(NOW(), INTERVAL 89 DAY), 31, 21),
(56, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 88 DAY), DATE_SUB(NOW(), INTERVAL 88 DAY), 32, 10),
(57, 1, 49.90, 49.90, 0.50, 0.50, DATE_SUB(NOW(), INTERVAL 87 DAY), DATE_SUB(NOW(), INTERVAL 87 DAY), 33, 19),
(58, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 87 DAY), DATE_SUB(NOW(), INTERVAL 87 DAY), 33, 6),
(59, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 86 DAY), DATE_SUB(NOW(), INTERVAL 86 DAY), 34, 10),
(60, 1, 39.90, 39.90, 0.18, 0.18, DATE_SUB(NOW(), INTERVAL 86 DAY), DATE_SUB(NOW(), INTERVAL 86 DAY), 34, 17),
(61, 2, 39.90, 79.80, 0.45, 0.90, DATE_SUB(NOW(), INTERVAL 85 DAY), DATE_SUB(NOW(), INTERVAL 85 DAY), 35, 22),
(62, 1, 54.90, 54.90, 0.75, 0.75, DATE_SUB(NOW(), INTERVAL 85 DAY), DATE_SUB(NOW(), INTERVAL 85 DAY), 35, 16),
(63, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 85 DAY), DATE_SUB(NOW(), INTERVAL 85 DAY), 35, 3),
(64, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 84 DAY), DATE_SUB(NOW(), INTERVAL 84 DAY), 36, 12),
(65, 1, 49.90, 49.90, 0.50, 0.50, DATE_SUB(NOW(), INTERVAL 83 DAY), DATE_SUB(NOW(), INTERVAL 83 DAY), 37, 19),
(66, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 82 DAY), DATE_SUB(NOW(), INTERVAL 82 DAY), 38, 4),
(67, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 82 DAY), DATE_SUB(NOW(), INTERVAL 82 DAY), 38, 3),
(68, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 81 DAY), DATE_SUB(NOW(), INTERVAL 81 DAY), 39, 22),
(69, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 81 DAY), DATE_SUB(NOW(), INTERVAL 81 DAY), 39, 9),
(70, 1, 27.90, 27.90, 0.15, 0.15, DATE_SUB(NOW(), INTERVAL 80 DAY), DATE_SUB(NOW(), INTERVAL 80 DAY), 40, 18),
(71, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 80 DAY), DATE_SUB(NOW(), INTERVAL 80 DAY), 40, 1),
(72, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 80 DAY), DATE_SUB(NOW(), INTERVAL 80 DAY), 40, 13),
(73, 1, 44.90, 44.90, 0.34, 0.34, DATE_SUB(NOW(), INTERVAL 79 DAY), DATE_SUB(NOW(), INTERVAL 79 DAY), 41, 11),
(74, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 78 DAY), DATE_SUB(NOW(), INTERVAL 78 DAY), 42, 6),
(75, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 77 DAY), DATE_SUB(NOW(), INTERVAL 77 DAY), 43, 15),
(76, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 77 DAY), DATE_SUB(NOW(), INTERVAL 77 DAY), 43, 2),
(77, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 76 DAY), DATE_SUB(NOW(), INTERVAL 76 DAY), 44, 6),
(78, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 76 DAY), DATE_SUB(NOW(), INTERVAL 76 DAY), 44, 15),
(79, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 75 DAY), DATE_SUB(NOW(), INTERVAL 75 DAY), 45, 12),
(80, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 75 DAY), DATE_SUB(NOW(), INTERVAL 75 DAY), 45, 12),
(81, 2, 59.90, 119.80, 0.20, 0.40, DATE_SUB(NOW(), INTERVAL 75 DAY), DATE_SUB(NOW(), INTERVAL 75 DAY), 45, 21),
(82, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 74 DAY), DATE_SUB(NOW(), INTERVAL 74 DAY), 46, 8),
(83, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 73 DAY), DATE_SUB(NOW(), INTERVAL 73 DAY), 47, 7),
(84, 1, 54.90, 54.90, 0.75, 0.75, DATE_SUB(NOW(), INTERVAL 72 DAY), DATE_SUB(NOW(), INTERVAL 72 DAY), 48, 16),
(85, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 72 DAY), DATE_SUB(NOW(), INTERVAL 72 DAY), 48, 5),
(86, 2, 59.90, 119.80, 0.20, 0.40, DATE_SUB(NOW(), INTERVAL 71 DAY), DATE_SUB(NOW(), INTERVAL 71 DAY), 49, 21),
(87, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 71 DAY), DATE_SUB(NOW(), INTERVAL 71 DAY), 49, 5),
(88, 2, 69.90, 139.80, 0.24, 0.48, DATE_SUB(NOW(), INTERVAL 70 DAY), DATE_SUB(NOW(), INTERVAL 70 DAY), 50, 14),
(89, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 70 DAY), DATE_SUB(NOW(), INTERVAL 70 DAY), 50, 1),
(90, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 70 DAY), DATE_SUB(NOW(), INTERVAL 70 DAY), 50, 9),
(91, 1, 69.90, 69.90, 0.24, 0.24, DATE_SUB(NOW(), INTERVAL 69 DAY), DATE_SUB(NOW(), INTERVAL 69 DAY), 51, 14),
(92, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 68 DAY), DATE_SUB(NOW(), INTERVAL 68 DAY), 52, 21),
(93, 1, 44.90, 44.90, 0.34, 0.34, DATE_SUB(NOW(), INTERVAL 67 DAY), DATE_SUB(NOW(), INTERVAL 67 DAY), 53, 11),
(94, 1, 34.90, 34.90, 0.12, 0.12, DATE_SUB(NOW(), INTERVAL 67 DAY), DATE_SUB(NOW(), INTERVAL 67 DAY), 53, 20),
(95, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 66 DAY), DATE_SUB(NOW(), INTERVAL 66 DAY), 54, 7),
(96, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 66 DAY), DATE_SUB(NOW(), INTERVAL 66 DAY), 54, 10),
(97, 2, 39.90, 79.80, 0.18, 0.36, DATE_SUB(NOW(), INTERVAL 65 DAY), DATE_SUB(NOW(), INTERVAL 65 DAY), 55, 17),
(98, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 65 DAY), DATE_SUB(NOW(), INTERVAL 65 DAY), 55, 22),
(99, 1, 39.90, 39.90, 0.18, 0.18, DATE_SUB(NOW(), INTERVAL 65 DAY), DATE_SUB(NOW(), INTERVAL 65 DAY), 55, 17),
(100, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 64 DAY), DATE_SUB(NOW(), INTERVAL 64 DAY), 56, 4),
(101, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 63 DAY), DATE_SUB(NOW(), INTERVAL 63 DAY), 57, 13),
(102, 2, 49.90, 99.80, 0.50, 1.00, DATE_SUB(NOW(), INTERVAL 62 DAY), DATE_SUB(NOW(), INTERVAL 62 DAY), 58, 19),
(103, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 62 DAY), DATE_SUB(NOW(), INTERVAL 62 DAY), 58, 4),
(104, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 61 DAY), DATE_SUB(NOW(), INTERVAL 61 DAY), 59, 3),
(105, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 61 DAY), DATE_SUB(NOW(), INTERVAL 61 DAY), 59, 1),
(106, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 60 DAY), DATE_SUB(NOW(), INTERVAL 60 DAY), 60, 10),
(107, 1, 49.90, 49.90, 0.50, 0.50, DATE_SUB(NOW(), INTERVAL 60 DAY), DATE_SUB(NOW(), INTERVAL 60 DAY), 60, 19),
(108, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 60 DAY), DATE_SUB(NOW(), INTERVAL 60 DAY), 60, 2),
(109, 2, 59.90, 119.80, 0.20, 0.40, DATE_SUB(NOW(), INTERVAL 59 DAY), DATE_SUB(NOW(), INTERVAL 59 DAY), 61, 13),
(110, 1, 44.90, 44.90, 0.34, 0.34, DATE_SUB(NOW(), INTERVAL 58 DAY), DATE_SUB(NOW(), INTERVAL 58 DAY), 62, 11),
(111, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 57 DAY), DATE_SUB(NOW(), INTERVAL 57 DAY), 63, 7),
(112, 2, 54.90, 109.80, 0.75, 1.50, DATE_SUB(NOW(), INTERVAL 57 DAY), DATE_SUB(NOW(), INTERVAL 57 DAY), 63, 16),
(113, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 56 DAY), DATE_SUB(NOW(), INTERVAL 56 DAY), 64, 3),
(114, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 56 DAY), DATE_SUB(NOW(), INTERVAL 56 DAY), 64, 6),
(115, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 55 DAY), DATE_SUB(NOW(), INTERVAL 55 DAY), 65, 15),
(116, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 55 DAY), DATE_SUB(NOW(), INTERVAL 55 DAY), 65, 12),
(117, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 55 DAY), DATE_SUB(NOW(), INTERVAL 55 DAY), 65, 13),
(118, 2, 39.90, 79.80, 0.45, 0.90, DATE_SUB(NOW(), INTERVAL 54 DAY), DATE_SUB(NOW(), INTERVAL 54 DAY), 66, 22),
(119, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 53 DAY), DATE_SUB(NOW(), INTERVAL 53 DAY), 67, 9),
(120, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 52 DAY), DATE_SUB(NOW(), INTERVAL 52 DAY), 68, 7),
(121, 1, 54.90, 54.90, 0.75, 0.75, DATE_SUB(NOW(), INTERVAL 52 DAY), DATE_SUB(NOW(), INTERVAL 52 DAY), 68, 16),
(122, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 51 DAY), DATE_SUB(NOW(), INTERVAL 51 DAY), 69, 5),
(123, 2, 59.90, 119.80, 0.20, 0.40, DATE_SUB(NOW(), INTERVAL 51 DAY), DATE_SUB(NOW(), INTERVAL 51 DAY), 69, 21),
(124, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 50 DAY), DATE_SUB(NOW(), INTERVAL 50 DAY), 70, 6),
(125, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 50 DAY), DATE_SUB(NOW(), INTERVAL 50 DAY), 70, 15),
(126, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 50 DAY), DATE_SUB(NOW(), INTERVAL 50 DAY), 70, 2),
(127, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 49 DAY), DATE_SUB(NOW(), INTERVAL 49 DAY), 71, 9),
(128, 2, 69.90, 139.80, 0.24, 0.48, DATE_SUB(NOW(), INTERVAL 48 DAY), DATE_SUB(NOW(), INTERVAL 48 DAY), 72, 14),
(129, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 47 DAY), DATE_SUB(NOW(), INTERVAL 47 DAY), 73, 21),
(130, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 47 DAY), DATE_SUB(NOW(), INTERVAL 47 DAY), 73, 12),
(131, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 46 DAY), DATE_SUB(NOW(), INTERVAL 46 DAY), 74, 21),
(132, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 46 DAY), DATE_SUB(NOW(), INTERVAL 46 DAY), 74, 8),
(133, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 45 DAY), DATE_SUB(NOW(), INTERVAL 45 DAY), 75, 10),
(134, 1, 34.90, 34.90, 0.12, 0.12, DATE_SUB(NOW(), INTERVAL 45 DAY), DATE_SUB(NOW(), INTERVAL 45 DAY), 75, 20),
(135, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 45 DAY), DATE_SUB(NOW(), INTERVAL 45 DAY), 75, 22),
(136, 1, 27.90, 27.90, 0.15, 0.15, DATE_SUB(NOW(), INTERVAL 44 DAY), DATE_SUB(NOW(), INTERVAL 44 DAY), 76, 18),
(137, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 43 DAY), DATE_SUB(NOW(), INTERVAL 43 DAY), 77, 5),
(138, 1, 69.90, 69.90, 0.24, 0.24, DATE_SUB(NOW(), INTERVAL 42 DAY), DATE_SUB(NOW(), INTERVAL 42 DAY), 78, 14),
(139, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 42 DAY), DATE_SUB(NOW(), INTERVAL 42 DAY), 78, 1),
(140, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 41 DAY), DATE_SUB(NOW(), INTERVAL 41 DAY), 79, 4),
(141, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 41 DAY), DATE_SUB(NOW(), INTERVAL 41 DAY), 79, 3),
(142, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 40 DAY), DATE_SUB(NOW(), INTERVAL 40 DAY), 80, 2),
(143, 1, 44.90, 44.90, 0.34, 0.34, DATE_SUB(NOW(), INTERVAL 40 DAY), DATE_SUB(NOW(), INTERVAL 40 DAY), 80, 11),
(144, 1, 34.90, 34.90, 0.12, 0.12, DATE_SUB(NOW(), INTERVAL 40 DAY), DATE_SUB(NOW(), INTERVAL 40 DAY), 80, 20),
(145, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 39 DAY), DATE_SUB(NOW(), INTERVAL 39 DAY), 81, 2),
(146, 2, 59.90, 119.80, 0.20, 0.40, DATE_SUB(NOW(), INTERVAL 38 DAY), DATE_SUB(NOW(), INTERVAL 38 DAY), 82, 13),
(147, 1, 44.90, 44.90, 0.34, 0.34, DATE_SUB(NOW(), INTERVAL 37 DAY), DATE_SUB(NOW(), INTERVAL 37 DAY), 83, 11),
(148, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 37 DAY), DATE_SUB(NOW(), INTERVAL 37 DAY), 83, 8),
(149, 1, 39.90, 39.90, 0.18, 0.18, DATE_SUB(NOW(), INTERVAL 36 DAY), DATE_SUB(NOW(), INTERVAL 36 DAY), 84, 17),
(150, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 36 DAY), DATE_SUB(NOW(), INTERVAL 36 DAY), 84, 4),
(151, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 35 DAY), DATE_SUB(NOW(), INTERVAL 35 DAY), 85, 6),
(152, 1, 54.90, 54.90, 0.75, 0.75, DATE_SUB(NOW(), INTERVAL 35 DAY), DATE_SUB(NOW(), INTERVAL 35 DAY), 85, 16),
(153, 2, 27.90, 55.80, 0.15, 0.30, DATE_SUB(NOW(), INTERVAL 35 DAY), DATE_SUB(NOW(), INTERVAL 35 DAY), 85, 18),
(154, 1, 69.90, 69.90, 0.24, 0.24, DATE_SUB(NOW(), INTERVAL 34 DAY), DATE_SUB(NOW(), INTERVAL 34 DAY), 86, 14),
(155, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 33 DAY), DATE_SUB(NOW(), INTERVAL 33 DAY), 87, 1),
(156, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 32 DAY), DATE_SUB(NOW(), INTERVAL 32 DAY), 88, 10),
(157, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 32 DAY), DATE_SUB(NOW(), INTERVAL 32 DAY), 88, 8),
(158, 1, 54.90, 54.90, 0.75, 0.75, DATE_SUB(NOW(), INTERVAL 31 DAY), DATE_SUB(NOW(), INTERVAL 31 DAY), 89, 16),
(159, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 31 DAY), DATE_SUB(NOW(), INTERVAL 31 DAY), 89, 5),
(160, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 30 DAY), 90, 21),
(161, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 30 DAY), 90, 7),
(162, 1, 54.90, 54.90, 0.75, 0.75, DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 30 DAY), 90, 16),
(163, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 29 DAY), DATE_SUB(NOW(), INTERVAL 29 DAY), 91, 3),
(164, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY), 92, 10),
(165, 2, 69.90, 139.80, 0.24, 0.48, DATE_SUB(NOW(), INTERVAL 27 DAY), DATE_SUB(NOW(), INTERVAL 27 DAY), 93, 14),
(166, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 27 DAY), DATE_SUB(NOW(), INTERVAL 27 DAY), 93, 22),
(167, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 26 DAY), DATE_SUB(NOW(), INTERVAL 26 DAY), 94, 13),
(168, 2, 39.90, 79.80, 0.45, 0.90, DATE_SUB(NOW(), INTERVAL 26 DAY), DATE_SUB(NOW(), INTERVAL 26 DAY), 94, 22),
(169, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 25 DAY), 95, 9),
(170, 1, 49.90, 49.90, 0.50, 0.50, DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 25 DAY), 95, 19),
(171, 1, 34.90, 34.90, 0.12, 0.12, DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 25 DAY), 95, 20),
(172, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 24 DAY), DATE_SUB(NOW(), INTERVAL 24 DAY), 96, 22),
(173, 1, 49.90, 49.90, 0.50, 0.50, DATE_SUB(NOW(), INTERVAL 23 DAY), DATE_SUB(NOW(), INTERVAL 23 DAY), 97, 19),
(174, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 22 DAY), DATE_SUB(NOW(), INTERVAL 22 DAY), 98, 6),
(175, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 22 DAY), DATE_SUB(NOW(), INTERVAL 22 DAY), 98, 15),
(176, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 21 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY), 99, 1),
(177, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 21 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY), 99, 4),
(178, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 20 DAY), 100, 3),
(179, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 20 DAY), 100, 3),
(180, 3, 59.90, 179.70, 0.20, 0.60, DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 20 DAY), 100, 12),
(181, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 19 DAY), DATE_SUB(NOW(), INTERVAL 19 DAY), 101, 21),
(182, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 18 DAY), DATE_SUB(NOW(), INTERVAL 18 DAY), 102, 2),
(183, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 17 DAY), DATE_SUB(NOW(), INTERVAL 17 DAY), 103, 15),
(184, 1, 44.90, 44.90, 0.34, 0.34, DATE_SUB(NOW(), INTERVAL 17 DAY), DATE_SUB(NOW(), INTERVAL 17 DAY), 103, 11),
(185, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 16 DAY), DATE_SUB(NOW(), INTERVAL 16 DAY), 104, 9),
(186, 1, 27.90, 27.90, 0.15, 0.15, DATE_SUB(NOW(), INTERVAL 16 DAY), DATE_SUB(NOW(), INTERVAL 16 DAY), 104, 18),
(187, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY), 105, 5),
(188, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY), 105, 6),
(189, 1, 54.90, 54.90, 0.75, 0.75, DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY), 105, 16),
(190, 1, 27.90, 27.90, 0.15, 0.15, DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY), 106, 18),
(191, 2, 39.90, 79.80, 0.45, 0.90, DATE_SUB(NOW(), INTERVAL 13 DAY), DATE_SUB(NOW(), INTERVAL 13 DAY), 107, 15),
(192, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 12 DAY), DATE_SUB(NOW(), INTERVAL 12 DAY), 108, 2),
(193, 1, 44.90, 44.90, 0.34, 0.34, DATE_SUB(NOW(), INTERVAL 12 DAY), DATE_SUB(NOW(), INTERVAL 12 DAY), 108, 11),
(194, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 11 DAY), DATE_SUB(NOW(), INTERVAL 11 DAY), 109, 8),
(195, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 11 DAY), DATE_SUB(NOW(), INTERVAL 11 DAY), 109, 9),
(196, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), 110, 5),
(197, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), 110, 21),
(198, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), 110, 8),
(199, 1, 39.90, 39.90, 0.18, 0.18, DATE_SUB(NOW(), INTERVAL 9 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY), 111, 17),
(200, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY), 112, 4),
(201, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), 113, 10),
(202, 1, 39.90, 39.90, 0.18, 0.18, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), 113, 17),
(203, 1, 39.90, 39.90, 0.45, 0.45, DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY), 114, 22),
(204, 1, 69.90, 69.90, 0.24, 0.24, DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY), 114, 14),
(205, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY), 115, 1),
(206, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY), 115, 10),
(207, 1, 49.90, 49.90, 0.50, 0.50, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY), 115, 19),
(208, 2, 34.90, 69.80, 0.12, 0.24, DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 4 DAY), 116, 20),
(209, 2, 39.90, 79.80, 0.45, 0.90, DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY), 117, 22),
(210, 1, 34.90, 34.90, 0.12, 0.12, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), 118, 20),
(211, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), 118, 7),
(212, 1, 54.90, 54.90, 0.75, 0.75, DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 119, 16),
(213, 2, 29.90, 59.80, 0.32, 0.64, DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 119, 1),
(214, 1, 59.90, 59.90, 0.20, 0.20, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 120, 13),
(215, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 120, 3),
(216, 1, 29.90, 29.90, 0.32, 0.32, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 120, 4);

INSERT IGNORE INTO movimentacao (id, tipo, descricao, valor, status_pagamento, data_vencimento, data_pagamento, status, created_at, updated_at, categoria_movimentacao_id, contraparte_id) VALUES
(1, 'Receita', 'Mercado Livre', 29.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 3 DAY)), DATE_SUB(NOW(), INTERVAL 2 DAY), 1, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), 1, 2),
(2, 'Receita', 'Shopee', 39.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 7 DAY)), DATE_SUB(NOW(), INTERVAL 5 DAY), 1, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY), 1, 3),
(3, 'Receita', 'Mercado Livre', 84.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 11 DAY)), DATE_SUB(NOW(), INTERVAL 11 DAY), 1, DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 11 DAY), 1, 2),
(4, 'Receita', 'Shopee', 59.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 15 DAY)), DATE_SUB(NOW(), INTERVAL 14 DAY), 1, DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY), 1, 3),
(5, 'Receita', 'Mercado Livre', 129.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 24 DAY)), DATE_SUB(NOW(), INTERVAL 22 DAY), 1, DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 22 DAY), 1, 2),
(6, 'Despesa', 'Sublimix Insumos', 420.00, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 19 DAY)), DATE_SUB(NOW(), INTERVAL 18 DAY), 1, DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 18 DAY), 2, 1),
(7, 'Despesa', 'Papel & Arte Cadernos', 510.00, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 23 DAY)), DATE_SUB(NOW(), INTERVAL 21 DAY), 1, DATE_SUB(NOW(), INTERVAL 35 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY), 3, 6),
(8, 'Despesa', 'Flash Entregas', 95.00, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 27 DAY)), DATE_SUB(NOW(), INTERVAL 28 DAY), 1, DATE_SUB(NOW(), INTERVAL 40 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY), 3, 5),
(9, 'Receita', 'Shopee', 74.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 40 DAY)), DATE_SUB(NOW(), INTERVAL 40 DAY), 1, DATE_SUB(NOW(), INTERVAL 45 DAY), DATE_SUB(NOW(), INTERVAL 40 DAY), 1, 3),
(10, 'Receita', 'Mercado Livre', 125.60, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 49 DAY)), DATE_SUB(NOW(), INTERVAL 48 DAY), 1, DATE_SUB(NOW(), INTERVAL 50 DAY), DATE_SUB(NOW(), INTERVAL 48 DAY), 1, 2),
(11, 'Receita', 'Shopee', 29.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 53 DAY)), DATE_SUB(NOW(), INTERVAL 51 DAY), 1, DATE_SUB(NOW(), INTERVAL 55 DAY), DATE_SUB(NOW(), INTERVAL 51 DAY), 1, 3),
(12, 'Receita', 'Mercado Livre', 29.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 57 DAY)), DATE_SUB(NOW(), INTERVAL 57 DAY), 1, DATE_SUB(NOW(), INTERVAL 60 DAY), DATE_SUB(NOW(), INTERVAL 57 DAY), 1, 2),
(13, 'Receita', 'Shopee', 57.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 61 DAY)), DATE_SUB(NOW(), INTERVAL 60 DAY), 1, DATE_SUB(NOW(), INTERVAL 65 DAY), DATE_SUB(NOW(), INTERVAL 60 DAY), 1, 3),
(14, 'Receita', 'Mercado Livre', 74.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 65 DAY)), DATE_SUB(NOW(), INTERVAL 63 DAY), 1, DATE_SUB(NOW(), INTERVAL 70 DAY), DATE_SUB(NOW(), INTERVAL 63 DAY), 1, 2),
(15, 'Receita', 'Shopee', 159.60, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 74 DAY)), DATE_SUB(NOW(), INTERVAL 74 DAY), 1, DATE_SUB(NOW(), INTERVAL 75 DAY), DATE_SUB(NOW(), INTERVAL 74 DAY), 1, 3),
(16, 'Despesa', 'Sublimix Insumos', 460.00, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 69 DAY)), DATE_SUB(NOW(), INTERVAL 70 DAY), 1, DATE_SUB(NOW(), INTERVAL 80 DAY), DATE_SUB(NOW(), INTERVAL 70 DAY), 3, 1),
(17, 'Despesa', 'Papel & Arte Cadernos', 380.00, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 73 DAY)), DATE_SUB(NOW(), INTERVAL 73 DAY), 1, DATE_SUB(NOW(), INTERVAL 85 DAY), DATE_SUB(NOW(), INTERVAL 73 DAY), 3, 6),
(18, 'Despesa', 'Flash Entregas', 88.00, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 77 DAY)), DATE_SUB(NOW(), INTERVAL 76 DAY), 1, DATE_SUB(NOW(), INTERVAL 90 DAY), DATE_SUB(NOW(), INTERVAL 76 DAY), 3, 5),
(19, 'Receita', 'Mercado Livre', 69.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 0 DAY)), DATE_SUB(NOW(), INTERVAL 0 DAY), 1, DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 1, 2),
(20, 'Receita', 'Shopee', 99.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 8 DAY)), DATE_SUB(NOW(), INTERVAL 6 DAY), 1, DATE_SUB(NOW(), INTERVAL 9 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY), 1, 3),
(21, 'Receita', 'Mercado Livre', 34.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 12 DAY)), DATE_SUB(NOW(), INTERVAL 12 DAY), 1, DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 12 DAY), 1, 2),
(22, 'Receita', 'Shopee', 29.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 16 DAY)), DATE_SUB(NOW(), INTERVAL 15 DAY), 1, DATE_SUB(NOW(), INTERVAL 19 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY), 1, 3),
(23, 'Receita', 'Mercado Livre', 179.60, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 20 DAY)), DATE_SUB(NOW(), INTERVAL 18 DAY), 1, DATE_SUB(NOW(), INTERVAL 24 DAY), DATE_SUB(NOW(), INTERVAL 18 DAY), 1, 2),
(24, 'Receita', 'Shopee', 85.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 24 DAY)), DATE_SUB(NOW(), INTERVAL 24 DAY), 1, DATE_SUB(NOW(), INTERVAL 29 DAY), DATE_SUB(NOW(), INTERVAL 24 DAY), 1, 3),
(25, 'Receita', 'Mercado Livre', 154.60, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 33 DAY)), DATE_SUB(NOW(), INTERVAL 32 DAY), 1, DATE_SUB(NOW(), INTERVAL 34 DAY), DATE_SUB(NOW(), INTERVAL 32 DAY), 1, 2),
(26, 'Despesa', 'Sublimix Insumos', 495.00, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 28 DAY)), DATE_SUB(NOW(), INTERVAL 27 DAY), 1, DATE_SUB(NOW(), INTERVAL 39 DAY), DATE_SUB(NOW(), INTERVAL 27 DAY), 3, 1),
(27, 'Despesa', 'Papel & Arte Cadernos', 545.00, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 32 DAY)), DATE_SUB(NOW(), INTERVAL 30 DAY), 1, DATE_SUB(NOW(), INTERVAL 44 DAY), DATE_SUB(NOW(), INTERVAL 30 DAY), 3, 6),
(28, 'Despesa', 'Flash Entregas', 92.00, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 36 DAY)), DATE_SUB(NOW(), INTERVAL 37 DAY), 1, DATE_SUB(NOW(), INTERVAL 49 DAY), DATE_SUB(NOW(), INTERVAL 37 DAY), 3, 5),
(29, 'Receita', 'Shopee', 59.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 49 DAY)), DATE_SUB(NOW(), INTERVAL 47 DAY), 1, DATE_SUB(NOW(), INTERVAL 54 DAY), DATE_SUB(NOW(), INTERVAL 47 DAY), 1, 3),
(30, 'Receita', 'Mercado Livre', 89.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 58 DAY)), DATE_SUB(NOW(), INTERVAL 58 DAY), 1, DATE_SUB(NOW(), INTERVAL 59 DAY), DATE_SUB(NOW(), INTERVAL 58 DAY), 1, 2),
(31, 'Receita', 'Shopee', 59.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 62 DAY)), DATE_SUB(NOW(), INTERVAL 61 DAY), 1, DATE_SUB(NOW(), INTERVAL 64 DAY), DATE_SUB(NOW(), INTERVAL 61 DAY), 1, 3),
(32, 'Receita', 'Mercado Livre', 54.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 66 DAY)), DATE_SUB(NOW(), INTERVAL 64 DAY), 1, DATE_SUB(NOW(), INTERVAL 69 DAY), DATE_SUB(NOW(), INTERVAL 64 DAY), 1, 2),
(33, 'Receita', 'Shopee', 85.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 70 DAY)), DATE_SUB(NOW(), INTERVAL 70 DAY), 1, DATE_SUB(NOW(), INTERVAL 74 DAY), DATE_SUB(NOW(), INTERVAL 70 DAY), 1, 3),
(34, 'Receita', 'Mercado Livre', 64.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 74 DAY)), DATE_SUB(NOW(), INTERVAL 73 DAY), 1, DATE_SUB(NOW(), INTERVAL 79 DAY), DATE_SUB(NOW(), INTERVAL 73 DAY), 1, 2),
(35, 'Receita', 'Shopee', 167.60, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 83 DAY)), DATE_SUB(NOW(), INTERVAL 81 DAY), 1, DATE_SUB(NOW(), INTERVAL 84 DAY), DATE_SUB(NOW(), INTERVAL 81 DAY), 1, 3),
(36, 'Despesa', 'Sublimix Insumos', 430.00, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 78 DAY)), DATE_SUB(NOW(), INTERVAL 79 DAY), 1, DATE_SUB(NOW(), INTERVAL 89 DAY), DATE_SUB(NOW(), INTERVAL 79 DAY), 2, 1),
(37, 'Despesa', 'Papel & Arte Cadernos', 470.00, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 0 DAY)), DATE_SUB(NOW(), INTERVAL 0 DAY), 1, DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 3, 6),
(38, 'Despesa', 'Flash Entregas', 97.00, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 0 DAY)), DATE_SUB(NOW(), INTERVAL 0 DAY), 1, DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 3, 5),
(39, 'Receita', 'Mercado Livre', 57.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 8 DAY)), DATE_SUB(NOW(), INTERVAL 8 DAY), 1, DATE_SUB(NOW(), INTERVAL 13 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY), 1, 2),
(40, 'Receita', 'Shopee', 119.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 17 DAY)), DATE_SUB(NOW(), INTERVAL 16 DAY), 1, DATE_SUB(NOW(), INTERVAL 18 DAY), DATE_SUB(NOW(), INTERVAL 16 DAY), 1, 3),
(41, 'Receita', 'Mercado Livre', 59.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 21 DAY)), DATE_SUB(NOW(), INTERVAL 19 DAY), 1, DATE_SUB(NOW(), INTERVAL 23 DAY), DATE_SUB(NOW(), INTERVAL 19 DAY), 1, 2),
(42, 'Receita', 'Shopee', 39.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 25 DAY)), DATE_SUB(NOW(), INTERVAL 25 DAY), 1, DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_SUB(NOW(), INTERVAL 25 DAY), 1, 3),
(43, 'Receita', 'Mercado Livre', 79.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 29 DAY)), DATE_SUB(NOW(), INTERVAL 28 DAY), 1, DATE_SUB(NOW(), INTERVAL 33 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY), 1, 2),
(44, 'Receita', 'Shopee', 59.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 33 DAY)), DATE_SUB(NOW(), INTERVAL 31 DAY), 1, DATE_SUB(NOW(), INTERVAL 38 DAY), DATE_SUB(NOW(), INTERVAL 31 DAY), 1, 3),
(45, 'Receita', 'Mercado Livre', 149.60, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 42 DAY)), DATE_SUB(NOW(), INTERVAL 42 DAY), 1, DATE_SUB(NOW(), INTERVAL 43 DAY), DATE_SUB(NOW(), INTERVAL 42 DAY), 1, 2),
(46, 'Despesa', 'Sublimix Insumos', 520.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 6 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 48 DAY), DATE_SUB(NOW(), INTERVAL 48 DAY), 3, 1),
(47, 'Despesa', 'Papel & Arte Cadernos', 610.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 7 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 53 DAY), DATE_SUB(NOW(), INTERVAL 53 DAY), 3, 6),
(48, 'Despesa', 'Flash Entregas', 102.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 9 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 58 DAY), DATE_SUB(NOW(), INTERVAL 58 DAY), 3, 5),
(49, 'Receita', 'Shopee', 87.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 58 DAY)), DATE_SUB(NOW(), INTERVAL 57 DAY), 1, DATE_SUB(NOW(), INTERVAL 63 DAY), DATE_SUB(NOW(), INTERVAL 57 DAY), 1, 3),
(50, 'Receita', 'Mercado Livre', 119.60, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 67 DAY)), DATE_SUB(NOW(), INTERVAL 65 DAY), 1, DATE_SUB(NOW(), INTERVAL 68 DAY), DATE_SUB(NOW(), INTERVAL 65 DAY), 1, 2),
(51, 'Receita', 'Shopee', 69.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 71 DAY)), DATE_SUB(NOW(), INTERVAL 71 DAY), 1, DATE_SUB(NOW(), INTERVAL 73 DAY), DATE_SUB(NOW(), INTERVAL 71 DAY), 1, 3),
(52, 'Receita', 'Mercado Livre', 144.60, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 75 DAY)), DATE_SUB(NOW(), INTERVAL 74 DAY), 1, DATE_SUB(NOW(), INTERVAL 78 DAY), DATE_SUB(NOW(), INTERVAL 74 DAY), 1, 2),
(53, 'Receita', 'Shopee', 59.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 79 DAY)), DATE_SUB(NOW(), INTERVAL 77 DAY), 1, DATE_SUB(NOW(), INTERVAL 83 DAY), DATE_SUB(NOW(), INTERVAL 77 DAY), 1, 3),
(54, 'Receita', 'Mercado Livre', 27.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 83 DAY)), DATE_SUB(NOW(), INTERVAL 83 DAY), 1, DATE_SUB(NOW(), INTERVAL 88 DAY), DATE_SUB(NOW(), INTERVAL 83 DAY), 1, 2),
(55, 'Receita', 'Shopee', 159.60, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 1 DAY)), DATE_SUB(NOW(), INTERVAL 0 DAY), 1, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 1, 3),
(56, 'Despesa', 'Sublimix Insumos', 540.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 2 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), 3, 1),
(57, 'Despesa', 'Papel & Arte Cadernos', 625.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 8 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 12 DAY), DATE_SUB(NOW(), INTERVAL 12 DAY), 3, 6),
(58, 'Despesa', 'Flash Entregas', 110.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 4 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 17 DAY), DATE_SUB(NOW(), INTERVAL 17 DAY), 3, 5),
(59, 'Receita', 'Mercado Livre', 104.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 17 DAY)), DATE_SUB(NOW(), INTERVAL 15 DAY), 1, DATE_SUB(NOW(), INTERVAL 22 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY), 1, 2),
(60, 'Receita', 'Shopee', 184.60, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 26 DAY)), DATE_SUB(NOW(), INTERVAL 26 DAY), 1, DATE_SUB(NOW(), INTERVAL 27 DAY), DATE_SUB(NOW(), INTERVAL 26 DAY), 1, 3),
(61, 'Receita', 'Mercado Livre', 129.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 30 DAY)), DATE_SUB(NOW(), INTERVAL 29 DAY), 1, DATE_SUB(NOW(), INTERVAL 32 DAY), DATE_SUB(NOW(), INTERVAL 29 DAY), 1, 2),
(62, 'Receita', 'Shopee', 139.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 34 DAY)), DATE_SUB(NOW(), INTERVAL 32 DAY), 1, DATE_SUB(NOW(), INTERVAL 37 DAY), DATE_SUB(NOW(), INTERVAL 32 DAY), 1, 3),
(63, 'Receita', 'Mercado Livre', 59.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 38 DAY)), DATE_SUB(NOW(), INTERVAL 38 DAY), 1, DATE_SUB(NOW(), INTERVAL 42 DAY), DATE_SUB(NOW(), INTERVAL 38 DAY), 1, 2),
(64, 'Receita', 'Shopee', 29.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 42 DAY)), DATE_SUB(NOW(), INTERVAL 41 DAY), 1, DATE_SUB(NOW(), INTERVAL 47 DAY), DATE_SUB(NOW(), INTERVAL 41 DAY), 1, 3),
(65, 'Receita', 'Mercado Livre', 104.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 51 DAY)), DATE_SUB(NOW(), INTERVAL 49 DAY), 1, DATE_SUB(NOW(), INTERVAL 52 DAY), DATE_SUB(NOW(), INTERVAL 49 DAY), 1, 2),
(66, 'Despesa', 'Sublimix Insumos', 560.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 12 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 57 DAY), DATE_SUB(NOW(), INTERVAL 57 DAY), 2, 1),
(67, 'Despesa', 'Papel & Arte Cadernos', 645.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 13 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 62 DAY), DATE_SUB(NOW(), INTERVAL 62 DAY), 3, 6),
(68, 'Despesa', 'Flash Entregas', 118.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 9 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 67 DAY), DATE_SUB(NOW(), INTERVAL 67 DAY), 3, 5),
(69, 'Receita', 'Shopee', 89.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 67 DAY)), DATE_SUB(NOW(), INTERVAL 67 DAY), 1, DATE_SUB(NOW(), INTERVAL 72 DAY), DATE_SUB(NOW(), INTERVAL 67 DAY), 1, 3),
(70, 'Receita', 'Mercado Livre', 122.60, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 76 DAY)), DATE_SUB(NOW(), INTERVAL 75 DAY), 1, DATE_SUB(NOW(), INTERVAL 77 DAY), DATE_SUB(NOW(), INTERVAL 75 DAY), 1, 2),
(71, 'Receita', 'Shopee', 39.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 80 DAY)), DATE_SUB(NOW(), INTERVAL 78 DAY), 1, DATE_SUB(NOW(), INTERVAL 82 DAY), DATE_SUB(NOW(), INTERVAL 78 DAY), 1, 3),
(72, 'Receita', 'Mercado Livre', 59.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 84 DAY)), DATE_SUB(NOW(), INTERVAL 84 DAY), 1, DATE_SUB(NOW(), INTERVAL 87 DAY), DATE_SUB(NOW(), INTERVAL 84 DAY), 1, 2),
(73, 'Receita', 'Shopee', 99.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 0 DAY)), DATE_SUB(NOW(), INTERVAL 0 DAY), 1, DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 1, 3),
(74, 'Receita', 'Mercado Livre', 69.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 1 DAY)), DATE_SUB(NOW(), INTERVAL 0 DAY), 1, DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 1, 2),
(75, 'Receita', 'Shopee', 99.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 10 DAY)), DATE_SUB(NOW(), INTERVAL 10 DAY), 1, DATE_SUB(NOW(), INTERVAL 11 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), 1, 3),
(76, 'Despesa', 'Sublimix Insumos', 575.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 7 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 16 DAY), DATE_SUB(NOW(), INTERVAL 16 DAY), 3, 1),
(77, 'Despesa', 'Papel & Arte Cadernos', 660.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 3 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 21 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY), 3, 6),
(78, 'Despesa', 'Flash Entregas', 121.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 10 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 26 DAY), DATE_SUB(NOW(), INTERVAL 26 DAY), 3, 5),
(79, 'Receita', 'Mercado Livre', 57.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 26 DAY)), DATE_SUB(NOW(), INTERVAL 25 DAY), 1, DATE_SUB(NOW(), INTERVAL 31 DAY), DATE_SUB(NOW(), INTERVAL 25 DAY), 1, 2),
(80, 'Receita', 'Shopee', 129.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 35 DAY)), DATE_SUB(NOW(), INTERVAL 33 DAY), 1, DATE_SUB(NOW(), INTERVAL 36 DAY), DATE_SUB(NOW(), INTERVAL 33 DAY), 1, 3),
(81, 'Receita', 'Mercado Livre', 109.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 39 DAY)), DATE_SUB(NOW(), INTERVAL 39 DAY), 1, DATE_SUB(NOW(), INTERVAL 41 DAY), DATE_SUB(NOW(), INTERVAL 39 DAY), 1, 2),
(82, 'Receita', 'Shopee', 219.50, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 43 DAY)), DATE_SUB(NOW(), INTERVAL 42 DAY), 1, DATE_SUB(NOW(), INTERVAL 46 DAY), DATE_SUB(NOW(), INTERVAL 42 DAY), 1, 3),
(83, 'Receita', 'Mercado Livre', 29.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 47 DAY)), DATE_SUB(NOW(), INTERVAL 45 DAY), 1, DATE_SUB(NOW(), INTERVAL 51 DAY), DATE_SUB(NOW(), INTERVAL 45 DAY), 1, 2),
(84, 'Receita', 'Shopee', 79.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 51 DAY)), DATE_SUB(NOW(), INTERVAL 51 DAY), 1, DATE_SUB(NOW(), INTERVAL 56 DAY), DATE_SUB(NOW(), INTERVAL 51 DAY), 1, 3),
(85, 'Receita', 'Mercado Livre', 199.60, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 60 DAY)), DATE_SUB(NOW(), INTERVAL 59 DAY), 1, DATE_SUB(NOW(), INTERVAL 61 DAY), DATE_SUB(NOW(), INTERVAL 59 DAY), 1, 2),
(86, 'Despesa', 'Sublimix Insumos', 590.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 4 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 66 DAY), DATE_SUB(NOW(), INTERVAL 66 DAY), 3, 1),
(87, 'Despesa', 'Papel & Arte Cadernos', 675.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 5 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 71 DAY), DATE_SUB(NOW(), INTERVAL 71 DAY), 3, 6),
(88, 'Despesa', 'Flash Entregas', 124.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 9 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 76 DAY), DATE_SUB(NOW(), INTERVAL 76 DAY), 3, 5),
(89, 'Receita', 'Shopee', 137.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 76 DAY)), DATE_SUB(NOW(), INTERVAL 74 DAY), 1, DATE_SUB(NOW(), INTERVAL 81 DAY), DATE_SUB(NOW(), INTERVAL 74 DAY), 1, 3),
(90, 'Receita', 'Mercado Livre', 89.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 85 DAY)), DATE_SUB(NOW(), INTERVAL 85 DAY), 1, DATE_SUB(NOW(), INTERVAL 86 DAY), DATE_SUB(NOW(), INTERVAL 85 DAY), 1, 2),
(91, 'Receita', 'Shopee', 59.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 0 DAY)), DATE_SUB(NOW(), INTERVAL 0 DAY), 1, DATE_SUB(NOW(), INTERVAL 0 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 1, 3),
(92, 'Receita', 'Mercado Livre', 127.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 2 DAY)), DATE_SUB(NOW(), INTERVAL 0 DAY), 1, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 0 DAY), 1, 2),
(93, 'Receita', 'Shopee', 119.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 6 DAY)), DATE_SUB(NOW(), INTERVAL 6 DAY), 1, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY), 1, 3),
(94, 'Receita', 'Mercado Livre', 119.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 10 DAY)), DATE_SUB(NOW(), INTERVAL 9 DAY), 1, DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY), 1, 2),
(95, 'Receita', 'Shopee', 39.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 19 DAY)), DATE_SUB(NOW(), INTERVAL 17 DAY), 1, DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 17 DAY), 1, 3),
(96, 'Despesa', 'Sublimix Insumos', 610.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 14 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 25 DAY), 2, 1),
(97, 'Despesa', 'Papel & Arte Cadernos', 695.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 15 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 30 DAY), 3, 6),
(98, 'Despesa', 'Flash Entregas', 128.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 4 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 35 DAY), DATE_SUB(NOW(), INTERVAL 35 DAY), 3, 5),
(99, 'Receita', 'Mercado Livre', 39.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 35 DAY)), DATE_SUB(NOW(), INTERVAL 35 DAY), 1, DATE_SUB(NOW(), INTERVAL 40 DAY), DATE_SUB(NOW(), INTERVAL 35 DAY), 1, 2),
(100, 'Receita', 'Shopee', 209.50, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 44 DAY)), DATE_SUB(NOW(), INTERVAL 43 DAY), 1, DATE_SUB(NOW(), INTERVAL 45 DAY), DATE_SUB(NOW(), INTERVAL 43 DAY), 1, 3),
(101, 'Receita', 'Mercado Livre', 29.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 48 DAY)), DATE_SUB(NOW(), INTERVAL 46 DAY), 1, DATE_SUB(NOW(), INTERVAL 50 DAY), DATE_SUB(NOW(), INTERVAL 46 DAY), 1, 2),
(102, 'Receita', 'Shopee', 59.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 52 DAY)), DATE_SUB(NOW(), INTERVAL 52 DAY), 1, DATE_SUB(NOW(), INTERVAL 55 DAY), DATE_SUB(NOW(), INTERVAL 52 DAY), 1, 3),
(103, 'Receita', 'Mercado Livre', 79.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 56 DAY)), DATE_SUB(NOW(), INTERVAL 55 DAY), 1, DATE_SUB(NOW(), INTERVAL 60 DAY), DATE_SUB(NOW(), INTERVAL 55 DAY), 1, 2),
(104, 'Receita', 'Shopee', 109.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 60 DAY)), DATE_SUB(NOW(), INTERVAL 58 DAY), 1, DATE_SUB(NOW(), INTERVAL 65 DAY), DATE_SUB(NOW(), INTERVAL 58 DAY), 1, 3),
(105, 'Receita', 'Mercado Livre', 94.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 69 DAY)), DATE_SUB(NOW(), INTERVAL 69 DAY), 1, DATE_SUB(NOW(), INTERVAL 70 DAY), DATE_SUB(NOW(), INTERVAL 69 DAY), 1, 2),
(106, 'Despesa', 'Sublimix Insumos', 630.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 10 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 75 DAY), DATE_SUB(NOW(), INTERVAL 75 DAY), 3, 1),
(107, 'Despesa', 'Papel & Arte Cadernos', 720.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 11 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 80 DAY), DATE_SUB(NOW(), INTERVAL 80 DAY), 3, 6),
(108, 'Despesa', 'Flash Entregas', 135.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 9 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 85 DAY), DATE_SUB(NOW(), INTERVAL 85 DAY), 3, 5),
(109, 'Receita', 'Shopee', 89.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 85 DAY)), DATE_SUB(NOW(), INTERVAL 84 DAY), 1, DATE_SUB(NOW(), INTERVAL 90 DAY), DATE_SUB(NOW(), INTERVAL 84 DAY), 1, 3),
(110, 'Receita', 'Mercado Livre', 104.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 3 DAY)), DATE_SUB(NOW(), INTERVAL 1 DAY), 1, DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 1, 2),
(111, 'Receita', 'Shopee', 29.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 7 DAY)), DATE_SUB(NOW(), INTERVAL 7 DAY), 1, DATE_SUB(NOW(), INTERVAL 9 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), 1, 3),
(112, 'Receita', 'Mercado Livre', 59.90, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 11 DAY)), DATE_SUB(NOW(), INTERVAL 10 DAY), 1, DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), 1, 2),
(113, 'Receita', 'Shopee', 59.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 15 DAY)), DATE_SUB(NOW(), INTERVAL 13 DAY), 1, DATE_SUB(NOW(), INTERVAL 19 DAY), DATE_SUB(NOW(), INTERVAL 13 DAY), 1, 3),
(114, 'Receita', 'Mercado Livre', 84.80, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 19 DAY)), DATE_SUB(NOW(), INTERVAL 19 DAY), 1, DATE_SUB(NOW(), INTERVAL 24 DAY), DATE_SUB(NOW(), INTERVAL 19 DAY), 1, 2),
(115, 'Receita', 'Shopee', 99.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 28 DAY)), DATE_SUB(NOW(), INTERVAL 27 DAY), 1, DATE_SUB(NOW(), INTERVAL 29 DAY), DATE_SUB(NOW(), INTERVAL 27 DAY), 1, 3),
(116, 'Despesa', 'Sublimix Insumos', 650.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 6 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 34 DAY), DATE_SUB(NOW(), INTERVAL 34 DAY), 3, 1),
(117, 'Despesa', 'Papel & Arte Cadernos', 740.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 7 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 39 DAY), DATE_SUB(NOW(), INTERVAL 39 DAY), 3, 6),
(118, 'Despesa', 'Flash Entregas', 140.00, 'Pendente', DATE_ADD(NOW(), INTERVAL 8 DAY), NULL, 1, DATE_SUB(NOW(), INTERVAL 44 DAY), DATE_SUB(NOW(), INTERVAL 44 DAY), 3, 5),
(119, 'Receita', 'Mercado Livre', 87.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 44 DAY)), DATE_SUB(NOW(), INTERVAL 42 DAY), 1, DATE_SUB(NOW(), INTERVAL 49 DAY), DATE_SUB(NOW(), INTERVAL 42 DAY), 1, 2),
(120, 'Receita', 'Shopee', 99.70, 'Pago', DATE(DATE_SUB(NOW(), INTERVAL 53 DAY)), DATE_SUB(NOW(), INTERVAL 53 DAY), 1, DATE_SUB(NOW(), INTERVAL 54 DAY), DATE_SUB(NOW(), INTERVAL 53 DAY), 1, 3);

DROP TABLE IF EXISTS v_dash_kpi_mes;
DROP VIEW IF EXISTS v_dash_kpi_mes;

DROP TABLE IF EXISTS v_dash_vendas_categoria;
DROP VIEW IF EXISTS v_dash_vendas_categoria;

DROP TABLE IF EXISTS v_dash_despesas_categoria;
DROP VIEW IF EXISTS v_dash_despesas_categoria;

DROP TABLE IF EXISTS v_dash_kpi_a_pagar;
DROP VIEW IF EXISTS v_dash_kpi_a_pagar;

DROP TABLE IF EXISTS v_dash_proximos_pagamentos;
DROP VIEW IF EXISTS v_dash_proximos_pagamentos;

DROP TABLE IF EXISTS vw_tipo_pedido;
DROP VIEW IF EXISTS vw_tipo_pedido;

DROP TABLE IF EXISTS vw_leadtime_funcionario;
DROP VIEW IF EXISTS vw_leadtime_funcionario;

DROP TABLE IF EXISTS vw_retrabalho_quantidade_mes;
DROP VIEW IF EXISTS vw_retrabalho_quantidade_mes;

DROP TABLE IF EXISTS vw_leadtime_etapa;
DROP VIEW IF EXISTS vw_leadtime_etapa;

DROP TABLE IF EXISTS vw_leadtime_mensal;
DROP VIEW IF EXISTS vw_leadtime_mensal;

DROP TABLE IF EXISTS vw_filtro_produto_item;
DROP VIEW IF EXISTS vw_filtro_produto_item;

DROP TABLE IF EXISTS vw_pedidos_mes;
DROP VIEW IF EXISTS vw_pedidos_mes;

DROP TABLE IF EXISTS vw_carga_trabalho;
DROP VIEW IF EXISTS vw_carga_trabalho;

DROP TABLE IF EXISTS vw_pedidos_sem_atualizacao;
DROP VIEW IF EXISTS vw_pedidos_sem_atualizacao;

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
    ROW_NUMBER() OVER (ORDER BY CM.id, MIN(M.data_pagamento)) AS id,
    CM.id AS categoria_id,
    CM.descricao AS nome_categoria,
    SUM(M.valor) AS valor_total,
    DATE(M.data_pagamento) AS data_referencia
FROM movimentacao M
         JOIN categoria_movimentacao CM ON M.categoria_movimentacao_id = CM.id
WHERE M.tipo = 'Despesa'
  AND M.status_pagamento = 'Pago'
  AND M.status = true
GROUP BY CM.id, CM.descricao, DATE(M.data_pagamento);

CREATE OR REPLACE VIEW v_dash_kpi_mes AS
WITH DadosMensais AS (
    SELECT
        DATE_FORMAT(M.data_pagamento, '%Y-%m-01') AS MES_REFERENCIA_FORMATADA,
        M.tipo AS TIPO_MOVIMENTACAO,
        SUM(M.valor) AS VALOR_MES
    FROM movimentacao M
             JOIN categoria_movimentacao CM ON M.categoria_movimentacao_id = CM.id
    WHERE M.status_pagamento = 'Pago'
      AND M.status = true
      AND M.data_pagamento IS NOT NULL
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

CREATE OR REPLACE VIEW v_dash_kpi_a_pagar AS
SELECT
    M.id AS id,
    M.valor AS valor,
    M.data_vencimento AS data_vencimento
FROM movimentacao M
WHERE M.tipo = 'Despesa'
  AND M.status_pagamento = 'Pendente'
  AND M.status = true;

CREATE OR REPLACE VIEW v_dash_proximos_pagamentos AS
SELECT
    M.id AS id,
    M.descricao AS descricao,
    M.valor AS valor,
    M.data_vencimento AS data_vencimento,
    CM.descricao AS categoria,
    DATEDIFF(CURDATE(), M.data_vencimento) AS dias_atraso
FROM movimentacao M
         JOIN categoria_movimentacao CM ON M.categoria_movimentacao_id = CM.id
WHERE M.tipo = 'Despesa'
  AND M.status_pagamento = 'Pendente'
  AND M.status = true;

-- ============================================================
--  Dashboard de Gestão de Pedidos — todas as views
-- ============================================================

CREATE OR REPLACE VIEW vw_tipo_pedido AS
SELECT
    p.id,
    p.origem,
    p.observacoes,
    sp.status,
    sp.status_role,
    CASE
        -- 1º Avaliar primeiro o Retrabalho (inclui verificação de NULL e ambas as roles)
        WHEN (
            (sp.status_role IS NULL OR sp.status_role NOT IN ('ENTREGUE', 'CANCELADO', 'FINALIZADO'))
            AND EXISTS (
                SELECT 1
                FROM pedido_status_pedido psp1
                JOIN status_pedido sp1 ON sp1.id = psp1.status_id
                WHERE psp1.pedido_id = p.id
                  AND psp1.status_atual = 0
                  AND sp1.status_role IN ('ENTREGUE', 'FINALIZADO')
            )
        ) THEN 'Retrabalho'

        -- 2º Avaliar Atrasos
        WHEN (p.data_limite IS NOT NULL AND p.data_limite <= NOW()
            AND (sp.status_role IS NULL OR sp.status_role NOT IN ('CANCELADO', 'ENTREGUE', 'FINALIZADO')))
            THEN 'Atrasado'

        -- 3º Condição normal
        ELSE 'Normal'
    END AS tipo_pedido
FROM pedido AS p
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
JOIN status_pedido sp ON sp.id = psp.status_id
WHERE p.ativo = 1
  AND psp.status_atual = 1;

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
  AND sp.status_role = 'FINALIZADO'
GROUP BY u.name;

CREATE OR REPLACE VIEW vw_retrabalho_quantidade_mes AS
SELECT
    DATE_FORMAT(psp_atual.created_at, '%Y-%m') AS mes,
    COUNT(DISTINCT p.id) AS quantidade_pedidos
FROM pedido p
JOIN pedido_status_pedido psp_atual
    ON psp_atual.pedido_id = p.id
   AND psp_atual.status_atual = 1
JOIN status_pedido sp_atual
    ON sp_atual.id = psp_atual.status_id
WHERE p.ativo = 1
  AND (
        sp_atual.status_role IS NULL
        OR sp_atual.status_role NOT IN ('ENTREGUE', 'CANCELADO')
      )
  AND EXISTS (
      SELECT 1
      FROM pedido_status_pedido psp_entregue
      JOIN status_pedido sp_entregue
          ON sp_entregue.id = psp_entregue.status_id
      WHERE psp_entregue.pedido_id = p.id
        AND sp_entregue.status_role = 'ENTREGUE'
  )
GROUP BY DATE_FORMAT(psp_atual.created_at, '%Y-%m')
ORDER BY mes ASC;

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

CREATE OR REPLACE VIEW vw_leadtime_mensal AS
SELECT
    DATE_FORMAT(psp.created_at, '%Y-%m')          AS mes,
    AVG(DATEDIFF(psp.created_at, p.created_at))   AS lead_time
FROM pedido AS p
JOIN pedido_status_pedido psp ON psp.pedido_id = p.id
JOIN status_pedido sp ON sp.id = psp.status_id
WHERE p.ativo = 1
  AND sp.status_role = 'ENTREGUE'
GROUP BY DATE_FORMAT(psp.created_at, '%Y-%m')
ORDER BY mes ASC;

CREATE OR REPLACE VIEW vw_filtro_produto_item AS
SELECT
    p.id                               AS id,
    pp.produto_id                      AS produto_id,
    COALESCE(prod.item_produto_id, 0)  AS qtd_prod
FROM pedido AS p
         LEFT JOIN produto_pedido pp ON pp.pedido_id = p.id
         LEFT JOIN produto prod ON pp.produto_id = prod.id
WHERE p.ativo = 1;

CREATE OR REPLACE VIEW vw_pedidos_mes AS
SELECT
    DATE_FORMAT(p.created_at, '%Y-%m') AS mes,
    COUNT(DISTINCT p.id) AS total_criados,
    COUNT(DISTINCT CASE 
        WHEN entrega.pedido_id IS NOT NULL THEN p.id 
    END) AS total_ja_entregues
FROM pedido p
LEFT JOIN (
    SELECT DISTINCT psp.pedido_id
    FROM pedido_status_pedido psp
    JOIN status_pedido sp ON sp.id = psp.status_id
    WHERE sp.status_role = 'ENTREGUE'
) entrega ON entrega.pedido_id = p.id
WHERE p.ativo = 1
GROUP BY DATE_FORMAT(p.created_at, '%Y-%m')
ORDER BY mes ASC;

CREATE OR REPLACE VIEW vw_carga_trabalho AS
SELECT
    u.name       AS funcionario,
    COUNT(p.id)  AS em_andamento
FROM pedido p
         JOIN usuario u ON u.id = p.usuario_id
         JOIN pedido_status_pedido psp ON psp.pedido_id = p.id AND psp.status_atual = 1
         JOIN status_pedido sp ON sp.id = psp.status_id
WHERE p.ativo = 1
  AND (sp.status_role IS NULL OR sp.status_role NOT IN ('ENTREGUE', 'CANCELADO', 'FINALIZADO'))
GROUP BY u.name;

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
  AND (sp.status_role IS NULL OR sp.status_role NOT IN ('ENTREGUE', 'CANCELADO', 'FINALIZADO'))
  AND DATEDIFF(NOW(), psp.created_at) >= 3
ORDER BY dias_parado DESC;
