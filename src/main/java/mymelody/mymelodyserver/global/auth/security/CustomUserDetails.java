package mymelody.mymelodyserver.global.auth.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final Long memberId;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public CustomUserDetails(Member member, Collection<GrantedAuthority> authorities) {
        this.memberId = member.getId();
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return memberId.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        //계정이 만료됐는지 리턴 -> true는 완료되지 않음 의미
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //계정이 잠겨있는지 리턴 -> true는 잠기지 않음
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //비밀번호가 만료됐는지 리턴 -> true는 만료X 의미
        return true;
    }

    @Override
    public boolean isEnabled() {
        //계정이 활성화돼 있는지 리턴 -> true는 활성화 상태 의미
        return true;
    }
}
