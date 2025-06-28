CREATE TABLE linked_account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    linked_id VARCHAR(255),
    linked_email VARCHAR(255),
    linked_name VARCHAR(255),
    provider VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES app_user(id)
);