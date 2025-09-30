package com.example.school.controllers;

import com.example.school.models.Term;
import com.example.school.services.TermService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private TermService termService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTerm_ShouldReturnSuccessResponse() {
        String termName = "Term 1";
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        int durationInWeeks = 10;

        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("success", "true");
        expectedResponse.put("message", "Term created successfully!");

        // Create the Term object
        Term term = new Term();
        term.setName(termName);
        term.setStartDate(startDate);
        term.setEndDate(startDate.plusWeeks(durationInWeeks));

        // Mock the term service behavior
        when(termService.saveTerm(any(Term.class))).thenReturn(term); // Mock the saveTerm method

        // Act
        ResponseEntity<Map<String, String>> response = adminController.createTerm(termName, startDate, durationInWeeks);

        // Verify
        verify(termService).saveTerm(any(Term.class)); // Verify saveTerm is called

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Assert HTTP response code
        assertEquals(expectedResponse, response.getBody()); // Assert response body
    }

    @Test
    void createTerm_ShouldReturnErrorResponse_WhenExceptionThrown() {
        String termName = "Term 1";
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        int durationInWeeks = 10;

        // Simulate an exception when saving the term
        doThrow(new RuntimeException("Service error")).when(termService).saveTerm(any(Term.class));

        // Act
        ResponseEntity<Map<String, String>> response = adminController.createTerm(termName, startDate, durationInWeeks);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()); // Assert HTTP response code
        assertEquals("Error creating term: Service error", response.getBody().get("message")); // Assert error message
    }
}
