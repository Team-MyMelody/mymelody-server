package mymelody.mymelodyserver.domain.Music.repository;

import mymelody.mymelodyserver.domain.Music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    Optional<Music> findByIsrc(String isrc);
}
