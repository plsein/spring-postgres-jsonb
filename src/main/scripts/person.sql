--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.8
-- Dumped by pg_dump version 9.5.8

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = pgschm, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: person; Type: TABLE; Schema: pgschm; Owner: admin
--

CREATE TABLE person (
    id integer NOT NULL,
    "firstName" character varying(50) NOT NULL,
    "lastName" character varying(50) NOT NULL,
    "additionalData" jsonb DEFAULT '{}'::jsonb NOT NULL
);


ALTER TABLE person OWNER TO admin;

--
-- Name: person_id_seq; Type: SEQUENCE; Schema: pgschm; Owner: admin
--

CREATE SEQUENCE person_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE person_id_seq OWNER TO admin;

--
-- Name: person_id_seq; Type: SEQUENCE OWNED BY; Schema: pgschm; Owner: admin
--

ALTER SEQUENCE person_id_seq OWNED BY person.id;


--
-- Name: id; Type: DEFAULT; Schema: pgschm; Owner: admin
--

ALTER TABLE ONLY person ALTER COLUMN id SET DEFAULT nextval('person_id_seq'::regclass);


--
-- Name: person_pkey; Type: CONSTRAINT; Schema: pgschm; Owner: admin
--

ALTER TABLE ONLY person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);

--
-- Name: person_additional_data_idx; Type: Index; Schema: pgschm; Owner: admin
--

CREATE INDEX on person USING GIN (additional_data jsonb_path_ops);

--
-- PostgreSQL database dump complete
--
