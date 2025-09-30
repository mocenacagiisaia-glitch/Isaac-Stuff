package com.example.school.repositories;

import com.example.school.models.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Integer> {
    // You can add custom query methods here if needed in the future
	
	    boolean existsByName(String name); // Custom method to check for existing classes
	

}
