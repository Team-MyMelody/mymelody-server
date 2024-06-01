package mymelody.mymelodyserver.domain.MyMelody.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.MyMelody.entity.MyMelody;
import org.springframework.data.domain.Page;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GetMyMelodiesByLocation {

    private final int totalPages;
    private final long totalElements;
    private final List<MyMelodyInfo> myMelodyInfos;

    public static GetMyMelodiesByLocation of(Page<MyMelody> myMelodies) {
        return new GetMyMelodiesByLocation(myMelodies.getTotalPages(),
                myMelodies.getTotalElements(), MyMelodyInfo.of(myMelodies.getContent()));
    }
}
