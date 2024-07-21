package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.comment.Comment;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
	@Id // id 기본키 지정
	@GeneratedValue(strategy=GenerationType.IDENTITY) // 고유한 번호 생성, 자동 1씩 증가
	private Integer id;
	
	@Column(length=200)
	private String subject;
	
	@Column(columnDefinition="TEXT") // 열 데이터에 텍스트를 넣을 수 있음
	private String content;
	
	private LocalDateTime createDate; // @Column을 사용 안해도 테이블 열로 인식
	//@Transient를 사용하면 테이블 열로 인식 안함(클래스의 속성 기능)
	// createDate(camel case)는 DB에서 create_date(underbar case)로 저장됨
	// 일반적으로는 setter를 허용하지 않고 메서드를 추가로 작성함
	
	@OneToMany(mappedBy="question", cascade=CascadeType.REMOVE)
	private List<Answer> answerList;
	// question 속성과 매핑됨
	// 질문을 삭제하면 DB에서 연관된 답변도 다 삭제됨
	
	@ManyToOne
	private SiteUser author; // 여러 개의 질문 당 한 명의 저자
	
	private LocalDateTime modifyDate;
	
	@ManyToMany
	Set<SiteUser> voter; // Set은 추천인이 중복되지 않도록 함
	// 자동으로 QuestionVoter라는 매개 테이블 생성
	
	@OneToMany(mappedBy = "question")
	private List<Comment> commentList;
}
