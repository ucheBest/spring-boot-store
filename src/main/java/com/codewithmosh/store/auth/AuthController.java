package com.codewithmosh.store.auth;

import com.codewithmosh.store.users.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(
        @Valid @RequestBody LoginUserRequest request,
        HttpServletResponse response
    ) {
        var authResponse = authService.loginUser(request);
        response.addCookie(authResponse.cookie());

        return ResponseEntity.ok(new JwtResponse(authResponse.accessToken()));
    }

    @GetMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(
        @CookieValue(name = "refreshToken") String token
    ) {
        var authResponse = authService.refreshToken(token);

        return ResponseEntity.ok(new JwtResponse(authResponse.accessToken()));
    }

    @GetMapping("/me")
    public UserDto me() {
        return authService.me();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
