package tutoring.Project.member.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tutoring.Project.member.entity.Address;
import tutoring.Project.member.entity.Member;
import tutoring.Project.member.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/")
    public List<Member> index() {

        return memberService.findAll();
    }

    @PostMapping("/member")
    public void save(
        @RequestBody String name, String nickname, String email, String password,
        String phone, LocalDate birth, Boolean isTest,
        String city, String street, String zipCode
    ) {
        Address address = new Address(city, street, zipCode);

        Member member = new Member();
        member.setName(name);
        member.setNickname(nickname);
        member.setEmail(email);
        member.setPassword(password);
        member.setPhone(phone);
        member.setBirth(birth);
        member.setIsTest(isTest);
        member.setAddress(address);

        memberService.save(member);
    }
}
