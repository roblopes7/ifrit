CREATE TABLE public.usuarios (
    id VARCHAR(255) NOT NULL,
    login VARCHAR(60) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    telefone VARCHAR(20),
    celular VARCHAR(20),
    perfil VARCHAR(20),
    ativo BOOLEAN
);

ALTER TABLE public.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);


