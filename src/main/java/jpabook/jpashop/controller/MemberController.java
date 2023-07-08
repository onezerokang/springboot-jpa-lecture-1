package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        // @Valid 뒤에 BindingResult가 있으면 에러가 있어도 에러가 result에 담기고 코드가 실행된다.
        // Form 객체 vs Entity: Entity를 Form처럼 사용하면 화면을 처리하기 위한 기능이 계속 추가되면서 처리하기 어려워진다.
        // 화면에 맞는 API는 Form 객체나 DTO를 사용해야 한다.
        // 화면을 고쳤는데 핵심 비즈니스 로직이 안맞을 수 있다.
        // !!!!!!!!!API를 만들 때는 절대 Entity를 반환해서는 안된다.!!!!!
        // Entity는 스펙이다. entity가 변경되면 return 값도 변해버린다.
        // 그리고 외부에 노출이 된다.
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "/members/memberList";
    }
}
