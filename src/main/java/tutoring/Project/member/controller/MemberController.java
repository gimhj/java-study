package tutoring.Project.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tutoring.Project.member.entity.Address;
import tutoring.Project.member.entity.Member;
import tutoring.Project.member.entity.MemberGrade;
import tutoring.Project.member.entity.MemberResponseDto;
import tutoring.Project.member.entity.SignInDto;
import tutoring.Project.member.entity.SignUpDto;
import tutoring.Project.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @GetMapping("")
    @Operation(summary = "전체조회")
    public List<MemberResponseDto> index() {
        List<Member> members = memberService.findAll();

        List<MemberResponseDto> result = members.stream()
            .map(member -> modelMapper.map(member, MemberResponseDto.class))
            .collect(Collectors.toList());

        return result;
    }

    @PostMapping("")
    @Operation(summary = "회원가입")
    public MemberResponseDto store(@Valid @RequestBody SignUpDto signUpDTO) {

        Address address = new Address(signUpDTO.getCity(), signUpDTO.getStreet(),
            signUpDTO.getZipcode()
        );
        String password = passwordEncoder.encode(signUpDTO.getPassword());

        Member member = new Member();
        member.setName(signUpDTO.getName());
        member.setNickname(signUpDTO.getNickname());
        member.setEmail(signUpDTO.getEmail());
        member.setPassword(password);
        member.setPhone(signUpDTO.getPhone());
        member.setBirth(signUpDTO.getBirth());
        member.setIsTest(false);
        member.setGrade(MemberGrade.BRONZE);
        member.setAddress(address);

        memberService.signUp(member);

        return modelMapper.map(member, MemberResponseDto.class);
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "회원 단일 조회")
    public MemberResponseDto show(@PathVariable("memberId") Long memberId) {
        Member member = memberService.findOne(memberId);

        return modelMapper.map(member, MemberResponseDto.class);
    }

    @PutMapping("/{memberId}/destroy")
    @Operation(summary = "회원 탈퇴")
    public ResponseEntity<Object> destroy(@PathVariable("memberId") Long memberId) {

        memberService.updateDeletedAt(memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/signIn")
    @Operation(summary = "로그인")
    public ResponseEntity<Object> signIn(@Valid @RequestBody SignInDto signInDTO) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/signOut")
    @Operation(summary = "로그아웃")
    public int signOut(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return HttpStatus.OK.value();
    }
}
