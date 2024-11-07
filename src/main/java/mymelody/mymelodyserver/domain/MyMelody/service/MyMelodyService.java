package mymelody.mymelodyserver.domain.MyMelody.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.Comment.entity.Comment;
import mymelody.mymelodyserver.domain.Comment.repository.CommentRepository;
import mymelody.mymelodyserver.domain.Likes.entity.Likes;
import mymelody.mymelodyserver.domain.Likes.repository.LikesRepository;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import mymelody.mymelodyserver.domain.Member.repository.MemberRepository;
import mymelody.mymelodyserver.domain.MyMelody.dto.response.GetMyMelodies;
import mymelody.mymelodyserver.domain.MyMelody.dto.response.MyMelodyInfo;
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
public class MyMelodyService {

    private static final Boolean IS_LIKED = true;
    private static final Boolean IS_NOT_LIKED = false;

    private final MyMelodyRepository myMelodyRepository;
    private final MemberRepository memberRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;

    public GetMyMelodies getMyMelodiesByLocationWithPagination(double latitude, double longitude,
            PageRequest pageRequest, Long memberId) {
        Pageable pageable = pageRequest.of();
        Page<MyMelody> myMelodies = myMelodyRepository.findAllByRange1km(latitude, longitude, pageable);

        List<MyMelodyInfo> myMelodyInfos = new ArrayList<>();
        if (memberId != 0) {
            Member member = memberRepository.findById(memberId).orElseThrow(
                    () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

            myMelodies.getContent().forEach(myMelody -> myMelodyInfos.add(MyMelodyInfo.of(myMelody,
                        likesRepository.existsByMyMelodyAndMember(myMelody, member))));
        } else {
            myMelodies.getContent().forEach(myMelody -> myMelodyInfos.add(MyMelodyInfo.of(myMelody, IS_NOT_LIKED)));
        }

        return GetMyMelodies.of(myMelodies.getTotalPages(), myMelodies.getTotalElements(),
                myMelodyInfos);
    }

    public GetMyMelodies getMyMelodiesByLikesWithPagination(PageRequest pageRequest, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Pageable pageable = pageRequest.of();
        Page<Likes> likes = likesRepository.findAllByMember(member, pageable);

        List<MyMelodyInfo> myMelodyInfos = likes.getContent().stream().map(like ->
                MyMelodyInfo.of(like.getMyMelody(), IS_LIKED)).toList();

        return GetMyMelodies.of(likes.getTotalPages(), likes.getTotalElements(), myMelodyInfos);
    }

    public GetMyMelodies getMyMelodiesByCommentWithPagination(PageRequest pageRequest, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Pageable pageable = pageRequest.of();
        Page<Comment> comments = commentRepository.findAllByMember(member, pageable);

        List<MyMelodyInfo> myMelodyInfos = comments.getContent().stream().map(comment ->
                        MyMelodyInfo.of(comment.getMyMelody(),
                                likesRepository.existsByMyMelodyAndMember(comment.getMyMelody(), member)))
                .toList();

        return GetMyMelodies.of(comments.getTotalPages(), comments.getTotalElements(), myMelodyInfos);
    }
}
