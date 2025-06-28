CREATE TABLE favourite_advert (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    advert_id BIGINT,
    timestamp DATETIME,
    FOREIGN KEY (user_id) REFERENCES app_user(id),
    FOREIGN KEY (advert_id) REFERENCES advert(id)
);
