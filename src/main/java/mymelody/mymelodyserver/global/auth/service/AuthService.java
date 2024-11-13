package mymelody.mymelodyserver.global.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import mymelody.mymelodyserver.domain.Member.entity.Role;
import mymelody.mymelodyserver.domain.Member.repository.MemberRepository;
import mymelody.mymelodyserver.global.auth.dto.response.TokenDto;
import mymelody.mymelodyserver.global.auth.jwt.JwtTokenProvider;
import mymelody.mymelodyserver.global.entity.ErrorCode;
import mymelody.mymelodyserver.global.exception.CustomException;
import mymelody.mymelodyserver.global.redis.RedisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
    private final RedisService redisService;

    public ResponseEntity<TokenDto> signIn(String code) {
        String accessToken = spotifyService.getAccessToken(code);
        User spotifyProfile = spotifyService.getSpotifyProfile(accessToken);

        if (!StringUtils.hasText(spotifyProfile.getId())) {
            throw new CustomException(ErrorCode.SPOTIFY_PROFILE_NOT_FOUND);
        }

        Optional<Member> optionalMember = memberRepository.findBySpotifyId(spotifyProfile.getId());

        if (optionalMember.isPresent()) {
            log.info("[AuthService] signIn");
            Member member = optionalMember.get();

            String refreshToken = jwtTokenProvider.createRefreshToken(member.getSpotifyId(), member.getRole().toString());
            member.setRefreshToken(refreshToken);

            return ResponseEntity.ok(TokenDto.of(
                    jwtTokenProvider.createAccessToken(member.getSpotifyId(), member.getRole().toString()),
                    refreshToken
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
                .role(Role.USER)
                .build();
        memberRepository.save(member);
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getSpotifyId(), member.getRole().toString());
        member.setRefreshToken(refreshToken);

        return ResponseEntity.status(HttpStatus.CREATED).body(TokenDto.of(
                jwtTokenProvider.createAccessToken(member.getSpotifyId(), member.getRole().toString()),
                refreshToken
        ));
    }

    public ResponseEntity<Void> logout(String token) {
        // 토큰의 유효 시간 가져와서 블랙리스트 등록
        Long expirationTime = jwtTokenProvider.getExpiration(token);
        redisService.setBlackList(token, "logout", expirationTime / 1000);

        Member member = memberRepository.findBySpotifyId(jwtTokenProvider.getSpotifyId(token))
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        redisService.setBlackList(member.getRefreshToken(), "logout", jwtTokenProvider.getExpiration(member.getRefreshToken()) / 1000);

        log.info("[AuthService] logout");
        return ResponseEntity.ok().build();
    }

    public String test(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return member.getNickname();
    }
}
