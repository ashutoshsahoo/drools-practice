package com.ashu.practice;

import com.ashu.practice.model.Applicant;
import com.ashu.practice.model.SuggestedRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class ApplicantRuleTest {


    private ApplicantService applicantService;

    @BeforeEach
    public void setup() {
        applicantService = new ApplicantService();
    }

    @Test
    void whenCriteriaMatching_ThenSuggestManagerRole() throws IOException {
        Applicant applicant = new Applicant("Davis", 37, 1600000.0, 11);
        SuggestedRole suggestedRole = new SuggestedRole();
        applicantService.suggestARoleForApplicant(applicant, suggestedRole);
        assertEquals("Manager", suggestedRole.getRole());
    }

    @Test
    void whenCriteriaMatching_ThenSuggestSeniorDeveloperRole() throws IOException {
        Applicant applicant = new Applicant("John", 37, 1200000.0, 8);
        SuggestedRole suggestedRole = new SuggestedRole();
        applicantService.suggestARoleForApplicant(applicant, suggestedRole);
        assertEquals("Senior developer", suggestedRole.getRole());
    }

    @Test
    void whenCriteriaMatching_ThenSuggestDeveloperRole() throws IOException {
        Applicant applicant = new Applicant("Davis", 37, 800000.0, 3);
        SuggestedRole suggestedRole = new SuggestedRole();
        applicantService.suggestARoleForApplicant(applicant, suggestedRole);
        assertEquals("Developer", suggestedRole.getRole());
    }

    @Test
    void whenCriteriaNotMatching_ThenNoRole() throws IOException {
        Applicant applicant = new Applicant("John", 37, 1200000.0, 5);
        SuggestedRole suggestedRole = new SuggestedRole();
        applicantService.suggestARoleForApplicant(applicant, suggestedRole);
        assertNull(suggestedRole.getRole());
    }
}