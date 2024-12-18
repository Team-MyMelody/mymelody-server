package mymelody.mymelodyserver.domain.Likes.repository;

import java.util.Optional;
import mymelody.mymelodyserver.domain.Likes.entity.Likes;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import mymelody.mymelodyserver.domain.MyMelody.entity.MyMelody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByMyMelodyAndMember(MyMelody myMelody, Member member);
    Boolean existsByMyMelodyAndMember(MyMelody myMelody, Member member);
    Page<Likes> findAllByMember(Member member, Pageable pageable);
}
