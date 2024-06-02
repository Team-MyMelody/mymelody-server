package mymelody.mymelodyserver.domain.Comment.dto.request;

import lombok.Getter;
import mymelody.mymelodyserver.domain.Comment.entity.Comment;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import mymelody.mymelodyserver.domain.MyMelody.entity.MyMelody;

@Getter
public class CreateComment {

    private Long myMelodyId;
    private String content;

    public Comment toEntity(Member member, MyMelody myMelody) {
        return Comment.builder()
                .content(content)
                .member(member)
                .myMelody(myMelody)
                .build();
    }
}
