package tutoring.Project.member.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tutoring.Project.member.entity.Address;
import tutoring.Project.member.entity.Member;
import tutoring.Project.member.entity.MemberDTO;
import tutoring.Project.member.entity.LoginDTO;
import tutoring.Project.member.service.MemberService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/index";
    }

    @GetMapping("/members/create")
    public String create(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "members/create";
    }

    @PostMapping("/members/store")
    public String save(@Valid MemberDTO memberDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "members/create";
        }

        Address address = new Address(memberDTO.getCity(), memberDTO.getStreet(), memberDTO.getZipcode());

        Member member = new Member();
        member.setName(memberDTO.getName());
        member.setNickname(memberDTO.getNickname());
        member.setEmail(memberDTO.getEmail());
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        member.setPassword(memberDTO.getPassword());
        member.setPhone(memberDTO.getPhone());
        member.setBirth(memberDTO.getBirth());
        member.setAddress(address);
        member.setType(memberDTO.getType());

        memberService.save(member);
        return "redirect:/";
    }

    @GetMapping("/members/login")
    public String login(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "members/login";
    }

    @GetMapping("/members/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }
}
