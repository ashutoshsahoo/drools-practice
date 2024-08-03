package com.ashu.practice;

import org.drools.model.codegen.ExecutableModelProject;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class MeasurementRuleTest {
    static final Logger LOG = LoggerFactory.getLogger(MeasurementRuleTest.class);

    @Test
    void test() {
        KieContainer kContainer = createKieContainer();

        LOG.info("Creating kieBase");
        KieBase kieBase = kContainer.getKieBase();

        LOG.info("There should be rules: ");
        for (KiePackage kp : kieBase.getKiePackages()) {
            for (Rule rule : kp.getRules()) {
                LOG.info("kp " + kp + " rule " + rule.getName());
            }
        }

        LOG.info("Creating kieSession");
        KieSession session = kieBase.newKieSession();

        try {
            LOG.info("Populating globals");
            Set<String> check = new HashSet<String>();
            session.setGlobal("controlSet", check);

            LOG.info("Now running data");

            Measurement mRed = new Measurement("color", "red");
            session.insert(mRed);
            session.fireAllRules();

            Measurement mGreen = new Measurement("color", "green");
            session.insert(mGreen);
            session.fireAllRules();

            Measurement mBlue = new Measurement("color", "blue");
            session.insert(mBlue);
            session.fireAllRules();

            LOG.info("Final checks");

            assertEquals(3, session.getObjects().size(), "Size of object in Working Memory is 3");
            assertTrue(check.contains("red"), "contains red");
            assertTrue(check.contains("green"), "contains green");
            assertTrue(check.contains("blue"), "contains blue");
        } finally {
            session.dispose();
        }
    }

    private KieContainer createKieContainer() {
        // Programmatically collect resources and build a KieContainer
        KieServices ks = KieServices.Factory.get();
        KieFileSystem kfs = ks.newKieFileSystem();
        String packagePath = "com.ashu.practice".replace(".", "/");
        kfs.write("src/main/resources/" + packagePath + "/color.drl",
                ks.getResources().newInputStreamResource(this.getClass().getClassLoader().getResourceAsStream(packagePath + "/color.drl")));
        ReleaseId releaseId = ks.newReleaseId("com.ashu.practice", "drools-practice", "1.0.0-SNAPSHOT");
        kfs.generateAndWritePomXML(releaseId);
        ks.newKieBuilder(kfs).buildAll(ExecutableModelProject.class);
        return ks.newKieContainer(releaseId);
    }
}