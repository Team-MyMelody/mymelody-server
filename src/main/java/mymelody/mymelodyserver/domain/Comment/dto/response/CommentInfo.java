package mymelody.mymelodyserver.domain.Comment.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.Comment.entity.Comment;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentInfo {

    private final Long commentId;
    private final Long memberId;
    private final String content;

    public static List<CommentInfo> of(List<Comment> comments) {
        return comments.stream()
                .map(comment -> new CommentInfo(comment.getId(), comment.getMember().getId(),
                        comment.getContent()))
                .toList();
    }
}
