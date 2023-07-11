--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5
-- Dumped by pg_dump version 14.8 (Ubuntu 14.8-1.pgdg20.04+1)

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
-- Name: assistants_assistantconfiguration; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.assistants_assistantconfiguration (
    id bigint NOT NULL,
    date_created timestamp with time zone NOT NULL,
    date_modified timestamp with time zone NOT NULL,
    config_id character varying(255) NOT NULL,
    intro_text text NOT NULL,
    qa_prompt text NOT NULL,
    name character varying(255) NOT NULL,
    default_assistant boolean NOT NULL,
    default_manager_id character varying(128) NOT NULL,
    language_code character varying(255) NOT NULL,
    max_tokens integer NOT NULL,
    model character varying(255) NOT NULL,
    temperature double precision NOT NULL,
    top_p double precision NOT NULL
);


ALTER TABLE public.assistants_assistantconfiguration OWNER TO postgres;

--
-- Name: assistants_assistantconfiguration_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.assistants_assistantconfiguration_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.assistants_assistantconfiguration_id_seq OWNER TO postgres;

--
-- Name: assistants_assistantconfiguration_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.assistants_assistantconfiguration_id_seq OWNED BY public.assistants_assistantconfiguration.id;


--
-- Name: assistants_assistantconfiguration_knowledge_base; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.assistants_assistantconfiguration_knowledge_base (
    id integer NOT NULL,
    assistantconfiguration_id bigint NOT NULL,
    file_id bigint NOT NULL
);


ALTER TABLE public.assistants_assistantconfiguration_knowledge_base OWNER TO postgres;

--
-- Name: assistants_assistantconfiguration_knowledge_base_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.assistants_assistantconfiguration_knowledge_base_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.assistants_assistantconfiguration_knowledge_base_id_seq OWNER TO postgres;

--
-- Name: assistants_assistantconfiguration_knowledge_base_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.assistants_assistantconfiguration_knowledge_base_id_seq OWNED BY public.assistants_assistantconfiguration_knowledge_base.id;


--
-- Name: auth_group; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.auth_group (
    id integer NOT NULL,
    name character varying(150) NOT NULL
);


ALTER TABLE public.auth_group OWNER TO postgres;

--
-- Name: auth_group_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.auth_group_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_group_id_seq OWNER TO postgres;

--
-- Name: auth_group_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.auth_group_id_seq OWNED BY public.auth_group.id;


--
-- Name: auth_group_permissions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.auth_group_permissions (
    id integer NOT NULL,
    group_id integer NOT NULL,
    permission_id integer NOT NULL
);


ALTER TABLE public.auth_group_permissions OWNER TO postgres;

--
-- Name: auth_group_permissions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.auth_group_permissions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_group_permissions_id_seq OWNER TO postgres;

--
-- Name: auth_group_permissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.auth_group_permissions_id_seq OWNED BY public.auth_group_permissions.id;


--
-- Name: auth_permission; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.auth_permission (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    content_type_id integer NOT NULL,
    codename character varying(100) NOT NULL
);


ALTER TABLE public.auth_permission OWNER TO postgres;

--
-- Name: auth_permission_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.auth_permission_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_permission_id_seq OWNER TO postgres;

--
-- Name: auth_permission_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.auth_permission_id_seq OWNED BY public.auth_permission.id;


--
-- Name: auth_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.auth_user (
    id integer NOT NULL,
    password character varying(128) NOT NULL,
    last_login timestamp with time zone,
    is_superuser boolean NOT NULL,
    username character varying(150) NOT NULL,
    first_name character varying(150) NOT NULL,
    last_name character varying(150) NOT NULL,
    email character varying(254) NOT NULL,
    is_staff boolean NOT NULL,
    is_active boolean NOT NULL,
    date_joined timestamp with time zone NOT NULL
);


ALTER TABLE public.auth_user OWNER TO postgres;

--
-- Name: auth_user_groups; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.auth_user_groups (
    id integer NOT NULL,
    user_id integer NOT NULL,
    group_id integer NOT NULL
);


ALTER TABLE public.auth_user_groups OWNER TO postgres;

--
-- Name: auth_user_groups_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.auth_user_groups_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_user_groups_id_seq OWNER TO postgres;

--
-- Name: auth_user_groups_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.auth_user_groups_id_seq OWNED BY public.auth_user_groups.id;


--
-- Name: auth_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.auth_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_user_id_seq OWNER TO postgres;

--
-- Name: auth_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.auth_user_id_seq OWNED BY public.auth_user.id;


--
-- Name: auth_user_user_permissions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.auth_user_user_permissions (
    id integer NOT NULL,
    user_id integer NOT NULL,
    permission_id integer NOT NULL
);


ALTER TABLE public.auth_user_user_permissions OWNER TO postgres;

--
-- Name: auth_user_user_permissions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.auth_user_user_permissions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_user_user_permissions_id_seq OWNER TO postgres;

--
-- Name: auth_user_user_permissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.auth_user_user_permissions_id_seq OWNED BY public.auth_user_user_permissions.id;


--
-- Name: clients_client; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clients_client (
    date_created timestamp with time zone NOT NULL,
    date_modified timestamp with time zone NOT NULL,
    id character varying(128) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255),
    description text,
    phone_number character varying(15) NOT NULL,
    email character varying(254),
    time_zone character varying(255) NOT NULL,
    assistant_configuration_id bigint,
    manager_id character varying(128) NOT NULL
);


ALTER TABLE public.clients_client OWNER TO postgres;

--
-- Name: configurations_configuration; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.configurations_configuration (
    date_created timestamp with time zone NOT NULL,
    date_modified timestamp with time zone NOT NULL,
    id character varying(128) NOT NULL,
    screens text,
    screens_compiled jsonb
);


ALTER TABLE public.configurations_configuration OWNER TO postgres;

--
-- Name: dashboard_userdashboardmodule; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dashboard_userdashboardmodule (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    module character varying(255) NOT NULL,
    app_label character varying(255),
    user_id integer,
    "column" integer NOT NULL,
    "order" integer NOT NULL,
    settings text NOT NULL,
    children text NOT NULL,
    collapsed boolean NOT NULL,
    CONSTRAINT dashboard_userdashboardmodule_column_check CHECK (("column" >= 0))
);


ALTER TABLE public.dashboard_userdashboardmodule OWNER TO postgres;

--
-- Name: dashboard_userdashboardmodule_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.dashboard_userdashboardmodule_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dashboard_userdashboardmodule_id_seq OWNER TO postgres;

--
-- Name: dashboard_userdashboardmodule_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.dashboard_userdashboardmodule_id_seq OWNED BY public.dashboard_userdashboardmodule.id;


--
-- Name: django_admin_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.django_admin_log (
    id integer NOT NULL,
    action_time timestamp with time zone NOT NULL,
    object_id text,
    object_repr character varying(200) NOT NULL,
    action_flag smallint NOT NULL,
    change_message text NOT NULL,
    content_type_id integer,
    user_id integer NOT NULL,
    CONSTRAINT django_admin_log_action_flag_check CHECK ((action_flag >= 0))
);


ALTER TABLE public.django_admin_log OWNER TO postgres;

--
-- Name: django_admin_log_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.django_admin_log_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.django_admin_log_id_seq OWNER TO postgres;

--
-- Name: django_admin_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.django_admin_log_id_seq OWNED BY public.django_admin_log.id;


--
-- Name: django_content_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.django_content_type (
    id integer NOT NULL,
    app_label character varying(100) NOT NULL,
    model character varying(100) NOT NULL
);


ALTER TABLE public.django_content_type OWNER TO postgres;

--
-- Name: django_content_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.django_content_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.django_content_type_id_seq OWNER TO postgres;

--
-- Name: django_content_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.django_content_type_id_seq OWNED BY public.django_content_type.id;


--
-- Name: django_migrations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.django_migrations (
    id integer NOT NULL,
    app character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    applied timestamp with time zone NOT NULL
);


ALTER TABLE public.django_migrations OWNER TO postgres;

--
-- Name: django_migrations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.django_migrations_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.django_migrations_id_seq OWNER TO postgres;

--
-- Name: django_migrations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.django_migrations_id_seq OWNED BY public.django_migrations.id;


--
-- Name: django_session; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.django_session (
    session_key character varying(40) NOT NULL,
    session_data text NOT NULL,
    expire_date timestamp with time zone NOT NULL
);


ALTER TABLE public.django_session OWNER TO postgres;

--
-- Name: jet_bookmark; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.jet_bookmark (
    id integer NOT NULL,
    url character varying(200) NOT NULL,
    title character varying(255) NOT NULL,
    user_id integer,
    date_add timestamp with time zone NOT NULL
);


ALTER TABLE public.jet_bookmark OWNER TO postgres;

--
-- Name: jet_bookmark_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.jet_bookmark_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.jet_bookmark_id_seq OWNER TO postgres;

--
-- Name: jet_bookmark_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.jet_bookmark_id_seq OWNED BY public.jet_bookmark.id;


--
-- Name: jet_pinnedapplication; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.jet_pinnedapplication (
    id integer NOT NULL,
    app_label character varying(255) NOT NULL,
    user_id integer,
    date_add timestamp with time zone NOT NULL
);


ALTER TABLE public.jet_pinnedapplication OWNER TO postgres;

--
-- Name: jet_pinnedapplication_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.jet_pinnedapplication_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.jet_pinnedapplication_id_seq OWNER TO postgres;

--
-- Name: jet_pinnedapplication_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.jet_pinnedapplication_id_seq OWNED BY public.jet_pinnedapplication.id;


--
-- Name: knowledge_file; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.knowledge_file (
    id bigint NOT NULL,
    date_created timestamp with time zone NOT NULL,
    date_modified timestamp with time zone NOT NULL,
    name character varying(255) NOT NULL,
    text text NOT NULL,
    item_status character varying(255) NOT NULL
);


ALTER TABLE public.knowledge_file OWNER TO postgres;

--
-- Name: knowledge_file_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.knowledge_file_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.knowledge_file_id_seq OWNER TO postgres;

--
-- Name: knowledge_file_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.knowledge_file_id_seq OWNED BY public.knowledge_file.id;


--
-- Name: managers_manager; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.managers_manager (
    date_created timestamp with time zone NOT NULL,
    date_modified timestamp with time zone NOT NULL,
    id character varying(128) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    description text,
    phone_number character varying(15),
    email character varying(254) NOT NULL,
    email_provider character varying(255) NOT NULL,
    time_zone character varying(255) NOT NULL,
    hitl_handle character varying(255),
    auth_url character varying(1023),
    authenticated boolean NOT NULL,
    assistant_configuration_id bigint
);


ALTER TABLE public.managers_manager OWNER TO postgres;

--
-- Name: assistants_assistantconfiguration id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assistants_assistantconfiguration ALTER COLUMN id SET DEFAULT nextval('public.assistants_assistantconfiguration_id_seq'::regclass);


--
-- Name: assistants_assistantconfiguration_knowledge_base id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assistants_assistantconfiguration_knowledge_base ALTER COLUMN id SET DEFAULT nextval('public.assistants_assistantconfiguration_knowledge_base_id_seq'::regclass);


--
-- Name: auth_group id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_group ALTER COLUMN id SET DEFAULT nextval('public.auth_group_id_seq'::regclass);


--
-- Name: auth_group_permissions id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_group_permissions ALTER COLUMN id SET DEFAULT nextval('public.auth_group_permissions_id_seq'::regclass);


--
-- Name: auth_permission id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_permission ALTER COLUMN id SET DEFAULT nextval('public.auth_permission_id_seq'::regclass);


--
-- Name: auth_user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_user ALTER COLUMN id SET DEFAULT nextval('public.auth_user_id_seq'::regclass);


--
-- Name: auth_user_groups id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_user_groups ALTER COLUMN id SET DEFAULT nextval('public.auth_user_groups_id_seq'::regclass);


--
-- Name: auth_user_user_permissions id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_user_user_permissions ALTER COLUMN id SET DEFAULT nextval('public.auth_user_user_permissions_id_seq'::regclass);


--
-- Name: dashboard_userdashboardmodule id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard_userdashboardmodule ALTER COLUMN id SET DEFAULT nextval('public.dashboard_userdashboardmodule_id_seq'::regclass);


--
-- Name: django_admin_log id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.django_admin_log ALTER COLUMN id SET DEFAULT nextval('public.django_admin_log_id_seq'::regclass);


--
-- Name: django_content_type id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.django_content_type ALTER COLUMN id SET DEFAULT nextval('public.django_content_type_id_seq'::regclass);


--
-- Name: django_migrations id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.django_migrations ALTER COLUMN id SET DEFAULT nextval('public.django_migrations_id_seq'::regclass);


--
-- Name: jet_bookmark id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jet_bookmark ALTER COLUMN id SET DEFAULT nextval('public.jet_bookmark_id_seq'::regclass);


--
-- Name: jet_pinnedapplication id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jet_pinnedapplication ALTER COLUMN id SET DEFAULT nextval('public.jet_pinnedapplication_id_seq'::regclass);


--
-- Name: knowledge_file id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.knowledge_file ALTER COLUMN id SET DEFAULT nextval('public.knowledge_file_id_seq'::regclass);


--
-- Data for Name: assistants_assistantconfiguration; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.assistants_assistantconfiguration (id, date_created, date_modified, config_id, intro_text, qa_prompt, name, default_assistant, default_manager_id, language_code, max_tokens, model, temperature, top_p) FROM stdin;
1	2023-06-13 16:02:28.398698+00	2023-06-14 13:58:36.154+00	NDbvNuVndr	Ja sam Moli, miočanski AI asistent.	Answer the question based on the context above. \r\nOnly answer questions about following topics: MIOC, raspored, fakulteti. \r\nIf the question isn't about these topics or the answer cannot be found in the context, \r\nsay "I don't know". Give short and precise answers, summarizing the key points. \r\nYou are friendly, patient and compassionate. \r\nAnswer complex questions in simple terms, over several turns. \r\nKeep the client engaged and always ask if they understood you. \r\nEnd messages in punctuation marks.	Moli	t	default-manager	hr	120	GPT-3	0.6	1
\.


--
-- Data for Name: assistants_assistantconfiguration_knowledge_base; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.assistants_assistantconfiguration_knowledge_base (id, assistantconfiguration_id, file_id) FROM stdin;
1	1	1
2	1	10
\.


--
-- Data for Name: auth_group; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.auth_group (id, name) FROM stdin;
1	managers
\.


--
-- Data for Name: auth_group_permissions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.auth_group_permissions (id, group_id, permission_id) FROM stdin;
1	1	37
2	1	38
3	1	39
4	1	40
5	1	45
6	1	46
7	1	47
8	1	48
9	1	49
10	1	50
11	1	51
12	1	52
13	1	53
14	1	54
15	1	55
16	1	56
\.


--
-- Data for Name: auth_permission; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.auth_permission (id, name, content_type_id, codename) FROM stdin;
1	Can add permission	1	add_permission
2	Can change permission	1	change_permission
3	Can delete permission	1	delete_permission
4	Can view permission	1	view_permission
5	Can add group	2	add_group
6	Can change group	2	change_group
7	Can delete group	2	delete_group
8	Can view group	2	view_group
9	Can add user	3	add_user
10	Can change user	3	change_user
11	Can delete user	3	delete_user
12	Can view user	3	view_user
13	Can add content type	4	add_contenttype
14	Can change content type	4	change_contenttype
15	Can delete content type	4	delete_contenttype
16	Can view content type	4	view_contenttype
17	Can add session	5	add_session
18	Can change session	5	change_session
19	Can delete session	5	delete_session
20	Can view session	5	view_session
21	Can add bookmark	6	add_bookmark
22	Can change bookmark	6	change_bookmark
23	Can delete bookmark	6	delete_bookmark
24	Can view bookmark	6	view_bookmark
25	Can add pinned application	7	add_pinnedapplication
26	Can change pinned application	7	change_pinnedapplication
27	Can delete pinned application	7	delete_pinnedapplication
28	Can view pinned application	7	view_pinnedapplication
29	Can add user dashboard module	8	add_userdashboardmodule
30	Can change user dashboard module	8	change_userdashboardmodule
31	Can delete user dashboard module	8	delete_userdashboardmodule
32	Can view user dashboard module	8	view_userdashboardmodule
33	Can add log entry	9	add_logentry
34	Can change log entry	9	change_logentry
35	Can delete log entry	9	delete_logentry
36	Can view log entry	9	view_logentry
37	Can add User	10	add_client
38	Can change User	10	change_client
39	Can delete User	10	delete_client
40	Can view User	10	view_client
41	Can add configuration	11	add_configuration
42	Can change configuration	11	change_configuration
43	Can delete configuration	11	delete_configuration
44	Can view configuration	11	view_configuration
45	Can add Manager	12	add_manager
46	Can change Manager	12	change_manager
47	Can delete Manager	12	delete_manager
48	Can view Manager	12	view_manager
49	Can add Assistant	13	add_assistantconfiguration
50	Can change Assistant	13	change_assistantconfiguration
51	Can delete Assistant	13	delete_assistantconfiguration
52	Can view Assistant	13	view_assistantconfiguration
53	Can add file	14	add_file
54	Can change file	14	change_file
55	Can delete file	14	delete_file
56	Can view file	14	view_file
\.


--
-- Data for Name: auth_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.auth_user (id, password, last_login, is_superuser, username, first_name, last_name, email, is_staff, is_active, date_joined) FROM stdin;
2	pbkdf2_sha256$260000$QNkblfhrr4Lg3CkWfIsjIR$hun74lTVRN9SJKEIEUwqHBMxKEFcBLs/QsRfYrqvZbQ=	\N	f	hello@mindsmiths.com	Mindsmiths	Admin	hello@mindsmiths.com	t	t	2023-06-13 16:02:28.160949+00
1	pbkdf2_sha256$260000$IzRiBKEe4K0UdZq8QfgZs6$JvEC63w22El2sx+StJt5oWPCxDgqr8Sg7OCRwKUIGjM=	2023-06-13 16:02:48.585289+00	t	admin				t	t	2023-06-13 16:02:22.665617+00
\.


--
-- Data for Name: auth_user_groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.auth_user_groups (id, user_id, group_id) FROM stdin;
1	2	1
\.


--
-- Data for Name: auth_user_user_permissions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.auth_user_user_permissions (id, user_id, permission_id) FROM stdin;
\.


--
-- Data for Name: clients_client; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clients_client (date_created, date_modified, id, first_name, last_name, description, phone_number, email, time_zone, assistant_configuration_id, manager_id) FROM stdin;
2023-06-13 16:03:50.759361+00	2023-06-13 16:04:55.021801+00	dFPflqJamaE2Imui	Domagoj	\N		385919532445	test@example.com	America/New_York	1	default-manager
\.


--
-- Data for Name: configurations_configuration; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.configurations_configuration (date_created, date_modified, id, screens, screens_compiled) FROM stdin;
2023-06-13 16:02:28.08482+00	2023-06-13 16:02:28.084853+00	ko5cRW3oJkHLLVE7	call-scheduled:\n  template: CenteredContent\n  Title:\n    text: "Call with manager scheduled!."	{"call-scheduled": {"Title": {"text": "Call with manager scheduled!."}, "template": "CenteredContent"}}
\.


--
-- Data for Name: dashboard_userdashboardmodule; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dashboard_userdashboardmodule (id, title, module, app_label, user_id, "column", "order", settings, children, collapsed) FROM stdin;
1	Tutorial	config.dashboard.TutorialModule	\N	1	0	0			f
2	Application models	jet.dashboard.modules.ModelList	knowledge	1	0	0	{"models": ["knowledge.*"], "exclude": null}		f
3	Recent Actions	jet.dashboard.modules.RecentActions	knowledge	1	1	0	{"limit": 10, "include_list": ["knowledge.*"], "exclude_list": null, "user": null}		f
\.


--
-- Data for Name: django_admin_log; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.django_admin_log (id, action_time, object_id, object_repr, action_flag, change_message, content_type_id, user_id) FROM stdin;
1	2023-06-13 16:03:10.410844+00	default-manager	Domagoj Gavranić	2	[{"changed": {"fields": ["First name", "Last name", "Description", "Phone number"]}}]	12	1
2	2023-06-13 16:03:21.100436+00	1	Moli	2	[{"changed": {"fields": ["Instructions", "Language code", "Temperature"]}}]	13	1
3	2023-06-13 16:03:25.493978+00	1	Moli	2	[]	13	1
4	2023-06-13 16:04:55.024745+00	dFPflqJamaE2Imui	Domagoj None	2	[{"changed": {"fields": ["Email"]}}]	10	1
5	2023-06-14 13:58:23.83861+00	1	Moli	2	[{"changed": {"fields": ["Persona", "Instructions"]}}]	13	1
6	2023-06-14 13:58:30.585123+00	1	Moli	2	[{"changed": {"fields": ["Knowledge base"]}}]	13	1
7	2023-06-14 13:58:36.162282+00	1	Moli	2	[]	13	1
8	2023-06-14 14:00:15.116331+00	10	mioc.txt	2	[{"changed": {"fields": ["Text"]}}]	14	1
9	2023-06-14 14:02:31.966505+00	10	mioc.txt	2	[{"changed": {"fields": ["Text"]}}]	14	1
10	2023-06-14 14:05:59.450911+00	10	mioc.txt	2	[{"changed": {"fields": ["Text"]}}]	14	1
11	2023-06-14 14:09:26.931643+00	11	ib.txt	3		14	1
12	2023-06-14 14:21:01.036632+00	10	mioc.txt	2	[{"changed": {"fields": ["Text"]}}]	14	1
\.


--
-- Data for Name: django_content_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.django_content_type (id, app_label, model) FROM stdin;
1	auth	permission
2	auth	group
3	auth	user
4	contenttypes	contenttype
5	sessions	session
6	jet	bookmark
7	jet	pinnedapplication
8	dashboard	userdashboardmodule
9	admin	logentry
10	clients	client
11	configurations	configuration
12	managers	manager
13	assistants	assistantconfiguration
14	knowledge	file
\.


--
-- Data for Name: django_migrations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.django_migrations (id, app, name, applied) FROM stdin;
1	contenttypes	0001_initial	2023-06-13 16:02:15.988738+00
2	auth	0001_initial	2023-06-13 16:02:16.161495+00
3	admin	0001_initial	2023-06-13 16:02:16.209546+00
4	admin	0002_logentry_remove_auto_add	2023-06-13 16:02:16.241303+00
5	admin	0003_logentry_add_action_flag_choices	2023-06-13 16:02:16.272885+00
6	managers	0001_initial	2023-06-13 16:02:16.31996+00
7	knowledge	0001_initial	2023-06-13 16:02:16.346782+00
8	assistants	0001_initial	2023-06-13 16:02:16.404296+00
9	assistants	0002_auto_20230605_0748	2023-06-13 16:02:16.559718+00
10	assistants	0003_alter_assistantconfiguration_model	2023-06-13 16:02:16.572407+00
11	contenttypes	0002_remove_content_type_name	2023-06-13 16:02:16.66391+00
12	auth	0002_alter_permission_name_max_length	2023-06-13 16:02:16.694059+00
13	auth	0003_alter_user_email_max_length	2023-06-13 16:02:16.736011+00
14	auth	0004_alter_user_username_opts	2023-06-13 16:02:16.772181+00
15	auth	0005_alter_user_last_login_null	2023-06-13 16:02:16.817081+00
16	auth	0006_require_contenttypes_0002	2023-06-13 16:02:16.841277+00
17	auth	0007_alter_validators_add_error_messages	2023-06-13 16:02:16.870933+00
18	auth	0008_alter_user_username_max_length	2023-06-13 16:02:16.890638+00
19	auth	0009_alter_user_last_name_max_length	2023-06-13 16:02:16.90504+00
20	auth	0010_alter_group_name_max_length	2023-06-13 16:02:16.921688+00
21	auth	0011_update_proxy_permissions	2023-06-13 16:02:16.958032+00
22	auth	0012_alter_user_first_name_max_length	2023-06-13 16:02:16.985859+00
23	clients	0001_initial	2023-06-13 16:02:17.051609+00
24	configurations	0001_initial	2023-06-13 16:02:17.072931+00
25	dashboard	0001_initial	2023-06-13 16:02:17.091696+00
26	dashboard	0002_auto_20201228_1929	2023-06-13 16:02:17.142463+00
27	jet	0001_initial	2023-06-13 16:02:17.217552+00
28	jet	0002_delete_userdashboardmodule	2023-06-13 16:02:17.22171+00
29	jet	0003_auto_20201228_1540	2023-06-13 16:02:17.22621+00
30	jet	0004_auto_20201228_1802	2023-06-13 16:02:17.2303+00
31	managers	0002_auto_20230605_0748	2023-06-13 16:02:17.301775+00
32	sessions	0001_initial	2023-06-13 16:02:17.337553+00
33	jet	0001_squashed_0004_auto_20201228_1802	2023-06-13 16:02:17.342002+00
\.


--
-- Data for Name: django_session; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.django_session (session_key, session_data, expire_date) FROM stdin;
kg8rver3tmdr2e6zvu0c2dgri1dacchl	.eJxVjEEOwiAQRe_C2hCoAoNL956hmWEGqRpISrsy3l2bdKHb_977LzXiupRx7TKPE6uzsurwuxGmh9QN8B3rrenU6jJPpDdF77Tra2N5Xnb376BgL9_6GGI0IRGCZMEcOIlYMIOBwOLw5Cn7aICSl4ye08DZkRCb4B2BBfX-ABIcOTY:1q96To:RYTSoclb3eRrL8v2d0XQZ8p-tUF1MZN-UoaXtn2rQkk	2023-06-27 16:02:48.588075+00
\.


--
-- Data for Name: jet_bookmark; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.jet_bookmark (id, url, title, user_id, date_add) FROM stdin;
\.


--
-- Data for Name: jet_pinnedapplication; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.jet_pinnedapplication (id, app_label, user_id, date_add) FROM stdin;
\.


--
-- Data for Name: knowledge_file; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.knowledge_file (id, date_created, date_modified, name, text, item_status) FROM stdin;
1	2023-06-13 16:02:28.425335+00	2023-06-13 16:02:33.060584+00	default-knowledge	Mindsmiths is an AI startup from Zagreb. We are dedicated to helping people make fast and confident decisions at scale.\nWith a focus on delivering speedy and confident decision-making capabilities, we aim to help individuals and \norganizations navigate both complex challenges and everyday struggles efficiently and effectively. Whether it's in\nhealthcare, finance, education, or other industries, our mission is to provide reliable guidance and support to help\npeople achieve their goals.\n\n\nMislav Malenica is the CEO and founder of Mindsmiths and the Croatian Artificial Intelligence Association president.\nHe’s an expert in AI, machine learning, game theory, and the modeling of human behavior. Mislav has 18 years of\nexperience in AI, first as a research fellow at Ruđer Bošković Institute and later through AI startup endeavors both in\nSilicon Valley and Europe. At Mindsmiths, Mislav and his team are building a tool for developing AI-first applications.\n\n\nLivio Benčik is an AI engineer focused on building and democratizing trustworthy AI. With 7 years of experience in\nmachine learning, data science, and software engineering, he is currently Head of AI at Mindsmiths. His main\nresponsibilities include leading and coordinating the development of the Mindsmiths platform, a technology used for\ncreating autonomous support systems.\n\n\nJuraj Malenica is a software engineer focused on developing complex AI solutions. He has years of experience building\nstable and scalable systems, organizing engineering teams to solve real-world problems, and bridging the gap between the\nengineering and product worlds. He currently serves as Head of Engineering at Mindsmiths.\n\n\nMajana Havranek is currently working as a Product Development Manager at Mindsmiths. Her main focus is on team\ndevelopment, process optimization, and a deep understanding of customer needs. With 15 years of experience in developing\ndigital products for a variety of clients across agencies, media, and technology companies, Majana has established\nherself as a leading expert in her field. She is also a co-founder of the pioneering social media agencies in Croatia.\n\n\nMonika is a Developer Relations & Marketing Professional at Mindsmiths, where she uses her psychology background to\nenhance the developer experience and foster an engaged developer community. She manages a range of projects and\nworkshops and develops innovative content, demonstrations, and initiatives to showcase the capabilities of the\nMindsmiths platform. \n\n\nGita is an AI engineer at Mindsmiths. \nDomagoj is a software engineer at Mindsmiths, he has a huge passion for karaoke and loves kebab. \nMario is working in the DevOps team at Mindsmiths. \nTin is part of Mindsmiths DevOps team, along with Mario, Hrco and Ante.\nLora is a software engineer at Mindsmiths. She is the queen of documentation. \n\nHrvoje is a DevOps engineer focused on building strong open-minded teams. He has 7 years of experience building\nsoftware solutions deployed in cloud and integrating complex systems. He is currently Head of Infrastructure at\nMindsmiths and his main responsibilities are bringing stability, security, and scalability of infrastructure.\n\nEmil is a junior software engineer at Mindsmiths. He is great at making coffee. \n\nIvan is working as a software engineer at Mindsmiths.\n\n\n“The future of customer service builds on AI to deliver engaging experiences and generate lasting value.” (McKinsey,\n2022)\n\n\nHere are 4 main reasons why AI agents are the future:\n1. Personalization\n2. Proactivity\n3. Expertise\n4. Multi-platform integration\n\n\nPersonalization: “I know you”\nIn today’s fast-paced world, people expect technology to provide them with personalized and convenient experiences. In\nthe past, apps were the go-to solution for delivering these experiences. However, as technology evolves, a new player\nis breaking through — it’s better, smarter, more flexible, and better looking 😎. These intelligent creatures are\ndesigned to learn from your previous interactions, preferences, and behaviors, providing you with increasingly relevant\nand helpful advice. It’s like having a friend who knows you and reacts to your needs (and is not sarcastic).\n\n\nProactivity: “I care for you”\nUnlike apps, which typically require the user to initiate an action, AI agents are proactive and can reach out to users\nwith relevant information or recommendations. For example, an AI agent could notify you of an upcoming event, or suggest\na product that might bring value to you. This can help you save time and effort, making the experience more seamless and\nconvenient. You could say AI agents are the ultimate wingman, always looking out for you and ensuring you get exactly\nwhat you need when you need it.\n\n\nExpertise: “I have the expertise”\nAI agents can be built with specific expertise to solve particular problems. A financial AI agent could help you manage\nyour investments and retirement plan. A health AI agent could remind you to take medication, book doctor appointments,\netc. This can also be very useful, not only for complex issues or specialized industries but also for everyday\ndecisions, like choosing what to eat in a day (tough one).\n\n\nMulti-platform integration\nFinally, AI agents can be integrated across multiple platforms and apps, making them accessible to users wherever they\nare. For example, a user could interact with an AI agent through WhatsApp, Telegram, E-mail, etc. This flexibility can\nhelp users stay engaged with the AI agent, making it a more integral part of their daily routine. Guess we can agree\nthat checking WhatsApp is a daily habit for most of us while using an app requires much more effort (you must download\nit, learn how to use it, and remember to check it out…) — and who wants to put effort into something that should make\nyour life easier?	ADDED AS KNOWLEDGE
10	2023-06-14 13:56:53.581125+00	2023-06-14 14:21:06.984747+00	mioc.txt	Zagrebačka XV. gimnazija, popularno zvana MIOC, je obrazovnim profilom prirodoslovno-matematička gimnazija. Osnovana je 1964. Danas je XV. gimnazija smještena u moderno opremljenoj zgradi (39 specijaliziranih učionica, 26 kabineta za nastavnike, s laboratorijima biologije, kemije i fizike u kojima svaki učenik ima svoje radno mjesto), na adresi "Jordanovac 8". Gimnaziju pohađa oko 1100 učenika, raspoređenih u 39 razreda, samo u jutarnjoj smjeni.\r\n\r\nDanas se učenici XV. gimnazije mogu obrazovati u dva programa, nacionalnom i međunarodnom. Nacionalni program slijedi prirodoslovno-matematičku strukturu i organizaciju nastave, oblikovanu kroz A, B, C program. Osim školskog programa, na raspolaganju su i izvanškolske aktivnosti. To su knjižnica, polivalentna dvorana, kantina, knjižara, športska dvorana; vanjski tereni za košarku, rukomet, odbojku, tenis, teren za mali nogomet prekriven umjetnom travom.\r\n\r\nInternacionalni ili IB program u MIOC je uveden 1991. godine pod entuzijazmom prof. Veronike Javor. To je internacionalni program Međunarodne mature (International Baccalaureate). Zagovornik, promicatelj i voditelj IB-a do 1993. je bila profesorica Javor, koja je iste godine imenovana ravnateljicom XV. gimnazije. Izvođenje međunarodnoga programa, priznatoga u zemljama širom svijeta, odobrila je međunarodna organizacija u području obrazovanja, International Baccalaureate Organization (IBO) sa sjedištem u Ženevi te Ministarstvo znanosti, obrazovanja i športa Republike Hrvatske. \r\nUz Diploma Programme, koji obuhvaća dva završna razreda gimnazije, na kraju četvrtoga razreda učenici polažu eksternu maturu, na ispitnim materijalima koji dolaze iz inozemstva, te se šalju na ispravljanje i ocjenjivanje ispitivačima koje imenuje IB organizacija. Od 1995. uveden je međunarodni program za prve i druge razrede gimnazije, Middle Years Programme. Jezik komunikacije u programu Međunarodne mature i Diploma programu je engleski, a realiziraju ga profesori XV. gimnazije, za to osposobljeni na međunarodnim seminarima.\r\n\r\nPostoje tri nacionalna programa: A, B i C.\r\nA program predstavlja standardni nacionalni program koji slijedi program prirodoslovno-matematičke gimnazije s određenim brojem sati drugoga stranog jezika (u MIOC-u je to Njemački jezik).\r\nB program predstavlja program s pojačanim brojem sati informatike.\r\nC program predstavlja program s pojačanim brojem sati matematike.	ADDED AS KNOWLEDGE
\.


--
-- Data for Name: managers_manager; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.managers_manager (date_created, date_modified, id, first_name, last_name, description, phone_number, email, email_provider, time_zone, hitl_handle, auth_url, authenticated, assistant_configuration_id) FROM stdin;
2023-06-13 16:02:28.142377+00	2023-06-13 16:03:10.404468+00	default-manager	Domagoj	Gavranić	Ravnatelj	0911234567	hello@mindsmiths.com	GMAIL	America/New_York	@everyone	https://ireland.api.nylas.com/oauth/authorize?redirect_uri=http%3A%2F%2Flocalhost%3A8003%2Fauth-callback&client_id=None&response_type=token&login_hint=hello%40mindsmiths.com&state=https%3A%2F%2F8000.workspace-ms-domagoj.msdev.mindsmiths.io%2FmEAAyi7yw1rBR7ib&scopes=calendar%2Croom_resources.read_only&provider=gmail	f	1
\.


--
-- Name: assistants_assistantconfiguration_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.assistants_assistantconfiguration_id_seq', 1, true);


--
-- Name: assistants_assistantconfiguration_knowledge_base_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.assistants_assistantconfiguration_knowledge_base_id_seq', 3, true);


--
-- Name: auth_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.auth_group_id_seq', 1, true);


--
-- Name: auth_group_permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.auth_group_permissions_id_seq', 16, true);


--
-- Name: auth_permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.auth_permission_id_seq', 56, true);


--
-- Name: auth_user_groups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.auth_user_groups_id_seq', 1, true);


--
-- Name: auth_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.auth_user_id_seq', 2, true);


--
-- Name: auth_user_user_permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.auth_user_user_permissions_id_seq', 1, false);


--
-- Name: dashboard_userdashboardmodule_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dashboard_userdashboardmodule_id_seq', 3, true);


--
-- Name: django_admin_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.django_admin_log_id_seq', 12, true);


--
-- Name: django_content_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.django_content_type_id_seq', 14, true);


--
-- Name: django_migrations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.django_migrations_id_seq', 33, true);


--
-- Name: jet_bookmark_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.jet_bookmark_id_seq', 1, false);


--
-- Name: jet_pinnedapplication_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.jet_pinnedapplication_id_seq', 1, false);


--
-- Name: knowledge_file_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.knowledge_file_id_seq', 11, true);


--
-- Name: assistants_assistantconfiguration_knowledge_base assistants_assistantconf_assistantconfiguration_i_1b6a7586_uniq; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assistants_assistantconfiguration_knowledge_base
    ADD CONSTRAINT assistants_assistantconf_assistantconfiguration_i_1b6a7586_uniq UNIQUE (assistantconfiguration_id, file_id);


--
-- Name: assistants_assistantconfiguration assistants_assistantconfiguration_config_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assistants_assistantconfiguration
    ADD CONSTRAINT assistants_assistantconfiguration_config_id_key UNIQUE (config_id);


--
-- Name: assistants_assistantconfiguration_knowledge_base assistants_assistantconfiguration_knowledge_base_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assistants_assistantconfiguration_knowledge_base
    ADD CONSTRAINT assistants_assistantconfiguration_knowledge_base_pkey PRIMARY KEY (id);


--
-- Name: assistants_assistantconfiguration assistants_assistantconfiguration_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assistants_assistantconfiguration
    ADD CONSTRAINT assistants_assistantconfiguration_name_key UNIQUE (name);


--
-- Name: assistants_assistantconfiguration assistants_assistantconfiguration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assistants_assistantconfiguration
    ADD CONSTRAINT assistants_assistantconfiguration_pkey PRIMARY KEY (id);


--
-- Name: auth_group auth_group_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_group
    ADD CONSTRAINT auth_group_name_key UNIQUE (name);


--
-- Name: auth_group_permissions auth_group_permissions_group_id_permission_id_0cd325b0_uniq; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_group_permissions
    ADD CONSTRAINT auth_group_permissions_group_id_permission_id_0cd325b0_uniq UNIQUE (group_id, permission_id);


--
-- Name: auth_group_permissions auth_group_permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_group_permissions
    ADD CONSTRAINT auth_group_permissions_pkey PRIMARY KEY (id);


--
-- Name: auth_group auth_group_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_group
    ADD CONSTRAINT auth_group_pkey PRIMARY KEY (id);


--
-- Name: auth_permission auth_permission_content_type_id_codename_01ab375a_uniq; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_permission
    ADD CONSTRAINT auth_permission_content_type_id_codename_01ab375a_uniq UNIQUE (content_type_id, codename);


--
-- Name: auth_permission auth_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_permission
    ADD CONSTRAINT auth_permission_pkey PRIMARY KEY (id);


--
-- Name: auth_user_groups auth_user_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_user_groups
    ADD CONSTRAINT auth_user_groups_pkey PRIMARY KEY (id);


--
-- Name: auth_user_groups auth_user_groups_user_id_group_id_94350c0c_uniq; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_user_groups
    ADD CONSTRAINT auth_user_groups_user_id_group_id_94350c0c_uniq UNIQUE (user_id, group_id);


--
-- Name: auth_user auth_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_user
    ADD CONSTRAINT auth_user_pkey PRIMARY KEY (id);


--
-- Name: auth_user_user_permissions auth_user_user_permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permissions_pkey PRIMARY KEY (id);


--
-- Name: auth_user_user_permissions auth_user_user_permissions_user_id_permission_id_14a6b632_uniq; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permissions_user_id_permission_id_14a6b632_uniq UNIQUE (user_id, permission_id);


--
-- Name: auth_user auth_user_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_user
    ADD CONSTRAINT auth_user_username_key UNIQUE (username);


--
-- Name: clients_client clients_client_phone_number_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients_client
    ADD CONSTRAINT clients_client_phone_number_key UNIQUE (phone_number);


--
-- Name: clients_client clients_client_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients_client
    ADD CONSTRAINT clients_client_pkey PRIMARY KEY (id);


--
-- Name: configurations_configuration configurations_configuration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.configurations_configuration
    ADD CONSTRAINT configurations_configuration_pkey PRIMARY KEY (id);


--
-- Name: dashboard_userdashboardmodule dashboard_userdashboardmodule_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard_userdashboardmodule
    ADD CONSTRAINT dashboard_userdashboardmodule_pkey PRIMARY KEY (id);


--
-- Name: django_admin_log django_admin_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.django_admin_log
    ADD CONSTRAINT django_admin_log_pkey PRIMARY KEY (id);


--
-- Name: django_content_type django_content_type_app_label_model_76bd3d3b_uniq; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.django_content_type
    ADD CONSTRAINT django_content_type_app_label_model_76bd3d3b_uniq UNIQUE (app_label, model);


--
-- Name: django_content_type django_content_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.django_content_type
    ADD CONSTRAINT django_content_type_pkey PRIMARY KEY (id);


--
-- Name: django_migrations django_migrations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.django_migrations
    ADD CONSTRAINT django_migrations_pkey PRIMARY KEY (id);


--
-- Name: django_session django_session_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.django_session
    ADD CONSTRAINT django_session_pkey PRIMARY KEY (session_key);


--
-- Name: jet_bookmark jet_bookmark_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jet_bookmark
    ADD CONSTRAINT jet_bookmark_pkey PRIMARY KEY (id);


--
-- Name: jet_pinnedapplication jet_pinnedapplication_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jet_pinnedapplication
    ADD CONSTRAINT jet_pinnedapplication_pkey PRIMARY KEY (id);


--
-- Name: knowledge_file knowledge_file_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.knowledge_file
    ADD CONSTRAINT knowledge_file_name_key UNIQUE (name);


--
-- Name: knowledge_file knowledge_file_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.knowledge_file
    ADD CONSTRAINT knowledge_file_pkey PRIMARY KEY (id);


--
-- Name: managers_manager managers_manager_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.managers_manager
    ADD CONSTRAINT managers_manager_email_key UNIQUE (email);


--
-- Name: managers_manager managers_manager_phone_number_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.managers_manager
    ADD CONSTRAINT managers_manager_phone_number_key UNIQUE (phone_number);


--
-- Name: managers_manager managers_manager_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.managers_manager
    ADD CONSTRAINT managers_manager_pkey PRIMARY KEY (id);


--
-- Name: assistants_assistantconf_default_manager_id_2a2037f5_like; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX assistants_assistantconf_default_manager_id_2a2037f5_like ON public.assistants_assistantconfiguration USING btree (default_manager_id varchar_pattern_ops);


--
-- Name: assistants_assistantconfig_assistantconfiguration_id_b3ebe1c9; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX assistants_assistantconfig_assistantconfiguration_id_b3ebe1c9 ON public.assistants_assistantconfiguration_knowledge_base USING btree (assistantconfiguration_id);


--
-- Name: assistants_assistantconfig_file_id_9abd681c; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX assistants_assistantconfig_file_id_9abd681c ON public.assistants_assistantconfiguration_knowledge_base USING btree (file_id);


--
-- Name: assistants_assistantconfiguration_config_id_8aca2619_like; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX assistants_assistantconfiguration_config_id_8aca2619_like ON public.assistants_assistantconfiguration USING btree (config_id varchar_pattern_ops);


--
-- Name: assistants_assistantconfiguration_default_manager_id_2a2037f5; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX assistants_assistantconfiguration_default_manager_id_2a2037f5 ON public.assistants_assistantconfiguration USING btree (default_manager_id);


--
-- Name: assistants_assistantconfiguration_name_a10fe352_like; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX assistants_assistantconfiguration_name_a10fe352_like ON public.assistants_assistantconfiguration USING btree (name varchar_pattern_ops);


--
-- Name: auth_group_name_a6ea08ec_like; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX auth_group_name_a6ea08ec_like ON public.auth_group USING btree (name varchar_pattern_ops);


--
-- Name: auth_group_permissions_group_id_b120cbf9; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX auth_group_permissions_group_id_b120cbf9 ON public.auth_group_permissions USING btree (group_id);


--
-- Name: auth_group_permissions_permission_id_84c5c92e; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX auth_group_permissions_permission_id_84c5c92e ON public.auth_group_permissions USING btree (permission_id);


--
-- Name: auth_permission_content_type_id_2f476e4b; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX auth_permission_content_type_id_2f476e4b ON public.auth_permission USING btree (content_type_id);


--
-- Name: auth_user_groups_group_id_97559544; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX auth_user_groups_group_id_97559544 ON public.auth_user_groups USING btree (group_id);


--
-- Name: auth_user_groups_user_id_6a12ed8b; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX auth_user_groups_user_id_6a12ed8b ON public.auth_user_groups USING btree (user_id);


--
-- Name: auth_user_user_permissions_permission_id_1fbb5f2c; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX auth_user_user_permissions_permission_id_1fbb5f2c ON public.auth_user_user_permissions USING btree (permission_id);


--
-- Name: auth_user_user_permissions_user_id_a95ead1b; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX auth_user_user_permissions_user_id_a95ead1b ON public.auth_user_user_permissions USING btree (user_id);


--
-- Name: auth_user_username_6821ab7c_like; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX auth_user_username_6821ab7c_like ON public.auth_user USING btree (username varchar_pattern_ops);


--
-- Name: clients_client_assistant_configuration_id_e443ab95; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX clients_client_assistant_configuration_id_e443ab95 ON public.clients_client USING btree (assistant_configuration_id);


--
-- Name: clients_client_id_11b3e1e4_like; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX clients_client_id_11b3e1e4_like ON public.clients_client USING btree (id varchar_pattern_ops);


--
-- Name: clients_client_manager_id_656c7ef3; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX clients_client_manager_id_656c7ef3 ON public.clients_client USING btree (manager_id);


--
-- Name: clients_client_manager_id_656c7ef3_like; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX clients_client_manager_id_656c7ef3_like ON public.clients_client USING btree (manager_id varchar_pattern_ops);


--
-- Name: clients_client_phone_number_0131e312_like; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX clients_client_phone_number_0131e312_like ON public.clients_client USING btree (phone_number varchar_pattern_ops);


--
-- Name: configurations_configuration_id_48c574bb_like; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX configurations_configuration_id_48c574bb_like ON public.configurations_configuration USING btree (id varchar_pattern_ops);


--
-- Name: dashboard_userdashboardmodule_user_id_97c13132; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX dashboard_userdashboardmodule_user_id_97c13132 ON public.dashboard_userdashboardmodule USING btree (user_id);


--
-- Name: django_admin_log_content_type_id_c4bce8eb; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX django_admin_log_content_type_id_c4bce8eb ON public.django_admin_log USING btree (content_type_id);


--
-- Name: django_admin_log_user_id_c564eba6; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX django_admin_log_user_id_c564eba6 ON public.django_admin_log USING btree (user_id);


--
-- Name: django_session_expire_date_a5c62663; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX django_session_expire_date_a5c62663 ON public.django_session USING btree (expire_date);


--
-- Name: django_session_session_key_c0390e0f_like; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX django_session_session_key_c0390e0f_like ON public.django_session USING btree (session_key varchar_pattern_ops);


--
-- Name: jet_bookmark_user_id_8efdc332; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX jet_bookmark_user_id_8efdc332 ON public.jet_bookmark USING btree (user_id);


--
-- Name: jet_pinnedapplication_user_id_7765bcf9; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX jet_pinnedapplication_user_id_7765bcf9 ON public.jet_pinnedapplication USING btree (user_id);


--
-- Name: knowledge_file_name_085bf8e7_like; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX knowledge_file_name_085bf8e7_like ON public.knowledge_file USING btree (name varchar_pattern_ops);


--
-- Name: managers_manager_assistant_configuration_id_4f5e4b58; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX managers_manager_assistant_configuration_id_4f5e4b58 ON public.managers_manager USING btree (assistant_configuration_id);


--
-- Name: managers_manager_email_17d8e474_like; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX managers_manager_email_17d8e474_like ON public.managers_manager USING btree (email varchar_pattern_ops);


--
-- Name: managers_manager_id_dea95d5e_like; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX managers_manager_id_dea95d5e_like ON public.managers_manager USING btree (id varchar_pattern_ops);


--
-- Name: managers_manager_phone_number_7f59ea0d_like; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX managers_manager_phone_number_7f59ea0d_like ON public.managers_manager USING btree (phone_number varchar_pattern_ops);


--
-- Name: assistants_assistantconfiguration_knowledge_base assistants_assistant_assistantconfigurati_b3ebe1c9_fk_assistant; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assistants_assistantconfiguration_knowledge_base
    ADD CONSTRAINT assistants_assistant_assistantconfigurati_b3ebe1c9_fk_assistant FOREIGN KEY (assistantconfiguration_id) REFERENCES public.assistants_assistantconfiguration(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: assistants_assistantconfiguration assistants_assistant_default_manager_id_2a2037f5_fk_managers_; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assistants_assistantconfiguration
    ADD CONSTRAINT assistants_assistant_default_manager_id_2a2037f5_fk_managers_ FOREIGN KEY (default_manager_id) REFERENCES public.managers_manager(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: assistants_assistantconfiguration_knowledge_base assistants_assistant_file_id_9abd681c_fk_knowledge; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assistants_assistantconfiguration_knowledge_base
    ADD CONSTRAINT assistants_assistant_file_id_9abd681c_fk_knowledge FOREIGN KEY (file_id) REFERENCES public.knowledge_file(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_group_permissions auth_group_permissio_permission_id_84c5c92e_fk_auth_perm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_group_permissions
    ADD CONSTRAINT auth_group_permissio_permission_id_84c5c92e_fk_auth_perm FOREIGN KEY (permission_id) REFERENCES public.auth_permission(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_group_permissions auth_group_permissions_group_id_b120cbf9_fk_auth_group_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_group_permissions
    ADD CONSTRAINT auth_group_permissions_group_id_b120cbf9_fk_auth_group_id FOREIGN KEY (group_id) REFERENCES public.auth_group(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_permission auth_permission_content_type_id_2f476e4b_fk_django_co; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_permission
    ADD CONSTRAINT auth_permission_content_type_id_2f476e4b_fk_django_co FOREIGN KEY (content_type_id) REFERENCES public.django_content_type(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_user_groups auth_user_groups_group_id_97559544_fk_auth_group_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_user_groups
    ADD CONSTRAINT auth_user_groups_group_id_97559544_fk_auth_group_id FOREIGN KEY (group_id) REFERENCES public.auth_group(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_user_groups auth_user_groups_user_id_6a12ed8b_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_user_groups
    ADD CONSTRAINT auth_user_groups_user_id_6a12ed8b_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES public.auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_user_user_permissions auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm FOREIGN KEY (permission_id) REFERENCES public.auth_permission(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_user_user_permissions auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES public.auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: clients_client clients_client_assistant_configurat_e443ab95_fk_assistant; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients_client
    ADD CONSTRAINT clients_client_assistant_configurat_e443ab95_fk_assistant FOREIGN KEY (assistant_configuration_id) REFERENCES public.assistants_assistantconfiguration(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: clients_client clients_client_manager_id_656c7ef3_fk_managers_manager_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients_client
    ADD CONSTRAINT clients_client_manager_id_656c7ef3_fk_managers_manager_id FOREIGN KEY (manager_id) REFERENCES public.managers_manager(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: dashboard_userdashboardmodule dashboard_userdashboardmodule_user_id_97c13132_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dashboard_userdashboardmodule
    ADD CONSTRAINT dashboard_userdashboardmodule_user_id_97c13132_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES public.auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: django_admin_log django_admin_log_content_type_id_c4bce8eb_fk_django_co; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.django_admin_log
    ADD CONSTRAINT django_admin_log_content_type_id_c4bce8eb_fk_django_co FOREIGN KEY (content_type_id) REFERENCES public.django_content_type(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: django_admin_log django_admin_log_user_id_c564eba6_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.django_admin_log
    ADD CONSTRAINT django_admin_log_user_id_c564eba6_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES public.auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: jet_bookmark jet_bookmark_user_id_8efdc332_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jet_bookmark
    ADD CONSTRAINT jet_bookmark_user_id_8efdc332_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES public.auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: jet_pinnedapplication jet_pinnedapplication_user_id_7765bcf9_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jet_pinnedapplication
    ADD CONSTRAINT jet_pinnedapplication_user_id_7765bcf9_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES public.auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: managers_manager managers_manager_assistant_configurat_4f5e4b58_fk_assistant; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.managers_manager
    ADD CONSTRAINT managers_manager_assistant_configurat_4f5e4b58_fk_assistant FOREIGN KEY (assistant_configuration_id) REFERENCES public.assistants_assistantconfiguration(id) DEFERRABLE INITIALLY DEFERRED;


--
-- PostgreSQL database dump complete
--

