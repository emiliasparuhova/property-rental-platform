CREATE TABLE photo (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   advert_id BIGINT,
   photo_data LONGBLOB,
   FOREIGN KEY (advert_id) REFERENCES advert(id)
);
