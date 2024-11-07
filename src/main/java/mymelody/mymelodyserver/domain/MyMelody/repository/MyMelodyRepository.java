package mymelody.mymelodyserver.domain.MyMelody.repository;

import mymelody.mymelodyserver.domain.Member.entity.Member;
import mymelody.mymelodyserver.domain.MyMelody.entity.MyMelody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyMelodyRepository extends JpaRepository<MyMelody, Long>, MyMelodyCustomRepository {

    Page<MyMelody> findAllByMember(Member member, Pageable pageable);
}
