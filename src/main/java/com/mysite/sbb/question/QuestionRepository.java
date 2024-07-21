package com.mysite.sbb.question;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/*
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
// @Query 에노테이션으로 JPA 대신 직접 쿼리를 작성할 때
 */

public interface QuestionRepository extends JpaRepository<Question, Integer>{
	Question findBySubject(String subject); // 메서드 인터페이스 정의
	Question findBySubjectAndContent(String subject, String content); // and
	// 인터페이스 정의시 JPA에서 findBy뒤의 이름으로 쿼리 생성해줌
	List<Question> findBySubjectLike(String subject); // sql like
	Page<Question> findAll(Pageable pageable); // page단위로 질문 가져오기
	Page<Question> findAll(Specification<Question> spec, Pageable pageable); // 검색용 specification 객체 활용
	/*
	 @Query("select "
            + "distinct q "
            + "from Question q " 
            + "left outer join SiteUser u1 on q.author=u1 "
            + "left outer join Answer a on a.question=q "
            + "left outer join SiteUser u2 on a.author=u2 "
            + "where "
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or u1.username like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.username like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
	 // @Query를 직접 적용, @Param("kw")는 @Query에 :kw로 전달됨
	 */
}
