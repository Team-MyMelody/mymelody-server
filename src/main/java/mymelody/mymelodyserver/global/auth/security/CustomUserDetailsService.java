package mymelody.mymelodyserver.global.auth.security;

import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import mymelody.mymelodyserver.domain.Member.repository.MemberRepository;
import mymelody.mymelodyserver.global.entity.ErrorCode;
import mymelody.mymelodyserver.global.exception.CustomException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String spotifyId) throws UsernameNotFoundException {
        Member member = memberRepository.findBySpotifyId(spotifyId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return new CustomUserDetails(member.getId(), Collections.singleton(new SimpleGrantedAuthority("USER")));
    }
}
