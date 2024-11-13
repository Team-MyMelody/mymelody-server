package mymelody.mymelodyserver.domain.Comment.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.Comment.entity.Comment;
import org.springframework.data.domain.Page;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GetCommentsByMyMelody {

    private final int totalPages;
    private final long totalElements;
    private final List<CommentInfo> commentInfos;

    public static GetCommentsByMyMelody of(Page<Comment> comments, Long memberId) {
        return new GetCommentsByMyMelody(comments.getTotalPages(), comments.getTotalElements(),
                CommentInfo.of(comments.getContent(), memberId));
    }
}
