package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.domain.enums.UserRole;
import individual.individualprojectbackend.persistence.UserRepository;
import individual.individualprojectbackend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseImplTest {

    @Mock
    UserRepository userRepositoryMock;

    @InjectMocks
    DeleteUserUseCaseImpl deleteUserUseCase;

    @Test
    void deleteUser_successful(){
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .name("Chaimai")
                .email("chaimai@gmail.com")
                .role(UserRole.TENANT)
                .build();

        deleteUserUseCase.deleteUser(userEntity.getId());

        verify(userRepositoryMock).deleteById(userEntity.getId());
    }

}