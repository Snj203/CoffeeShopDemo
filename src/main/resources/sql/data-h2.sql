
-- Users Table Insert --------------------------------------------------------------------------
INSERT INTO users (username, password, enabled,  is_email_verified)
SELECT 'agai', '$2a$14$aD/89GEqrTVyN.JWm1PW0OcjACrNO/qs0JspHnNO3zLAF7AkeUpp.', true, true
    WHERE NOT EXISTS(SELECT FROM users WHERE username = 'agai');

INSERT INTO users (username, password, enabled, is_email_verified)
SELECT 'sanjar', '$2a$10$VGtKxeBB2jpv7tBg9z185O/4UYJNJPR1zbx59yGe1gCSYMBLrKu3K', true, true
    WHERE NOT EXISTS(SELECT FROM users WHERE username = 'sanjar');

INSERT INTO users (username, password, enabled,  is_email_verified)
SELECT 'default-user', '$2a$14$OrjmmCvJ4OLSvtvVCjSe.uUT3.shR6y7UNgaoL6uGCH690dS4dEEG', true, true
    WHERE NOT EXISTS(SELECT FROM users WHERE username = 'default-user');

INSERT INTO users (username, password, enabled,  is_email_verified)
SELECT 'default-viewer', '$2a$14$OrjmmCvJ4OLSvtvVCjSe.uUT3.shR6y7UNgaoL6uGCH690dS4dEEG', true, true
    WHERE NOT EXISTS(SELECT FROM users WHERE username = 'default-viewer');

-- Authorities Table Insert --------------------------------------------------------------------------
INSERT INTO authorities (username, authority)
SELECT 'sanjar', 'ROLE_USER'
    WHERE NOT EXISTS(SELECT FROM authorities WHERE username = 'sanjar' AND authority = 'ROLE_USER');

INSERT INTO authorities (username, authority)
SELECT 'sanjar', 'ROLE_ADMIN'
    WHERE NOT EXISTS(SELECT FROM authorities WHERE username = 'sanjar' AND authority = 'ROLE_ADMIN');

INSERT INTO authorities (username, authority)
SELECT 'sanjar', 'ROLE_VIEWER'
    WHERE NOT EXISTS(SELECT FROM authorities WHERE username = 'sanjar' AND authority = 'ROLE_VIEWER');

INSERT INTO authorities (username, authority)
SELECT 'agai', 'ROLE_USER'
    WHERE NOT EXISTS(SELECT FROM authorities WHERE username = 'agai' AND authority = 'ROLE_USER');

INSERT INTO authorities (username, authority)
SELECT 'agai', 'ROLE_ADMIN'
    WHERE NOT EXISTS(SELECT FROM authorities WHERE username = 'agai' AND authority = 'ROLE_ADMIN');

INSERT INTO authorities (username, authority)
SELECT 'agai', 'ROLE_VIEWER'
    WHERE NOT EXISTS(SELECT FROM authorities WHERE username = 'agai' AND authority = 'ROLE_VIEWER');

INSERT INTO authorities (username, authority)
SELECT 'default-user', 'ROLE_USER'
    WHERE NOT EXISTS(SELECT FROM authorities WHERE username = 'default-user' AND authority = 'ROLE_USER');

INSERT INTO authorities (username, authority)
SELECT 'default-viewer', 'ROLE_VIEWER'
    WHERE NOT EXISTS(SELECT FROM authorities WHERE username = 'default-viewer' AND authority = 'ROLE_VIEWER');
-- _________

