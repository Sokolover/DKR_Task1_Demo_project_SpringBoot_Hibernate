BEGIN;


DROP TABLE IF EXISTS public.client;


CREATE TABLE public.client
(
    id          BIGINT NOT NULL UNIQUE,
    first_name  VARCHAR(100),
    second_name VARCHAR(100),
    age         INTEGER,
    salary      NUMERIC,
    PRIMARY KEY (id)
);

CREATE SEQUENCE "Client_id_seq";
ALTER TABLE client
    ALTER COLUMN id SET DEFAULT nextval('public."Client_id_seq"');
ALTER SEQUENCE "Client_id_seq" OWNED BY client.id;

CREATE INDEX idx_client_salary ON client (salary);


END;