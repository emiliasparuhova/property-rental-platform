package individual.individualprojectbackend.persistence;

import individual.individualprojectbackend.domain.enums.PropertyFurnishingType;
import individual.individualprojectbackend.domain.enums.PropertyType;
import individual.individualprojectbackend.persistence.entity.AddressEntity;
import individual.individualprojectbackend.persistence.entity.AdvertEntity;
import individual.individualprojectbackend.persistence.entity.PropertyEntity;
import individual.individualprojectbackend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest

class AdvertRepositoryTest {

    @Autowired
    AdvertRepository advertRepository;

    @Autowired
    UserRepository userRepository;


    private AdvertEntity createTestAdvertEntity() {
        UserEntity user = UserEntity.builder()
                .name("Test")
                .build();

        userRepository.save(user);

        return AdvertEntity.builder()
                .price(1500.00)
                .description("Spacious 2-bedroom apartment for rent")
                .utilitiesIncluded(true)
                .creationDate(null)
                .creator(user)
                .property(PropertyEntity.builder()
                        .size(1000)
                        .numberOfRooms(2)
                        .propertyType(PropertyType.APARTMENT)
                        .furnishingType(PropertyFurnishingType.FURNISHED)
                        .address(AddressEntity.builder()
                                .city("Example City")
                                .street("123 Main Street")
                                .zipcode("12345")
                                .build())
                        .build())
                .availableFrom(null)
                .photos(null)
                .build();
    }


    @Test
    void saveAdvert_returnsSavedAdvertEntity_whenSuccessful() {
        AdvertEntity advertToSave = createTestAdvertEntity();

        AdvertEntity savedAdvertEntity = advertRepository.save(advertToSave);

        assertNotNull(savedAdvertEntity.getId());
        assertEquals(advertToSave.getPrice(), savedAdvertEntity.getPrice());
        assertEquals(advertToSave.getDescription(), savedAdvertEntity.getDescription());
    }

    @Test
    void findAdvertById_returnsAdvertEntity_whenAdvertExists() {
        AdvertEntity savedAdvertEntity = advertRepository.save(createTestAdvertEntity());

        Optional<AdvertEntity> result = advertRepository.findById(savedAdvertEntity.getId());

        assertTrue(result.isPresent());
        assertEquals(savedAdvertEntity.getId(), result.get().getId());
        assertEquals(savedAdvertEntity.getPrice(), result.get().getPrice());
    }

    @Test
    void findAdvertById_returnsEmptyOptional_whenAdvertDoesNotExist() {
        Optional<AdvertEntity> result = advertRepository.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void updateAdvert_updatesAdvertEntity_whenSuccessful() {
        AdvertEntity savedAdvertEntity = advertRepository.save(createTestAdvertEntity());

        savedAdvertEntity.setPrice(2000);
        savedAdvertEntity.setDescription("Updated Description");
        AdvertEntity updatedAdvertEntity = advertRepository.save(savedAdvertEntity);

        assertEquals(savedAdvertEntity.getId(), updatedAdvertEntity.getId());
        assertEquals(2000, updatedAdvertEntity.getPrice());
        assertEquals("Updated Description", updatedAdvertEntity.getDescription());
    }

    @Test
    void deleteAdvert_deletesAdvertEntity_whenSuccessful() {
        AdvertEntity savedAdvertEntity = advertRepository.save(createTestAdvertEntity());

        advertRepository.delete(savedAdvertEntity);

        assertFalse(advertRepository.findById(savedAdvertEntity.getId()).isPresent());
    }

    @Test
    void findByFilters_returnsMatchingAdvertEntities_whenFiltersProvided() {
        AdvertEntity advert1 = createTestAdvertEntity();
        AdvertEntity advert2 = createTestAdvertEntity();
        advertRepository.save(advert1);
        advertRepository.save(advert2);

        Pageable pageable = PageRequest.of(0, 10);

        Page<AdvertEntity> resultPage = advertRepository.findByFilters(
                "Example City",
                0.0,
                0.0,
                PropertyType.APARTMENT,
                PropertyFurnishingType.FURNISHED,
                pageable
        );

        List<AdvertEntity> result = resultPage.getContent();

        assertEquals(2, result.size());
    }

    @Test
    void findByFilters_returnsEmptyList_whenNoMatchesFound() {
        AdvertEntity advert1 = createTestAdvertEntity();
        AdvertEntity advert2 = createTestAdvertEntity();
        advertRepository.save(advert1);
        advertRepository.save(advert2);

        Pageable pageable = PageRequest.of(0, 10);

        Page<AdvertEntity> resultPage = advertRepository.findByFilters(
                "Nonexistent City",
                0.0,
                0.0,
                null,
                null,
                pageable
        );

        List<AdvertEntity> result = resultPage.getContent();

        assertEquals(0, result.size());
    }

    @Test
    void findByCreator_IdOrderByCreationDateDesc_returnsMatchingAdvertEntities_whenCreatorIdProvided() {
        AdvertEntity advert = createTestAdvertEntity();
        Long creatorId = advert.getCreator().getId();
        advertRepository.save(advert);

        List<AdvertEntity> result = advertRepository.findByCreator_IdOrderByCreationDateDesc(creatorId);

        assertEquals(1, result.size());
    }

    @Test
    void countAdvertsByCity_returnsCount_whenCityExists() {
        AdvertEntity advert = createTestAdvertEntity();
        advertRepository.save(advert);

        Optional<Integer> result = advertRepository.countAdvertsByCity("Example City");

        assertTrue(result.isPresent());
        assertEquals(1, result.get());
    }

    @Test
    void countAdvertsByCity_returns0_whenCityDoesNotExist() {
        Optional<Integer> result = advertRepository.countAdvertsByCity("Nonexistent City");

        assertEquals(0, result.get());
    }

    @Test
    void calculateAveragePriceByCity_returnsAveragePrice_whenCityExists() {
        AdvertEntity advert1 = createTestAdvertEntity();
        AdvertEntity advert2 = createTestAdvertEntity();
        advertRepository.save(advert1);
        advertRepository.save(advert2);

        double expectedResult = (advert1.getPrice() + advert2.getPrice())/2;
        Optional<Double> result = advertRepository.calculateAveragePriceByCity("Example City");

        assertTrue(result.isPresent());
        assertEquals(result.get(), expectedResult);
    }
}