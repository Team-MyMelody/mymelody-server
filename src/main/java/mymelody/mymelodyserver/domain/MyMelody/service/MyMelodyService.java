package mymelody.mymelodyserver.domain.MyMelody.service;

import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.MyMelody.dto.response.GetMyMelodiesByLocation;
import mymelody.mymelodyserver.domain.MyMelody.entity.MyMelody;
import mymelody.mymelodyserver.domain.MyMelody.repository.MyMelodyRepository;
import mymelody.mymelodyserver.global.common.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyMelodyService {

    private final MyMelodyRepository myMelodyRepository;

    public GetMyMelodiesByLocation getMyMelodiesByLocationWithPagination(double latitude, double longitude,
            PageRequest pageRequest) {
        Pageable pageable = pageRequest.of();
        Page<MyMelody> myMelodies = myMelodyRepository.findAllByRange1km(latitude, longitude, pageable);

        return GetMyMelodiesByLocation.of(myMelodies);
    }
}
