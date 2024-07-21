package com.mysite.sbb.comment;

import java.time.LocalDateTime;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne; //질문1개 당 답변 여러개
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private SiteUser author; // 해당 유저가 쓴 댓글들
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	
	private LocalDateTime modifyDate;
	
	@ManyToOne
	private Question question; // 해당 질문에 딸린 댓글들
	
	@ManyToOne
	private Answer answer; // 해당 답변에 딸린 댓글들
	
	// 댓글을 수정하거나 삭제한 후에 질문 상세 페이지로 리다이렉트하기 위해 
	// 댓글을 통해 질문의 id를 알아내기
	public Integer getQuestionId() {
		Integer result = null;
		if (this.question != null) {
			result = this.question.getId();
		} else if (this.answer != null) {
			result = this.answer.getQuestion().getId();
		} 
		return result;
	}
}
