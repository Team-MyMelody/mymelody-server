package mymelody.mymelodyserver.global.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import mymelody.mymelodyserver.domain.Member.entity.Role;
import mymelody.mymelodyserver.domain.Member.repository.MemberRepository;
import mymelody.mymelodyserver.global.auth.dto.response.TokenDto;
import mymelody.mymelodyserver.global.auth.jwt.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.michaelthelin.spotify.model_objects.specification.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    private final SpotifyService spotifyService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<TokenDto> signIn(String code) {
        String accessToken = spotifyService.getAccessToken(code);
        User spotifyProfile = spotifyService.getSpotifyProfile(accessToken);
        Optional<Member> optionalMember = memberRepository.findBySpotifyId(spotifyProfile.getId());
        if (memberRepository.findBySpotifyId(spotifyProfile.getId()).isPresent()) {
            log.info("[AuthService] signIn");
            Member member = optionalMember.get();
            // 스포티파이 로직에서 spotifyAccessToken 계속 필요하다면 저장 로직 추가
            return ResponseEntity.ok(TokenDto.of(
                    jwtTokenProvider.createAccessToken(member.getSpotifyId(), member.getRole().toString()),
                    jwtTokenProvider.createRefreshToken(member.getSpotifyId(), member.getRole().toString())
            ));
        } else {
            log.info("[AuthService] signUp");
            return signUp(accessToken, spotifyProfile);
        }
    }

    private ResponseEntity<TokenDto> signUp(String accessToken, User spotifyProfile) {
        Member member = Member.builder()
                .spotifyId(spotifyProfile.getId())
                .nickname(spotifyProfile.getDisplayName())
//                .spotifyAccessToken(accessToken)
                .role(Role.USER)
                .build();
        memberRepository.save(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(TokenDto.of(
                jwtTokenProvider.createAccessToken(member.getSpotifyId(), member.getRole().toString()),
                jwtTokenProvider.createRefreshToken(member.getSpotifyId(), member.getRole().toString())
        ));
    }
}
