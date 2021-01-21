SET search_path = public, pg_catalog;
DROP TRIGGER IF EXISTS insert_player_stats ON public.players;
DROP TRIGGER IF EXISTS insert_account_activity ON public.accounts;
DROP TABLE IF EXISTS public.account_access;
DROP TABLE IF EXISTS public.levelup;
DROP TABLE IF EXISTS public.goods;
DROP TABLE IF EXISTS public.template_other;
DROP TABLE IF EXISTS public.template_items;
DROP TABLE IF EXISTS public.templates;
DROP SEQUENCE IF EXISTS public.templates_id_seq;
DROP TABLE IF EXISTS public.player_friends;
DROP SEQUENCE IF EXISTS public.player_friends_player_account_id_seq;
DROP TABLE IF EXISTS public.player_eqipment;
DROP SEQUENCE IF EXISTS public.player_eqipment_id_seq;
DROP TABLE IF EXISTS public.items;
DROP SEQUENCE IF EXISTS public.items_id_seq;
DROP TABLE IF EXISTS public.channels;
DROP SEQUENCE IF EXISTS public.channels_id_seq;
DROP TABLE IF EXISTS public.ipsystem;
DROP SEQUENCE IF EXISTS public.ipsystem_id_seq;
DROP TABLE IF EXISTS public.account_banned;
DROP TABLE IF EXISTS public.gameservers;
DROP SEQUENCE IF EXISTS public.gameservers_id_seq;
DROP TABLE IF EXISTS public.player_clan;
DROP TABLE IF EXISTS public.clans;
DROP SEQUENCE IF EXISTS public.clans_id_seq;
DROP TABLE IF EXISTS public.player_stats;
DROP TABLE IF EXISTS public.players;
DROP SEQUENCE IF EXISTS public.players_id_seq;
DROP TABLE IF EXISTS public.account_bonuses;
DROP TABLE IF EXISTS public.account_activity;
DROP TABLE IF EXISTS public.accounts;
DROP SEQUENCE IF EXISTS public.accounts_id_seq;
DROP TABLE IF EXISTS public.settings;
DROP FUNCTION IF EXISTS public.insert_player_stats ();
DROP FUNCTION IF EXISTS public.insert_account_activity ();
--
-- Definition for function insert_account_activity (OID = 24792) : 
--
SET check_function_bodies = false;
CREATE FUNCTION public.insert_account_activity (
)
RETURNS trigger
AS 
$body$
            BEGIN
            INSERT INTO account_activity(account_id) VALUES (NEW.id);
            RETURN NEW;
            END$body$
    LANGUAGE plpgsql;
--
-- Definition for function insert_player_stats (OID = 24794) : 
--
CREATE FUNCTION public.insert_player_stats (
)
RETURNS trigger
AS 
$body$
            BEGIN
            INSERT INTO player_stats(player_id) VALUES (NEW.id);
            RETURN NEW;
            END$body$
    LANGUAGE plpgsql;
--
-- Structure for table settings (OID = 156619) : 
--
CREATE TABLE public.settings (
    type varchar(50) NOT NULL,
    name varchar(50) NOT NULL,
    value varchar(50) NOT NULL,
    regexp varchar(50) NOT NULL
) WITHOUT OIDS;
--
-- Definition for sequence accounts_id_seq (OID = 156624) : 
--
CREATE SEQUENCE public.accounts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table accounts (OID = 156626) : 
--
CREATE TABLE public.accounts (
    id bigint DEFAULT nextval('accounts_id_seq'::regclass) NOT NULL,
    login varchar(50) NOT NULL,
    password varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    money integer DEFAULT 0 NOT NULL,
    active boolean DEFAULT true NOT NULL
) WITHOUT OIDS;
--
-- Structure for table account_activity (OID = 156638) : 
--
CREATE TABLE public.account_activity (
    account_id bigint NOT NULL,
    last_active timestamp with time zone DEFAULT now() NOT NULL,
    last_ip varchar(15) DEFAULT '127.0.0.1'::character varying NOT NULL,
    total_active integer DEFAULT 0 NOT NULL
) WITHOUT OIDS;
--
-- Structure for table account_bonuses (OID = 156651) : 
--
CREATE TABLE public.account_bonuses (
    account_id bigint NOT NULL,
    bonus integer NOT NULL,
    bonus_expire timestamp with time zone NOT NULL
) WITHOUT OIDS;
--
-- Definition for sequence players_id_seq (OID = 156659) : 
--
CREATE SEQUENCE public.players_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table players (OID = 156661) : 
--
CREATE TABLE public.players (
    id bigint DEFAULT nextval('players_id_seq'::regclass) NOT NULL,
    account_id bigint NOT NULL,
    name varchar(35) NOT NULL,
    color integer DEFAULT 0 NOT NULL,
    rank integer DEFAULT 0 NOT NULL,
    gp integer DEFAULT 0 NOT NULL,
    exp integer DEFAULT 0 NOT NULL,
    pc_cafe integer DEFAULT 0 NOT NULL,
    online boolean DEFAULT false NOT NULL
) WITHOUT OIDS;
--
-- Structure for table player_stats (OID = 156680) : 
--
CREATE TABLE public.player_stats (
    player_id bigint NOT NULL,
    season_fights integer DEFAULT 0 NOT NULL,
    season_wins integer DEFAULT 0 NOT NULL,
    season_losts integer DEFAULT 0 NOT NULL,
    season_kills integer DEFAULT 0 NOT NULL,
    season_deaths integer DEFAULT 0 NOT NULL,
    season_seria_wins integer DEFAULT 0 NOT NULL,
    season_kpd integer DEFAULT 0 NOT NULL,
    season_escapes integer DEFAULT 0 NOT NULL,
    fights integer DEFAULT 0 NOT NULL,
    wins integer DEFAULT 0 NOT NULL,
    losts integer DEFAULT 0 NOT NULL,
    kills integer DEFAULT 0 NOT NULL,
    deaths integer DEFAULT 0 NOT NULL,
    seria_wins integer DEFAULT 0 NOT NULL,
    kpd integer DEFAULT 0 NOT NULL,
    escapes integer DEFAULT 0 NOT NULL
) WITHOUT OIDS;
--
-- Definition for sequence clans_id_seq (OID = 156706) : 
--
CREATE SEQUENCE public.clans_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table clans (OID = 156708) : 
--
CREATE TABLE public.clans (
    id bigint DEFAULT nextval('clans_id_seq'::regclass) NOT NULL,
    name varchar(50) NOT NULL,
    rank integer NOT NULL,
    owner_id bigint NOT NULL,
    color integer NOT NULL,
    logo1 integer,
    logo2 integer,
    logo3 integer,
    logo4 integer
) WITHOUT OIDS;
--
-- Structure for table player_clan (OID = 156723) : 
--
CREATE TABLE public.player_clan (
    clan_id bigint NOT NULL,
    player_id bigint NOT NULL,
    rank integer NOT NULL
) WITHOUT OIDS;
--
-- Definition for sequence gameservers_id_seq (OID = 156738) : 
--
CREATE SEQUENCE public.gameservers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table gameservers (OID = 156740) : 
--
CREATE TABLE public.gameservers (
    id bigint DEFAULT nextval('gameservers_id_seq'::regclass) NOT NULL,
    password varchar(100) NOT NULL,
    type varchar(25) NOT NULL,
    max_players integer NOT NULL,
    CONSTRAINT check_max_player CHECK (((max_players % 10) = 0))
) WITHOUT OIDS;
--
-- Structure for table account_banned (OID = 156747) : 
--
CREATE TABLE public.account_banned (
    type varchar(25) NOT NULL,
    value varchar(25) NOT NULL,
    date_lock timestamp with time zone NOT NULL,
    date_unlock timestamp with time zone NOT NULL,
    owner_id bigint NOT NULL,
    reason varchar(150) NOT NULL
) WITHOUT OIDS;
--
-- Definition for sequence ipsystem_id_seq (OID = 156750) : 
--
CREATE SEQUENCE public.ipsystem_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table ipsystem (OID = 156752) : 
--
CREATE TABLE public.ipsystem (
    id bigint DEFAULT nextval('ipsystem_id_seq'::regclass) NOT NULL,
    type varchar(5) NOT NULL,
    startpoint varchar(15) NOT NULL,
    endpoint varchar(15) NOT NULL
) WITHOUT OIDS;
--
-- Definition for sequence channels_id_seq (OID = 156758) : 
--
CREATE SEQUENCE public.channels_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table channels (OID = 156760) : 
--
CREATE TABLE public.channels (
    id bigint DEFAULT nextval('channels_id_seq'::regclass) NOT NULL,
    gameserver_id bigint NOT NULL,
    type varchar(25) NOT NULL,
    announce varchar(150) NOT NULL
) WITHOUT OIDS;
--
-- Definition for sequence items_id_seq (OID = 156771) : 
--
CREATE SEQUENCE public.items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table items (OID = 156773) : 
--
CREATE TABLE public.items (
    id bigint DEFAULT nextval('items_id_seq'::regclass) NOT NULL,
    type varchar(25) NOT NULL,
    slot_type varchar(25) NOT NULL,
    consume_type varchar(25) NOT NULL,
    consume_value integer,
    repair_credits integer,
    repair_points integer,
    repair_quantity integer,
    CONSTRAINT check_consume_type CHECK (((((consume_type)::text = 'DURABLE'::text) OR ((consume_type)::text = 'TEMPORARY'::text)) OR ((consume_type)::text = 'PERMANENT'::text))),
    CONSTRAINT check_consume_type_value CHECK (CASE WHEN (((consume_type)::text = 'TEMPORARY'::text) OR ((consume_type)::text = 'DURABLE'::text)) THEN (consume_value IS NOT NULL) ELSE true END),
    CONSTRAINT check_slot_type_by_type CHECK (CASE WHEN ((type)::text = 'WEAPON'::text) THEN ((((((slot_type)::text = 'PRIM'::text) OR ((slot_type)::text = 'SUB'::text)) OR ((slot_type)::text = 'MELEE'::text)) OR ((slot_type)::text = 'THROWING'::text)) OR ((slot_type)::text = 'ITEM'::text)) ELSE CASE WHEN ((type)::text = 'CHARACTER'::text) THEN ((((((slot_type)::text = 'CHAR_RED'::text) OR ((slot_type)::text = 'CHAR_BLUE'::text)) OR ((slot_type)::text = 'CHAR_HEAD'::text)) OR ((slot_type)::text = 'CHAR_ITEM'::text)) OR ((slot_type)::text = 'CHAR_DINO'::text)) ELSE true END END),
    CONSTRAINT check_type CHECK (((((type)::text = 'WEAPON'::text) OR ((type)::text = 'CHARACTER'::text)) OR ((type)::text = 'COUPON'::text)))
) WITHOUT OIDS;
--
-- Definition for sequence player_eqipment_id_seq (OID = 156783) : 
--
CREATE SEQUENCE public.player_eqipment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table player_eqipment (OID = 156785) : 
--
CREATE TABLE public.player_eqipment (
    id bigint DEFAULT nextval('player_eqipment_id_seq'::regclass) NOT NULL,
    player_id bigint NOT NULL,
    item_id bigint NOT NULL,
    count integer NOT NULL,
    loc varchar(25) NOT NULL,
    consume_lost integer
) WITHOUT OIDS;
--
-- Definition for sequence player_friends_player_account_id_seq (OID = 156803) : 
--
CREATE SEQUENCE public.player_friends_player_account_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table player_friends (OID = 156805) : 
--
CREATE TABLE public.player_friends (
    player_account_id bigint DEFAULT nextval('player_friends_player_account_id_seq'::regclass) NOT NULL,
    friend_id integer
) WITHOUT OIDS;
--
-- Definition for sequence templates_id_seq (OID = 156823) : 
--
CREATE SEQUENCE public.templates_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table templates (OID = 156825) : 
--
CREATE TABLE public.templates (
    id bigint DEFAULT nextval('templates_id_seq'::regclass) NOT NULL,
    date_start timestamp with time zone NOT NULL,
    date_stop timestamp with time zone NOT NULL,
    active boolean NOT NULL
) WITHOUT OIDS;
--
-- Structure for table template_items (OID = 156831) : 
--
CREATE TABLE public.template_items (
    template_id bigint NOT NULL,
    item_id bigint NOT NULL,
    count integer NOT NULL,
    equipped integer NOT NULL
) WITHOUT OIDS;
--
-- Structure for table template_other (OID = 156844) : 
--
CREATE TABLE public.template_other (
    template_id bigint NOT NULL,
    startmoney integer NOT NULL,
    startgp integer NOT NULL
) WITHOUT OIDS;
--
-- Structure for table goods (OID = 156852) : 
--
CREATE TABLE public.goods (
    good_id integer NOT NULL,
    item_id integer NOT NULL,
    quantity integer NOT NULL,
    pricecredits integer NOT NULL,
    pricepoints integer NOT NULL,
    stocktype integer NOT NULL
) WITHOUT OIDS;
--
-- Structure for table levelup (OID = 156857) : 
--
CREATE TABLE public.levelup (
    rank integer NOT NULL,
    "needExp" integer NOT NULL,
    "allExp" integer NOT NULL,
    "rewardGP" integer NOT NULL,
    "rewardItems" integer[] NOT NULL
) WITHOUT OIDS;
--
-- Structure for table account_access (OID = 156865) : 
--
CREATE TABLE public.account_access (
    accountid integer NOT NULL,
    level integer NOT NULL
) WITHOUT OIDS;

--
-- Data for table public.gameservers (OID = 156740) (LIMIT 0,1)
--
INSERT INTO gameservers (id, password, type, max_players)
VALUES (1, 'test', 'NORMAL_1', 300);

--
-- Data for table public.channels (OID = 156760) (LIMIT 0,10)
--
INSERT INTO channels (id, gameserver_id, type, announce)
VALUES (0, 1, 'NORMAL_1', 'This is 1 channel. Type: NORMAL_1');

INSERT INTO channels (id, gameserver_id, type, announce)
VALUES (1, 1, 'NORMAL_2', 'This is 2 channel. Type: NORMAL_2');

INSERT INTO channels (id, gameserver_id, type, announce)
VALUES (2, 1, 'BEGINERS', 'This is 4 channel. Type: BEGINERS');

INSERT INTO channels (id, gameserver_id, type, announce)
VALUES (3, 1, 'CLAN', 'This is 4 channel. Type: CLAN');

INSERT INTO channels (id, gameserver_id, type, announce)
VALUES (4, 1, 'EXPERT_1', 'This is 5 channel. Type: EXPERT_1');

INSERT INTO channels (id, gameserver_id, type, announce)
VALUES (5, 1, 'EXPERT_2', 'This is 6 channel. Type: EXPERT_2');

INSERT INTO channels (id, gameserver_id, type, announce)
VALUES (6, 1, 'CHAMPIONSHIP', 'This is 7 channel. Type: CHAMPIONSHIP');

INSERT INTO channels (id, gameserver_id, type, announce)
VALUES (7, 1, 'STATE', 'This is 8 channel. Type: STATE');

INSERT INTO channels (id, gameserver_id, type, announce)
VALUES (8, 1, 'NORMAL_1', 'This is 9 channel. Type: NORMAL_1');

INSERT INTO channels (id, gameserver_id, type, announce)
VALUES (9, 1, 'NORMAL_2', 'This is 10 channel. Type: NORMAL_2');

--
-- Data for table public.items (OID = 156773) (LIMIT 0,442)
--
INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001001003, 'CHARACTER', 'CHAR_RED', 'PERMANENT', 300, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001001005, 'CHARACTER', 'CHAR_RED', 'PERMANENT', 300, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001001007, 'CHARACTER', 'CHAR_RED', 'PERMANENT', 300, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001001010, 'CHARACTER', 'CHAR_RED', 'PERMANENT', 300, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001001011, 'CHARACTER', 'CHAR_RED', 'PERMANENT', 300, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001001013, 'CHARACTER', 'CHAR_RED', 'PERMANENT', 300, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001001015, 'CHARACTER', 'CHAR_RED', 'PERMANENT', 300, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001001017, 'CHARACTER', 'CHAR_RED', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001001021, 'CHARACTER', 'CHAR_RED', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001001022, 'CHARACTER', 'CHAR_RED', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001001034, 'CHARACTER', 'CHAR_RED', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001002004, 'CHARACTER', 'CHAR_BLUE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001002006, 'CHARACTER', 'CHAR_BLUE', 'PERMANENT', 0, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001002008, 'CHARACTER', 'CHAR_BLUE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001002009, 'CHARACTER', 'CHAR_BLUE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001002012, 'CHARACTER', 'CHAR_BLUE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001002014, 'CHARACTER', 'CHAR_BLUE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001002016, 'CHARACTER', 'CHAR_BLUE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001002018, 'CHARACTER', 'CHAR_BLUE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001002019, 'CHARACTER', 'CHAR_BLUE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001002020, 'CHARACTER', 'CHAR_BLUE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1001002033, 'CHARACTER', 'CHAR_BLUE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1006001024, 'CHARACTER', 'CHAR_DINO', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1006001029, 'CHARACTER', 'CHAR_DINO', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1006001030, 'CHARACTER', 'CHAR_DINO', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1006001031, 'CHARACTER', 'CHAR_DINO', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1006001032, 'CHARACTER', 'CHAR_DINO', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1102003001, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1102003002, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1102003003, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1102003004, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1102003005, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1102003006, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1102003007, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1102003008, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1103003001, 'CHARACTER', 'CHAR_ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1103003002, 'CHARACTER', 'CHAR_ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1103003003, 'CHARACTER', 'CHAR_ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1103003004, 'CHARACTER', 'CHAR_ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1103003005, 'CHARACTER', 'CHAR_ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1103003006, 'CHARACTER', 'CHAR_ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1103003007, 'CHARACTER', 'CHAR_ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1103003008, 'CHARACTER', 'CHAR_ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1103003009, 'CHARACTER', 'CHAR_ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1103003010, 'CHARACTER', 'CHAR_ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003001, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003002, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003003, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003004, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003005, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003006, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003007, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003008, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003009, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003010, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003011, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003012, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003013, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003014, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003015, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003016, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003017, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003018, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003019, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003020, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003021, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003022, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003023, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003024, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003025, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003026, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003027, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003028, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003029, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003030, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003031, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003035, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003036, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003037, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (1104003038, 'CHARACTER', 'CHAR_HEAD', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100001030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100002030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100004030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100005030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100006030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100007030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100008030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100009030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100010030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100011030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100012030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100013030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100014030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100015030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100017030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100018030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100019030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100020030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100021030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100022030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100023030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100024030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100025030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100026030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100027030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100028030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100029030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100030030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100031030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100032030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100033030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100034030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100035030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100036030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100037030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100038030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100040030, 'COUPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004001, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004002, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004003, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004004, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004006, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004007, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004008, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004009, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004010, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004011, 'WEAPON', 'PRIM', 'DURABLE', 350, 2, 3, 100);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004012, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004013, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004014, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004015, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004016, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004019, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004020, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004021, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004022, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004023, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004024, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004025, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004026, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004027, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004028, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004029, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004032, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004033, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004034, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004035, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004036, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004039, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004043, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004045, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004046, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004047, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004048, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004050, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004052, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004053, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004054, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004057, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004058, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004059, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004060, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004061, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004062, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004063, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004064, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004065, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004066, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004067, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004068, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004069, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004070, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004071, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004072, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004073, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004074, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004075, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004076, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004077, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004078, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004079, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004080, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004081, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004082, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004083, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004084, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004085, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200004086, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200018004, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200018005, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200018006, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200018007, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200018008, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200018009, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200018010, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (200018011, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100016002, 'WEAPON', 'PRIM', 'PERMANENT', 0, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (500010001, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (500010002, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (500010003, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (500010004, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006001, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006002, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006003, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006004, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006005, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006006, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006007, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006008, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006009, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006010, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006011, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006012, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006014, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006015, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006016, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006017, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006018, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006019, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006020, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (400006021, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003001, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003002, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003003, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003004, 'WEAPON', 'PRIM', 'PERMANENT', 0, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003005, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003009, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003010, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003011, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003013, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003014, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003015, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003017, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003018, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003019, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003020, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003021, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003022, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003023, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003024, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003025, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003026, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003027, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003028, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003029, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003030, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003031, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003032, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003033, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003034, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003035, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003036, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003037, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003038, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003039, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003040, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003041, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003042, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003043, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003044, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003045, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003046, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003047, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003048, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003049, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003050, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003051, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003052, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003053, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003054, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003055, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003056, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003057, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003058, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003059, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003060, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003061, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003062, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003063, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003064, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003065, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003068, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003069, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003071, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003072, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003073, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003074, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003075, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003076, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003077, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003078, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003079, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003080, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003081, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003082, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003083, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003084, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003085, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003086, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003087, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003088, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003089, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003090, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003091, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003092, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003093, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003094, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003095, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003096, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003097, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003098, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003099, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003100, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003101, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003102, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (100003103, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005001, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005002, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005003, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005004, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005005, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005006, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005007, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005008, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005009, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005010, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005011, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005012, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005014, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005015, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005016, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005017, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005018, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005019, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005020, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005021, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005022, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005023, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005024, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005025, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005026, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005027, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005028, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005029, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005030, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005031, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005032, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005033, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005034, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005035, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005036, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005037, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005038, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005039, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005040, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005041, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005042, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005043, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005044, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005045, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005046, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005047, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005048, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005049, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005050, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005052, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005053, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (300005054, 'WEAPON', 'PRIM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002001, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002002, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002003, 'WEAPON', 'SUB', 'TEMPORARY', 708565, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002005, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002007, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002008, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002009, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002010, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002011, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002012, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002013, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002014, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002015, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002016, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002017, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002018, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002019, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002020, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002021, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002022, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002023, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002026, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002027, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002028, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601002029, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601013001, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601013002, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601013003, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601013004, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601014001, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601014002, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601014004, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601014005, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601014006, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601014007, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (601014011, 'WEAPON', 'SUB', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001001, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001002, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001004, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001006, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001007, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001008, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001009, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001011, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001012, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001013, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001014, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001017, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001018, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001019, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001021, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001023, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702001024, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (700015001, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (700015002, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (700015003, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702023001, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702023002, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702023003, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702023004, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (702023005, 'WEAPON', 'MELEE', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (803007001, 'WEAPON', 'THROWING', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (803007004, 'WEAPON', 'THROWING', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (803007006, 'WEAPON', 'THROWING', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (803007008, 'WEAPON', 'THROWING', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (803007009, 'WEAPON', 'THROWING', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (803007018, 'WEAPON', 'THROWING', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (904007002, 'WEAPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (904007003, 'WEAPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (904007005, 'WEAPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (904007007, 'WEAPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (904007011, 'WEAPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (904007013, 'WEAPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

INSERT INTO items (id, type, slot_type, consume_type, consume_value, repair_credits, repair_points, repair_quantity)
VALUES (904007014, 'WEAPON', 'ITEM', 'PERMANENT', NULL, 0, 0, 0);

--
-- Data for table public.templates (OID = 156825) (LIMIT 0,4)
--
INSERT INTO templates (id, date_start, date_stop, active)
VALUES (0, '2013-01-01 00:00:00+04', '2016-01-01 00:00:00+04', true);

INSERT INTO templates (id, date_start, date_stop, active)
VALUES (1, '2013-01-01 00:00:00+04', '2016-01-01 00:00:00+04', true);

INSERT INTO templates (id, date_start, date_stop, active)
VALUES (2, '2013-01-01 00:00:00+04', '2016-01-01 00:00:00+04', true);

INSERT INTO templates (id, date_start, date_stop, active)
VALUES (3, '2013-01-01 00:00:00+04', '2016-01-01 00:00:00+04', true);

--
-- Data for table public.template_items (OID = 156831) (LIMIT 0,25)
--
INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (0, 100003004, 1, 1);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (0, 200004006, 1, 0);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (0, 300005003, 1, 0);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (0, 601002003, 1, 1);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (0, 702001001, 1, 1);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (0, 1001001005, 1, 1);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (0, 1001002006, 1, 1);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (0, 803007001, 1, 1);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (0, 904007002, 1, 1);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (0, 1102003001, 1, 1);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (0, 1006001024, 1, 1);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (2, 100003023, 1, 0);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (2, 100003037, 1, 0);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (2, 100003064, 1, 0);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (2, 200004026, 1, 0);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (2, 200004075, 1, 0);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (2, 300005024, 1, 0);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (2, 300005006, 1, 0);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (2, 300005015, 1, 0);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (2, 1001002033, 1, 0);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (2, 1001001034, 1, 0);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (2, 1104003012, 1, 0);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (2, 702001009, 1, 0);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (2, 601002017, 1, 0);

INSERT INTO template_items (template_id, item_id, count, equipped)
VALUES (2, 803007018, 1, 0);

--
-- Data for table public.template_other (OID = 156844) (LIMIT 0,1)
--
INSERT INTO template_other (template_id, startmoney, startgp)
VALUES (3, 500, 35000);

--
-- Data for table public.goods (OID = 156852) (LIMIT 0,43)
--
INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000001, 100003001, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000002, 100003002, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000003, 100003003, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000004, 100003005, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000005, 100003022, 300, 0, 26000, 1);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000006, 100003042, 300, 0, 32000, 1);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000007, 100003043, 300, 0, 40000, 1);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000008, 100003053, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000009, 100003069, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000010, 100003049, 300, 0, 40000, 1);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000011, 100003050, 300, 0, 50000, 1);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000012, 200004001, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000013, 200004002, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000014, 200004004, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000015, 200004008, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000016, 200004033, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000017, 200004034, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000019, 200004043, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000020, 200018004, 300, 0, 60000, 1);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000021, 300005001, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000022, 300005002, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000023, 300005004, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000024, 300005021, 300, 0, 40000, 1);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000025, 300005018, 300, 0, 45000, 1);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000026, 500010001, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000027, 500010002, 300, 0, 28000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000028, 400006001, 300, 0, 26000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000029, 400006004, 300, 0, 28000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000030, 601002001, 300, 0, 30000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000031, 601002002, 300, 0, 24000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000032, 601002022, 300, 0, 35000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000033, 702001002, 300, 0, 15000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000035, 702023001, 300, 0, 5000, 1);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000036, 803007008, 300, 0, 20000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000037, 803007009, 300, 0, 30000, 1);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000038, 904007003, 300, 0, 5000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10000039, 904007014, 300, 0, 50000, 1);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10010001, 1001001003, 300, 0, 15000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10010002, 1001002004, 300, 0, 15000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10010003, 1102003002, 300, 0, 10000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10010004, 1104003001, 300, 0, 12000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10010005, 1104003002, 300, 0, 12000, 0);

INSERT INTO goods (good_id, item_id, quantity, pricecredits, pricepoints, stocktype)
VALUES (10010006, 1104003020, 300, 0, 18000, 0);

--
-- Data for table public.levelup (OID = 156857) (LIMIT 0,51)
--
INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (0, 2000, 0, 0, '{0,0,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (1, 3000, 2000, 1000, '{70000302,10008905,90000605,100000401}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (2, 4000, 5000, 2000, '{10010205,90000605,100000401,70002605}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (3, 5000, 9000, 4000, '{50000105,90000705,10007102,100003801}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (4, 7000, 14000, 6000, '{100002906,90000805,10004105,100003802}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (5, 9000, 21000, 8000, '{60003005,60002905,90000905,100003202}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (6, 11000, 30000, 8000, '{10015605,90001005,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (7, 13500, 41000, 8000, '{100003001,90001105,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (8, 16000, 54500, 10000, '{60002505,60002605,90001205,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (9, 18500, 70500, 10000, '{10008905,90001305,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (10, 21000, 89000, 10000, '{60000505,60000405,100004005,90001405}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (11, 24000, 110000, 10000, '{10010105,90001505,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (12, 27000, 134000, 12000, '{60000805,70000905,90001605,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (13, 30000, 161000, 12000, '{100002701,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (14, 33000, 191000, 12000, '{60000705,70000805,100003901,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (15, 36000, 224000, 12000, '{100006402,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (16, 41000, 260000, 12000, '{100003102,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (17, 46000, 301000, 14000, '{20000902,90002105,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (18, 51000, 347000, 14000, '{100003405,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (19, 56000, 398000, 14000, '{100007705,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (20, 62000, 454000, 14000, '{100000201,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (21, 68000, 516000, 16000, '{100000802,90002205,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (22, 74000, 584000, 16000, '{100002906,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (23, 80000, 658000, 16000, '{100000401,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (24, 86000, 738000, 16000, '{10012105,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (25, 93000, 824000, 16000, '{70004005,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (26, 100000, 917000, 18000, '{70002605,90002305,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (27, 107000, 1017000, 18000, '{100004801,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (28, 114000, 1124000, 18000, '{60000405,60002705,100003901,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (29, 121000, 1238000, 18000, '{70002405,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (30, 151000, 1359000, 18000, '{100002606,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (31, 181000, 1510000, 20000, '{70006105,90002405,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (32, 211000, 1691000, 20000, '{110000203,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (33, 241000, 1902000, 20000, '{100003505,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (34, 271000, 2143000, 20000, '{60000705,60002605,100003901,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (35, 311000, 2414000, 20000, '{100003602,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (36, 351000, 2725000, 22000, '{100010805,90002505,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (37, 391000, 3076000, 22000, '{60000805,60002805,100003901,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (38, 413000, 3467000, 22000, '{100003001,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (39, 471000, 3898000, 22000, '{100002801,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (40, 521000, 4369000, 22000, '{100003905,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (41, 571000, 4890000, 24000, '{70001905,90002605,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (42, 621000, 5461000, 24000, '{100008905,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (43, 671000, 6082000, 24000, '{70002005,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (44, 721000, 6753000, 24000, '{100009005,100003901,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (45, 781000, 7474000, 24000, '{60001205,60001105,70002305,100003901}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (46, 851000, 8255000, 28000, '{0,0,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (47, 931000, 9106000, 32000, '{0,0,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (48, 1021000, 10037000, 36000, '{0,0,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (49, 1021000, 11058000, 36000, '{0,0,0,0}');

INSERT INTO levelup (rank, "needExp", "allExp", "rewardGP", "rewardItems")
VALUES (50, 0, 12179000, 60000, '{0,0,0,0}');

--
-- Definition for index pk_databasechangelog (OID = 156612) : 
--
ALTER TABLE ONLY databasechangelog
    ADD CONSTRAINT pk_databasechangelog
    PRIMARY KEY (id, author, filename);
--
-- Definition for index pk_databasechangeloglock (OID = 156617) : 
--
ALTER TABLE ONLY databasechangeloglock
    ADD CONSTRAINT pk_databasechangeloglock
    PRIMARY KEY (id);
--
-- Definition for index pk_settings (OID = 156622) : 
--
ALTER TABLE ONLY settings
    ADD CONSTRAINT pk_settings
    PRIMARY KEY (name);
--
-- Definition for index pk_accounts (OID = 156632) : 
--
ALTER TABLE ONLY accounts
    ADD CONSTRAINT pk_accounts
    PRIMARY KEY (id);
--
-- Definition for index accounts_login_key (OID = 156634) : 
--
ALTER TABLE ONLY accounts
    ADD CONSTRAINT accounts_login_key
    UNIQUE (login);
--
-- Definition for index accounts_email_key (OID = 156636) : 
--
ALTER TABLE ONLY accounts
    ADD CONSTRAINT accounts_email_key
    UNIQUE (email);
--
-- Definition for index account_activity_account_id_key (OID = 156644) : 
--
ALTER TABLE ONLY account_activity
    ADD CONSTRAINT account_activity_account_id_key
    UNIQUE (account_id);
--
-- Definition for index account_activity_account (OID = 156646) : 
--
ALTER TABLE ONLY account_activity
    ADD CONSTRAINT account_activity_account
    FOREIGN KEY (account_id) REFERENCES accounts(id);
--
-- Definition for index account_bonuses_account (OID = 156654) : 
--
ALTER TABLE ONLY account_bonuses
    ADD CONSTRAINT account_bonuses_account
    FOREIGN KEY (account_id) REFERENCES accounts(id);
--
-- Definition for index pk_players (OID = 156671) : 
--
ALTER TABLE ONLY players
    ADD CONSTRAINT pk_players
    PRIMARY KEY (id);
--
-- Definition for index players_account_id_key (OID = 156673) : 
--
ALTER TABLE ONLY players
    ADD CONSTRAINT players_account_id_key
    UNIQUE (account_id);
--
-- Definition for index player_account (OID = 156675) : 
--
ALTER TABLE ONLY players
    ADD CONSTRAINT player_account
    FOREIGN KEY (account_id) REFERENCES accounts(id);
--
-- Definition for index player_stats_player_id_key (OID = 156699) : 
--
ALTER TABLE ONLY player_stats
    ADD CONSTRAINT player_stats_player_id_key
    UNIQUE (player_id);
--
-- Definition for index player_stats_player (OID = 156701) : 
--
ALTER TABLE ONLY player_stats
    ADD CONSTRAINT player_stats_player
    FOREIGN KEY (player_id) REFERENCES players(id);
--
-- Definition for index pk_clans (OID = 156712) : 
--
ALTER TABLE ONLY clans
    ADD CONSTRAINT pk_clans
    PRIMARY KEY (id);
--
-- Definition for index clans_name_key (OID = 156714) : 
--
ALTER TABLE ONLY clans
    ADD CONSTRAINT clans_name_key
    UNIQUE (name);
--
-- Definition for index clans_owner_id_key (OID = 156716) : 
--
ALTER TABLE ONLY clans
    ADD CONSTRAINT clans_owner_id_key
    UNIQUE (owner_id);
--
-- Definition for index clans_player (OID = 156718) : 
--
ALTER TABLE ONLY clans
    ADD CONSTRAINT clans_player
    FOREIGN KEY (owner_id) REFERENCES players(id);
--
-- Definition for index player_clan_player_id_key (OID = 156726) : 
--
ALTER TABLE ONLY player_clan
    ADD CONSTRAINT player_clan_player_id_key
    UNIQUE (player_id);
--
-- Definition for index player_clan_clan (OID = 156728) : 
--
ALTER TABLE ONLY player_clan
    ADD CONSTRAINT player_clan_clan
    FOREIGN KEY (clan_id) REFERENCES clans(id);
--
-- Definition for index player_clan_player (OID = 156733) : 
--
ALTER TABLE ONLY player_clan
    ADD CONSTRAINT player_clan_player
    FOREIGN KEY (player_id) REFERENCES players(id);
--
-- Definition for index pk_gameservers (OID = 156744) : 
--
ALTER TABLE ONLY gameservers
    ADD CONSTRAINT pk_gameservers
    PRIMARY KEY (id);
--
-- Definition for index pk_ipsystem (OID = 156756) : 
--
ALTER TABLE ONLY ipsystem
    ADD CONSTRAINT pk_ipsystem
    PRIMARY KEY (id);
--
-- Definition for index channels_id_gameserver_id_key (OID = 156764) : 
--
ALTER TABLE ONLY channels
    ADD CONSTRAINT channels_id_gameserver_id_key
    UNIQUE (id, gameserver_id);
--
-- Definition for index channel_id_gameserver_id (OID = 156766) : 
--
ALTER TABLE ONLY channels
    ADD CONSTRAINT channel_id_gameserver_id
    FOREIGN KEY (gameserver_id) REFERENCES gameservers(id);
--
-- Definition for index items_id_key (OID = 156777) : 
--
ALTER TABLE ONLY items
    ADD CONSTRAINT items_id_key
    UNIQUE (id);
--
-- Definition for index pk_player_eqipment (OID = 156789) : 
--
ALTER TABLE ONLY player_eqipment
    ADD CONSTRAINT pk_player_eqipment
    PRIMARY KEY (id, loc);
--
-- Definition for index player_eqipment_player (OID = 156791) : 
--
ALTER TABLE ONLY player_eqipment
    ADD CONSTRAINT player_eqipment_player
    FOREIGN KEY (player_id) REFERENCES players(id);
--
-- Definition for index player_eqipment_item (OID = 156796) : 
--
ALTER TABLE ONLY player_eqipment
    ADD CONSTRAINT player_eqipment_item
    FOREIGN KEY (item_id) REFERENCES items(id);
--
-- Definition for index player_eqipment_player_id_item_id_key (OID = 156801) : 
--
ALTER TABLE ONLY player_eqipment
    ADD CONSTRAINT player_eqipment_player_id_item_id_key
    UNIQUE (player_id, item_id);
--
-- Definition for index player_friends_player_account_id_key (OID = 156809) : 
--
ALTER TABLE ONLY player_friends
    ADD CONSTRAINT player_friends_player_account_id_key
    UNIQUE (player_account_id);
--
-- Definition for index player_friends_player_account_id_friend_id_key (OID = 156811) : 
--
ALTER TABLE ONLY player_friends
    ADD CONSTRAINT player_friends_player_account_id_friend_id_key
    UNIQUE (player_account_id, friend_id);
--
-- Definition for index player_friend (OID = 156813) : 
--
ALTER TABLE ONLY player_friends
    ADD CONSTRAINT player_friend
    FOREIGN KEY (friend_id) REFERENCES players(id);
--
-- Definition for index player_player (OID = 156818) : 
--
ALTER TABLE ONLY player_friends
    ADD CONSTRAINT player_player
    FOREIGN KEY (player_account_id) REFERENCES players(id);
--
-- Definition for index pk_templates (OID = 156829) : 
--
ALTER TABLE ONLY templates
    ADD CONSTRAINT pk_templates
    PRIMARY KEY (id);
--
-- Definition for index template_item_template_i (OID = 156834) : 
--
ALTER TABLE ONLY template_items
    ADD CONSTRAINT template_item_template_i
    FOREIGN KEY (template_id) REFERENCES templates(id);
--
-- Definition for index template_item_item (OID = 156839) : 
--
ALTER TABLE ONLY template_items
    ADD CONSTRAINT template_item_item
    FOREIGN KEY (item_id) REFERENCES items(id);
--
-- Definition for index template_other_template_i (OID = 156847) : 
--
ALTER TABLE ONLY template_other
    ADD CONSTRAINT template_other_template_i
    FOREIGN KEY (template_id) REFERENCES templates(id);
--
-- Definition for index goods_good_id_key (OID = 156855) : 
--
ALTER TABLE ONLY goods
    ADD CONSTRAINT goods_good_id_key
    UNIQUE (good_id);
--
-- Definition for index levelup_rank_key (OID = 156863) : 
--
ALTER TABLE ONLY levelup
    ADD CONSTRAINT levelup_rank_key
    UNIQUE (rank);
--
-- Definition for index pk_account_access (OID = 156868) : 
--
ALTER TABLE ONLY account_access
    ADD CONSTRAINT pk_account_access
    PRIMARY KEY (accountid);
--
-- Definition for trigger insert_account_activity (OID = 156870) : 
--
CREATE TRIGGER insert_account_activity
    AFTER INSERT ON accounts
    FOR EACH ROW
    EXECUTE PROCEDURE insert_account_activity ();
--
-- Definition for trigger insert_player_stats (OID = 156871) : 
--
CREATE TRIGGER insert_player_stats
    AFTER INSERT ON players
    FOR EACH ROW
    EXECUTE PROCEDURE insert_player_stats ();
--
-- Data for sequence public.accounts_id_seq (OID = 156624)
--
SELECT pg_catalog.setval('accounts_id_seq', 1, false);
--
-- Data for sequence public.players_id_seq (OID = 156659)
--
SELECT pg_catalog.setval('players_id_seq', 1, false);
--
-- Data for sequence public.clans_id_seq (OID = 156706)
--
SELECT pg_catalog.setval('clans_id_seq', 1, false);
--
-- Data for sequence public.gameservers_id_seq (OID = 156738)
--
SELECT pg_catalog.setval('gameservers_id_seq', 1, false);
--
-- Data for sequence public.ipsystem_id_seq (OID = 156750)
--
SELECT pg_catalog.setval('ipsystem_id_seq', 1, false);
--
-- Data for sequence public.channels_id_seq (OID = 156758)
--
SELECT pg_catalog.setval('channels_id_seq', 1, false);
--
-- Data for sequence public.items_id_seq (OID = 156771)
--
SELECT pg_catalog.setval('items_id_seq', 1, false);
--
-- Data for sequence public.player_eqipment_id_seq (OID = 156783)
--
SELECT pg_catalog.setval('player_eqipment_id_seq', 1, false);
--
-- Data for sequence public.player_friends_player_account_id_seq (OID = 156803)
--
SELECT pg_catalog.setval('player_friends_player_account_id_seq', 1, false);
--
-- Data for sequence public.templates_id_seq (OID = 156823)
--
SELECT pg_catalog.setval('templates_id_seq', 1, false);
--
-- Comments
--
COMMENT ON SCHEMA public IS 'standard public schema';
COMMENT ON TABLE public.accounts IS 'Пользователи';
COMMENT ON COLUMN public.accounts.id IS 'ID аккаунта';
COMMENT ON COLUMN public.accounts.login IS 'Логин';
COMMENT ON COLUMN public.accounts.password IS 'Пароль';
COMMENT ON COLUMN public.accounts.email IS 'Email';
COMMENT ON COLUMN public.accounts.money IS 'Деньги';
COMMENT ON COLUMN public.accounts.active IS 'Активирован';
COMMENT ON TABLE public.account_activity IS 'Активность пользователей';
COMMENT ON COLUMN public.account_activity.account_id IS 'ID аккаунта';
COMMENT ON COLUMN public.account_activity.last_active IS 'Последняя активность';
COMMENT ON COLUMN public.account_activity.last_ip IS 'Последний ip';
COMMENT ON COLUMN public.account_activity.total_active IS 'Всего в онлайне (в минутах)';
COMMENT ON TABLE public.account_bonuses IS 'Бонусы пользователей';
COMMENT ON COLUMN public.account_bonuses.account_id IS 'ID аккаунта';
COMMENT ON COLUMN public.account_bonuses.bonus IS 'Тип бонуса';
COMMENT ON COLUMN public.account_bonuses.bonus_expire IS 'Истекает';
COMMENT ON TABLE public.players IS 'Игроки';
COMMENT ON COLUMN public.players.id IS 'ID игрока';
COMMENT ON COLUMN public.players.account_id IS 'ID аккаунта';
COMMENT ON COLUMN public.players.name IS 'Имя';
COMMENT ON COLUMN public.players.color IS 'Цвет имени';
COMMENT ON COLUMN public.players.rank IS 'Ранг';
COMMENT ON COLUMN public.players.gp IS 'ГП';
COMMENT ON COLUMN public.players.exp IS 'EXP';
COMMENT ON COLUMN public.players.pc_cafe IS 'EXP';
COMMENT ON COLUMN public.players.online IS 'В онлайне?';
COMMENT ON TABLE public.player_stats IS 'Статистика игроков';
COMMENT ON COLUMN public.player_stats.player_id IS 'ID игрока';
COMMENT ON COLUMN public.player_stats.season_fights IS 'Всего боев за сезон';
COMMENT ON COLUMN public.player_stats.season_wins IS 'Всего побед за сезон';
COMMENT ON COLUMN public.player_stats.season_losts IS 'Всего проигрышей за сезон';
COMMENT ON COLUMN public.player_stats.season_kills IS 'Всего убито за сезон';
COMMENT ON COLUMN public.player_stats.season_deaths IS 'Всего умер за сезон';
COMMENT ON COLUMN public.player_stats.season_seria_wins IS 'Серия побед';
COMMENT ON COLUMN public.player_stats.season_kpd IS 'КПД';
COMMENT ON COLUMN public.player_stats.season_escapes IS 'Всего сбежал за сезон';
COMMENT ON COLUMN public.player_stats.fights IS 'Всего боев';
COMMENT ON COLUMN public.player_stats.wins IS 'Всего побед';
COMMENT ON COLUMN public.player_stats.losts IS 'Всего проигрышей';
COMMENT ON COLUMN public.player_stats.kills IS 'Всего убито';
COMMENT ON COLUMN public.player_stats.deaths IS 'Всего умер';
COMMENT ON COLUMN public.player_stats.seria_wins IS 'Серия побед';
COMMENT ON COLUMN public.player_stats.kpd IS 'КПД';
COMMENT ON COLUMN public.player_stats.escapes IS 'Всего сбежал';
COMMENT ON TABLE public.clans IS 'Кланы';
COMMENT ON COLUMN public.clans.id IS 'ID клана';
COMMENT ON COLUMN public.clans.name IS 'Название клана';
COMMENT ON COLUMN public.clans.rank IS 'Ранг клана';
COMMENT ON COLUMN public.clans.owner_id IS 'Игрок создавший клан';
COMMENT ON COLUMN public.clans.color IS 'Цвет названия клана';
COMMENT ON COLUMN public.clans.logo1 IS 'Лого';
COMMENT ON COLUMN public.clans.logo2 IS 'Лого';
COMMENT ON COLUMN public.clans.logo3 IS 'Лого';
COMMENT ON COLUMN public.clans.logo4 IS 'Лого';
COMMENT ON TABLE public.player_clan IS 'Привязка игрока к клану';
COMMENT ON COLUMN public.player_clan.clan_id IS 'Клан игрока';
COMMENT ON COLUMN public.player_clan.player_id IS 'Игрок';
COMMENT ON COLUMN public.player_clan.rank IS 'Ранг игрока';
COMMENT ON TABLE public.gameservers IS 'Игровые сервера';
COMMENT ON COLUMN public.gameservers.id IS 'ID сервера';
COMMENT ON COLUMN public.gameservers.password IS 'Пароль (md5)';
COMMENT ON COLUMN public.gameservers.type IS 'Тип сервера';
COMMENT ON COLUMN public.gameservers.max_players IS 'Макс игроков';
COMMENT ON TABLE public.account_banned IS 'Заблокированные аккаунты';
COMMENT ON COLUMN public.account_banned.type IS 'Тип бана (IP/MAC)';
COMMENT ON COLUMN public.account_banned.value IS 'Значение типа (Либо ip, либо Mac)';
COMMENT ON COLUMN public.account_banned.date_lock IS 'Дата начала блокировки';
COMMENT ON COLUMN public.account_banned.date_unlock IS 'Дата окончания блокировки';
COMMENT ON COLUMN public.account_banned.owner_id IS 'Кто заблокировал';
COMMENT ON COLUMN public.account_banned.reason IS 'Причина блокировки';
COMMENT ON TABLE public.ipsystem IS 'Система IP';
COMMENT ON COLUMN public.ipsystem.id IS 'ИД';
COMMENT ON COLUMN public.ipsystem.type IS 'Тип ранга';
COMMENT ON COLUMN public.ipsystem.startpoint IS 'Начальный IP';
COMMENT ON COLUMN public.ipsystem.endpoint IS 'Конечный IP или маска';
COMMENT ON TABLE public.channels IS 'Список каналов';
COMMENT ON COLUMN public.channels.id IS 'ID';
COMMENT ON COLUMN public.channels.gameserver_id IS 'ID';
COMMENT ON COLUMN public.channels.type IS 'Тип канала';
COMMENT ON COLUMN public.channels.announce IS 'Aнонс канала';
COMMENT ON TABLE public.items IS 'Предметы';
COMMENT ON COLUMN public.items.id IS 'ID';
COMMENT ON COLUMN public.items.type IS 'Тип предмета';
COMMENT ON COLUMN public.items.slot_type IS 'Тип слотов в которые может быть одет';
COMMENT ON COLUMN public.items.consume_type IS 'Жизненный цикл предмета';
COMMENT ON COLUMN public.items.consume_value IS 'Жизненный цикл предмета';
COMMENT ON COLUMN public.items.repair_credits IS 'Цена в кредитах за починку 1%';
COMMENT ON COLUMN public.items.repair_points IS 'Цена в очках за починку 1%';
COMMENT ON COLUMN public.items.repair_quantity IS 'Максимальная прочность предмета';
COMMENT ON TABLE public.player_eqipment IS 'Предметы игрока';
COMMENT ON COLUMN public.player_eqipment.id IS 'Уникальный ID предмета';
COMMENT ON COLUMN public.player_eqipment.player_id IS 'id игрока';
COMMENT ON COLUMN public.player_eqipment.item_id IS 'id предмета';
COMMENT ON COLUMN public.player_eqipment.count IS 'Кол-во предметов';
COMMENT ON COLUMN public.player_eqipment.loc IS 'где находится';
COMMENT ON COLUMN public.player_eqipment.consume_lost IS 'Дата удаления предмета';
COMMENT ON TABLE public.player_friends IS 'Друзья плеера';
COMMENT ON COLUMN public.player_friends.player_account_id IS 'ID плеера';
COMMENT ON COLUMN public.player_friends.friend_id IS 'ID друга';
COMMENT ON TABLE public.templates IS 'Шаблоны';
COMMENT ON COLUMN public.templates.id IS 'id шаблона';
COMMENT ON COLUMN public.templates.date_start IS 'id предмета';
COMMENT ON COLUMN public.templates.date_stop IS 'Тип слотов в которые одет';
COMMENT ON COLUMN public.templates.active IS 'Антивный шаблон';
COMMENT ON TABLE public.template_items IS 'Шаблоны';
COMMENT ON COLUMN public.template_items.template_id IS 'id шаблона';
COMMENT ON COLUMN public.template_items.item_id IS 'id предмета';
COMMENT ON COLUMN public.template_items.count IS 'Кол-во предметов';
COMMENT ON COLUMN public.template_items.equipped IS 'Одет ли предмет';
COMMENT ON TABLE public.template_other IS 'Шаблоны';
COMMENT ON COLUMN public.template_other.template_id IS 'id шаблона';
COMMENT ON COLUMN public.template_other.startmoney IS 'Кол-во предметов';
COMMENT ON COLUMN public.template_other.startgp IS 'Кол-во предметов';
COMMENT ON TABLE public.goods IS 'Товары';
COMMENT ON COLUMN public.goods.good_id IS 'id товара';
COMMENT ON COLUMN public.goods.item_id IS 'id предмета';
COMMENT ON COLUMN public.goods.quantity IS 'количество боев';
COMMENT ON COLUMN public.goods.pricecredits IS 'цена в кредитах';
COMMENT ON COLUMN public.goods.pricepoints IS 'цена в очках';
COMMENT ON COLUMN public.goods.stocktype IS 'Тип';
COMMENT ON TABLE public.levelup IS 'G';
COMMENT ON COLUMN public.levelup.rank IS 'Ранг';
COMMENT ON COLUMN public.levelup."needExp" IS 'Сколько нужно набрать опыта до следущего АПА';
COMMENT ON COLUMN public.levelup."allExp" IS 'Сколько всего опыта должно быть';
COMMENT ON COLUMN public.levelup."rewardGP" IS 'Сколько денег за АП дадут';
COMMENT ON COLUMN public.levelup."rewardItems" IS 'Итемы за АП';
COMMENT ON TABLE public.account_access IS 'АдминЛист';
COMMENT ON COLUMN public.account_access.accountid IS 'ID аккаунта';
COMMENT ON COLUMN public.account_access.level IS 'Уровень';
