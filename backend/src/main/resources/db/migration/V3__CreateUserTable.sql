CREATE TABLE app_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    description VARCHAR(500),
    address_id BIGINT,
    gender VARCHAR(255),
    birth_date DATE,
    profile_picture LONGBLOB,
    join_date DATE,
    role VARCHAR(255),
    status VARCHAR(255),
    hashed_password VARCHAR(255),
    FOREIGN KEY (address_id) REFERENCES address(id)
);
