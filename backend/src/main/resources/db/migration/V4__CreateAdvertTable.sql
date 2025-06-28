CREATE TABLE advert (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    price DOUBLE NOT NULL,
    description VARCHAR(3000) NOT NULL,
    property_id BIGINT,
    user_id BIGINT,
    utilities_included BOOLEAN,
    available_from DATE,
    creation_date DATE,
    FOREIGN KEY (property_id) REFERENCES property(id),
    FOREIGN KEY (user_id) REFERENCES app_user(id)
);
