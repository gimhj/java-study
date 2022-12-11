package tutoring.Project.auth.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import tutoring.Project.auth.token.AuthenticationToken;
import tutoring.Project.member.entity.Member;
import tutoring.Project.member.service.MemberService;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthFilter extends OncePerRequestFilter {

    private final MemberService memberService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String token = getToken(request);
        if (token != null) {
            byte[] decodeToken = Base64.getDecoder().decode(token);
            String email = new String(decodeToken);
            Optional<Member> member = memberService.findByEmail(email);

            if (member.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            SecurityContextHolder.getContext().setAuthentication(new AuthenticationToken(
                member.get(),
                null,
                null
            ));
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
