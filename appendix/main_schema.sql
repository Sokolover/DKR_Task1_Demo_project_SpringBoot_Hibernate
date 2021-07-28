BEGIN;


DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

GRANT
    ALL
    ON SCHEMA public TO postgres;
GRANT ALL
    ON SCHEMA public TO public;

--------------------------------------------------------------------------------------

--**********************************************************************************--

----------------------------------Employee_Timesheet----------------------------------

CREATE TABLE public.employee
(
    id   BIGINT NOT NULL UNIQUE,
    name VARCHAR(100) UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE public.timesheet
(
    id          BIGINT NOT NULL UNIQUE,
    day_amount  INTEGER,
    employee_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE public.working_day
(
    id           BIGINT NOT NULL UNIQUE,
    day_name     VARCHAR(30),
    timesheet_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE public.day_segment
(
    id     BIGINT NOT NULL UNIQUE,
    period VARCHAR(30),
    PRIMARY KEY (id)
);

CREATE TABLE public.working_day_to_day_segment
(
    working_day_id BIGINT,
    day_segment_id BIGINT
);


ALTER TABLE public.working_day
    ADD FOREIGN KEY (timesheet_id)
        REFERENCES public.timesheet (id)
        ON DELETE CASCADE
        NOT VALID;

ALTER TABLE public.working_day_to_day_segment
    ADD FOREIGN KEY (working_day_id)
        REFERENCES public.working_day (id)
        ON DELETE CASCADE
        NOT VALID;

ALTER TABLE public.working_day_to_day_segment
    ADD FOREIGN KEY (day_segment_id)
        REFERENCES public.day_segment (id)
        ON DELETE CASCADE
        NOT VALID;

ALTER TABLE public.timesheet
    ADD FOREIGN KEY (employee_id)
        REFERENCES public.employee (id)
        ON DELETE CASCADE
        NOT VALID;


CREATE SEQUENCE "working_day_id_seq";
ALTER TABLE working_day
    ALTER COLUMN id SET DEFAULT nextval('public."working_day_id_seq"');
ALTER SEQUENCE "working_day_id_seq" OWNED BY working_day.id;

CREATE SEQUENCE "day_segment_id_seq";
ALTER TABLE day_segment
    ALTER COLUMN id SET DEFAULT nextval('public."day_segment_id_seq"');
ALTER SEQUENCE "day_segment_id_seq" OWNED BY day_segment.id;

CREATE SEQUENCE "employee_id_seq";
ALTER TABLE employee
    ALTER COLUMN id SET DEFAULT nextval('public."employee_id_seq"');
ALTER SEQUENCE "employee_id_seq" OWNED BY employee.id;

CREATE SEQUENCE "timesheet_id_seq";
ALTER TABLE timesheet
    ALTER COLUMN id SET DEFAULT nextval('public."timesheet_id_seq"');
ALTER SEQUENCE "timesheet_id_seq" OWNED BY timesheet.id;

--------------------------------------------------------------------------------------

--**********************************************************************************--

------------------------------------Directory_Fact------------------------------------

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

CREATE INDEX index_directory_name ON directory (name);
CREATE INDEX index_directory_name_phone_address ON directory (name, phone, address);

--------------------------------------------------------------------------------------

--**********************************************************************************--

------------------------------------Directory_Fact------------------------------------

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

--------------------------------------------------------------------------------------

--**********************************************************************************--

----------------------------------------Client----------------------------------------

CREATE TABLE public.client
(
    id          BIGINT NOT NULL UNIQUE,
    first_name  VARCHAR(100),
    second_name VARCHAR(100),
    age         INTEGER,
    salary      NUMERIC,
    PRIMARY KEY (id)
);

CREATE SEQUENCE "client_id_seq";
ALTER TABLE client
    ALTER COLUMN id SET DEFAULT nextval('public."client_id_seq"');
ALTER SEQUENCE "client_id_seq" OWNED BY client.id;

CREATE INDEX idx_client_salary ON client (salary);

--------------------------------------------------------------------------------------


END;