--
-- PostgreSQL database dump
--

-- Dumped from database version 13.5
-- Dumped by pg_dump version 13.5

-- Started on 2023-09-30 17:27:41

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 201 (class 1259 OID 47999)
-- Name: cidade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cidade (
    codigo_ibge character varying(255),
    nome character varying(255),
    pais character varying(255),
    string character varying(255) NOT NULL,
    uf character varying(255)
);


ALTER TABLE public.cidade OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 47825)
-- Name: cidade_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cidade_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cidade_seq OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 48049)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    id character varying(255) NOT NULL,
    nome_fantasia character varying(255),
    razao_social character varying(255),
    responsavel character varying(255),
    idcliente_contato character varying(255),
    idcliente_endereco character varying(255),
    cnpj character varying(255)
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 48057)
-- Name: cliente_contato; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente_contato (
    id character varying(255) NOT NULL,
    celular character varying(25),
    celular2 character varying(25),
    email character varying(50),
    email2 character varying(50),
    responsavel character varying(255),
    telefone character varying(25),
    telefone2 character varying(25)
);


ALTER TABLE public.cliente_contato OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 48065)
-- Name: cliente_endereco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente_endereco (
    id character varying(255) NOT NULL,
    bairro character varying(255),
    cep character varying(9),
    logradouro character varying(255),
    numero character varying(20),
    idcidade character varying(255)
);


ALTER TABLE public.cliente_endereco OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 48007)
-- Name: empresa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.empresa (
    id character varying(255) NOT NULL,
    idempresa_contato character varying(255),
    idempresa_endereco character varying(255),
    nome_fantasia character varying(255),
    razao_social character varying(255),
    responsavel character varying(255),
    cnpj character varying(255)
);


ALTER TABLE public.empresa OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 48015)
-- Name: empresa_contato; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.empresa_contato (
    celular character varying(25),
    celular2 character varying(25),
    telefone character varying(25),
    telefone2 character varying(25),
    email character varying(50),
    email2 character varying(50),
    id character varying(255) NOT NULL,
    responsavel character varying(255)
);


ALTER TABLE public.empresa_contato OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 48023)
-- Name: empresa_endereco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.empresa_endereco (
    cep character varying(9),
    numero character varying(20),
    bairro character varying(255),
    id character varying(255) NOT NULL,
    idcidade character varying(255),
    logradouro character varying(255)
);


ALTER TABLE public.empresa_endereco OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 48088)
-- Name: fatura; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fatura (
    id character varying(255) NOT NULL,
    competencia character varying(255),
    descricao character varying(255),
    emissao date,
    observacao character varying(255),
    status character varying(255),
    valor numeric(38,2),
    vencimento date,
    idcliente character varying(255),
    idempresa character varying(255),
    CONSTRAINT fatura_status_check CHECK (((status)::text = ANY ((ARRAY['PENDENTE'::character varying, 'CANCELADA'::character varying, 'PAGA'::character varying])::text[])))
);


ALTER TABLE public.fatura OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 48097)
-- Name: fatura_pagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fatura_pagamento (
    id character varying(255) NOT NULL,
    data_recebimento date,
    valor_desconto numeric(38,2),
    observacao character varying(255),
    valor_pago numeric(38,2),
    idfatura character varying(255)
);


ALTER TABLE public.fatura_pagamento OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 48120)
-- Name: mensalidade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mensalidade (
    id character varying(255) NOT NULL,
    competencia character varying(255),
    descricao character varying(255),
    emissao date,
    observacao character varying(255),
    valor numeric(38,2),
    vencimento date,
    idcliente character varying(255),
    idempresa character varying(255)
);


ALTER TABLE public.mensalidade OWNER TO postgres;

--
-- TOC entry 2898 (class 2606 OID 48006)
-- Name: cidade cidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cidade
    ADD CONSTRAINT cidade_pkey PRIMARY KEY (string);


--
-- TOC entry 2908 (class 2606 OID 48064)
-- Name: cliente_contato cliente_contato_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente_contato
    ADD CONSTRAINT cliente_contato_pkey PRIMARY KEY (id);


--
-- TOC entry 2910 (class 2606 OID 48072)
-- Name: cliente_endereco cliente_endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente_endereco
    ADD CONSTRAINT cliente_endereco_pkey PRIMARY KEY (id);


--
-- TOC entry 2906 (class 2606 OID 48056)
-- Name: cliente cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id);


--
-- TOC entry 2902 (class 2606 OID 48022)
-- Name: empresa_contato empresa_contato_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa_contato
    ADD CONSTRAINT empresa_contato_pkey PRIMARY KEY (id);


--
-- TOC entry 2904 (class 2606 OID 48030)
-- Name: empresa_endereco empresa_endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa_endereco
    ADD CONSTRAINT empresa_endereco_pkey PRIMARY KEY (id);


--
-- TOC entry 2900 (class 2606 OID 48014)
-- Name: empresa empresa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT empresa_pkey PRIMARY KEY (id);


--
-- TOC entry 2914 (class 2606 OID 48104)
-- Name: fatura_pagamento fatura_pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fatura_pagamento
    ADD CONSTRAINT fatura_pagamento_pkey PRIMARY KEY (id);


--
-- TOC entry 2912 (class 2606 OID 48096)
-- Name: fatura fatura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fatura
    ADD CONSTRAINT fatura_pkey PRIMARY KEY (id);


--
-- TOC entry 2916 (class 2606 OID 48127)
-- Name: mensalidade mensalidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensalidade
    ADD CONSTRAINT mensalidade_pkey PRIMARY KEY (id);


--
-- TOC entry 2926 (class 2606 OID 48128)
-- Name: mensalidade fk1bo2g75fsmy9mv106vm1r6oho; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensalidade
    ADD CONSTRAINT mensalidade_cliente_fk FOREIGN KEY (idcliente) REFERENCES public.cliente(id);


--
-- TOC entry 2917 (class 2606 OID 48031)
-- Name: empresa fk7fuwyhvq987mgya78um7r0ivw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT empresa_contato_fk FOREIGN KEY (idempresa_contato) REFERENCES public.empresa_contato(id);


--
-- TOC entry 2924 (class 2606 OID 48110)
-- Name: fatura fkd7lsnjc2ii7pisfo03hws4sck; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fatura
    ADD CONSTRAINT fatura_empresa_fk FOREIGN KEY (idempresa) REFERENCES public.empresa(id);


--
-- TOC entry 2921 (class 2606 OID 48078)
-- Name: cliente fkge3sslojtp6tsqtv41agxnuww; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_endereco_fk FOREIGN KEY (idcliente_endereco) REFERENCES public.cliente_endereco(id);


--
-- TOC entry 2925 (class 2606 OID 48115)
-- Name: fatura_pagamento fkhug3lvna5w6nuamyo9i9vk70; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fatura_pagamento
    ADD CONSTRAINT fatura_pagamento_fatura_fk FOREIGN KEY (idfatura) REFERENCES public.fatura(id);


--
-- TOC entry 2920 (class 2606 OID 48073)
-- Name: cliente fkkccae3s7qy1gcwub6y6p79hay; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_cliente_contato_fk FOREIGN KEY (idcliente_contato) REFERENCES public.cliente_contato(id);


--
-- TOC entry 2918 (class 2606 OID 48036)
-- Name: empresa fklkglytacm7pugr6a80da4sk5p; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT empresa_empresa_endereco_fk FOREIGN KEY (idempresa_endereco) REFERENCES public.empresa_endereco(id);


--
-- TOC entry 2923 (class 2606 OID 48105)
-- Name: fatura fkoy54cuf6hc8ssrxhasx6qba9m; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fatura
    ADD CONSTRAINT fatura_cliente_fk FOREIGN KEY (idcliente) REFERENCES public.cliente(id);


--
-- TOC entry 2927 (class 2606 OID 48133)
-- Name: mensalidade fkqwk46xhxlkjjxo8bhvv4k9c2b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mensalidade
    ADD CONSTRAINT mensalidade_empresa_fk FOREIGN KEY (idempresa) REFERENCES public.empresa(id);


--
-- TOC entry 2919 (class 2606 OID 48041)
-- Name: empresa_endereco fkr00rcd674hy91wc5hwpnovdou; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa_endereco
    ADD CONSTRAINT empresa_endereco_cidade_fk FOREIGN KEY (idcidade) REFERENCES public.cidade(string);


--
-- TOC entry 2922 (class 2606 OID 48083)
-- Name: cliente_endereco fkr70ueo0trs8e7b4349ws6lyfo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente_endereco
    ADD CONSTRAINT cliente_endereco_cidade_fk FOREIGN KEY (idcidade) REFERENCES public.cidade(string);


-- Completed on 2023-09-30 17:27:41

--
-- PostgreSQL database dump complete
--

