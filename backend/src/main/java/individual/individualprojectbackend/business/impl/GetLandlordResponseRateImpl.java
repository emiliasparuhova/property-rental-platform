package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetLandlordResponseRate;
import individual.individualprojectbackend.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetLandlordResponseRateImpl implements GetLandlordResponseRate {

    private UserRepository userRepository;

    @Override
    public Double getLandlordResponseRate(Long id) {
        return userRepository.getLandlordResponseRate(id);
    }
}
