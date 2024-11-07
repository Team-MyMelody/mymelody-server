package mymelody.mymelodyserver.domain.MyMelody.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.Likes.repository.LikesRepository;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import mymelody.mymelodyserver.domain.Member.repository.MemberRepository;
import mymelody.mymelodyserver.domain.MyMelody.dto.response.GetMyMelodiesByLocation;
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

    private final MyMelodyRepository myMelodyRepository;
    private final MemberRepository memberRepository;
    private final LikesRepository likesRepository;

    public GetMyMelodiesByLocation getMyMelodiesByLocationWithPagination(double latitude, double longitude,
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
            myMelodies.getContent().forEach(myMelody -> myMelodyInfos.add(MyMelodyInfo.of(myMelody, false)));
        }

        return GetMyMelodiesByLocation.of(myMelodies.getTotalPages(), myMelodies.getTotalElements(),
                myMelodyInfos);
    }
}
