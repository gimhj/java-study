package tutoring.Project.auth.service;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tutoring.Project.member.entity.Member;
import tutoring.Project.member.repository.MemberRepository;

@Service("userDetailsService")
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final Set<GrantedAuthority> authorities = new HashSet<>();

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmailForAuth(email);

        if (member == null) {
            throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
        }

        MemberContext memberContext = new MemberContext(member, authorities);

        return memberContext;
    }
}
