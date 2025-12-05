CREATE TABLE IF NOT EXISTS public.cs1_incident_types
(
    id int NOT NULL,
    name character varying(255),
    name_eng character varying(255),
    CONSTRAINT cs1_incident_types_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS public.cs1_incident_types_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 10000
    CACHE 1;
    
INSERT INTO public.cs1_incident_types VALUES (1, 'NP', 'N/A');
INSERT INTO public.cs1_incident_types VALUES (2, 'Incendio', 'fire');
INSERT INTO public.cs1_incident_types VALUES (3, 'Miasma', 'miasma');
    
CREATE TABLE IF NOT EXISTS public.cs1_municipalities
(
    id int NOT NULL,
    name character varying(255),
    name_eng character varying(255),
    CONSTRAINT cs1_municipalities_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS public.cs1_municipalities_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 100
    CACHE 1;    

INSERT INTO public.cs1_municipalities VALUES (1, 'NP', 'N/A');
INSERT INTO public.cs1_municipalities VALUES (2, 'Fiumicino', 'Fiumicino');
INSERT INTO public.cs1_municipalities VALUES (3, 'Roma', 'Rome');
INSERT INTO public.cs1_municipalities VALUES (4, 'Torino', 'Turin');
    
CREATE TABLE IF NOT EXISTS public.cs1_odor_inducing_types
(
    id int NOT NULL,
    name character varying(255),
    name_eng character varying(255),
    CONSTRAINT cs1_odor_inducing_types_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS public.cs1_odor_inducing_types_seq
    INCREMENT 1
    START 20
    MINVALUE 1
    MAXVALUE 100
    CACHE 1;    

INSERT INTO public.cs1_odor_inducing_types VALUES (1, 'NP', 'N/A');
INSERT INTO public.cs1_odor_inducing_types VALUES (2, 'Altro - Non identificabile', 'Other - Unidentifiable');
INSERT INTO public.cs1_odor_inducing_types VALUES (3, 'Anidride solforosa', 'Sulfur Dioxide');
INSERT INTO public.cs1_odor_inducing_types VALUES (4, 'Bitume', 'Bitumen');
INSERT INTO public.cs1_odor_inducing_types VALUES (5, 'Bruciato', 'Burnt');
INSERT INTO public.cs1_odor_inducing_types VALUES (6, 'Chimico', 'Chemical');
INSERT INTO public.cs1_odor_inducing_types VALUES (7, 'Chimico / Solvente', 'chemical / solvent');
INSERT INTO public.cs1_odor_inducing_types VALUES (8, 'Fognatura / Uova marce', 'Sewerage / Rotten Eggs');
INSERT INTO public.cs1_odor_inducing_types VALUES (9, 'Fuliggine', 'Soot');
INSERT INTO public.cs1_odor_inducing_types VALUES (10, 'Idrocarburi', 'Hydrocarbons');
INSERT INTO public.cs1_odor_inducing_types VALUES (11, 'Materiali', 'Materials');
INSERT INTO public.cs1_odor_inducing_types VALUES (12, 'Plastica', 'Plastic');
INSERT INTO public.cs1_odor_inducing_types VALUES (13, 'Plastica bruciata', 'Burnt plastic');
INSERT INTO public.cs1_odor_inducing_types VALUES (14, 'Rifiuti / Plastica bruciata', 'Waste / Burned plastic');
INSERT INTO public.cs1_odor_inducing_types VALUES (15, 'Rifiuto (Tmb, Trasferenza, etc.)', 'Refusal (Tmb, Transfer, etc.)');
INSERT INTO public.cs1_odor_inducing_types VALUES (16, 'Secco', 'Dry');
INSERT INTO public.cs1_odor_inducing_types VALUES (17, 'Sterpaglie', 'Brushwood');
INSERT INTO public.cs1_odor_inducing_types VALUES (18, 'Vegetazione bruciata', 'Burnt vegetation');
INSERT INTO public.cs1_odor_inducing_types VALUES (19, 'Acre', 'Acrid');

CREATE TABLE IF NOT EXISTS public.cs1_data
(
    id int NOT NULL,
    incident_type_id int NOT NULL,
    municipality_id int NOT NULL,
    place character varying(255),
    date timestamp without time zone,
    odor_inducing_type_id int NOT NULL,
    intensity double precision,
    burn_surface double precision,
    visible_smoke boolean,
    link character varying(255),
    coordinate geometry(Point,4326),
    CONSTRAINT cs1_data_pkey PRIMARY KEY (id),
    CONSTRAINT cs1_data_incident_type_fkey FOREIGN KEY (incident_type_id)
        REFERENCES public.cs1_incident_types (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cs1_data_municipality_fkex FOREIGN KEY (municipality_id)
        REFERENCES public.cs1_municipalities (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cs1_data_odor_inducing_type_fkex FOREIGN KEY (odor_inducing_type_id)
        REFERENCES public.cs1_odor_inducing_types (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE SEQUENCE IF NOT EXISTS public.cs1_data_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 10000
    CACHE 1;

    
CREATE OR REPLACE FUNCTION ST_CS1DataToGeoJson()
RETURNS jsonb AS
$BODY$
    SELECT jsonb_build_object(
        'type',     'FeatureCollection',
        'features', jsonb_agg(feature)
    )
    FROM (
      SELECT jsonb_build_object(
        'type',       'Feature',
        'id',         row.id,
        'geometry',   ST_AsGeoJSON(coordinate)::jsonb,
        'properties', to_jsonb(row) - 'id' - 'coordinate' - 'name'
      ) AS feature
      FROM (SELECT * FROM public.cs1_data) row) features;
$BODY$
LANGUAGE SQL;