package mymelody.mymelodyserver.domain.Member.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class FollowInfo {
    private Long memberId;
    private String nickname;

    @QueryProjection
    public FollowInfo(Long memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }
}
