package mymelody.mymelodyserver.domain.Comment.service;

import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.Comment.dto.request.CreateComment;
import mymelody.mymelodyserver.domain.Comment.dto.response.GetCommentsByMyMelody;
import mymelody.mymelodyserver.domain.Comment.entity.Comment;
import mymelody.mymelodyserver.domain.Comment.repository.CommentRepository;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import mymelody.mymelodyserver.domain.Member.repository.MemberRepository;
import mymelody.mymelodyserver.domain.MyMelody.entity.MyMelody;
import mymelody.mymelodyserver.domain.MyMelody.repository.MyMelodyRepository;
import mymelody.mymelodyserver.global.common.PageRequest;
import mymelody.mymelodyserver.global.entity.ErrorCode;
import mymelody.mymelodyserver.global.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final MyMelodyRepository myMelodyRepository;

    @Transactional
    public void createComment(CreateComment createComment, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        MyMelody myMelody = myMelodyRepository.findById(createComment.getMyMelodyId()).orElseThrow(
                () -> new CustomException(ErrorCode.MYMELODY_NOT_FOUND));

        commentRepository.save(createComment.toEntity(member, myMelody));
        myMelody.increaseTotalComments();
    }

    public GetCommentsByMyMelody getCommentsByMyMelody(Long myMelodyId, PageRequest pageRequest,
            Long memberId) {
        MyMelody myMelody = myMelodyRepository.findById(myMelodyId).orElseThrow(
                () -> new CustomException(ErrorCode.MYMELODY_NOT_FOUND));

        Pageable pageable = pageRequest.of();
        Page<Comment> comments = commentRepository.findAllByMyMelody(myMelody, pageable);

        if (memberId != 0 && !memberRepository.existsById(memberId)) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

        return GetCommentsByMyMelody.of(comments, memberId);
    }

    @Transactional
    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!comment.getMember().equals(member)) {
            throw new CustomException(ErrorCode.INVALID_COMMENT_DELETE_REQUEST);
        }

        commentRepository.delete(comment);
    }
}
