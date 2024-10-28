-- Create the USERS table
CREATE TABLE USERS (
    username VARCHAR_IGNORECASE(50) NOT NULL PRIMARY KEY,
    password VARCHAR_IGNORECASE(500) NOT NULL,
    enabled BOOLEAN NOT NULL
);

-- Create the AUTHORITIES table
CREATE TABLE AUTHORITIES (
    username VARCHAR_IGNORECASE(50) NOT NULL,
    authority VARCHAR_IGNORECASE(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES USERS (username)
);

-- Create a unique index on the AUTHORITIES table for username and authority
CREATE UNIQUE INDEX ix_auth_username ON AUTHORITIES (username, authority);
