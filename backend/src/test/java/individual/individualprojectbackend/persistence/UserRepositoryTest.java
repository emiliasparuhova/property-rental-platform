package individual.individualprojectbackend.persistence;

import individual.individualprojectbackend.business.converter.UserConverter;
import individual.individualprojectbackend.domain.Address;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.domain.enums.Gender;
import individual.individualprojectbackend.domain.enums.UserRole;
import individual.individualprojectbackend.domain.enums.UserStatus;
import individual.individualprojectbackend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private UserEntity createTestUserEntity(){
        User user = User.builder()
                .name("Test User")
                .email("test@example.com")
                .address(Address.builder().city("Test City").street("Test Street").zipcode("Test Zipcode").build())
                .gender(Gender.MALE)
                .birthDate(null)
                .profilePicture(null)
                .joinDate(null)
                .role(UserRole.TENANT)
                .status(UserStatus.WORKING)
                .build();

        return UserConverter.convertToEntity(user);
    }

    @Test
    void saveUser_returnsTheSavedUserEntity_whenSuccessful() {
        UserEntity userToSave = createTestUserEntity();

        UserEntity savedUser = userRepository.save(userToSave);

        assertNotNull(savedUser.getId());
        assertEquals(userToSave.getName(), savedUser.getName());
        assertEquals(userToSave.getEmail(), savedUser.getEmail());
        assertEquals(userToSave.getRole(), savedUser.getRole());
    }

    @Test
    void findUserById_returnsUserEntity_whenUserExists() {
        UserEntity savedUser = userRepository.save(createTestUserEntity());

            Optional<UserEntity> result = userRepository.findById(savedUser.getId());

        assertTrue(result.isPresent());
        assertEquals(savedUser.getId(), result.get().getId());
        assertEquals(savedUser.getName(), result.get().getName());
    }

    @Test
    void findAdvertById_returnsEmptyOptional_whenAdvertDoesNotExist() {
        Optional<UserEntity> result = userRepository.findById(1000L);

        assertFalse(result.isPresent());
    }

    @Test
    void updateUser_updatesUserEntity_whenSuccessful() {
        UserEntity savedUser = userRepository.save(createTestUserEntity());

        savedUser.setName("UpdatedName");
        savedUser.setEmail("updated.email@example.com");
        UserEntity updatedUser = userRepository.save(savedUser);

        assertEquals(savedUser.getId(), updatedUser.getId());
        assertEquals("UpdatedName", updatedUser.getName());
        assertEquals("updated.email@example.com", updatedUser.getEmail());
    }

    @Test
    void deleteUser_deletesUserEntity_whenSuccessful() {
        UserEntity savedUser = userRepository.save(createTestUserEntity());

        userRepository.delete(savedUser);

        assertFalse(userRepository.findById(savedUser.getId()).isPresent());
    }

    @Test
    void findByEmail_returnsUserEntity_whenUserExists() {
        UserEntity savedUser = userRepository.save(createTestUserEntity());

        Optional<UserEntity> result = userRepository.findByEmail(savedUser.getEmail());

        assertTrue(result.isPresent());
        assertEquals(savedUser.getId(), result.get().getId());
        assertEquals(savedUser.getName(), result.get().getName());
        assertEquals(savedUser.getEmail(), result.get().getEmail());
    }

    @Test
    void findByEmail_returnsEmptyOptional_whenUserDoesNotExist() {
        Optional<UserEntity> result = userRepository.findByEmail("nonexistent.email@example.com");

        assertFalse(result.isPresent());
    }

    @Test
    void existsByEmail_returnsTrue_whenUserExists() {
        UserEntity savedUser = userRepository.save(createTestUserEntity());

        assertTrue(userRepository.existsByEmail(savedUser.getEmail()));
    }

    @Test
    void existsByEmail_returnsFalse_whenUserDoesNotExist() {
        assertFalse(userRepository.existsByEmail("nonexistent.email@example.com"));
    }


}