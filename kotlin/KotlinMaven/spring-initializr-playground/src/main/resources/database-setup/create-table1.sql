-- public.table1 definition

-- Drop table

-- DROP TABLE public.table1;

CREATE TABLE public.table1 (
	id serial NOT NULL DEFAULT nextval('table1_id_seq'::regclass),
	"name" varchar(255) NULL
);

-- Permissions

ALTER TABLE public.table1 OWNER TO njsoly;
GRANT ALL ON TABLE public.table1 TO njsoly;

