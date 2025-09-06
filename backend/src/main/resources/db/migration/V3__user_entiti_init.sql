CREATE TABLE user_roles
(
    role_id BIGINT NOT NULL,
    user_id UUID   NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
);

CREATE TABLE users
(
    id                      UUID         NOT NULL,
    email                   VARCHAR(255) NOT NULL,
    username                VARCHAR(255) NOT NULL,
    password                VARCHAR(255),
    oauth_id                VARCHAR(255),
    outside_auth_provider   VARCHAR(255) NOT NULL,
    first_name              VARCHAR(255),
    last_name               VARCHAR(255),
    enabled                 BOOLEAN      NOT NULL,
    account_non_expired     BOOLEAN      NOT NULL,
    account_non_locked      BOOLEAN      NOT NULL,
    credentials_non_expired BOOLEAN      NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_74165e195b2f7b25de690d14a UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_9f5538ac61519f968b1881462 UNIQUE (oauth_id, outside_auth_provider);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

CREATE INDEX idxEmail ON users (email);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role_entity FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user_entity FOREIGN KEY (user_id) REFERENCES users (id);