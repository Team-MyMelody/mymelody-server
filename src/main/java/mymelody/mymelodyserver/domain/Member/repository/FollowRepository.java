package mymelody.mymelodyserver.domain.Member.repository;

import mymelody.mymelodyserver.domain.Member.entity.Follow;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom {
    Optional<Follow> findByFollowerAndFollowing(Member follower, Member following);
}
