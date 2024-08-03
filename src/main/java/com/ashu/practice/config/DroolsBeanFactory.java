package com.ashu.practice.config;

import org.drools.decisiontable.DecisionTableProviderImpl;
import org.drools.model.codegen.ExecutableModelProject;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DroolsBeanFactory {

    private static final Logger LOG = LoggerFactory.getLogger(DroolsBeanFactory.class);
    private final KieServices kieServices = KieServices.Factory.get();


    public KieSession getKieSession() {
        KieFileSystem kieFileSystem = getKieFileSystem();
        ReleaseId releaseId = kieServices.newReleaseId(
                "com.ashu.practice",
                "drools-practice",
                "1.0.0-SNAPSHOT");
        kieFileSystem.generateAndWritePomXML(releaseId);
        kieServices.newKieBuilder(kieFileSystem).buildAll(ExecutableModelProject.class);
        KieContainer kieContainer = kieServices.newKieContainer(releaseId);
//        LOG.info("release id:{}", releaseId);
        return kieContainer.newKieSession();
    }

    private KieFileSystem getKieFileSystem() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        List<String> rules = List.of(
                "com/ashu/practice/SuggestApplicant.drl",
                "com/ashu/practice/Product_rules_v1_1.drl.xlsx"
        );
        for (String rule : rules) {
            kieFileSystem.write(ResourceFactory.newClassPathResource(rule));
        }
        return kieFileSystem;
    }


    public KieSession getKieSession(Resource dt) {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(dt);
        kieServices.newKieBuilder(kieFileSystem).buildAll();
        KieRepository kieRepository = kieServices.getRepository();
        ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
        KieContainer kieContainer = kieServices.newKieContainer(krDefaultReleaseId);
        return kieContainer.newKieSession();
    }


    /*
     * Can be used for debugging
     * Input excelFile example: com/ashu/practice/Discount.drl.xls
     */
    public String getDrlFromExcel(String excelFile) {
        DecisionTableConfiguration configuration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
        configuration.setInputType(DecisionTableInputType.XLS);
        Resource dt = ResourceFactory.newClassPathResource(excelFile, getClass());
        DecisionTableProviderImpl decisionTableProvider = new DecisionTableProviderImpl();
        return decisionTableProvider.loadFromResource(dt, null);
    }

}
