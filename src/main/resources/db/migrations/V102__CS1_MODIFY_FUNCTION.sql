CREATE OR REPLACE FUNCTION ST_CS1DataPropertiesToJson(id int)
RETURNS jsonb AS
$BODY$
      SELECT jsonb_build_object(
	           'incident_type', incident_type.name_eng,
	           'municipality', municipality.name_eng,
	           'odor_inducing_type', odor_inducing_type.name_eng
	          )
      FROM (SELECT o.name_eng FROM public.cs1_data as d 
	  join public.cs1_incident_types as o on d.incident_type_id = o.id where d.id = $1)  incident_type,
	  (SELECT o.name_eng FROM public.cs1_data as d 
	  join public.cs1_municipalities as o on d.municipality_id = o.id where d.id = $1)  municipality,
	  (SELECT o.name_eng FROM public.cs1_data as d 
	  join public.cs1_odor_inducing_types as o on d.odor_inducing_type_id = o.id where d.id = $1)  odor_inducing_type;
$BODY$
LANGUAGE SQL;

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
        'properties', to_jsonb(row) - 'id' - 'coordinate' - 'name' - 'incident_type_id' - 'municipality_id' - 'odor_inducing_type_id' || ST_CS1DataPropertiesToJson(row.id)
      ) AS feature
      FROM (SELECT * FROM public.cs1_data) row) features;
$BODY$
LANGUAGE SQL;