package mymelody.mymelodyserver.domain.MyMelody.dto.request;

import lombok.Getter;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import mymelody.mymelodyserver.domain.Music.entity.Music;
import mymelody.mymelodyserver.domain.MyMelody.entity.MyMelody;

@Getter
public class CreateMyMelody {
    private double longitude;
    private double latitude;
    private String isrc;
    private String content;

    public MyMelody toEntity(Member member, Music music) {
        return MyMelody.builder()
                .longitude(longitude)
                .latitude(latitude)
                .content(content)
                .member(member)
                .music(music)
                .build();
    }
}
