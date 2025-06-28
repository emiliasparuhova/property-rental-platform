package individual.individualprojectbackend.business;

public interface AuthenticateUserUseCase {
    String authenticateUser(String email, String password);
}
