package tutoring.Project.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import tutoring.Project.auth.token.AuthenticationToken;
import tutoring.Project.member.entity.Member;

@Slf4j
public class SingUpProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String X_REQUESTED_WITH = "X-Requested-With";

    private ObjectMapper objectMapper = new ObjectMapper();

    public SingUpProcessingFilter() {
        super(new AntPathRequestMatcher("/api/members/signIn", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request,
        HttpServletResponse response
    )
        throws AuthenticationException, IOException, ServletException {

        if (!isAjax(request)) {
            throw new IllegalStateException("Authentication is not supported");
        }

        Member member = objectMapper.readValue(request.getReader(), Member.class);

        if (StringUtils.isEmpty(member.getEmail()) || StringUtils.isEmpty(member.getPassword())) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }
        AuthenticationToken token = new AuthenticationToken(
            member.getEmail(),
            member.getPassword()
        );

        return this.getAuthenticationManager().authenticate(token);
    }

    private boolean isAjax(HttpServletRequest request) {

        if (XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH))) {
            return true;
        }

        return false;
    }
}
