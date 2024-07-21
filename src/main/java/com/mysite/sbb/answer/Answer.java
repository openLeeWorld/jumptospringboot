package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mysite.sbb.comment.Comment;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne; //질문1개 당 답변 여러개
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
	@Id // id 기본키 지정
	@GeneratedValue(strategy=GenerationType.IDENTITY) // 고유한 번호 생성, 자동 1씩 증가
	private Integer id;
	
	@Column(columnDefinition="TEXT") // 열 데이터에 텍스트를 넣을 수 있음
	private String content;
	
	private LocalDateTime createDate; // @Column을 사용 안해도 테이블 열로 인식
	
	@ManyToOne // 부모(질문): 자식(답변)
	private Question question;
	
	@ManyToOne
	private SiteUser author;
	
	private LocalDateTime modifyDate;
	
	@ManyToMany
	Set<SiteUser> voter; // Set은 추천인이 중복되지 않도록 함
	// 자동으로 AnswerVoter라는 매개 테이블 생성
	
	@OneToMany(mappedBy = "answer")
	private List<Comment> commentList;
}
