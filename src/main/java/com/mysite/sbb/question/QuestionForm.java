package com.mysite.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
	@NotEmpty(message="제목은 필수항목입니다.")
	@Size(max=200) // 200byte를 넘으면 안됨
	private String subject;
	
	@NotEmpty(message="내용은 필수항목입니다.")
	private String content; 
	// 바인딩: 템플릿의 입력 항목과 폼 클래스의 속성들이 매핑
}
