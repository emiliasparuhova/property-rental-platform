package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.converter.UserConverter;
import individual.individualprojectbackend.business.exception.InvalidCredentialsException;
import individual.individualprojectbackend.configuration.security.token.AccessToken;
import individual.individualprojectbackend.configuration.security.token.AccessTokenEncoder;
import individual.individualprojectbackend.configuration.security.token.impl.AccessTokenImpl;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticateUserUseCaseImplTest {

    @Mock
    UserRepository userRepositoryMock;

    @Mock
    AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    AuthenticateUserUseCaseImpl authenticateUserUseCase;


    @Test
    void authenticateUser_returnAccessTokenWhenUserExistsAndCredentialsMatch(){
        PasswordHashingService passwordHashingService = new PasswordHashingService();
        String password = "password";
        String hashedPassword = passwordHashingService.hashPassword(password);

        User user = User.builder()
                .id(1L)
                .name("Chaimai")
                .email("chaimai@gmail.com")
                .role(UserRole.TENANT)
                .address(Address.builder().build())
                .hashedPassword(hashedPassword)
                .build();

        UserEntity userEntity = UserConverter.convertToEntity(user);

        AccessToken accessToken = new AccessTokenImpl(user.getEmail(), user.getId(), user.getRole().name());

        when(userRepositoryMock.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(userEntity));
        when(accessTokenEncoder.encode(accessToken)).thenReturn("someAccessTokenValue");

        String actualResult = authenticateUserUseCase.authenticateUser(user.getEmail(), "password");

        assertEquals("someAccessTokenValue", actualResult);
        verify(userRepositoryMock).findByEmail(user.getEmail());
    }

    @Test
    void authenticateUser_throwsExceptionWhenCredentialsDontMatch(){
        PasswordHashingService passwordHashingService = new PasswordHashingService();
        String password = "password";
        String hashedPassword = passwordHashingService.hashPassword(password);

        User user = User.builder()
                .id(1L)
                .name("Chaimai")
                .email("chaimai@gmail.com")
                .role(UserRole.TENANT)
                .address(Address.builder().build())
                .hashedPassword(hashedPassword)
                .build();

        UserEntity userEntity = UserConverter.convertToEntity(user);

        when(userRepositoryMock.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(userEntity));

        assertThrows(InvalidCredentialsException.class, () -> authenticateUserUseCase.authenticateUser("chaimai@gmail.com", "differentPassword"));
        verify(userRepositoryMock).findByEmail(user.getEmail());
    }

    @Test
    void authenticateUser_throwsExceptionWhenUserDoesntExist(){
        when(userRepositoryMock.findByEmail("email@mail.com")).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> authenticateUserUseCase.authenticateUser("email@mail.com", "password"));
        verify(userRepositoryMock).findByEmail("email@mail.com");
    }
}