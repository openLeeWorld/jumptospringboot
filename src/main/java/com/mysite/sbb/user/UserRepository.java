package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long>{
// SiteUser의 기본키 타입이 Long
	Optional<SiteUser> findByusername(String username);
}
