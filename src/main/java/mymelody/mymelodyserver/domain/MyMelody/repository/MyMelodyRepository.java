package mymelody.mymelodyserver.domain.MyMelody.repository;

import mymelody.mymelodyserver.domain.MyMelody.entity.MyMelody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyMelodyRepository extends JpaRepository<MyMelody, Long>, MyMelodyCustomRepository {

}
