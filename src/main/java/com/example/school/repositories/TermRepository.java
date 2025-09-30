package com.example.school.repositories;

import com.example.school.models.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {
	
	
}
