package mymelody.mymelodyserver.global.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.global.auth.dto.response.TokenDto;
import mymelody.mymelodyserver.global.auth.jwt.JwtTokenProvider;
import mymelody.mymelodyserver.global.auth.security.CustomUserDetails;
import mymelody.mymelodyserver.global.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/login")
    public ResponseEntity<TokenDto> signIn(@RequestParam("code") String code) {
        return authService.signIn(code);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        return authService.logout(token);
    }

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userDetails.getUsername();
    }
}
