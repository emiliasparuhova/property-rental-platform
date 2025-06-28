CREATE TABLE chat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    advert_id BIGINT,
    landlord_id BIGINT,
    tenant_id BIGINT,
    FOREIGN KEY (advert_id) REFERENCES advert(id),
    FOREIGN KEY (landlord_id) REFERENCES app_user(id),
    FOREIGN KEY (tenant_id) REFERENCES app_user(id)
);
