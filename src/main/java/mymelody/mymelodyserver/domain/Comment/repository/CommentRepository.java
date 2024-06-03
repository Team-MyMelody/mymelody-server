package mymelody.mymelodyserver.domain.Comment.repository;

import mymelody.mymelodyserver.domain.Comment.entity.Comment;
import mymelody.mymelodyserver.domain.MyMelody.entity.MyMelody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByMyMelody(MyMelody myMelody, Pageable pageable);
}
