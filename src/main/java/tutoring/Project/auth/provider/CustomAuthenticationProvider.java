package tutoring.Project.auth.provider;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tutoring.Project.auth.exeption.DeletedMemberException;
import tutoring.Project.auth.exeption.ExpiredMemberException;
import tutoring.Project.auth.service.MemberContext;
import tutoring.Project.auth.token.AuthenticationToken;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        MemberContext memberContext = (MemberContext) userDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(password, memberContext.getMember().getPassword())) {
            throw new BadCredentialsException("인증 정보가 올바르지 않습니다.");
        }

        if (memberContext.getMember().getDeletedAt() != null) {
            throw new DeletedMemberException("탈퇴된 계정입니다.");
        }

        LocalDateTime expiredDate = memberContext.getMember().getLoginAt().plusYears(1);
        if (LocalDateTime.now().isAfter(expiredDate)) {
            throw new ExpiredMemberException("휴면 계정입니다.");
        }

        AuthenticationToken authenticationToken =
            new AuthenticationToken(
                memberContext.getMember(),
                null,
                memberContext.getAuthorities()
            );

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AuthenticationToken.class);
    }

}
