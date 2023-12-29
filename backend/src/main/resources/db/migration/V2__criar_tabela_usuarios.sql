CREATE TABLE public.usuarios (
    id character varying(255) NOT NULL,
    login character varying(60) NOT NULL UNIQUE,
    nome character varying(255) NOT NULL,
    senha character varying(255) NOT NULL,
    email character varying(255),
    telefone character varying(20),
    celular character varying(20),
    perfil character varying(20),
    ativo boolean
);

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);

