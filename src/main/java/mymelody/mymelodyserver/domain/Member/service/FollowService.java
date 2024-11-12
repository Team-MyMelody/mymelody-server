package mymelody.mymelodyserver.domain.Member.service;

import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.Member.dto.response.FollowInfo;
import mymelody.mymelodyserver.domain.Member.dto.response.GetFollowPage;
import mymelody.mymelodyserver.domain.Member.entity.Follow;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import mymelody.mymelodyserver.domain.Member.repository.FollowRepository;
import mymelody.mymelodyserver.domain.Member.repository.MemberRepository;
import mymelody.mymelodyserver.global.common.PageRequest;
import mymelody.mymelodyserver.global.entity.ErrorCode;
import mymelody.mymelodyserver.global.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    @Transactional
    public ResponseEntity<?> follow(Long followingId, Long memberId) {
        Member follower = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Member followingMember = memberRepository.findById(followingId)
                .orElseThrow(() -> new CustomException(ErrorCode.FOLLOWING_MEMBER_NOT_FOUND));

        followRepository.findByFollowerAndFollowing(follower, followingMember)
                .ifPresentOrElse(
                        followRepository::delete,
                        () -> followRepository.save(Follow.builder().follower(follower).following(followingMember).build())
                );

        return ResponseEntity.status(HttpStatus.OK).build();
    }


    public GetFollowPage getFollowerPage(Long memberId, PageRequest pageRequest) {
        Member following = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Pageable pageable = pageRequest.of();
        Page<FollowInfo> followerInfoPage = followRepository.getFollowerInfoPage(following, pageable);

        return GetFollowPage.from(followerInfoPage);
    }


    public GetFollowPage getFollowingPage(Long memberId, PageRequest pageRequest) {
        Member follower = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Pageable pageable = pageRequest.of();
        Page<FollowInfo> followingInfoPage = followRepository.getFollowingInfoPage(follower, pageable);

        return GetFollowPage.from(followingInfoPage);
    }
}
