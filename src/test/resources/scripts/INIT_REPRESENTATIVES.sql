INSERT INTO representative (id, first_name, last_name, position, region, notice, status, last_visit, scheduled_visit, customer_id, `phone_numbers`, `emails`, `contact_type`)
VALUES (1,'John','Doe','CEO','V4','nothing special','ACTIVE','2020-10-07','2021-04-25',1,'[{"type": "HOME", "contact": "+420920920920", "primary": true}, {"type": "HOME", "contact": "+420920920920", "primary": false}]','[{"type": "WORK", "contact": "john@example.com", "primary": true}]','MAIL');
INSERT INTO representative (id, first_name, last_name, position, region, notice, status, last_visit, scheduled_visit, customer_id, `phone_numbers`, `emails`, `contact_type`)
VALUES (2,'Jane','Smith','CFO','EMEA','anything special','INACTIVE','2020-10-07','2021-04-25',NULL,'[]','[]','PHONE');
