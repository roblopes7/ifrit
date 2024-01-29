-- Criação da tabela "cidade"
CREATE TABLE public.cidade (
    nome VARCHAR(255),
    pais VARCHAR(255),
    id VARCHAR(255) NOT NULL,
    uf VARCHAR(255)
);

-- Criação da sequência "cidade_seq"
CREATE SEQUENCE public.cidade_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Criação da tabela "cliente"
CREATE TABLE public.cliente (
    id VARCHAR(255) NOT NULL,
    nome_fantasia VARCHAR(255),
    razao_social VARCHAR(255),
    responsavel VARCHAR(255),
    idcliente_contato VARCHAR(255),
    idcliente_endereco VARCHAR(255),
    cnpj VARCHAR(255)
);

-- Criação da tabela "cliente_contato"
CREATE TABLE public.cliente_contato (
    id VARCHAR(255) NOT NULL,
    celular VARCHAR(25),
    celular2 VARCHAR(25),
    email VARCHAR(50),
    email2 VARCHAR(50),
    responsavel VARCHAR(255),
    telefone VARCHAR(25),
    telefone2 VARCHAR(25)
);

-- Criação da tabela "cliente_endereco"
CREATE TABLE public.cliente_endereco (
    id VARCHAR(255) NOT NULL,
    bairro VARCHAR(255),
    cep VARCHAR(9),
    logradouro VARCHAR(255),
    numero VARCHAR(20),
    idcidade VARCHAR(255)
);

-- Criação da tabela "empresa"
CREATE TABLE public.empresa (
    id VARCHAR(255) NOT NULL,
    idempresa_contato VARCHAR(255),
    idempresa_endereco VARCHAR(255),
    nome_fantasia VARCHAR(255),
    razao_social VARCHAR(255),
    responsavel VARCHAR(255),
    cnpj VARCHAR(255)
);

-- Criação da tabela "empresa_contato"
CREATE TABLE public.empresa_contato (
    celular VARCHAR(25),
    celular2 VARCHAR(25),
    telefone VARCHAR(25),
    telefone2 VARCHAR(25),
    email VARCHAR(50),
    email2 VARCHAR(50),
    id VARCHAR(255) NOT NULL,
    responsavel VARCHAR(255)
);

-- Criação da tabela "empresa_endereco"
CREATE TABLE public.empresa_endereco (
    cep VARCHAR(9),
    numero VARCHAR(20),
    bairro VARCHAR(255),
    id VARCHAR(255) NOT NULL,
    idcidade VARCHAR(255),
    logradouro VARCHAR(255)
);

-- Criação da tabela "fatura"
CREATE TABLE public.fatura (
    id VARCHAR(255) NOT NULL,
    competencia VARCHAR(255),
    descricao VARCHAR(255),
    emissao DATE,
    observacao VARCHAR(255),
    status VARCHAR(255),
    valor NUMERIC(38,2),
    vencimento DATE,
    idcliente VARCHAR(255),
    idempresa VARCHAR(255),
    CONSTRAINT fatura_status_check CHECK (status IN ('PENDENTE', 'CANCELADA', 'PAGA'))
);

-- Criação da tabela "fatura_pagamento"
CREATE TABLE public.fatura_pagamento (
    id VARCHAR(255) NOT NULL,
    data_recebimento DATE,
    valor_desconto NUMERIC(38,2),
    observacao VARCHAR(255),
    valor_pago NUMERIC(38,2),
    idfatura VARCHAR(255)
);

-- Criação da tabela "mensalidade"
CREATE TABLE public.mensalidade (
    id VARCHAR(255) NOT NULL,
    competencia VARCHAR(255),
    descricao VARCHAR(255),
    emissao DATE,
    observacao VARCHAR(255),
    valor NUMERIC(38,2),
    vencimento DATE,
    idcliente VARCHAR(255),
    idempresa VARCHAR(255)
);

-- Adicionando chaves primárias
ALTER TABLE public.cidade ADD CONSTRAINT cidade_pkey PRIMARY KEY (id);
ALTER TABLE public.cliente_contato ADD CONSTRAINT cliente_contato_pkey PRIMARY KEY (id);
ALTER TABLE public.cliente_endereco ADD CONSTRAINT cliente_endereco_pkey PRIMARY KEY (id);
ALTER TABLE public.cliente ADD CONSTRAINT cliente_pkey PRIMARY KEY (id);
ALTER TABLE public.empresa_contato ADD CONSTRAINT empresa_contato_pkey PRIMARY KEY (id);
ALTER TABLE public.empresa_endereco ADD CONSTRAINT empresa_endereco_pkey PRIMARY KEY (id);
ALTER TABLE public.empresa ADD CONSTRAINT empresa_pkey PRIMARY KEY (id);
ALTER TABLE public.fatura_pagamento ADD CONSTRAINT fatura_pagamento_pkey PRIMARY KEY (id);
ALTER TABLE public.fatura ADD CONSTRAINT fatura_pkey PRIMARY KEY (id);
ALTER TABLE public.mensalidade ADD CONSTRAINT mensalidade_pkey PRIMARY KEY (id);

-- Adicionando chaves estrangeiras
ALTER TABLE public.mensalidade ADD CONSTRAINT mensalidade_cliente_fk FOREIGN KEY (idcliente) REFERENCES public.cliente(id);
ALTER TABLE public.empresa ADD CONSTRAINT empresa_contato_fk FOREIGN KEY (idempresa_contato) REFERENCES public.empresa_contato(id);
ALTER TABLE public.fatura ADD CONSTRAINT fatura_empresa_fk FOREIGN KEY (idempresa) REFERENCES public.empresa(id);
ALTER TABLE public.cliente ADD CONSTRAINT cliente_endereco_fk FOREIGN KEY (idcliente_endereco) REFERENCES public.cliente_endereco(id);
ALTER TABLE public.fatura_pagamento ADD CONSTRAINT fatura_pagamento_fatura_fk FOREIGN KEY (idfatura) REFERENCES public.fatura(id);
ALTER TABLE public.cliente ADD CONSTRAINT cliente_cliente_contato_fk FOREIGN KEY (idcliente_contato) REFERENCES public.cliente_contato(id);
ALTER TABLE public.empresa ADD CONSTRAINT empresa_empresa_endereco_fk FOREIGN KEY (idempresa_endereco) REFERENCES public.empresa_endereco(id);
ALTER TABLE public.fatura ADD CONSTRAINT fatura_cliente_fk FOREIGN KEY (idcliente) REFERENCES public.cliente(id);
ALTER TABLE public.mensalidade ADD CONSTRAINT mensalidade_empresa_fk FOREIGN KEY (idempresa) REFERENCES public.empresa(id);
ALTER TABLE public.empresa_endereco ADD CONSTRAINT empresa_endereco_cidade_fk FOREIGN KEY (idcidade) REFERENCES public.cidade(id);
ALTER TABLE public.cliente_endereco ADD CONSTRAINT cliente_endereco_cidade_fk FOREIGN KEY (idcidade) REFERENCES public.cidade(id);
