package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.question.QuestionService;

@SpringBootTest
class SbbApplicationTests {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
    private QuestionService questionService;
	
	/*
	 @Test
	 void test_create() {
	 	//Question q1 = new Question();
		//q1.setSubject("sbb가 무엇인가요?");
		//q1.setContent("sbb에 대해서 알고 싶습니다.");
		//q1.setCreateDate(LocalDateTime.now());
		//this.questionRepository.save(q1); // 첫번째 질문 저장
		
		//Question q2 = new Question();
		//q2.setSubject("스프링부트 모델 질문입니다.");
		//q2.setContent("id는 자동으로 생성되나요?");
		//q2.setCreateDate(LocalDateTime.now());
		//this.questionRepository.save(q2); // 두번째 질문 저장
	 }
	@Test
	void testJpa() {
	
		 List<Question> all = this.questionRepository.findAll();
		 assertEquals(2, all.size()); // findAll는 SQL: SELECT * 와 같음
		 
		 Question q = all.get(0); // 0번째 인걸 가져옴
		 assertEquals("sbb가 무엇인가요?", q.getSubject());		 
	}
	
	@Test
	void testJpa2() {
		 
		 Optional<Question> oq = this.questionRepository.findById(1);
		 if(oq.isPresent()) { // Optional은 null값을 유연하게 처리하기 위한 클래스
			 Question q = oq.get();
			 assertEquals("sbb가 무엇인가요?", q.getSubject());
		 }
	}
	
	@Test
	void testJpa3() {
	
		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, q.getId());
	}
	
	@Test
	void testJpa4() {
	
		Question q = this.questionRepository.findBySubjectAndContent(
				"sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(1, q.getId());
	}
	
	@Test
	void testJpa5() {
	
		List<Question> qList = this.questionRepository.findBySubjectLike(
				"sbb%"); // sbb로 시작하는 문자열
		Question q = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}
	*/
	/*
	@Test
	void test_modify() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent()); // 참인지를 테스트
		Question q = oq.get();
		q.setSubject("수정된 제목");
		this.questionRepository.save(q);
	}
	*/
	/*
	@Test
	void test_delete() {
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent()); // 참인지를 테스트
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}
	*/
	/*
	@Test
	void test_answer_create() {

		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent()); // 참인지를 테스트
		Question q = oq.get();
		
		Answer a = new Answer();
		a.setContent("네 자동으로 생성합니다.");
		a.setQuestion(q); // 어떤 질문인가?
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}
	*/
	/*
	@Transactional // 트랜잭션을 테스트 함수에 적용하면 DB 세션 유지
	@Test
	void test_answer() {

		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent()); // 참인지를 테스트
		
		Answer a = oa.get();
		a.setContent("네 자동으로 생성합니다.");
		assertEquals(2, a.getQuestion().getId());
		
		////////////////////////////////////////////////////////////////////
		
		Optional<Question> oq = this.questionRepository.findById(2);
		// 그냥 하면 DB 세션이 끊김(q객체를 조회할 때가 아니라 
		// getAnswerList 메서드를 호출하는 시점에 가져오기 때문: Lazy)
		// DB 세션 문제는 테스트 코드에서만 발생한다.
		assertTrue(oa.isPresent());
		Question q = oq.get();
		
		List<Answer> answerList = q.getAnswerList();
		
		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성합니다.", answerList.get(0).getContent());
	}
	*/
	
	/*
    @Test
    void test_mock_data_create() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용무";
            this.questionService.create(subject, content, null);
        }
    }
    */
}
