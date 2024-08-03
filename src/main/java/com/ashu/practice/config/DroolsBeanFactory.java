package com.ashu.practice.config;

import org.drools.model.codegen.ExecutableModelProject;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DroolsBeanFactory {

    private static final Logger LOG = LoggerFactory.getLogger(DroolsBeanFactory.class);
    private final KieServices kieServices = KieServices.Factory.get();


    public KieSession getKieSession() {
        KieFileSystem kieFileSystem = getKieFileSystem();
        ReleaseId releaseId = kieServices.newReleaseId("com.ashu.practice", "drools-practice", "1.0.0-SNAPSHOT");
        kieFileSystem.generateAndWritePomXML(releaseId);
        kieServices.newKieBuilder(kieFileSystem).buildAll(ExecutableModelProject.class);
        KieContainer kieContainer = kieServices.newKieContainer(releaseId);
//        LOG.info("release id:{}", releaseId);
        return kieContainer.newKieSession();
    }

    private KieFileSystem getKieFileSystem() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        List<String> rules = List.of("com/ashu/practice/SuggestApplicant.drl","com/ashu/practice/Product_rules.drl.xls");
        for (String rule : rules) {
            kieFileSystem.write(ResourceFactory.newClassPathResource(rule));
        }
        return kieFileSystem;
    }

}
