package mymelody.mymelodyserver.domain.Member.repository;

import mymelody.mymelodyserver.domain.Member.dto.response.FollowInfo;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepositoryCustom {
    Page<FollowInfo> getFollowerInfoPage(Member following, Pageable pageable);

    Page<FollowInfo> getFollowingInfoPage(Member follower, Pageable pageable);
}
