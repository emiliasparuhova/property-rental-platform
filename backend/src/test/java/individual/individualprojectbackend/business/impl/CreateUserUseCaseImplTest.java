package individual.individualprojectbackend.business.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import individual.individualprojectbackend.business.converter.UserConverter;
import individual.individualprojectbackend.business.exception.EmailAlreadyInUseException;
import individual.individualprojectbackend.domain.Address;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.domain.enums.UserRole;
import individual.individualprojectbackend.persistence.UserRepository;
import individual.individualprojectbackend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    UserRepository userRepositoryMock;

    @InjectMocks
    CreateUserUseCaseImpl createUserUseCase;

    @Test
    void createUser_successfulWhenInputIsValid(){
        User user = User.builder()
                .id(1L)
                .name("Chaimai")
                .email("chaimai@gmail.com")
                .role(UserRole.TENANT)
                .address(Address.builder().build())
                .build();

        UserEntity userEntity = UserConverter.convertToEntity(user);

        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(userEntity);

        Long actualResult = createUserUseCase.createUser(user, "password");
        Long expectedResult = 1L;

        assertEquals(expectedResult, actualResult);
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
    }

    @Test
    void createUser_throwsEmailAlreadyInUseException(){
        User user = User.builder()
                .name("Chaimai")
                .email("chaimai@gmail.com")
                .role(UserRole.TENANT)
                .build();

        when(userRepositoryMock.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyInUseException.class, () -> createUserUseCase.createUser(user, "password"));
        verify(userRepositoryMock).existsByEmail("chaimai@gmail.com");
    }
}