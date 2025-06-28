package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.DeleteAdvertUseCase;
import individual.individualprojectbackend.persistence.AdvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteAdvertUseCaseImpl implements DeleteAdvertUseCase {
    private AdvertRepository advertRepository;

    @Override
    public void deleteAdvert(Long id) {
        advertRepository.deleteById(id);
    }
}
