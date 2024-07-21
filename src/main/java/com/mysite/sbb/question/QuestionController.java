package com.mysite.sbb.question;
import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question") // URL prefix
@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionService questionService;
	// 생성자 애너테이션 방식으로 questionRepository 객체를 주입한다.
	// final이 붙은 속성을 포함하는 생성자를 자동으로 만들어주는 역할
	private final UserService userService;
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw) { 
		// 매개변수로 Model을 지정하면 객체가 스프링부트에 의해 자동으로 생성됨
		// Model 객체에 값을 담아두면 템플릿에서 그 값을 사용
		// 검색어가 입력되지 않을 경우 kw이 null이 되는 것을 방지하기 위해 빈 문자열이 기본
		Page<Question> paging = this.questionService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		return "question_list"; // resource/templates/question_list.html참조
 	}
	
	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	
	@PreAuthorize("isAuthenticated()") // 로그인 한 경우에만 실행
	@GetMapping("/create") 
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated()") // 로그아웃 상태면 로그인 페이지로 강제 이동
	@PostMapping("/create")  // method overloading
	public String questionCreate(@Valid QuestionForm questionForm,
			BindingResult bindingResult, Principal principal) {
		// @Valid 하면 검증 기능이 동작한다, BindingResult는 항상 @Valid 뒤에 
		if (bindingResult.hasErrors()) {
			return "question_form";
		} // 검증 실패 시 질문 작성 화면으로 돌아감
		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		// 현재 로그인 사용자 이름(스프링 시큐리티)을 통해 이름을 가져옴 
		this.questionService.create(questionForm.getSubject(),
			questionForm.getContent(), siteUser); // 질문을 저장한다.
		return "redirect:/question/list"; // 질문 저장 후 질문 목록으로 이동
	} // 검증 성공 시 질문 등록
	
	
	@PreAuthorize("isAuthenticated()") // 로그아웃 상태면 로그인 페이지로 강제 이동
	@GetMapping("/modify/{id}")  
	public String questionModify(QuestionForm questionForm,
			 @PathVariable("id") Integer id, Principal principal) {
		
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent()); // 화면 채워놓기
		return "question_form";
	} 
	
	@PreAuthorize("isAuthenticated()") // 로그아웃 상태면 로그인 페이지로 강제 이동
	@PostMapping("/modify/{id}")  
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
			Principal principal, @PathVariable("id") Integer id) {
		
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		this.questionService.modify(question, questionForm.getSubject(), 
				questionForm.getContent());
		return String.format("redirect:/question/detail/%s", id);
	} 
	
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
}
