package mymelody.mymelodyserver.domain.Member.dto.response;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetFollowPage {
    private int totalPages;
    private long totalElements;
    private List<FollowInfo> followInfos;

    public static GetFollowPage from(Page<FollowInfo> followerInfoPage) {
        return GetFollowPage.builder()
                .totalPages(followerInfoPage.getTotalPages())
                .totalElements(followerInfoPage.getTotalElements())
                .followInfos(followerInfoPage.getContent())
                .build();
    }
}
