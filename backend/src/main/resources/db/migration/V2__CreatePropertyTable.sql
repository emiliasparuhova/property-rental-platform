CREATE TABLE property (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    size INT,
    number_of_rooms INT,
    property_type VARCHAR(255),
    furnishing_type VARCHAR(255),
    address_id BIGINT,
    FOREIGN KEY (address_id) REFERENCES address(id)
);
