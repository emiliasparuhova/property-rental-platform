package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.converter.UserConverter;
import individual.individualprojectbackend.business.exception.EmailAlreadyInUseException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseImplTest {

    @Mock
    UserRepository userRepositoryMock;

    @InjectMocks
    UpdateUserUseCaseImpl updateUserUseCase;

    @Test
    void updateUser_successfulWhenUserIsNotNull(){
        User user = User.builder()
                .id(1L)
                .name("Chaimai")
                .email("chaimai@gmail.com")
                .address(Address.builder().build())
                .build();

        UserEntity userEntity = UserConverter.convertToEntity(user);

        User updatedUser = User.builder()
                .id(user.getId())
                .name("Chai Mai")
                .email("chaimai@mail.com")
                .address(Address.builder().build())
                .build();

        UserEntity updatedUserEntity = UserConverter.convertToEntity(updatedUser);

        when(userRepositoryMock.findById(user.getId())).thenReturn(Optional.ofNullable(userEntity));

        updateUserUseCase.updateUser(updatedUser);

        verify(userRepositoryMock).save(updatedUserEntity);
    }

    @Test
    void updateUser_throwsEmailAlreadyInUseException(){
        User updatedUser = User.builder()
                .id(1L)
                .name("Chaimai")
                .email("chaimai@gmail.com")
                .role(UserRole.TENANT)
                .build();

        User existingUser = User.builder()
                .id(1L)
                .name("Chaimai")
                .email("chai@gmail.com")
                .role(UserRole.TENANT)
                .build();

        UserEntity userEntity = UserConverter.convertToEntity(existingUser);

        when(userRepositoryMock.findById(any(Long.class))).thenReturn(Optional.ofNullable(userEntity));
        when(userRepositoryMock.existsByEmail(updatedUser.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyInUseException.class, () -> updateUserUseCase.updateUser(updatedUser));
        verify(userRepositoryMock).existsByEmail("chaimai@gmail.com");
    }

}