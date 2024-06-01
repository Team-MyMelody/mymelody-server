package mymelody.mymelodyserver.domain.MyMelody.repository;

import mymelody.mymelodyserver.domain.MyMelody.entity.MyMelody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyMelodyCustomRepository {

    Page<MyMelody> findAllByRange1km(double latitude, double longitude, Pageable pageable);
}
