BEGIN;


DROP TABLE IF EXISTS public.employee CASCADE;
DROP TABLE IF EXISTS public.timesheet;
DROP TABLE IF EXISTS public.working_day;
DROP TABLE IF EXISTS public.working_day_to_day_segment;
DROP TABLE IF EXISTS public.day_segment;


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


END;