BEGIN;


DROP TABLE IF EXISTS public.student CASCADE;
DROP TABLE IF EXISTS public.contact_info;


CREATE TABLE public.student
(
    id         BIGINT       NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.contact_info
(
    id         BIGINT      NOT NULL UNIQUE,
    city       VARCHAR(100),
    phone      VARCHAR(13) NOT NULL,
    student_id BIGINT,
    PRIMARY KEY (id)
);


ALTER TABLE public.contact_info
    ADD FOREIGN KEY (student_id)
        REFERENCES public.student (id)
        ON DELETE CASCADE
        NOT VALID;


CREATE SEQUENCE "student_seq";
ALTER TABLE student
    ALTER COLUMN id SET DEFAULT nextval('public."student_seq"');
ALTER SEQUENCE "student_seq" OWNED BY student.id;

CREATE SEQUENCE "contact_info_seq";
ALTER TABLE contact_info
    ALTER COLUMN id SET DEFAULT nextval('public."contact_info_seq"');
ALTER SEQUENCE "contact_info_seq" OWNED BY contact_info.id;


END;