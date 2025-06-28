package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.CompareUserIdWithTokenUseCase;
import individual.individualprojectbackend.business.GetUserIdFromTokenUseCase;
import individual.individualprojectbackend.business.exception.UserIdMismatchException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CompareUserIdWithTokenUseCaseImpl implements CompareUserIdWithTokenUseCase {
    private final GetUserIdFromTokenUseCase getUserIdFromTokenUseCase;
    @Override
    public void compareIdWithToken(Long userId, String accessToken) {
        Long idFromToken = getUserIdFromTokenUseCase.getIdFromToken(accessToken);

        if (!Objects.equals(userId, idFromToken)){
            throw new UserIdMismatchException();
        }

    }
}
