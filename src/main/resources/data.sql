--
-- PostgreSQL database dump
--

-- Dumped from database version 15.1
-- Dumped by pg_dump version 15.1

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
-- Name: comment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comment (
                                id integer NOT NULL,
                                author_name character varying(255),
                                date date,
                                message text,
                                parent_id integer,
                                post_id integer
);


ALTER TABLE public.comment OWNER TO postgres;

--
-- Name: comments_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.comments_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.comments_sequence OWNER TO postgres;

--
-- Name: news_tag; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.news_tag (
                                 id integer NOT NULL,
                                 tag character varying(255)
);


ALTER TABLE public.news_tag OWNER TO postgres;

--
-- Name: posts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.posts (
                              id integer NOT NULL,
                              author_name character varying(255),
                              header character varying(255),
                              original_photo_name character varying(255),
                              photourl character varying(255),
                              published_date date,
                              text text,
                              user_id integer
);


ALTER TABLE public.posts OWNER TO postgres;

--
-- Name: posts_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.posts_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.posts_sequence OWNER TO postgres;

--
-- Name: posts_tags; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.posts_tags (
                                   post_id integer NOT NULL,
                                   tag_id integer NOT NULL
);


ALTER TABLE public.posts_tags OWNER TO postgres;

--
-- Name: refreshtoken; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.refreshtoken (
                                     id integer NOT NULL,
                                     expiry_date timestamp without time zone NOT NULL,
                                     token character varying(255) NOT NULL,
                                     user_id integer
);


ALTER TABLE public.refreshtoken OWNER TO postgres;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
                              id integer NOT NULL,
                              name character varying(255)
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: roles_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_sequence OWNER TO postgres;

--
-- Name: tags_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tags_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tags_sequence OWNER TO postgres;

--
-- Name: tokens_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tokens_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tokens_sequence OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
                              id integer NOT NULL,
                              activation_code character varying(255),
                              active boolean NOT NULL,
                              email character varying(255),
                              first_name character varying(255),
                              last_name character varying(255),
                              original_photo_name character varying(255),
                              password character varying(255),
                              photo_path character varying(255),
                              username character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_favourite_news; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_favourite_news (
                                             user_id integer NOT NULL,
                                             post_id integer NOT NULL
);


ALTER TABLE public.users_favourite_news OWNER TO postgres;

--
-- Name: users_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_roles (
                                    user_id integer NOT NULL,
                                    role_id integer NOT NULL
);


ALTER TABLE public.users_roles OWNER TO postgres;

--
-- Name: users_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_sequence OWNER TO postgres;

--
-- Data for Name: comment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.comment (id, author_name, date, message, parent_id, post_id) FROM stdin;
\.


--
-- Data for Name: news_tag; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.news_tag (id, tag) FROM stdin;
1	SPORT
2	POLITICS
3	STARS
4	ART
5	FASHION
\.


--
-- Data for Name: posts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.posts (id, author_name, header, original_photo_name, photourl, published_date, text, user_id) FROM stdin;
\.


--
-- Data for Name: posts_tags; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.posts_tags (post_id, tag_id) FROM stdin;
\.


--
-- Data for Name: refreshtoken; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.refreshtoken (id, expiry_date, token, user_id) FROM stdin;
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, name) FROM stdin;
1	ROLE_READER
2	ROLE_EDITOR
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, activation_code, active, email, first_name, last_name, original_photo_name, password, photo_path, username) FROM stdin;
1	\N	t	tarantuldeveloper@gmail.com	Bekzhan	Satiev	default-profile.jpg	$2a$10$8Dr1RgyQad.a9PsYMn66h.JE42ENHCBgbPnrCfmibxds8MzVk43rO	D:/mega-front/src/img/default-profile.jpg	beks
\.


--
-- Data for Name: users_favourite_news; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users_favourite_news (user_id, post_id) FROM stdin;
\.


--
-- Data for Name: users_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users_roles (user_id, role_id) FROM stdin;
1	1
1	2
\.


--
-- Name: comments_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comments_sequence', 1, false);


--
-- Name: posts_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.posts_sequence', 1, false);


--
-- Name: roles_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_sequence', 2, true);


--
-- Name: tags_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tags_sequence', 5, true);


--
-- Name: tokens_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tokens_sequence', 1, true);


--
-- Name: users_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_sequence', 1, true);


--
-- Name: comment comment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_pkey PRIMARY KEY (id);


--
-- Name: news_tag news_tag_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.news_tag
    ADD CONSTRAINT news_tag_pkey PRIMARY KEY (id);


--
-- Name: posts posts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.posts
    ADD CONSTRAINT posts_pkey PRIMARY KEY (id);


--
-- Name: posts_tags posts_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.posts_tags
    ADD CONSTRAINT posts_tags_pkey PRIMARY KEY (post_id, tag_id);


--
-- Name: refreshtoken refreshtoken_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refreshtoken
    ADD CONSTRAINT refreshtoken_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: users uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: refreshtoken uk_or156wbneyk8noo4jstv55ii3; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refreshtoken
    ADD CONSTRAINT uk_or156wbneyk8noo4jstv55ii3 UNIQUE (token);


--
-- Name: users ukr43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users_roles users_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- Name: users_favourite_news fk2f8epdm8vc40c02jdm7d5p22f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_favourite_news
    ADD CONSTRAINT fk2f8epdm8vc40c02jdm7d5p22f FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: users_roles fk2o0jvgh89lemvvo17cbqvdxaa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT fk2o0jvgh89lemvvo17cbqvdxaa FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: posts fk5lidm6cqbc7u4xhqpxm898qme; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.posts
    ADD CONSTRAINT fk5lidm6cqbc7u4xhqpxm898qme FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: comment fk7jok1s6lywoh0srylq8lt7tmn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fk7jok1s6lywoh0srylq8lt7tmn FOREIGN KEY (post_id) REFERENCES public.posts(id);


--
-- Name: refreshtoken fka652xrdji49m4isx38pp4p80p; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refreshtoken
    ADD CONSTRAINT fka652xrdji49m4isx38pp4p80p FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: posts_tags fkcreclgob71ibo58gsm6l5wp6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.posts_tags
    ADD CONSTRAINT fkcreclgob71ibo58gsm6l5wp6 FOREIGN KEY (post_id) REFERENCES public.posts(id) ON DELETE CASCADE;


--
-- Name: posts_tags fkdbex4lh5t73j1ijogtlv6fsaw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.posts_tags
    ADD CONSTRAINT fkdbex4lh5t73j1ijogtlv6fsaw FOREIGN KEY (tag_id) REFERENCES public.news_tag(id);


--
-- Name: comment fkde3rfu96lep00br5ov0mdieyt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fkde3rfu96lep00br5ov0mdieyt FOREIGN KEY (parent_id) REFERENCES public.comment(id) ON DELETE CASCADE;


--
-- Name: users_favourite_news fkfbv1af0x14o6twmqb1nk7xui3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_favourite_news
    ADD CONSTRAINT fkfbv1af0x14o6twmqb1nk7xui3 FOREIGN KEY (post_id) REFERENCES public.posts(id);


--
-- Name: users_roles fkj6m8fwv7oqv74fcehir1a9ffy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT fkj6m8fwv7oqv74fcehir1a9ffy FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- PostgreSQL database dump complete
--

