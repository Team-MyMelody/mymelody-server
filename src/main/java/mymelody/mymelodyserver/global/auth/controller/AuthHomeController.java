package mymelody.mymelodyserver.global.auth.controller;

import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.global.auth.dto.response.TokenDto;
import mymelody.mymelodyserver.global.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthHomeController {
    private final AuthService authService;

    @RequestMapping(value = "/")
    public String index() {
        System.out.println("index.html 호출");
        return "index";
    }

    @RequestMapping(value = "/api/v1/auth/login")
    public ResponseEntity<TokenDto> login(@RequestParam("code") String code) {
        return authService.signIn(code);
    }
}
