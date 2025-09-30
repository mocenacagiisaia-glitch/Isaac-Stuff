package com.example.school.repositories;

import com.example.school.models.Parent;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface ParentRepository extends JpaRepository<Parent, Integer> {
    Parent findByEmail(String email);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Parent p WHERE p.id = :userId")
    void deleteByUserId(int userId);
}

