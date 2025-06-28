package individual.individualprojectbackend.business;

public interface GetUserIdFromTokenUseCase {
    Long getIdFromToken(String accessToken);
}
