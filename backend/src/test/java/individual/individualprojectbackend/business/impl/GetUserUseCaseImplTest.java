package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.converter.UserConverter;
import individual.individualprojectbackend.domain.Address;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.domain.enums.UserRole;
import individual.individualprojectbackend.persistence.UserRepository;
import individual.individualprojectbackend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserUseCaseImplTest {

    @Mock
    UserRepository userRepositoryMock;

    @InjectMocks
    GetUserUseCaseImpl getUserUseCase;

    @Test
    void getUserById_successfulWhenUserExists(){
        User user = User.builder()
                .id(1L)
                .name("Chaimai")
                .email("chaimai@gmail.com")
                .role(UserRole.TENANT)
                .address(Address.builder().build())
                .build();

        UserEntity userEntity = UserConverter.convertToEntity(user);

        when(userRepositoryMock.findById(user.getId())).thenReturn(Optional.ofNullable(userEntity));

        User actualResult = getUserUseCase.getUser(user.getId());

        assertNotNull(actualResult);
        assertEquals(user.getId(), actualResult.getId());
        verify(userRepositoryMock, times(1)).findById(user.getId());
    }

    @Test
    void getUserById_returnsNull_whenUserDoesntExist(){
        Long id = 1L;

        when(userRepositoryMock.findById(id)).thenReturn(Optional.empty());

        assertNull(getUserUseCase.getUser(id));
        verify(userRepositoryMock, times(1)).findById(id);
    }

}