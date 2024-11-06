package mymelody.mymelodyserver.domain.Likes.service;

import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.Likes.entity.Likes;
import mymelody.mymelodyserver.domain.Likes.repository.LikesRepository;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import mymelody.mymelodyserver.domain.Member.repository.MemberRepository;
import mymelody.mymelodyserver.domain.MyMelody.entity.MyMelody;
import mymelody.mymelodyserver.domain.MyMelody.repository.MyMelodyRepository;
import mymelody.mymelodyserver.global.entity.ErrorCode;
import mymelody.mymelodyserver.global.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesService {

    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final MyMelodyRepository myMelodyRepository;

    @Transactional
    public void createLikes(Long myMelodyId, Long memberId) {
        MyMelody myMelody = myMelodyRepository.findById(myMelodyId).orElseThrow(
                () -> new CustomException(ErrorCode.MYMELODY_NOT_FOUND));
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        likesRepository.save(Likes.builder()
                .myMelody(myMelody)
                .member(member)
                .build());
        myMelody.increaseTotalLikes();
    }

    @Transactional
    public void deleteLikes(Long myMelodyId, Long memberId) {
        MyMelody myMelody = myMelodyRepository.findById(myMelodyId).orElseThrow(
                () -> new CustomException(ErrorCode.MYMELODY_NOT_FOUND));
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Likes likes = likesRepository.findByMyMelodyAndMember(myMelody, member).orElseThrow(
                () -> new CustomException(ErrorCode.LIKES_NOT_FOUND));

        likesRepository.delete(likes);
        myMelody.decreaseTotalLikes();
    }
}
