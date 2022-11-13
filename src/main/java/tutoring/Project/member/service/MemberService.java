package tutoring.Project.member.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tutoring.Project.member.entity.Member;
import tutoring.Project.member.exception.AlreadyExistMemberException;
import tutoring.Project.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member signUp(Member member) {

        validateDuplicateMember(member);

        memberRepository.save(member);
        return member;
    }

    private void validateDuplicateMember(Member member) {

        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());

        if (!findMember.isEmpty()) {
            throw new AlreadyExistMemberException("이미 존재하는 회원입니다.");
        }
    }
}
