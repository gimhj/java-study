package tutoring.Project.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tutoring.Project.member.entity.Address;
import tutoring.Project.member.entity.Member;
import tutoring.Project.member.entity.MemberGrade;
import tutoring.Project.member.entity.SignUpDTO;
import tutoring.Project.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("")
    @Operation(summary = "회원가입")
    public Member store(
        @Valid @RequestBody SignUpDTO signUpDTO
    ) {

        Address address = new Address(signUpDTO.getCity(), signUpDTO.getStreet(),
            signUpDTO.getZipcode());

        Member member = new Member();
        member.setName(signUpDTO.getName());
        member.setNickname(signUpDTO.getNickname());
        member.setEmail(signUpDTO.getEmail());
        member.setPassword(signUpDTO.getPassword());
        member.setPhone(signUpDTO.getPhone());
        member.setBirth(signUpDTO.getBirth());
        member.setGrade(MemberGrade.BRONZE);
        member.setAddress(address);

        memberService.signUp(member);

        return member;
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "회원 단일 조회")
    public Member show(
        @PathVariable("memberId") Long memberId
    ) {

        return memberService.findOne(memberId);
    }
}
