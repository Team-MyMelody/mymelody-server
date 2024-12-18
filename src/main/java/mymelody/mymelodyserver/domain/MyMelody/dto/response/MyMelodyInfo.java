package mymelody.mymelodyserver.domain.MyMelody.dto.response;

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
    private final String content;
    private final int totalLikes;
    private final int totalComments;

    private final String nickname;
    private final Boolean isLiked;

    private final String isrc;

    public static MyMelodyInfo of(MyMelody myMelody, Boolean isLiked) {
        return new MyMelodyInfo(myMelody.getId(), myMelody.getLatitude(), myMelody.getLongitude(),
                myMelody.getContent(), myMelody.getTotalLikes(), myMelody.getTotalComments(),
                myMelody.getMember().getNickname(), isLiked, myMelody.getMusic().getIsrc());
    }
}
