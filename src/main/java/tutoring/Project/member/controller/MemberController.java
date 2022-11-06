package tutoring.Project.member.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tutoring.Project.member.entity.Address;
import tutoring.Project.member.entity.Member;
import tutoring.Project.member.entity.MemberDTO;
import tutoring.Project.member.service.MemberService;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("")
    public String index(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "members/create";
    }

    @PostMapping("")
    public String save(@Valid MemberDTO memberDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "members/create";
        }

        Address address = new Address(memberDTO.getCity(), memberDTO.getStreet(), memberDTO.getZipcode());

        Member member = new Member();
        member.setName(memberDTO.getName());
        member.setNickname(memberDTO.getNickname());
        member.setEmail(memberDTO.getEmail());
        member.setPassword(memberDTO.getPassword());
        member.setPhone(memberDTO.getPhone());
        member.setBirth(memberDTO.getBirth());
        member.setAddress(address);

        memberService.save(member);
        return "redirect:/";
    }
}
