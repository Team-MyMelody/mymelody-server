package mymelody.mymelodyserver.domain.MyMelody.repository;

import java.util.List;
import mymelody.mymelodyserver.domain.MyMelody.entity.MyMelody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MyMelodyRepository extends JpaRepository<MyMelody, Long>{

    @Query(value = "SELECT * FROM my_melody m "
            + "WHERE ST_Distance_Sphere(Point(:longitude,:latitude),POINT(m.longitude, m.latitude)) <= 1000",
            nativeQuery = true)
    Page<MyMelody> findAllByRange1km(double latitude, double longitude, Pageable pageable);

    @Query(value = "SELECT * FROM my_melody m "
            + "WHERE ST_Distance_Sphere(Point(:longitude,:latitude),POINT(m.longitude, m.latitude)) <= 1000",
            nativeQuery = true)
    List<MyMelody> findAllByRange1km(double latitude, double longitude);
}
