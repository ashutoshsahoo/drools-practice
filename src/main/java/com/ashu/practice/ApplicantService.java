package com.ashu.practice;

import com.ashu.practice.config.DroolsBeanFactory;
import com.ashu.practice.model.Applicant;
import com.ashu.practice.model.SuggestedRole;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicantService {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicantService.class);
    KieSession kieSession = new DroolsBeanFactory().getKieSession();

    public void suggestARoleForApplicant(Applicant applicant, SuggestedRole suggestedRole) {
        try {
            kieSession.insert(applicant);
            kieSession.setGlobal("suggestedRole", suggestedRole);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
        LOG.info(suggestedRole.getRole());
    }

}
