package com.ashu.practice;

import com.ashu.practice.config.DroolsBeanFactory;
import com.ashu.practice.model.Product;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);
    private final KieSession kieSession = new DroolsBeanFactory().getKieSession();

    public Product applyLabelToProduct(Product product) {
        try {
            kieSession.insert(product);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
        LOG.info(product.getLabel());
        return product;
    }

}