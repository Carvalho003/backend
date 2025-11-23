import random
from faker import Faker
from datetime import datetime, timedelta

fake = Faker('pt_BR')
random.seed(42)
Faker.seed(42)

NUM_CLIENTES = 200
NUM_USUARIOS = 20
NUM_ENDERECOS = 200
NUM_CATEGORIA_MOV = 10
NUM_CATEGORIA_TEMA = 10
NUM_CONTRAPARTES = 50
NUM_ITEM_PRODUTO = 100
NUM_TEMA_PRODUTO = 20
NUM_PRODUTO = 200
NUM_FOTO_PRODUTO = 200
NUM_PEDIDO = 300
NUM_STATUS_PEDIDO = 5
NUM_PEDIDO_STATUS_PEDIDO = 300
NUM_PRODUTO_PEDIDO = 400
NUM_MOVIMENTACAO = 300

def rand_bit():
    return random.randint(0,1)

def rand_date(start_year=2023, end_year=2025):
    start = datetime(start_year, 1, 1)
    end = datetime(end_year, 12, 31)
    return fake.date_time_between(start_date=start, end_date=end).strftime('%Y-%m-%d %H:%M:%S')

with open('popular_banco.sql', 'w', encoding='utf-8') as f:
    # categoria_movimentacao
    f.write("-- categoria_movimentacao\nINSERT INTO categoria_movimentacao (descricao, status) VALUES\n")
    for i in range(NUM_CATEGORIA_MOV):
        desc = fake.word().capitalize()
        status = random.randint(0,1)
        f.write(f"('{desc}', {status}){',' if i < NUM_CATEGORIA_MOV-1 else ';'}\n")

    # categoria_tema
    f.write("\n-- categoria_tema\nINSERT INTO categoria_tema (ativo, titulo) VALUES\n")
    for i in range(NUM_CATEGORIA_TEMA):
        ativo = random.randint(0,1)
        titulo = fake.word().capitalize()
        f.write(f"({ativo}, '{titulo}'){',' if i < NUM_CATEGORIA_TEMA-1 else ';'}\n")

    # cliente
    f.write("\n-- cliente\nINSERT INTO cliente (created_at, nome, telefone, updated_at) VALUES\n")
    for i in range(NUM_CLIENTES):
        dt = rand_date()
        nome = fake.name()
        tel = fake.phone_number().replace(' ', '').replace('-', '')
        f.write(f"('{dt}', '{nome}', '{tel}', '{dt}'){',' if i < NUM_CLIENTES-1 else ';'}\n")

    # usuario
    f.write("\n-- usuario\nINSERT INTO usuario (cargo, cpf, created_at, data_nasc, email, foto, name, password, status, updated_at) VALUES\n")
    for i in range(NUM_USUARIOS):
        cargo = random.choice(['Administrador', 'Vendedor', 'Designer', 'Produção'])
        cpf = fake.cpf()
        created_at = rand_date()
        data_nasc = fake.date_of_birth(minimum_age=18, maximum_age=60)
        email = fake.email()
        foto = f'foto_{fake.md5(raw_output=False)}.jpg'
        name = fake.name()
        password = fake.password(length=12)
        status = random.randint(0,1)
        f.write(f"('{cargo}', '{cpf}', '{created_at}', '{data_nasc}', '{email}', '{foto}', '{name}', '{password}', {status}, NULL){',' if i < NUM_USUARIOS-1 else ';'}\n")

    # endereco_cliente: garantir pelo menos um por cliente
    f.write("\n-- endereco_cliente\nINSERT INTO endereco_cliente (ativo, bairro, cep, cidade, complemento, created_at, estado, logradouro, municipio, num_logradouro, uf, updated_at, cliente_id) VALUES\n")
    enderecos_gerados = 0
    for cliente_id in range(1, NUM_CLIENTES+1):
        ativo = random.randint(0,1)
        bairro = fake.bairro()
        cep = fake.postcode()
        cidade = fake.city()
        complemento = fake.street_address()
        created_at = rand_date()
        estado = fake.estado_nome()
        logradouro = fake.street_name()
        municipio = cidade
        num_logradouro = str(random.randint(1,9999))
        uf = fake.estado_sigla()
        f.write(f"({ativo}, '{bairro}', '{cep}', '{cidade}', '{complemento}', '{created_at}', '{estado}', '{logradouro}', '{municipio}', '{num_logradouro}', '{uf}', NULL, {cliente_id}){',' if cliente_id < NUM_CLIENTES -1 else ';' }\n")
        enderecos_gerados += 1

    # Gera o restante dos endereços normalmente
   

    # contraparte
    f.write("\n-- contraparte\nINSERT INTO contraparte (created_at, descricao, nome, segmento, status, tipo_contrato, updated_at) VALUES\n")
    for i in range(NUM_CONTRAPARTES):
        created_at = rand_date()
        descricao = fake.sentence(nb_words=3)
        nome = fake.company()
        segmento = random.choice(['Fornecedor', 'Cliente', 'Parceiro'])
        status = random.randint(0,1)
        tipo_contrato = random.choice(['Compra', 'Venda', 'Parceria'])
        f.write(f"('{created_at}', '{descricao}', '{nome}', '{segmento}', {status}, '{tipo_contrato}', NULL){',' if i < NUM_CONTRAPARTES-1 else ';'}\n")

    # item_produto
    f.write("\n-- item_produto\nINSERT INTO item_produto (altura, ativo, comprimento, created_at, custo_producao, descricao, largura, material, peso, prazo_producao, preco_promocional, preco_venda, updated_at) VALUES\n")
    for i in range(NUM_ITEM_PRODUTO):
        altura = round(random.uniform(5.0, 30.0),2)
        ativo = random.randint(0,1)
        comprimento = round(random.uniform(5.0, 30.0),2)
        created_at = rand_date()
        custo_producao = round(random.uniform(5.0, 50.0),2)
        descricao = fake.sentence(nb_words=3)
        largura = round(random.uniform(5.0, 30.0),2)
        material = random.choice(['Cerâmica', 'Papelão', 'Plástico', 'Vidro', 'Madeira'])
        peso = round(random.uniform(0.1, 2.0),2)
        prazo_producao = random.randint(3,15)
        preco_promocional = round(random.uniform(10.0, 100.0),2)
        preco_venda = preco_promocional + round(random.uniform(5.0, 30.0),2)
        f.write(f"({altura}, {ativo}, {comprimento}, '{created_at}', {custo_producao}, '{descricao}', {largura}, '{material}', {peso}, {prazo_producao}, {preco_promocional}, {preco_venda}, NULL){',' if i < NUM_ITEM_PRODUTO-1 else ';'}\n")

    # tema_produto
    f.write("\n-- tema_produto\nINSERT INTO tema_produto (ativo, created_at, descricao, updated_at, categoria_tema_id) VALUES\n")
    for i in range(NUM_TEMA_PRODUTO):
        ativo = random.randint(0,1)
        created_at = rand_date()
        descricao = random.choice(['Ben 10', 'Frozen', 'Casamento', 'Festa Junina', 'Aniversário', 'Natal', 'Super Heróis', 'Princesas'])
        categoria_tema_id = random.randint(1, NUM_CATEGORIA_TEMA)
        f.write(f"({ativo}, '{created_at}', '{descricao}', NULL, {categoria_tema_id}){',' if i < NUM_TEMA_PRODUTO-1 else ';'}\n")

    # produto
    f.write("\n-- produto\nINSERT INTO produto (ativo, created_at, descricao, titulo, updated_at, item_produto_id, tema_produto_id) VALUES\n")
    for i in range(NUM_PRODUTO):
        ativo = random.randint(0,1)
        created_at = rand_date()
        descricao = fake.sentence(nb_words=4)
        titulo = fake.word().capitalize() + " " + random.choice(['Caneca', 'Caderno', 'Copo', 'Kit'])
        item_produto_id = random.randint(1, NUM_ITEM_PRODUTO)
        tema_produto_id = random.randint(1, NUM_TEMA_PRODUTO)
        f.write(f"({ativo}, '{created_at}', '{descricao}', '{titulo}', NULL, {item_produto_id}, {tema_produto_id}){',' if i < NUM_PRODUTO-1 else ';'}\n")

    # foto_produto: garantir pelo menos uma por produto
    f.write("\n-- foto_produto\nINSERT INTO foto_produto (created_at, foto, updated_at, produto_id) VALUES\n")
    fotos_geradas=0
    for produto_id in range(1, NUM_PRODUTO+1):
        created_at=rand_date()
        foto_nome=f'foto_{produto_id}.jpg'
        f.write(f"('{created_at}','{foto_nome}',NULL,{produto_id}){',' if produto_id < NUM_PRODUTO - 1 else ';'}\n")
        fotos_geradas+=1
  

    # pedido
    f.write("\n-- pedido\nINSERT INTO pedido (ativo, created_at, data_limite, observacoes, origem, peso_total, preco_total, updated_at, cliente_id, usuario_id) VALUES\n")
    for i in range(NUM_PEDIDO):
        ativo=random.randint(0,1)
        created_at=rand_date()
        data_limite_dt=datetime.strptime(created_at,'%Y-%m-%d %H:%M:%S')+timedelta(days=random.randint(3,15))
        data_limite_str=data_limite_dt.strftime('%Y-%m-%d %H:%M:%S')
        observacoes=fake.sentence(nb_words=4)
        origem=random.choice(['Instagram','WhatsApp','Elo7','Site'])
        peso_total=round(random.uniform(0.2,3.0),2)
        preco_total=round(random.uniform(20.0,300.0),2)
        cliente_id=random.randint(1,NUM_CLIENTES)
        usuario_id=random.randint(1,NUM_USUARIOS)
        f.write(f"({ativo},'{created_at}','{data_limite_str}','{observacoes}','{origem}',{peso_total},{preco_total},NULL,{cliente_id},{usuario_id}){',' if i<NUM_PEDIDO-1 else ';'}\n")

    # status_pedido
    cores_status=['#FF0000','#00FF00','#0000FF','#FFFF00','#FFA500']
    nomes_status=['Novo','Em produção','Finalizado','Entregue','Cancelado']
    f.write("\n-- status_pedido\nINSERT INTO status_pedido (ativo, cor, created_at, ordem_kanban,status ,updated_at) VALUES\n")
    for i in range(NUM_STATUS_PEDIDO):
        ativo=random.randint(0,1)
        cor=cores_status[i%len(cores_status)]
        created_at=rand_date()
        ordem_kanban=i
        status=nomes_status[i%len(nomes_status)]
        f.write(f"({ativo},'{cor}','{created_at}',{ordem_kanban},'{status}',NULL){',' if i<NUM_STATUS_PEDIDO-1 else ';'}\n")

    # pedido_status_pedido: garantir pelo menos dois por pedido e só um com status atual=1
    f.write("\n-- pedido_status_pedido\nINSERT INTO pedido_status_pedido (created_at,status_atual ,updated_at,pedido_id,status_id) VALUES\n")
    psp_gerados=0
    for pedido_id in range(1, NUM_PEDIDO+1):
        created_at_1=rand_date()
        created_at_2=rand_date()
        
        status_ids=list(range(1,NUM_STATUS_PEDIDO+1))
        
        status_id_1=random.choice(status_ids)
        
        status_ids.remove(status_id_1)
        
        status_id_2=random.choice(status_ids)
        
        # Um com status atual=1 e outro com status atual=0
        f.write(f"('{created_at_1}',1,NULL,{pedido_id},{status_id_1}),\n")
        
        virgula_final=',' if psp_gerados<NUM_PEDIDO_STATUS_PEDIDO-2 else ''
        
        psp_gerados+=1
        
        virgula_final2=',' if psp_gerados<NUM_PEDIDO_STATUS_PEDIDO-2 else ''
        
        f.write(f"('{created_at_2}',0,NULL,{pedido_id},{status_id_2}){',' if i < NUM_PEDIDO-1 else ';'}\n")
        
        psp_gerados+=1

   

    # produto_pedido: garantir pelo menos um por pedido
    f.write("\n-- produto_pedido\nINSERT INTO produto_pedido (created_at,peso_total,peso_unitario ,preco_total ,preco_unitario ,qtd_produto ,updated_at ,pedido_id ,produto_id) VALUES\n")
    pp_gerados=0
    for pedido_id in range(1, NUM_PEDIDO+1):
      created_at=rand_date()
      peso_unitario=round(random.uniform(0.2,3.0),2)
      qtd_produto=random.randint(1,5)
      preco_unitario=round(random.uniform(20.0,150.0),2)
      preco_total=preco_unitario*qtd_produto
      peso_total=peso_unitario*qtd_produto
      produto_id=random.randint(1,NUM_PRODUTO)
      virgula_final=',' if pp_gerados<NUM_PRODUTO_PEDIDO-1 else ';'
      f.write(f"('{created_at}',{peso_total},{peso_unitario},{preco_total},{preco_unitario},{qtd_produto},NULL,{pedido_id},{produto_id}){',' if pedido_id < NUM_PEDIDO -1 else ';'}\n")
      pp_gerados+=1

    

    # movimentacao
    tipos=['Receita','Despesa']
    f.write("\n-- movimentacao\nINSERT INTO movimentacao (created_at ,descricao ,status ,tipo ,updated_at ,valor ,categoria_movimentacao_id ,contraparte_id) VALUES\n")
    for i in range(NUM_MOVIMENTACAO):
      created_at=rand_date()
      descricao=fake.sentence(nb_words=3)
      status=random.randint(0,1)
      tipo=random.choice(tipos)
      valor=round(random.uniform(-200.0 if tipo=='Despesa' else 20.0,
                                 -10.0 if tipo=='Despesa' else 500.0),2)
      categoria_movimentacao_id=random.randint(1,NUM_CATEGORIA_MOV)
      contraparte_id=random.randint(1,NUM_CONTRAPARTES)
      virgula_final=';' if i==NUM_MOVIMENTACAO-1 else ','
      f.write(f"('{created_at}','{descricao}',{status},'{tipo}',NULL,{valor},{categoria_movimentacao_id},{contraparte_id}){virgula_final}\n")

print("Arquivo popular_banco.sql gerado com sucesso!")