package mymelody.mymelodyserver.domain.MyMelody.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.MyMelody.dto.response.GetMyMelodiesByLocation;
import mymelody.mymelodyserver.domain.MyMelody.dto.response.MyMelodyInfo;
import mymelody.mymelodyserver.domain.MyMelody.entity.MyMelody;
import mymelody.mymelodyserver.domain.MyMelody.repository.MyMelodyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyMelodyService {

    private final MyMelodyRepository myMelodyRepository;

    public GetMyMelodiesByLocation getMyMelodiesByLocationWithPagination(double latitude, double longitude,
            int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MyMelody> myMelodies = myMelodyRepository.findAllByRange1km(latitude, longitude, pageable);

        return GetMyMelodiesByLocation.of(myMelodies);
    }

    public List<MyMelodyInfo> getMyMelodiesByLocation(double latitude, double longitude) {
        List<MyMelody> myMelodies = myMelodyRepository.findAllByRange1km(latitude, longitude);

        return MyMelodyInfo.of(myMelodies);
    }
}
