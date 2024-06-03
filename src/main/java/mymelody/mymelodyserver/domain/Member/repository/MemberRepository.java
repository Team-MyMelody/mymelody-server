package mymelody.mymelodyserver.domain.Member.repository;

import mymelody.mymelodyserver.domain.Member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}
