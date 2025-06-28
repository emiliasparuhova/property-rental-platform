package individual.individualprojectbackend.business;

public interface CompareUserIdWithTokenUseCase {
    void compareIdWithToken(Long userId, String accessToken);
}
