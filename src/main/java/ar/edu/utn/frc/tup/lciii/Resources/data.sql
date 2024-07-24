
--
-- TOC entry 215 (class 1259 OID 65716)
-- Name: events; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.events (
    event_id integer NOT NULL,
    description character varying(255) NOT NULL
);


--
-- TOC entry 216 (class 1259 OID 65719)
-- Name: Events_event_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."Events_event_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4935 (class 0 OID 0)
-- Dependencies: 216
-- Name: Events_event_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."Events_event_id_seq" OWNED BY public.events.event_id;


--
-- TOC entry 217 (class 1259 OID 65724)
-- Name: card; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.card (
    card_id integer NOT NULL,
    card_type_id integer,
    event_id integer,
    square_id integer,
    message character varying(255),
    amount integer,
    card_type_to_pick_up_id integer,
    salvageable_by_player boolean
);


--
-- TOC entry 218 (class 1259 OID 65727)
-- Name: card_card_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.card_card_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4936 (class 0 OID 0)
-- Dependencies: 218
-- Name: card_card_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.card_card_id_seq OWNED BY public.card.card_id;


--
-- TOC entry 219 (class 1259 OID 65728)
-- Name: card_type; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.card_type (
    card_type_id integer NOT NULL,
    description character varying(255) NOT NULL
);


--
-- TOC entry 220 (class 1259 OID 65731)
-- Name: card_type_card_type_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.card_type_card_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4937 (class 0 OID 0)
-- Dependencies: 220
-- Name: card_type_card_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.card_type_card_type_id_seq OWNED BY public.card_type.card_type_id;


--
-- TOC entry 221 (class 1259 OID 65736)
-- Name: game; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.game (
    game_id integer NOT NULL,
    datesetup date NOT NULL,
    finished boolean NOT NULL,
    user_id bigint NOT NULL,
    game jsonb NOT NULL
);


--
-- TOC entry 222 (class 1259 OID 65741)
-- Name: game_game_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.game_game_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4938 (class 0 OID 0)
-- Dependencies: 222
-- Name: game_game_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.game_game_id_seq OWNED BY public.game.game_id;


--
-- TOC entry 223 (class 1259 OID 65754)
-- Name: province; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.province (
    province_id integer NOT NULL,
    description character varying(255)
);


--
-- TOC entry 224 (class 1259 OID 65757)
-- Name: province_province_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.province_province_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4939 (class 0 OID 0)
-- Dependencies: 224
-- Name: province_province_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.province_province_id_seq OWNED BY public.province.province_id;


--
-- TOC entry 225 (class 1259 OID 65758)
-- Name: square; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.square (
    square_id integer NOT NULL,
    square_type_id integer,
    name_square character varying(255),
    province_id integer,
    zone_id integer,
    upgradeprice integer,
    amount integer,
    event_id integer,
    event_when_passing_id integer,
    price bigint,
    card_type_to_pick_id integer,
    rentbyupgrade bigint[]
);


--
-- TOC entry 226 (class 1259 OID 65761)
-- Name: square_square_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.square_square_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4940 (class 0 OID 0)
-- Dependencies: 226
-- Name: square_square_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.square_square_id_seq OWNED BY public.square.square_id;


--
-- TOC entry 227 (class 1259 OID 65762)
-- Name: square_type; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.square_type (
    square_type_id integer NOT NULL,
    description character varying(255) NOT NULL
);


--
-- TOC entry 228 (class 1259 OID 65765)
-- Name: square_type_square_type_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.square_type_square_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4941 (class 0 OID 0)
-- Dependencies: 228
-- Name: square_type_square_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.square_type_square_type_id_seq OWNED BY public.square_type.square_type_id;


--
-- TOC entry 232 (class 1259 OID 65814)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 229 (class 1259 OID 65766)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    user_id bigint DEFAULT nextval('public.user_id_seq'::regclass) NOT NULL,
    username character varying NOT NULL,
    password character varying NOT NULL,
    name character varying
);


--
-- TOC entry 230 (class 1259 OID 65771)
-- Name: zone; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.zone (
    zone_id integer NOT NULL,
    description character varying(255)
);


--
-- TOC entry 231 (class 1259 OID 65774)
-- Name: zone_zone_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.zone_zone_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4942 (class 0 OID 0)
-- Dependencies: 231
-- Name: zone_zone_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.zone_zone_id_seq OWNED BY public.zone.zone_id;


--
-- TOC entry 4729 (class 2604 OID 65777)
-- Name: card card_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.card ALTER COLUMN card_id SET DEFAULT nextval('public.card_card_id_seq'::regclass);


--
-- TOC entry 4730 (class 2604 OID 65778)
-- Name: card_type card_type_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.card_type ALTER COLUMN card_type_id SET DEFAULT nextval('public.card_type_card_type_id_seq'::regclass);


--
-- TOC entry 4728 (class 2604 OID 65775)
-- Name: events event_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.events ALTER COLUMN event_id SET DEFAULT nextval('public."Events_event_id_seq"'::regclass);


--
-- TOC entry 4731 (class 2604 OID 65780)
-- Name: game game_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.game ALTER COLUMN game_id SET DEFAULT nextval('public.game_game_id_seq'::regclass);


--
-- TOC entry 4732 (class 2604 OID 65784)
-- Name: province province_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.province ALTER COLUMN province_id SET DEFAULT nextval('public.province_province_id_seq'::regclass);


--
-- TOC entry 4733 (class 2604 OID 65785)
-- Name: square square_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.square ALTER COLUMN square_id SET DEFAULT nextval('public.square_square_id_seq'::regclass);


--
-- TOC entry 4734 (class 2604 OID 65786)
-- Name: square_type square_type_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.square_type ALTER COLUMN square_type_id SET DEFAULT nextval('public.square_type_square_type_id_seq'::regclass);


--
-- TOC entry 4736 (class 2604 OID 65787)
-- Name: zone zone_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.zone ALTER COLUMN zone_id SET DEFAULT nextval('public.zone_zone_id_seq'::regclass);


--
-- TOC entry 4913 (class 0 OID 65724)
-- Dependencies: 217
-- Data for Name: card; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.card VALUES (1, 1, 1, NULL, 'Por venta de acciones cobre 1000.', 1000, NULL, false);
INSERT INTO public.card VALUES (2, 1, 1, NULL, 'Ha ganado un concurso agrícola. Cobre 2000.', 2000, NULL, false);
INSERT INTO public.card VALUES (3, 1, 1, NULL, 'Hereda 2000.', 2000, NULL, false);
INSERT INTO public.card VALUES (4, 1, 1, NULL, 'Devolución de impuesto, cobre 400.', 400, NULL, false);
INSERT INTO public.card VALUES (5, 1, 1, NULL, 'Ha ganado un concurso agrícola. Cobre 2000.', 2000, NULL, false);
INSERT INTO public.card VALUES (6, 1, 1, NULL, '5% de interés sobre cédulas hipotecarias. Cobre 500.', 500, NULL, false);
INSERT INTO public.card VALUES (7, 1, 1, NULL, 'Ha obtenido un segundo premio de belleza. Cobre 200.', 200, NULL, false);
INSERT INTO public.card VALUES (8, 1, 1, NULL, 'Error en los cálculos del Banco. Cobre 4000.', 4000, NULL, false);
INSERT INTO public.card VALUES (9, 1, 7, NULL, 'Es su cumpleaños. Cobre 200 de cada uno de sus jugadores.', 200, NULL, false);
INSERT INTO public.card VALUES (10, 1, 5, 2, 'Vuelve atrás hasta Formosa Zona Sur.', NULL, NULL, false);
INSERT INTO public.card VALUES (11, 1, 5, 2, 'Siga hasta la Formosa Zona Sur', NULL, NULL, false);
INSERT INTO public.card VALUES (12, 1, 3, NULL, 'Pague 200 de multa o levante una tarjeta de SUERTE.', -200, 2, false);
INSERT INTO public.card VALUES (13, 1, 3, 15, 'Marche preso directamente.', NULL, NULL, false);
INSERT INTO public.card VALUES (14, 1, 1, NULL, 'Pague su póliza de seguro con 1000.', -1000, NULL, false);
INSERT INTO public.card VALUES (15, 1, 1, NULL, 'Gastos de Farmacia. Pague 1000.', -1000, NULL, false);
INSERT INTO public.card VALUES (16, 1, 4, NULL, 'Con esta tarjeta sale usted de la Comisaría. Conservela o véndala.', NULL, NULL, true);
INSERT INTO public.card VALUES (17, 2, 4, NULL, 'Ha ganado la grande. Cobre 10.000.', 10000, NULL, false);
INSERT INTO public.card VALUES (18, 2, 5, 1, 'Siga hasta la salida.', NULL, NULL, false);
INSERT INTO public.card VALUES (20, 2, 1, NULL, 'Gano en las carreras. Cobre 3000.', 3000, NULL, false);
INSERT INTO public.card VALUES (21, 2, 6, 17, 'Haga un paseo hasta la Bodega. Cobre 5000 si pasa por la salida.', 5000, NULL, false);
INSERT INTO public.card VALUES (22, 2, 6, 14, 'Siga hasta Salta, Zona norte. Si pasa por la salida cobre 5000.', 5000, NULL, false);
INSERT INTO public.card VALUES (23, 2, 6, 26, 'Siga hasta Santa Fe, Zona Norte. Si pasa por la salida cobre 5000.', 5000, NULL, false);
INSERT INTO public.card VALUES (24, 2, 8, 41, 'Siga hasta Buenos Aires, Zona Norte.', NULL, NULL, false);
INSERT INTO public.card VALUES (25, 2, 8, NULL, 'Vuelva tres pasos atrás.', -3, NULL, false);
INSERT INTO public.card VALUES (26, 2, 1, NULL, 'Cobre 1000 por intereses bancarios.', 1000, NULL, false);
INSERT INTO public.card VALUES (27, 2, 1, NULL, 'Multa por exceso de velocidad. Pague 300.', -300, NULL, false);
INSERT INTO public.card VALUES (28, 2, 1, NULL, 'Multa caminera. Pague 400.', -400, NULL, false);
INSERT INTO public.card VALUES (29, 2, 3, NULL, 'Marche preso directamente.', NULL, NULL, false);
INSERT INTO public.card VALUES (30, 2, 9, NULL, 'Sus propiedades tienen que ser reparadas. Pague al banco 500 por cada chacra y 2500 por cada estancia.', NULL, NULL, false);
INSERT INTO public.card VALUES (31, 2, 1, NULL, 'Pague 3000 por gastos colegiales.', -3000, NULL, false);
INSERT INTO public.card VALUES (32, 2, 9, NULL, 'Por compra de semilla pague al Banco 800 por cada chacra, 4000 por cada estancia.', NULL, NULL, false);
INSERT INTO public.card VALUES (33, 2, 4, NULL, 'Habeas Corpus concedido. Con esta tarjeta sale usted gratuitamente de la Comisaría. Conservela o vendela.', NULL, NULL, true);


--
-- TOC entry 4915 (class 0 OID 65728)
-- Dependencies: 219
-- Data for Name: card_type; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.card_type VALUES (1, 'DESTINY');
INSERT INTO public.card_type VALUES (2, 'LUCK');


--
-- TOC entry 4911 (class 0 OID 65716)
-- Dependencies: 215
-- Data for Name: events; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.events VALUES (1, 'MONEY');
INSERT INTO public.events VALUES (3, 'GO_TO_JAIL');
INSERT INTO public.events VALUES (4, 'GET_OUT_OF_JAIL');
INSERT INTO public.events VALUES (5, 'MOOVE_TO_SQUARE');
INSERT INTO public.events VALUES (6, 'MOOVE_TO_SQUARE_AND_EARN_AT_EXIT');
INSERT INTO public.events VALUES (7, 'CHARGE_ALL_PLAYERS');
INSERT INTO public.events VALUES (8, 'MOOVE_X_STEPS');
INSERT INTO public.events VALUES (9, 'PAY_PER_UPGRADE');
INSERT INTO public.events VALUES (10, 'PAY_AND_PICKUP_CARD_TYPE');
INSERT INTO public.events VALUES (11, 'PICK_UP_CARD_TYPE');
INSERT INTO public.events VALUES (12, 'JAIL');


--
-- TOC entry 4917 (class 0 OID 65736)
-- Dependencies: 221
-- Data for Name: game; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4919 (class 0 OID 65754)
-- Dependencies: 223
-- Data for Name: province; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.province VALUES (2, 'SALTA');
INSERT INTO public.province VALUES (3, 'FORMOSA');
INSERT INTO public.province VALUES (5, 'MENDOZA');
INSERT INTO public.province VALUES (6, 'TUCUMAN');
INSERT INTO public.province VALUES (7, 'CORDOBA');
INSERT INTO public.province VALUES (8, 'RIO_NEGRO');
INSERT INTO public.province VALUES (4, 'SANTA_FE');
INSERT INTO public.province VALUES (1, 'BUENOS_AIRES');


--
-- TOC entry 4921 (class 0 OID 65758)
-- Dependencies: 225
-- Data for Name: square; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.square VALUES (1, 1, 'Salida', NULL, NULL, NULL, 5000, NULL, 1, NULL, NULL, NULL);
INSERT INTO public.square VALUES (5, 1, 'IMPUESTO A LOS REDITOS Pague 5000', NULL, NULL, NULL, -500, 1, NULL, NULL, NULL, NULL);
INSERT INTO public.square VALUES (8, 1, 'PREMIO GANADERO Cobre 2500', NULL, NULL, NULL, 2500, 1, NULL, NULL, NULL, NULL);
INSERT INTO public.square VALUES (9, 3, 'Compañia Petrolera', NULL, NULL, NULL, NULL, NULL, NULL, 3800, NULL, NULL);
INSERT INTO public.square VALUES (11, 1, 'DESTINO (?)', NULL, NULL, NULL, NULL, 11, NULL, NULL, 1, NULL);
INSERT INTO public.square VALUES (13, 4, 'FERROCARRIL Gral. Belgrano', NULL, NULL, NULL, NULL, NULL, NULL, 3600, NULL, NULL);
INSERT INTO public.square VALUES (15, 1, 'Comisaria', NULL, NULL, NULL, NULL, 12, NULL, NULL, NULL, NULL);
INSERT INTO public.square VALUES (16, 1, 'SUERTE (!)', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2, NULL);
INSERT INTO public.square VALUES (17, 3, 'Bodega', NULL, NULL, NULL, NULL, NULL, NULL, 3800, NULL, NULL);
INSERT INTO public.square VALUES (19, 4, 'FERROCARRIL Gral. San Martín', NULL, NULL, NULL, NULL, NULL, NULL, 3600, NULL, NULL);
INSERT INTO public.square VALUES (22, 1, 'DESCANSO', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.square VALUES (23, 4, 'FERROCARRIL Gral. B. Mitre', NULL, NULL, NULL, NULL, NULL, NULL, 3600, NULL, NULL);
INSERT INTO public.square VALUES (27, 1, 'DESTINO (?)', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL);
INSERT INTO public.square VALUES (28, 4, 'FERROCARRIL Gral. Urquiza', NULL, NULL, NULL, NULL, NULL, NULL, 3600, NULL, NULL);
INSERT INTO public.square VALUES (29, 1, 'LIBRE ESTACIONAMIENTO', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.square VALUES (32, 3, 'Ingenio', NULL, NULL, NULL, NULL, NULL, NULL, 5000, NULL, NULL);
INSERT INTO public.square VALUES (36, 1, 'MARCHE PRESO', NULL, NULL, NULL, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO public.square VALUES (37, 1, 'SUERTE (!)', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2, NULL);
INSERT INTO public.square VALUES (39, 1, 'DESTINO (?)', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL);
INSERT INTO public.square VALUES (42, 1, 'IMPUESTO A LAS VENTAS', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.square VALUES (7, 2, 'Rio Negro Zona Norte', 8, 1, 1000, NULL, NULL, NULL, 2200, NULL, '{150,750,2000,5700,8500,11500}');
INSERT INTO public.square VALUES (10, 2, 'Salta Zona Sur', 2, 3, 1500, NULL, NULL, NULL, 2600, NULL, '{200,1000,2800,8500,12000,14200}');
INSERT INTO public.square VALUES (4, 2, 'Formosa Zona Norte', 3, 1, 1000, NULL, NULL, NULL, 1200, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (2, 2, 'Formosa Zona Sur', 3, 3, 1000, NULL, NULL, NULL, 1000, NULL, '{40,200,600,1700,3000,4750}');
INSERT INTO public.square VALUES (3, 2, 'Formosa Zona Centro', 3, 2, 1000, NULL, NULL, NULL, 1000, NULL, '{40,200,600,1700,3000,4750}');
INSERT INTO public.square VALUES (12, 2, 'Salta Zona Centro', 2, 2, 1500, NULL, NULL, NULL, 2600, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (14, 2, 'Salta Zona Norte', 2, 1, 1500, NULL, NULL, NULL, 3000, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (18, 2, 'Mendoza Zona Sur', 5, 3, 2000, NULL, NULL, NULL, 3400, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (20, 2, 'Mendoza Zona Centro', 5, 2, 2000, NULL, NULL, NULL, 3400, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (21, 2, 'Mendoza Zona Norte', 5, 1, 2000, NULL, NULL, NULL, 3800, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (24, 2, 'Santa Fé Zona Sur', 4, 3, 2500, NULL, NULL, NULL, 4200, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (25, 2, 'Santa Fé Zona Centro', 4, 2, 2500, NULL, NULL, NULL, 4200, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (26, 2, 'Santa Fé Zona Norte', 4, 1, 2500, NULL, NULL, NULL, 4600, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (30, 2, 'Tucumán Zona Sur', 6, 3, 2500, NULL, NULL, NULL, 5000, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (31, 2, 'Tucumán Zona Norte', 6, 1, 3000, NULL, NULL, NULL, 5400, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (33, 2, 'Córdoba Zona Sur', 7, 3, 3000, NULL, NULL, NULL, 6000, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (34, 2, 'Córdoba Zona Centro', 7, 2, 3000, NULL, NULL, NULL, 6000, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (35, 2, 'Córdoba Zona Norte', 7, 1, 3000, NULL, NULL, NULL, 6400, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (38, 2, 'Buenos Aires Zona Sur', 1, 3, 4000, NULL, NULL, NULL, 7000, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (40, 2, 'Buenos Aires Zona Centro', 1, 2, 4000, NULL, NULL, NULL, 7000, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (41, 2, 'Buenos Aires Zona Norte', 1, 1, 4000, NULL, NULL, NULL, 7400, NULL, '{80,400,800,3400,6000,9500}');
INSERT INTO public.square VALUES (6, 2, 'Rio Negro Zona Sur', 8, 3, 1000, NULL, NULL, NULL, 2000, NULL, '{110,570,1700,5150,7600,9500}');


--
-- TOC entry 4923 (class 0 OID 65762)
-- Dependencies: 227
-- Data for Name: square_type; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.square_type VALUES (1, 'NORMAL');
INSERT INTO public.square_type VALUES (2, 'TERRAIN_PROPERY_SQUARE');
INSERT INTO public.square_type VALUES (3, 'COMPANY_PROPERY_SQUARE');
INSERT INTO public.square_type VALUES (4, 'RAILWAY_PROPERY_SQUARE');


--
-- TOC entry 4925 (class 0 OID 65766)
-- Dependencies: 229
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4926 (class 0 OID 65771)
-- Dependencies: 230
-- Data for Name: zone; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.zone VALUES (1, 'NORTE');
INSERT INTO public.zone VALUES (2, 'CENTRO');
INSERT INTO public.zone VALUES (3, 'SUR');


--
-- TOC entry 4943 (class 0 OID 0)
-- Dependencies: 216
-- Name: Events_event_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."Events_event_id_seq"', 1, false);


--
-- TOC entry 4944 (class 0 OID 0)
-- Dependencies: 218
-- Name: card_card_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.card_card_id_seq', 1, false);


--
-- TOC entry 4945 (class 0 OID 0)
-- Dependencies: 220
-- Name: card_type_card_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.card_type_card_type_id_seq', 1, false);


--
-- TOC entry 4946 (class 0 OID 0)
-- Dependencies: 222
-- Name: game_game_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.game_game_id_seq', 1, false);


--
-- TOC entry 4947 (class 0 OID 0)
-- Dependencies: 224
-- Name: province_province_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.province_province_id_seq', 1, false);


--
-- TOC entry 4948 (class 0 OID 0)
-- Dependencies: 226
-- Name: square_square_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.square_square_id_seq', 1, false);


--
-- TOC entry 4949 (class 0 OID 0)
-- Dependencies: 228
-- Name: square_type_square_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.square_type_square_type_id_seq', 1, false);


--
-- TOC entry 4950 (class 0 OID 0)
-- Dependencies: 232
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.user_id_seq', 1, false);


--
-- TOC entry 4951 (class 0 OID 0)
-- Dependencies: 231
-- Name: zone_zone_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.zone_zone_id_seq', 1, false);


--
-- TOC entry 4738 (class 2606 OID 65789)
-- Name: events Events_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT "Events_pkey" PRIMARY KEY (event_id);


--
-- TOC entry 4740 (class 2606 OID 65793)
-- Name: card card_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.card
    ADD CONSTRAINT card_pkey PRIMARY KEY (card_id);


--
-- TOC entry 4742 (class 2606 OID 65795)
-- Name: card_type card_type_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.card_type
    ADD CONSTRAINT card_type_pkey PRIMARY KEY (card_type_id);


--
-- TOC entry 4744 (class 2606 OID 65799)
-- Name: game game_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.game
    ADD CONSTRAINT game_pkey PRIMARY KEY (game_id);


--
-- TOC entry 4746 (class 2606 OID 65807)
-- Name: province province_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.province
    ADD CONSTRAINT province_pkey PRIMARY KEY (province_id);


--
-- TOC entry 4748 (class 2606 OID 65809)
-- Name: square square_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.square
    ADD CONSTRAINT square_pkey PRIMARY KEY (square_id);


--
-- TOC entry 4750 (class 2606 OID 65811)
-- Name: square_type square_type_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.square_type
    ADD CONSTRAINT square_type_pkey PRIMARY KEY (square_type_id);


--
-- TOC entry 4752 (class 2606 OID 65813)
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (user_id);


--
-- TOC entry 4754 (class 2606 OID 65925)
-- Name: users users_unique; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_unique UNIQUE (username);


--
-- TOC entry 4756 (class 2606 OID 65817)
-- Name: zone zone_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.zone
    ADD CONSTRAINT zone_pkey PRIMARY KEY (zone_id);


--
-- TOC entry 4757 (class 2606 OID 65833)
-- Name: card card_card_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.card
    ADD CONSTRAINT card_card_type_id_fkey FOREIGN KEY (card_type_id) REFERENCES public.card_type(card_type_id);


--
-- TOC entry 4758 (class 2606 OID 65838)
-- Name: card card_card_type_to_pick_up_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.card
    ADD CONSTRAINT card_card_type_to_pick_up_id_fkey FOREIGN KEY (card_type_to_pick_up_id) REFERENCES public.card_type(card_type_id);


--
-- TOC entry 4759 (class 2606 OID 65843)
-- Name: card card_event_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.card
    ADD CONSTRAINT card_event_id_fkey FOREIGN KEY (event_id) REFERENCES public.events(event_id);


--
-- TOC entry 4760 (class 2606 OID 65848)
-- Name: card card_square_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.card
    ADD CONSTRAINT card_square_id_fkey FOREIGN KEY (square_id) REFERENCES public.square(square_id);


--
-- TOC entry 4761 (class 2606 OID 65919)
-- Name: game game_users_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.game
    ADD CONSTRAINT game_users_fk FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- TOC entry 4762 (class 2606 OID 65888)
-- Name: square square_card_type_to_pick_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.square
    ADD CONSTRAINT square_card_type_to_pick_id_fkey FOREIGN KEY (card_type_to_pick_id) REFERENCES public.card_type(card_type_id);


--
-- TOC entry 4763 (class 2606 OID 65893)
-- Name: square square_event_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.square
    ADD CONSTRAINT square_event_id_fkey FOREIGN KEY (event_id) REFERENCES public.events(event_id);


--
-- TOC entry 4764 (class 2606 OID 65898)
-- Name: square square_event_when_passing_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.square
    ADD CONSTRAINT square_event_when_passing_id_fkey FOREIGN KEY (event_when_passing_id) REFERENCES public.events(event_id);


--
-- TOC entry 4765 (class 2606 OID 65903)
-- Name: square square_province_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.square
    ADD CONSTRAINT square_province_id_fkey FOREIGN KEY (province_id) REFERENCES public.province(province_id);


--
-- TOC entry 4766 (class 2606 OID 65908)
-- Name: square square_square_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.square
    ADD CONSTRAINT square_square_type_id_fkey FOREIGN KEY (square_type_id) REFERENCES public.square_type(square_type_id);


--
-- TOC entry 4767 (class 2606 OID 65913)
-- Name: square square_zone_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.square
    ADD CONSTRAINT square_zone_id_fkey FOREIGN KEY (zone_id) REFERENCES public.zone(zone_id);


-- Completed on 2024-07-02 11:47:41

--
-- PostgreSQL database dump complete
--

