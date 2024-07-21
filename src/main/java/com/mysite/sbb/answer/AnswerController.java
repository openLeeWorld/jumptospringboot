package com.mysite.sbb.answer;

import java.security.Principal; // 현재 로그인한 사용자의 정보를 알려면 Principal 객체를 사용

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
	
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;
	
	@PreAuthorize("isAuthenticated()") // 로그인 한 경우에만 실행
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Integer id,
			@Valid AnswerForm answerForm, BindingResult bindingResult,
			Principal principal) {
		
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		// principal 객체를 통해 로그인한 사용자명을 얻고 답변을 등록시 사용
		if (bindingResult.hasErrors()) {
			model.addAttribute("question", question);
			return "question_detail";
		} // 검증 실패 시 다시 답변 등록
		
		Answer answer = 
				this.answerService.create(question, answerForm.getContent(), siteUser); 
		//답변을 저장한다.
		return String.format("redirect:/question/detail/%s#answer_%s", 
				answer.getQuestion().getId(), answer.getId());
	} // 검증 성공 시 답변 등록
	
	@PreAuthorize("isAuthenticated()") // 로그아웃 상태면 로그인 페이지로 강제 이동
	@GetMapping("/modify/{id}")  
	public String answerModify(AnswerForm answerForm,
			 @PathVariable("id") Integer id, Principal principal) {
		
		Answer answer = this.answerService.getAnswer(id);
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		answerForm.setContent(answer.getContent()); // 화면 채워놓기
		return "answer_form";
	} 
	
	@PreAuthorize("isAuthenticated()") // 로그아웃 상태면 로그인 페이지로 강제 이동
	@PostMapping("/modify/{id}")  
	public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
			@PathVariable("id") Integer id, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			return "answer_form";
		}
		
		Answer answer = this.answerService.getAnswer(id);
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		this.answerService.modify(answer, answerForm.getContent());
		return String.format("redirect:/question/detail/%s#answer_%s", 
				answer.getQuestion().getId(), answer.getId());
	} 
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.answerService.delete(answer);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.answerService.vote(answer, siteUser);
        return String.format("redirect:/question/detail/%s#answer_%s", 
				answer.getQuestion().getId(), answer.getId());
    }
}


