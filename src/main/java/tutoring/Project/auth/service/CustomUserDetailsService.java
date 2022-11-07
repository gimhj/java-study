package tutoring.Project.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tutoring.Project.member.entity.Member;
import tutoring.Project.member.repository.MemberRepository;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException("UserNotFoundException");
        }

        List<GrantedAuthority> types = new ArrayList<>();
        types.add(new SimpleGrantedAuthority(member.getType()));

        MemberContext memberContext = new MemberContext(member, types);

        return memberContext;
    }
}
