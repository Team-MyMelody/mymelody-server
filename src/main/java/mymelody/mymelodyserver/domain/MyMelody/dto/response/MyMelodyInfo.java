package mymelody.mymelodyserver.domain.MyMelody.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.MyMelody.entity.MyMelody;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MyMelodyInfo {

    private final Long myMelodyId;
    private final double latitude;
    private final double longitude;
    private final String comment;
    private final int totalLikes;
    private final int totalComments;

    private final Long memberId;
    private final String nickname;

    private final String isrc;

    public static List<MyMelodyInfo> of(List<MyMelody> myMelodies) {
        return myMelodies.stream()
                .map(myMelody -> new MyMelodyInfo(myMelody.getId(), myMelody.getLatitude(),
                        myMelody.getLongitude(), myMelody.getComment(), myMelody.getTotalLikes(),
                        myMelody.getTotalComments(), myMelody.getMember().getId(),
                        myMelody.getMember().getNickname(), myMelody.getMusic().getIsrc()))
                .toList();
    }
}
