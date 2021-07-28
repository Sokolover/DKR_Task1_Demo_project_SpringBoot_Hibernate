BEGIN;


DROP TABLE IF EXISTS public.directory CASCADE;
DROP TABLE IF EXISTS public.fact;


CREATE TABLE public.directory
(
    id       BIGINT       NOT NULL UNIQUE,
    name     VARCHAR(100) NOT NULL UNIQUE,
    address  VARCHAR(200) NOT NULL,
    address2 VARCHAR(200),
    city_id  VARCHAR(30)  NOT NULL,
    phone    VARCHAR(13)  NOT NULL,
    postcode VARCHAR(7)   NOT NULL,
    district VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE public.fact
(
    id                BIGINT  NOT NULL UNIQUE,
    short_description TEXT    NOT NULL,
    full_description  TEXT    NOT NULL,
    rate              INTEGER NOT NULL,
    comment           TEXT    NOT NULL,
    directory_id      BIGINT,
    PRIMARY KEY (id)
);


ALTER TABLE public.fact
    ADD FOREIGN KEY (directory_id)
        REFERENCES public.directory (id)
        ON DELETE CASCADE
        NOT VALID;


CREATE SEQUENCE "directory_id_seq";
ALTER TABLE directory
    ALTER COLUMN id SET DEFAULT nextval('public."directory_id_seq"');
ALTER SEQUENCE "directory_id_seq" OWNED BY directory.id;

CREATE SEQUENCE "fact_id_seq";
ALTER TABLE fact
    ALTER COLUMN id SET DEFAULT nextval('public."fact_id_seq"');
ALTER SEQUENCE "fact_id_seq" OWNED BY fact.id;


END;