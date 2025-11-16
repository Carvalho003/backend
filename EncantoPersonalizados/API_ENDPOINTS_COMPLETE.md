# 📋 Mapeamento Completo de Endpoints - Sistema O Encanto

## 🎯 Visão Geral

Este documento mapeia **TODOS** os endpoints REST necessários para o sistema, organizados por **domínio** e indicando em **quais telas** são utilizados.

---

## 🔐 1. AUTENTICAÇÃO

### `POST /api/auth/login`
**Descrição**: Autenticação de funcionários no sistema  
**Usado em**:
- ✅ Tela de Login (App.tsx)

**Request Body**:
```json
{
  "email": "admin@encanto.com",
  "password": "admin123"
}
```

**Response**:
```json
{
  "token": "jwt_token_here",
  "funcionario": {
    "id": "uuid",
    "nome": "Maria Silva",
    "email": "maria@encanto.com",
    "cargo": "Social Media",
    "imagemUrl": "url_da_foto"
  }
}
```

---

### `POST /api/auth/logout`
**Descrição**: Encerra sessão do funcionário  
**Response**: 200 OK

---

### `POST /api/auth/forgot-password`
**Descrição**: Solicita recuperação de senha  
**Request Body**:
```json
{
  "email": "funcionario@encanto.com"
}
```
**Response**: 200 OK

---

### `POST /api/auth/reset-password`
**Descrição**: Redefine senha com token  
**Request Body**:
```json
{
  "token": "reset_token",
  "novaSenha": "novaSenha123"
}
```
**Response**: 200 OK

---

## 👥 2. FUNCIONÁRIOS (Usuários do Sistema)

### `GET /api/funcionarios`
**Descrição**: Lista todos os funcionários do sistema  
**Usado em**:
- ✅ Tela de Funcionários (a ser desenvolvida)
- ✅ Filtros e relatórios

**Query Params**:
- `page` (int, opcional): Número da página
- `size` (int, opcional): Tamanho da página
- `search` (string, opcional): Busca por nome/email/CPF
- `status` (string, opcional): Filtra por status (Ativo/Inativo)
- `cargo` (string, opcional): Filtra por cargo

**Response**:
```json
{
  "content": [
    {
      "id": "uuid",
      "nome": "Maria Silva",
      "cpf": "123.456.789-00",
      "dataNascimento": "1990-05-15",
      "email": "maria@encanto.com",
      "cargo": "Social Media",
      "status": "Ativo",
      "imagemUrl": "url_da_foto",
      "dataCadastro": "2024-01-15T10:30:00",
      "dataUltimaAtualizacao": "2025-11-10T14:20:00"
    }
  ],
  "totalElements": 10,
  "totalPages": 1
}
```

---

### `GET /api/funcionarios/{id}`
**Descrição**: Busca funcionário específico por ID  
**Response**: Funcionário completo

---

### `POST /api/funcionarios`
**Descrição**: Cadastra novo funcionário  
**Usado em**:
- ✅ Tela de Funcionários (modal de cadastro)

**Request Body**:
```json
{
  "nome": "Maria Silva",
  "cpf": "123.456.789-00",
  "dataNascimento": "1990-05-15",
  "email": "maria@encanto.com",
  "senha": "senhaSegura123",
  "cargo": "Social Media",
  "status": "Ativo",
  "imagemBase64": "data:image/jpeg;base64,..."
}
```

**Response**: Funcionário criado (201 Created)

---

### `PUT /api/funcionarios/{id}`
**Descrição**: Atualiza dados do funcionário  
**Request Body**: Mesmo formato do POST (senha opcional)

**Response**: Funcionário atualizado (200 OK)

---

### `DELETE /api/funcionarios/{id}`
**Descrição**: Remove funcionário (soft delete, muda status para Inativo)  
**Response**: 204 No Content

---

### `POST /api/funcionarios/{id}/upload-foto`
**Descrição**: Upload de foto do funcionário  
**Request**: multipart/form-data com arquivo de imagem  
**Response**: URL da imagem (200 OK)

---

### `PATCH /api/funcionarios/{id}/alterar-senha`
**Descrição**: Altera senha do funcionário  
**Request Body**:
```json
{
  "senhaAtual": "senhaAntiga123",
  "novaSenha": "novaSenha456"
}
```
**Response**: 200 OK

---

## 🧑‍🤝‍🧑 3. CLIENTES (Clientes Finais)

### `GET /api/clientes`
**Descrição**: Lista todos os clientes que fazem pedidos  
**Usado em**:
- ✅ Cadastro de Pedidos (select de clientes)
- ✅ Detalhamento de Pedido (select de clientes)
- ✅ Modal de Listagem de Clientes
- ✅ Tela Kanban (visualização de pedidos)

**Query Params**:
- `page` (int, opcional): Número da página
- `size` (int, opcional): Tamanho da página
- `search` (string, opcional): Busca por nome/telefone/email

**Response**:
```json
{
  "content": [
    {
      "id": "uuid",
      "nome": "João da Silva",
      "telefone": "(11) 98765-4321",
      "email": "joao@email.com",
      "enderecos": [
        {
          "id": "uuid",
          "cep": "01310-100",
          "rua": "Av. Paulista",
          "numero": "1578",
          "bairro": "Bela Vista",
          "cidade": "São Paulo",
          "estado": "SP",
          "complemento": "Apto 501"
        }
      ],
      "dataCadastro": "2024-03-10T09:15:00",
      "totalPedidos": 15,
      "valorTotalCompras": 2350.00
    }
  ],
  "totalElements": 150,
  "totalPages": 15
}
```

---

### `GET /api/clientes/{id}`
**Descrição**: Busca cliente específico por ID  
**Usado em**:
- ✅ Detalhamento de Pedido (ao carregar dados do cliente)
- ✅ Modal de Edição de Cliente

**Response**: Cliente completo com histórico de pedidos

---

### `POST /api/clientes`
**Descrição**: Cadastra novo cliente  
**Usado em**:
- ✅ Modal de Novo Cliente (Cadastro de Pedidos)
- ✅ Modal de Novo Cliente (Detalhamento de Pedido)

**Request Body**:
```json
{
  "nome": "João da Silva",
  "telefone": "(11) 98765-4321",
  "email": "joao@email.com",
  "enderecos": [
    {
      "cep": "01310-100",
      "rua": "Av. Paulista",
      "numero": "1578",
      "bairro": "Bela Vista",
      "cidade": "São Paulo",
      "estado": "SP",
      "complemento": "Apto 501"
    }
  ]
}
```

**Response**: Cliente criado (201 Created)

---

### `PUT /api/clientes/{id}`
**Descrição**: Atualiza cliente existente  
**Request Body**: Mesmo formato do POST  
**Response**: Cliente atualizado (200 OK)

---

### `DELETE /api/clientes/{id}`
**Descrição**: Remove cliente (soft delete)  
**Response**: 204 No Content

---

### `POST /api/clientes/{id}/enderecos`
**Descrição**: Adiciona novo endereço ao cliente  
**Request Body**:
```json
{
  "cep": "01310-100",
  "rua": "Av. Paulista",
  "numero": "1578",
  "bairro": "Bela Vista",
  "cidade": "São Paulo",
  "estado": "SP",
  "complemento": "Apto 501"
}
```
**Response**: Endereço criado (201 Created)

---

### `PUT /api/clientes/{clienteId}/enderecos/{enderecoId}`
**Descrição**: Atualiza endereço do cliente  
**Response**: Endereço atualizado (200 OK)

---

### `DELETE /api/clientes/{clienteId}/enderecos/{enderecoId}`
**Descrição**: Remove endereço do cliente  
**Response**: 204 No Content

---

## 📦 4. PRODUTOS

### `GET /api/produtos`
**Descrição**: Lista todos os produtos com filtros  
**Usado em**:
- ✅ Tela de Produtos (ProductsApp.tsx)
- ✅ Cadastro de Pedidos (galeria de produtos)
- ✅ Modal de Adicionar Produto (Detalhamento de Pedido)

**Query Params**:
- `search` (string, opcional): Busca por título, descrição
- `categoria` (uuid, opcional): Filtra por ID da categoria
- `tema` (uuid, opcional): Filtra por ID do tema
- `item` (uuid, opcional): Filtra por ID do item
- `page` (int, opcional): Número da página
- `size` (int, opcional): Tamanho da página

**Response**:
```json
{
  "content": [
    {
      "id": "uuid",
      "titulo": "Caneca do Ben 10",
      "descricao": "Caneca personalizada com estampa do Ben 10",
      "imagemUrl": "url_da_imagem",
      "categoria": {
        "id": "uuid",
        "nome": "Herói"
      },
      "tema": {
        "id": "uuid",
        "nome": "Ben 10",
        "categoriaId": "uuid"
      },
      "item": {
        "id": "uuid",
        "nome": "Caneca"
      },
      "precoUnitario": 35.00,
      "pesoUnitario": 0.35,
      "diasProducao": 3,
      "ativo": true,
      "fotosAdicionais": [
        "url_foto_1",
        "url_foto_2"
      ]
    }
  ],
  "totalElements": 150,
  "totalPages": 15
}
```

---

### `GET /api/produtos/{id}`
**Descrição**: Busca produto específico por ID  
**Response**: Produto completo com todas as fotos

---

### `POST /api/produtos`
**Descrição**: Cadastra novo produto  
**Usado em**:
- ✅ Tela de Cadastro de Produtos

**Request Body**:
```json
{
  "titulo": "Caneca do Ben 10",
  "descricao": "Caneca personalizada",
  "imagemPrincipalBase64": "data:image/jpeg;base64,...",
  "categoriaId": "uuid",
  "temaId": "uuid",
  "itemId": "uuid",
  "precoUnitario": 35.00,
  "pesoUnitario": 0.35,
  "diasProducao": 3
}
```

**Response**: Produto criado (201 Created)

---

### `PUT /api/produtos/{id}`
**Descrição**: Atualiza produto existente  
**Request Body**: Mesmo formato do POST  
**Response**: Produto atualizado (200 OK)

---

### `DELETE /api/produtos/{id}`
**Descrição**: Remove produto (soft delete, marca como inativo)  
**Response**: 204 No Content

---

### `POST /api/produtos/{id}/fotos`
**Descrição**: Adiciona foto adicional ao produto  
**Request**: multipart/form-data com arquivo de imagem  
**Response**: URL da foto (201 Created)

---

### `DELETE /api/produtos/{id}/fotos/{fotoId}`
**Descrição**: Remove foto adicional do produto  
**Response**: 204 No Content

---

## 🏷️ 5. CATEGORIAS DE PRODUTOS

### `GET /api/categorias-produtos`
**Descrição**: Lista todas as categorias de produtos  
**Usado em**:
- ✅ Tela de Produtos (filtros)
- ✅ Cadastro de Produtos (select de categoria)
- ✅ Modal de gerenciamento de categorias

**Response**:
```json
[
  {
    "id": "uuid",
    "nome": "Herói",
    "ordem": 1,
    "ativo": true,
    "quantidadeTemas": 5,
    "quantidadeProdutos": 25
  },
  {
    "id": "uuid",
    "nome": "Princesa",
    "ordem": 2,
    "ativo": true,
    "quantidadeTemas": 8,
    "quantidadeProdutos": 40
  }
]
```

---

### `POST /api/categorias-produtos`
**Descrição**: Cria nova categoria  
**Request Body**:
```json
{
  "nome": "Herói",
  "ordem": 1
}
```
**Response**: Categoria criada (201 Created)

---

### `PUT /api/categorias-produtos/{id}`
**Descrição**: Atualiza categoria  
**Response**: Categoria atualizada (200 OK)

---

### `DELETE /api/categorias-produtos/{id}`
**Descrição**: Remove categoria (se não tiver temas vinculados)  
**Response**: 204 No Content

---

## 🎨 6. TEMAS

### `GET /api/temas`
**Descrição**: Lista todos os temas  
**Usado em**:
- ✅ Tela de Produtos (filtros)
- ✅ Cadastro de Produtos (select de tema)
- ✅ Modal de gerenciamento de temas

**Query Params**:
- `categoriaId` (uuid, opcional): Filtra por categoria

**Response**:
```json
[
  {
    "id": "uuid",
    "nome": "Ben 10",
    "categoria": {
      "id": "uuid",
      "nome": "Herói"
    },
    "ordem": 1,
    "ativo": true,
    "quantidadeProdutos": 5
  }
]
```

---

### `POST /api/temas`
**Descrição**: Cria novo tema  
**Request Body**:
```json
{
  "nome": "Ben 10",
  "categoriaId": "uuid",
  "ordem": 1
}
```
**Response**: Tema criado (201 Created)

---

### `PUT /api/temas/{id}`
**Descrição**: Atualiza tema  
**Response**: Tema atualizado (200 OK)

---

### `DELETE /api/temas/{id}`
**Descrição**: Remove tema (se não tiver produtos vinculados)  
**Response**: 204 No Content

---

## 🎁 7. ITENS

### `GET /api/itens`
**Descrição**: Lista todos os tipos de itens  
**Usado em**:
- ✅ Tela de Produtos (filtros)
- ✅ Cadastro de Produtos (select de item)

**Response**:
```json
[
  {
    "id": "uuid",
    "nome": "Caneca",
    "ordem": 1,
    "ativo": true,
    "quantidadeProdutos": 50
  },
  {
    "id": "uuid",
    "nome": "Caderno",
    "ordem": 2,
    "ativo": true,
    "quantidadeProdutos": 35
  }
]
```

---

### `POST /api/itens`
**Descrição**: Cria novo item  
**Request Body**:
```json
{
  "nome": "Caneca",
  "ordem": 1
}
```
**Response**: Item criado (201 Created)

---

### `PUT /api/itens/{id}`
**Descrição**: Atualiza item  
**Response**: Item atualizado (200 OK)

---

### `DELETE /api/itens/{id}`
**Descrição**: Remove item (se não tiver produtos vinculados)  
**Response**: 204 No Content

---

## 📋 8. PEDIDOS

### `GET /api/pedidos`
**Descrição**: Lista todos os pedidos com filtros  
**Usado em**:
- ✅ Tela Kanban (colunas organizadas por status)
- ✅ Relatórios e dashboards

**Query Params**:
- `statusId` (uuid, opcional): Filtra por status
- `dataInicio` (date, opcional): Data inicial
- `dataFim` (date, opcional): Data final
- `clienteId` (uuid, opcional): Filtra por cliente
- `search` (string, opcional): Busca por código/cliente
- `page` (int, opcional)
- `size` (int, opcional)

**Response**:
```json
{
  "content": [
    {
      "id": "uuid",
      "codigo": "PED-001",
      "cliente": {
        "id": "uuid",
        "nome": "João da Silva",
        "telefone": "(11) 98765-4321"
      },
      "endereco": {
        "id": "uuid",
        "rua": "Av. Paulista",
        "numero": "1578",
        "bairro": "Bela Vista",
        "cidade": "São Paulo",
        "estado": "SP",
        "complemento": "Apto 501"
      },
      "observacoes": "Cliente pediu embalagem especial",
      "status": {
        "id": "uuid",
        "nome": "Em Andamento",
        "cor": "#F4ACB7",
        "ordem": 2
      },
      "produtos": [
        {
          "id": "uuid",
          "produto": {
            "id": "uuid",
            "titulo": "Caneca Ben 10",
            "imagemUrl": "url"
          },
          "quantidade": 2,
          "precoUnitario": 35.00,
          "precoTotal": 70.00,
          "pesoUnitario": 0.35,
          "pesoTotal": 0.70
        }
      ],
      "valorTotal": 70.00,
      "pesoTotal": 0.70,
      "dataPrevisaoEntrega": "2025-11-25",
      "dataCriacao": "2025-11-10T14:30:00",
      "dataAtualizacao": "2025-11-13T16:20:00"
    }
  ],
  "totalElements": 100,
  "totalPages": 10
}
```

---

### `GET /api/pedidos/{id}`
**Descrição**: Busca pedido específico por ID  
**Usado em**:
- ✅ Detalhamento de Pedido (modal completo)
- ✅ Tela Kanban (ao clicar no card)

**Response**: Pedido completo (mesmo formato da lista acima)

---

### `POST /api/pedidos`
**Descrição**: Cria novo pedido  
**Usado em**:
- ✅ Cadastro de Pedidos

**Request Body**:
```json
{
  "clienteId": "uuid",
  "enderecoId": "uuid",
  "observacoes": "Cliente pediu embalagem especial",
  "statusId": "uuid",
  "produtos": [
    {
      "produtoId": "uuid",
      "quantidade": 2,
      "precoUnitario": 35.00,
      "pesoUnitario": 0.35
    }
  ]
}
```

**Response**: Pedido criado (201 Created)

---

### `PUT /api/pedidos/{id}`
**Descrição**: Atualiza pedido completo  
**Usado em**:
- ✅ Detalhamento de Pedido (botão "Salvar Alterações")

**Request Body**: Mesmo formato do POST  
**Response**: Pedido atualizado (200 OK)

---

### `PATCH /api/pedidos/{id}/status`
**Descrição**: Atualiza apenas o status do pedido  
**Usado em**:
- ✅ Tela Kanban (drag & drop entre colunas)
- ✅ Detalhamento de Pedido (mudança de status)

**Request Body**:
```json
{
  "statusId": "uuid"
}
```

**Response**: 200 OK

---

### `POST /api/pedidos/{id}/produtos`
**Descrição**: Adiciona produto ao pedido  
**Usado em**:
- ✅ Detalhamento de Pedido (botão "Adicionar Produto")

**Request Body**:
```json
{
  "produtoId": "uuid",
  "quantidade": 1,
  "precoUnitario": 35.00,
  "pesoUnitario": 0.35
}
```

**Response**: 201 Created

---

### `PUT /api/pedidos/{pedidoId}/produtos/{produtoId}`
**Descrição**: Atualiza produto do pedido (quantidade, preço)  
**Request Body**:
```json
{
  "quantidade": 3,
  "precoUnitario": 35.00
}
```
**Response**: 200 OK

---

### `DELETE /api/pedidos/{pedidoId}/produtos/{produtoId}`
**Descrição**: Remove produto do pedido  
**Usado em**:
- ✅ Detalhamento de Pedido (botão "Remover" do produto)

**Response**: 204 No Content

---

### `DELETE /api/pedidos/{id}`
**Descrição**: Remove pedido (soft delete)  
**Response**: 204 No Content

---

## 📊 9. STATUS DE PEDIDOS

### `GET /api/status-pedidos`
**Descrição**: Lista todos os status disponíveis  
**Usado em**:
- ✅ Cadastro de Pedidos (select de status)
- ✅ Detalhamento de Pedido (select de status)
- ✅ Tela Kanban (colunas dinâmicas)

**Response**:
```json
[
  {
    "id": "uuid",
    "nome": "A Fazer",
    "cor": "#FFCAD4",
    "ordem": 1,
    "sistema": true,
    "quantidadePedidos": 15
  },
  {
    "id": "uuid",
    "nome": "Em Andamento",
    "cor": "#F4ACB7",
    "ordem": 2,
    "sistema": true,
    "quantidadePedidos": 23
  },
  {
    "id": "uuid",
    "nome": "Para Enviar",
    "cor": "#FFE5D9",
    "ordem": 3,
    "sistema": true,
    "quantidadePedidos": 8
  },
  {
    "id": "uuid",
    "nome": "Enviados",
    "cor": "#D8E2DC",
    "ordem": 4,
    "sistema": true,
    "quantidadePedidos": 45
  }
]
```

---

### `POST /api/status-pedidos`
**Descrição**: Cria novo status personalizado  
**Usado em**:
- ✅ Tela Kanban (gerenciamento de status)

**Request Body**:
```json
{
  "nome": "Aguardando Material",
  "cor": "#9D8189",
  "ordem": 5
}
```

**Response**: Status criado (201 Created)

---

### `PUT /api/status-pedidos/{id}`
**Descrição**: Atualiza status existente  
**Response**: Status atualizado (200 OK)

---

### `DELETE /api/status-pedidos/{id}`
**Descrição**: Remove status personalizado (não permite remover status do sistema)  
**Response**: 204 No Content

---

### `PATCH /api/status-pedidos/reordenar`
**Descrição**: Reordena os status (drag & drop nas colunas)  
**Request Body**:
```json
{
  "ordenacao": [
    { "id": "uuid", "ordem": 1 },
    { "id": "uuid", "ordem": 2 },
    { "id": "uuid", "ordem": 3 }
  ]
}
```
**Response**: 200 OK

---

## 💰 10. FINANCEIRO - RECEITAS

### `GET /api/financeiro/receitas`
**Descrição**: Lista receitas com filtros e totalizadores  
**Usado em**:
- ✅ Dashboard Financeiro (FinanceApp.tsx - KPIs e gráficos)

**Query Params**:
- `dataInicio` (date, obrigatório): Data inicial
- `dataFim` (date, obrigatório): Data final
- `categoriaId` (uuid, opcional): Filtra por categoria de produto
- `temaId` (uuid, opcional): Filtra por tema

**Response**:
```json
{
  "total": 28000.00,
  "variacao": 12.5,
  "receitas": [
    {
      "id": "uuid",
      "descricao": "Venda - Pedido PED-001",
      "valor": 70.00,
      "data": "2025-11-15",
      "categoria": {
        "id": "uuid",
        "nome": "Herói"
      },
      "tema": {
        "id": "uuid",
        "nome": "Ben 10"
      },
      "pedidoId": "uuid"
    }
  ]
}
```

---

### `GET /api/financeiro/receitas/evolucao-mensal`
**Descrição**: Retorna evolução de receitas mês a mês  
**Usado em**:
- ✅ Dashboard Financeiro (gráfico de evolução mensal)

**Query Params**:
- `ano` (int, obrigatório): Ano
- `meses` (int, opcional): Quantidade de meses (padrão: 6)

**Response**:
```json
[
  {
    "mes": "Jan",
    "ano": 2025,
    "receitas": 15000.00,
    "despesas": 8500.00,
    "lucro": 6500.00
  },
  {
    "mes": "Fev",
    "ano": 2025,
    "receitas": 18000.00,
    "despesas": 9200.00,
    "lucro": 8800.00
  }
]
```

---

### `GET /api/financeiro/receitas/por-categoria`
**Descrição**: Retorna receitas agrupadas por categoria de produto  
**Usado em**:
- ✅ Dashboard Financeiro (gráfico de categorias mais vendidas)

**Query Params**:
- `dataInicio` (date, obrigatório)
- `dataFim` (date, obrigatório)

**Response**:
```json
[
  {
    "categoria": "Herói",
    "receita": 12500.00,
    "quantidadePedidos": 45,
    "percentual": 35.5
  },
  {
    "categoria": "Princesa",
    "receita": 18200.00,
    "quantidadePedidos": 67,
    "percentual": 51.7
  }
]
```

---

### `GET /api/financeiro/receitas/por-tema`
**Descrição**: Retorna receitas agrupadas por tema  
**Query Params**:
- `dataInicio` (date, obrigatório)
- `dataFim` (date, obrigatório)
- `categoriaId` (uuid, opcional): Filtra por categoria

**Response**:
```json
[
  {
    "tema": "Ben 10",
    "categoria": "Herói",
    "receita": 3500.00,
    "quantidadePedidos": 12
  }
]
```

---

## 💸 11. FINANCEIRO - DESPESAS

### `GET /api/financeiro/despesas`
**Descrição**: Lista despesas com filtros  
**Usado em**:
- ✅ Dashboard Financeiro (KPIs e listagens)
- ✅ Tela de Gestão de Despesas (a ser desenvolvida)

**Query Params**:
- `dataInicio` (date, obrigatório)
- `dataFim` (date, obrigatório)
- `categoriaId` (uuid, opcional): Filtra por categoria de movimentação
- `contraparteId` (uuid, opcional): Filtra por contraparte
- `pago` (boolean, opcional): Filtra por status de pagamento

**Response**:
```json
{
  "total": 12000.00,
  "totalPago": 8000.00,
  "totalAPagar": 4000.00,
  "variacao": -5.3,
  "despesas": [
    {
      "id": "uuid",
      "titulo": "Salário - Maria Silva",
      "descricao": "Pagamento mensal - Novembro 2025",
      "valor": 3500.00,
      "dataVencimento": "2025-11-18",
      "dataPagamento": null,
      "pago": false,
      "categoria": {
        "id": "uuid",
        "nome": "Funcionários"
      },
      "contraparte": {
        "id": "uuid",
        "nome": "Maria Silva",
        "tipo": "FUNCIONARIO"
      },
      "recorrente": true,
      "observacoes": "Transferência via PIX"
    }
  ]
}
```

---

### `GET /api/financeiro/despesas/proximos-pagamentos`
**Descrição**: Lista despesas futuras não pagas ordenadas por vencimento  
**Usado em**:
- ✅ Dashboard Financeiro (box "Próximos Pagamentos")
- ✅ KPI "A Pagar"

**Query Params**:
- `dias` (int, opcional): Próximos X dias (padrão: 30)

**Response**:
```json
[
  {
    "id": "uuid",
    "titulo": "Salário - Maria Silva",
    "valor": 3500.00,
    "dataVencimento": "2025-11-18",
    "categoria": "Funcionários",
    "contraparte": "Maria Silva",
    "diasRestantes": 3,
    "atrasado": false
  },
  {
    "id": "uuid",
    "titulo": "Fornecedor - Canecas",
    "valor": 1200.00,
    "dataVencimento": "2025-11-12",
    "categoria": "Fornecedores",
    "contraparte": "Gráfica XYZ",
    "diasRestantes": -3,
    "atrasado": true
  }
]
```

---

### `GET /api/financeiro/despesas/por-categoria`
**Descrição**: Retorna despesas agrupadas por categoria  
**Usado em**:
- ✅ Dashboard Financeiro (box "Despesas por Categoria")

**Query Params**:
- `dataInicio` (date, obrigatório)
- `dataFim` (date, obrigatório)

**Response**:
```json
[
  {
    "categoria": "Funcionários",
    "valor": 15000.00,
    "quantidadeDespesas": 5,
    "percentual": 60.0
  },
  {
    "categoria": "Fornecedores",
    "valor": 8500.00,
    "quantidadeDespesas": 12,
    "percentual": 34.0
  }
]
```

---

### `POST /api/financeiro/despesas`
**Descrição**: Cria nova despesa  
**Usado em**:
- ✅ Tela de Gestão Financeira (modal de cadastro)

**Request Body**:
```json
{
  "titulo": "Salário - Maria Silva",
  "descricao": "Pagamento mensal - Novembro 2025",
  "valor": 3500.00,
  "dataVencimento": "2025-11-18",
  "categoriaId": "uuid",
  "contraparteId": "uuid",
  "recorrente": true,
  "observacoes": "Transferência via PIX"
}
```

**Response**: Despesa criada (201 Created)

---

### `PUT /api/financeiro/despesas/{id}`
**Descrição**: Atualiza despesa  
**Response**: Despesa atualizada (200 OK)

---

### `DELETE /api/financeiro/despesas/{id}`
**Descrição**: Remove despesa  
**Response**: 204 No Content

---

### `PATCH /api/financeiro/despesas/{id}/marcar-pago`
**Descrição**: Marca despesa como paga  
**Request Body**:
```json
{
  "dataPagamento": "2025-11-15",
  "observacoes": "Pago via PIX"
}
```

**Response**: 200 OK

---

### `PATCH /api/financeiro/despesas/{id}/marcar-pendente`
**Descrição**: Marca despesa como pendente novamente  
**Response**: 200 OK

---

## 📂 12. CATEGORIAS DE MOVIMENTAÇÃO FINANCEIRA

### `GET /api/categorias-movimentacao`
**Descrição**: Lista categorias de movimentação financeira  
**Usado em**:
- ✅ Dashboard Financeiro (filtros)
- ✅ Cadastro de Despesas

**Query Params**:
- `tipo` (string, opcional): RECEITA ou DESPESA

**Response**:
```json
[
  {
    "id": "uuid",
    "nome": "Funcionários",
    "tipo": "DESPESA",
    "sistema": true,
    "ativo": true
  },
  {
    "id": "uuid",
    "nome": "Fornecedores",
    "tipo": "DESPESA",
    "sistema": false,
    "ativo": true
  },
  {
    "id": "uuid",
    "nome": "Prolabore",
    "tipo": "DESPESA",
    "sistema": false,
    "ativo": true
  }
]
```

---

### `POST /api/categorias-movimentacao`
**Descrição**: Cria nova categoria  
**Request Body**:
```json
{
  "nome": "Marketing",
  "tipo": "DESPESA"
}
```
**Response**: Categoria criada (201 Created)

---

### `PUT /api/categorias-movimentacao/{id}`
**Descrição**: Atualiza categoria  
**Response**: Categoria atualizada (200 OK)

---

### `DELETE /api/categorias-movimentacao/{id}`
**Descrição**: Remove categoria (não permite remover categorias do sistema)  
**Response**: 204 No Content

---

## 👥 13. CONTRAPARTES (Entidades Financeiras)

### `GET /api/contrapartes`
**Descrição**: Lista contrapartes (funcionários, fornecedores, freelancers)  
**Usado em**:
- ✅ Cadastro de Despesas (select de contraparte)
- ✅ Filtros financeiros
- ✅ Modal de gerenciamento de contrapartes

**Query Params**:
- `tipo` (string, opcional): FUNCIONARIO, FORNECEDOR, FREELANCER
- `search` (string, opcional): Busca por nome/documento

**Response**:
```json
[
  {
    "id": "uuid",
    "nome": "Maria Silva",
    "tipo": "FUNCIONARIO",
    "documento": "123.456.789-00",
    "telefone": "(11) 98765-4321",
    "email": "maria@encanto.com",
    "ativo": true,
    "funcionarioId": "uuid"
  },
  {
    "id": "uuid",
    "nome": "Gráfica XYZ Ltda",
    "tipo": "FORNECEDOR",
    "documento": "12.345.678/0001-90",
    "telefone": "(11) 3456-7890",
    "email": "contato@graficaxyz.com",
    "ativo": true,
    "funcionarioId": null
  }
]
```

---

### `GET /api/contrapartes/{id}`
**Descrição**: Busca contraparte específica  
**Response**: Contraparte completa

---

### `POST /api/contrapartes`
**Descrição**: Cria nova contraparte  
**Request Body**:
```json
{
  "nome": "Freelancer ABC",
  "tipo": "FREELANCER",
  "documento": "987.654.321-00",
  "telefone": "(11) 99876-5432",
  "email": "freelancer@email.com",
  "funcionarioId": null
}
```

**Response**: Contraparte criada (201 Created)

---

### `PUT /api/contrapartes/{id}`
**Descrição**: Atualiza contraparte  
**Response**: Contraparte atualizada (200 OK)

---

### `DELETE /api/contrapartes/{id}`
**Descrição**: Remove contraparte (soft delete)  
**Response**: 204 No Content

---

## 📊 14. DASHBOARD & RELATÓRIOS

### `GET /api/dashboard/resumo`
**Descrição**: Retorna resumo geral do dashboard  
**Usado em**:
- ✅ Tela inicial administrativa

**Query Params**:
- `dataInicio` (date, obrigatório)
- `dataFim` (date, obrigatório)

**Response**:
```json
{
  "periodo": {
    "dataInicio": "2025-11-01",
    "dataFim": "2025-11-30"
  },
  "receitas": {
    "total": 28000.00,
    "variacao": 12.5
  },
  "despesas": {
    "total": 12000.00,
    "variacao": -5.3
  },
  "lucro": {
    "total": 16000.00,
    "variacao": 23.1
  },
  "pedidos": {
    "total": 45,
    "porStatus": {
      "A Fazer": 8,
      "Em Andamento": 12,
      "Para Enviar": 15,
      "Enviados": 10
    }
  },
  "proximosPagamentos": 4500.00,
  "topCategorias": [
    {
      "categoria": "Princesa",
      "receita": 12000.00,
      "percentual": 42.8
    }
  ]
}
```

---

### `GET /api/dashboard/pedidos-hoje`
**Descrição**: Lista pedidos com previsão de entrega para hoje  
**Response**: Lista de pedidos

---

### `GET /api/dashboard/produtos-mais-vendidos`
**Descrição**: Lista produtos mais vendidos  
**Query Params**:
- `dataInicio` (date, obrigatório)
- `dataFim` (date, obrigatório)
- `limit` (int, opcional): Quantidade de resultados (padrão: 10)

**Response**:
```json
[
  {
    "produto": {
      "id": "uuid",
      "titulo": "Caneca Ben 10",
      "imagemUrl": "url"
    },
    "quantidadeVendida": 45,
    "receitaTotal": 1575.00
  }
]
```

---

## 🔔 15. NOTIFICAÇÕES

### `GET /api/notificacoes`
**Descrição**: Lista notificações do usuário logado  
**Query Params**:
- `lida` (boolean, opcional): Filtra por lida/não lida
- `page` (int, opcional)
- `size` (int, opcional)

**Response**:
```json
{
  "content": [
    {
      "id": "uuid",
      "titulo": "Pedido pronto para envio",
      "mensagem": "O pedido PED-001 está pronto para envio",
      "tipo": "PEDIDO",
      "lida": false,
      "data": "2025-11-15T14:30:00",
      "link": "/pedidos/uuid"
    }
  ],
  "totalNaoLidas": 5,
  "totalElements": 20,
  "totalPages": 2
}
```

---

### `PATCH /api/notificacoes/{id}/marcar-lida`
**Descrição**: Marca notificação como lida  
**Response**: 200 OK

---

### `PATCH /api/notificacoes/marcar-todas-lidas`
**Descrição**: Marca todas as notificações como lidas  
**Response**: 200 OK

---

## 🎯 RESUMO POR DOMÍNIO

| Domínio | Quantidade de Endpoints |
|---------|------------------------|
| **1. Autenticação** | 4 |
| **2. Funcionários** | 8 |
| **3. Clientes** | 9 |
| **4. Produtos** | 7 |
| **5. Categorias de Produtos** | 4 |
| **6. Temas** | 4 |
| **7. Itens** | 4 |
| **8. Pedidos** | 8 |
| **9. Status de Pedidos** | 5 |
| **10. Receitas** | 4 |
| **11. Despesas** | 8 |
| **12. Categorias de Movimentação** | 4 |
| **13. Contrapartes** | 5 |
| **14. Dashboard** | 3 |
| **15. Notificações** | 3 |
| **TOTAL** | **80 endpoints** |

---

## 📋 RESUMO POR TELA

### 🔐 Login (App.tsx)
- `POST /api/auth/login`
- `POST /api/auth/forgot-password`

### 👥 Funcionários
- `GET /api/funcionarios`
- `POST /api/funcionarios`
- `PUT /api/funcionarios/{id}`
- `DELETE /api/funcionarios/{id}`
- `POST /api/funcionarios/{id}/upload-foto`
- `PATCH /api/funcionarios/{id}/alterar-senha`

### 📦 Produtos (ProductsApp.tsx)
- `GET /api/produtos`
- `GET /api/categorias-produtos`
- `GET /api/temas`
- `GET /api/itens`
- `POST /api/categorias-produtos`
- `POST /api/temas`
- `POST /api/itens`

### 📝 Cadastro de Produtos
- `POST /api/produtos`
- `POST /api/produtos/{id}/fotos`
- `GET /api/categorias-produtos`
- `GET /api/temas`
- `GET /api/itens`

### 🎨 Kanban de Pedidos
- `GET /api/pedidos`
- `GET /api/status-pedidos`
- `PATCH /api/pedidos/{id}/status`
- `GET /api/pedidos/{id}`
- `POST /api/status-pedidos`
- `PUT /api/status-pedidos/{id}`
- `PATCH /api/status-pedidos/reordenar`

### 📋 Cadastro de Pedidos
- `GET /api/clientes`
- `POST /api/clientes`
- `GET /api/produtos`
- `GET /api/status-pedidos`
- `POST /api/pedidos`

### 🔍 Detalhamento de Pedido
- `GET /api/pedidos/{id}`
- `PUT /api/pedidos/{id}`
- `POST /api/pedidos/{id}/produtos`
- `PUT /api/pedidos/{pedidoId}/produtos/{produtoId}`
- `DELETE /api/pedidos/{pedidoId}/produtos/{produtoId}`
- `GET /api/clientes`
- `GET /api/produtos`

### 💰 Dashboard Financeiro (FinanceApp.tsx)
- `GET /api/financeiro/receitas`
- `GET /api/financeiro/despesas`
- `GET /api/financeiro/despesas/proximos-pagamentos`
- `GET /api/financeiro/receitas/evolucao-mensal`
- `GET /api/financeiro/receitas/por-categoria`
- `GET /api/financeiro/despesas/por-categoria`
- `GET /api/categorias-movimentacao`

### 💸 Gestão de Despesas
- `GET /api/financeiro/despesas`
- `POST /api/financeiro/despesas`
- `PUT /api/financeiro/despesas/{id}`
- `DELETE /api/financeiro/despesas/{id}`
- `PATCH /api/financeiro/despesas/{id}/marcar-pago`
- `GET /api/contrapartes`
- `GET /api/categorias-movimentacao`

---

## 💡 RECOMENDAÇÕES DE IMPLEMENTAÇÃO

### 1. **Paginação**
- Implementar em todos os endpoints GET que retornam listas
- Padrão Spring Data: `Pageable` e `Page<T>`

### 2. **Validações**
- Bean Validation em todos os DTOs
- Validações customizadas para regras de negócio

### 3. **Segurança**
- JWT para autenticação
- Roles: ADMIN, GERENTE, FUNCIONARIO
- Endpoints protegidos com `@PreAuthorize`

### 4. **Upload de Arquivos**
- Armazenamento em S3 ou servidor local
- Validação de tipo e tamanho de arquivo
- Geração de thumbnails para fotos

### 5. **Cache**
- Redis para cache de:
  - Status de pedidos
  - Categorias, temas e itens
  - Dados estáticos

### 6. **WebSocket** (Opcional)
- Notificações em tempo real
- Atualização do Kanban em tempo real

### 7. **Datas e Timezone**
- Padronizar formato ISO 8601
- Considerar timezone do servidor
- Usar `LocalDate`, `LocalDateTime` e `ZonedDateTime`

### 8. **Soft Delete**
- Implementar em todas as entidades principais
- Campo `ativo` ou `deletedAt`

### 9. **Auditoria**
- Campos de auditoria em todas as entidades:
  - `dataCriacao`
  - `dataAtualizacao`
  - `criadoPor`
  - `atualizadoPor`

### 10. **Performance**
- Índices em campos de busca frequente
- Lazy loading em relacionamentos
- Projeções para queries específicas

---

## 🔄 RELACIONAMENTOS PRINCIPAIS

```
CATEGORIA_PRODUTO (1) ─── (N) TEMA (1) ─── (N) PRODUTO
                                                   │
                                                   │ (N)
ITEM (1) ──────────────────────────────────── (N) │
                                                   │
                                                   │ (N)
CLIENTE (1) ─── (N) ENDERECO              PEDIDO_PRODUTO (N) ─── (1) PEDIDO
                    │                              │
                    │ (1)                          │
                    │                              │ (1)
                    └──────────────────────── (1) PEDIDO (N) ─── (1) STATUS_PEDIDO


FUNCIONARIO (1) ─── (1) CONTRAPARTE (1) ─── (N) DESPESA (N) ─── (1) CATEGORIA_MOVIMENTACAO
```

---

**Documento gerado em**: 15/11/2025  
**Versão**: 2.0 - COMPLETO E REVISADO  
**Status**: ✅ Pronto para implementação Spring Boot
