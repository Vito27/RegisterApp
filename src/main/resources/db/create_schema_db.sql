CREATE SCHEMA IF NOT EXISTS register;

CREATE SEQUENCE record_record_id_seq START with 1;
CREATE TABLE IF NOT EXISTS register.record
(
    record_id        BIGINT DEFAULT nextval('record_record_id_seq') NOT NULL,
    id_number        BIGINT UNIQUE,
    comment          VARCHAR(255),
    amount           BIGINT,
    face_value       BIGINT,
    total_face_value BIGINT,
    release_date     DATE,
    status           VARCHAR(20),
    CONSTRAINT PK__record__record_id PRIMARY KEY (record_id)
);

CREATE SEQUENCE change_history_change_history_id_seq START with 1;
CREATE TABLE IF NOT EXISTS register.change_history
(
    change_history_id BIGINT DEFAULT nextval('change_history_change_history_id_seq') NOT NULL,
    id_number         BIGINT,
    field_name        VARCHAR(50),
    old_value         VARCHAR(255),
    new_value         VARCHAR(255),
    CONSTRAINT PK__change_history__change_history_id PRIMARY KEY (change_history_id)
);