package com.example.school.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.school.models.Term;
import com.example.school.repositories.TermRepository; // Import your TermRepository

@Service
public class TermService {

    @Autowired
    private TermRepository termRepository; // Ensure you have a repository for Term

    public void createTerm(Term term) {
        termRepository.save(term); // Save the term to the database
    }

    public List<Term> getAllTerms() {
        return termRepository.findAll(); // Return a list of all terms
    }
    
    // Method to save a term
    public Term saveTerm(Term term) {
        return termRepository.save(term);
    }
}
