package mymelody.mymelodyserver.domain.MyMelody.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GetMyMelodies {

    private final int totalPages;
    private final long totalElements;
    private final List<MyMelodyInfo> myMelodyInfos;

    public static GetMyMelodies of(int totalPages, long totalElements,
            List<MyMelodyInfo> myMelodyInfos) {
        return new GetMyMelodies(totalPages, totalElements, myMelodyInfos);
    }
}
