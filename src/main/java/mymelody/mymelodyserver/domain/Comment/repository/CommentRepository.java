package mymelody.mymelodyserver.domain.Comment.repository;

import mymelody.mymelodyserver.domain.Comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
