-- ============================================================
-- Blood Bank Simplified Schema
-- Entities: users, donors, blood_donations, blood_usages
-- Inventory is CALCULATED (donations - usages) per blood type
-- ============================================================

CREATE TABLE IF NOT EXISTS users (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(150) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(20)  NOT NULL DEFAULT 'STAFF',
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS donors (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(150) NOT NULL,
    phone      VARCHAR(20)  NOT NULL,
    blood_type VARCHAR(10)  NOT NULL,
    email      VARCHAR(255),
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS blood_donations (
    id            BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    donor_id      BIGINT      NOT NULL,
    blood_type    VARCHAR(10) NOT NULL,
    quantity      INT         NOT NULL,
    donation_date DATE        NOT NULL,
    created_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_donation_donor FOREIGN KEY (donor_id) REFERENCES donors(id)
);

CREATE TABLE IF NOT EXISTS blood_usages (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    blood_type VARCHAR(10)   NOT NULL,
    quantity   INT           NOT NULL,
    used_date  DATE          NOT NULL,
    note       VARCHAR(500),
    created_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- Default admin user  (password: admin123)
-- BCrypt hash of "admin123"
-- ============================================================
INSERT IGNORE INTO users (name, email, password, role)
VALUES ('Admin', 'admin@bloodbank.com',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN');
