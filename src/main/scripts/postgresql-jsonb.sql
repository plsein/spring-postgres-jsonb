
/*
{"contact":
[
	{"email": {"home": "home@email.com", "work": "work@email.com"}, "phone": {"home": "123-4567-8910", "work": "012-3456-7891"}},
	{"email": {"home": "home@mail.com", "work": "work@mail.com"}, "phone": {"home": "1123-4567-891", "work": "112-3456-7891"}}
],
"hobbies": {"arts": ["drawing", "painting"], "sports": ["cricket", "football"]}, "regDate": 1506530079004, "eyeColor": "blue", "renewalDate": "2017-09-27 16:34:39", "favoriteColors": ["red", "green", "yellow", "blue"]
}
*/

/*
{"contact": [{"email": {"home": "home@email.com", "work": "work@email.com"}, "phone": {"home": "123-4567-8910", "work": "012-3456-7891"}}, {"email": {"home": "home@mail.com", "work": "work@mail.com"}, "phone": {"home": "1123-4567-891", "work": "112-3456-7891"}}], "hobbies": {"arts": ["drawing", "painting"], "sports": ["cricket", "football"]}, "regDate": 1506530079004, "eyeColor": "brown", "renewalDate": "2017-09-27 16:34:39", "favoriteColors": ["red", "green", "yellow", "blue"]}
{"contact": [{"email": {"home": "home@email.com", "work": "work@email.com"}, "phone": {"home": "123-4567-8910", "work": "012-3456-7891"}}, {"email": {"home": "home@mail.com", "work": "work@mail.com"}, "phone": {"home": "1123-4567-891", "work": "112-3456-7891"}}], "hobbies": {"arts": ["drawing", "painting"], "sports": ["cricket", "football"]}, "regDate": 1506530183377, "eyeColor": "blue", "renewalDate": "2017-09-27 16:36:23", "favoriteColors": ["red", "green", "yellow", "blue"]}
{"contact": [{"email": {"home": "home@email.com", "work": "work@email.com"}, "phone": {"home": "123-4567-8910", "work": "012-3456-7891"}}, {"email": {"home": "home@mail.com", "work": "work@mail.com"}, "phone": {"home": "1123-4567-891", "work": "112-3456-7891"}}], "hobbies": {"arts": ["drawing", "painting"], "sports": ["cricket", "football"]}, "regDate": 1506594355593, "eyeColor": "blue", "renewalDate": "2017-09-28 10:25:55", "favoriteColors": ["red", "green", "yellow", "blue"]}
*/



--> Creating Index on jsonb field:

-- creating gin index with option jsonb_path_ops
CREATE INDEX on pgschm.person USING GIN ("additional_data" jsonb_path_ops);



--> Select:

SELECT
"firstName"
, "additionalData" #>> '{contact,0,email,home}' as home_email
, "additionalData" #>> '{eyeColor}' as eye_color
, "additionalData" #>> '{renewalDate}' as renewal_date
FROM pgschm.person
WHERE "additionalData" @> '{"hobbies":{"arts":["painting"]}}'
 AND "additionalData" @> '{"contact":[{"email":{"home":"home@email.com"}}]}'
Group by "firstName", "additionalData"
ORDER BY 2;

SELECT
"firstName"
, "additionalData" #>> '{contact,0,email,home}' as home_email
, "additionalData" #>> '{eyeColor}' as eye_color
, "additionalData" #>> '{renewalDate}' as renewal_date
FROM pgschm.person
WHERE "additionalData" @> '{"hobbies":{"arts":["painting"]}}'
 AND "additionalData" @> '{"contact":[{"email":{"home":"home@email.com"}}]}'
Group by "firstName", "additionalData"
ORDER BY renewal_date DESC;

-- count array elements inside json
SELECT id, jsonb_array_length("additionalData" #> '{contact}') as contacts
FROM pgschm.person GROUP BY id ORDER BY contacts;

-- count
SELECT p."additionalData" #>> '{eyeColor}' as eye_color, count(p.id) as count
FROM pgschm.person p GROUP BY eye_color ORDER BY count;



--> Update:

-- update text value of a root key
UPDATE pgschm.person SET "additionalData" = jsonb_set("additionalData", '{eyeColor}', to_jsonb('blue'::TEXT))
	WHERE id = 1 AND "additionalData" ? 'eyeColor';

-- add value to array
UPDATE pgschm.person SET "additionalData" = jsonb_set("additionalData", '{hobbies,sports}', "additionalData"#>'{hobbies,sports}' || '["baseball"]'::jsonb)
	WHERE id = 1 AND "additionalData" @> '{"hobbies":{"sports":[]}}' AND NOT "additionalData" @> '{"hobbies":{"sports":["baseball"]}}';

-- add value to array
UPDATE pgschm.person SET "additionalData" = jsonb_set("additionalData", '{hobbies,sports}', "additionalData"#>'{hobbies,sports}' || CAST('["baseball"]' as jsonb))
	WHERE id = 1 AND NOT "additionalData" @> '{"hobbies":{"sports":["baseball"]}}' AND "additionalData" @> '{"hobbies":{"sports":[]}}';

-- update json value of a key
UPDATE pgschm.person SET "additionalData" = jsonb_set("additionalData", '{hobbies}', "additionalData"#>'{hobbies}' || '{"arts": ["painting"], "sports": ["baseball"]}'::jsonb)
	WHERE id = 1 AND "additionalData" ? 'hobbies';



--> Delete:

-- delete path (array value or kvp):

UPDATE pgschm.Person SET "additionalData" = "additionalData" #- '{hobbies,sports,-1}'
	WHERE id = 1 AND "additionalData" @> CAST('{"hobbies":{"sports":[]}}' as jsonb) AND "additionalData" @> CAST('{"hobbies":{"sports":["baseball"]}}' as jsonb);


UPDATE pgschm.Person SET "additionalData" = "additionalData" #- '{hobbies,sports}'
	WHERE id = 1 AND "additionalData" @> CAST('{"hobbies":{"sports":[]}}' as jsonb);

-- In Java code:
-- UPDATE pgschm.Person SET "additionalData" = "additionalData" #- string_to_array(?, ',')
--    WHERE id = 1 AND "additionalData" @> CAST(? as jsonb) AND NOT "additionalData" @> CAST(? as jsonb);

-- delete value from array
UPDATE pgschm.Person SET "additionalData" = jsonb_set("additionalData", '{hobbies,sports}', ("additionalData" #> '{hobbies,sports}') - 'baseball')
	WHERE id = 1 AND "additionalData" @> '{"hobbies":{"sports":["baseball"]}}';

-- delete nested key/value pair
UPDATE pgschm.Person SET "additionalData" = jsonb_set("additionalData", '{hobbies}', ("additionalData" #> '{hobbies}') - 'sports')
	WHERE id = 1 AND "additionalData" @> '{"hobbies":{}}';

-- delete root key/value pair
Update pgschm.Person SET "additionalData" = "additionalData" - 'regDate'
	WHERE id = 1 AND "additionalData" ? 'regDate';
