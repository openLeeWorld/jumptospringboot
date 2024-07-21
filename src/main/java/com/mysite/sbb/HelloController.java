package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 컨트롤러로 작용
public class HelloController {
	@GetMapping("/hello") // URL 요청ㅇ
	@ResponseBody // 
	public String hello() {
			return "Hello Spring Boot Board";
	}
}

