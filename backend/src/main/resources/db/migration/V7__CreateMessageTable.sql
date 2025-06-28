CREATE TABLE message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT,
    content VARCHAR(10000),
    chat_id BIGINT,
    timestamp DATETIME,
    FOREIGN KEY (sender_id) REFERENCES app_user(id),
    FOREIGN KEY (chat_id) REFERENCES chat(id)
);
