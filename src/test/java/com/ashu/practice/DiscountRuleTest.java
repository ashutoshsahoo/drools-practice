package com.ashu.practice;

import com.ashu.practice.config.DroolsBeanFactory;
import com.ashu.practice.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountRuleTest {

    private KieSession kSession;

    @BeforeEach
    public void setup() {
        Resource resource = ResourceFactory.newClassPathResource("com/ashu/practice/Discount.drl.xls", getClass());
        kSession = new DroolsBeanFactory().getKieSession(resource);
    }

    @Test
    void giveIndividualLongStanding_whenFireRule_thenCorrectDiscount() {
        Customer customer = new Customer(Customer.CustomerType.INDIVIDUAL, 5);
        kSession.insert(customer);
        kSession.fireAllRules();
        assertEquals(15, customer.getDiscount());
    }

    @Test
    void giveIndividualRecent_whenFireRule_thenCorrectDiscount() {
        Customer customer = new Customer(Customer.CustomerType.INDIVIDUAL, 1);
        kSession.insert(customer);
        kSession.fireAllRules();
        assertEquals(5, customer.getDiscount());
    }

    @Test
    void giveBusinessAny_whenFireRule_thenCorrectDiscount() {
        Customer customer = new Customer(Customer.CustomerType.BUSINESS, 0);
        kSession.insert(customer);
        kSession.fireAllRules();
        assertEquals(20, customer.getDiscount());
    }

}
