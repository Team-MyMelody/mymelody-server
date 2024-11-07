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
    private final String nickname;
    private final String content;
    private final Boolean isWriter;

    public static List<CommentInfo> of(List<Comment> comments, Long memberId) {
        return comments.stream()
                .map(comment -> new CommentInfo(comment.getId(), comment.getMember().getNickname(),
                        comment.getContent(), comment.getMember().getId().equals(memberId)))
                .toList();
    }
}
