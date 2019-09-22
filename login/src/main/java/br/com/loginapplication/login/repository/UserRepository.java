package br.com.loginapplication.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.loginapplication.login.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
}
