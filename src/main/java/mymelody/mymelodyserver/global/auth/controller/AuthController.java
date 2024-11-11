package mymelody.mymelodyserver.global.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mymelody.mymelodyserver.global.auth.dto.response.TokenDto;
import mymelody.mymelodyserver.global.auth.jwt.JwtTokenProvider;
import mymelody.mymelodyserver.global.auth.security.CustomUserDetails;
import mymelody.mymelodyserver.global.auth.service.AuthService;
import mymelody.mymelodyserver.global.entity.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "로그인 API")
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "로그인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인/회원가입 성공, accessToken/refreshToken 발행",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "유효한 코드가 아닙니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "스포티파이 계정 정보를 찾을 수 없습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestParam("code") String code) {
        return authService.signIn(code);
    }

    @Operation(summary = "로그아웃")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "404", description = "해당 사용자를 찾을 수 없습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        log.info("[AuthController] logout");
        String token = jwtTokenProvider.resolveToken(request);
        return authService.logout(token);
    }

    @Operation(summary = "테스트용 - 토큰에 담긴 사용자 id 반환")
    @GetMapping("/test")
    public String test(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userDetails.getUsername();
    }
}
