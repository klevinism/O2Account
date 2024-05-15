package com.o2dent.lib.accounts;

import com.o2dent.lib.accounts.entity.Account;
import com.o2dent.lib.accounts.entity.Business;
import com.o2dent.lib.accounts.entity.Role;
import com.o2dent.lib.accounts.helpers.exceptions.SubdomainExistsException;
import com.o2dent.lib.accounts.persistance.AccountService;
import com.o2dent.lib.accounts.persistance.BusinessService;
import org.hibernate.id.GUIDGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {Configurations.class})
@DataJpaTest
@EntityScan
public class BusinessServiceIntegrationTest {
    @Autowired
    BusinessService businessService;
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    private AccountService accountService;

    private Business createDefaultBusiness() throws SubdomainExistsException {
        Account account = new Account();
        account.setName("TestName");
        account.setSurname("TestLastName");
        account.setAccount(true);
        account.setActive(true);
        account.setEmail("test@test.com");

        createDefaultRoles(account);

        Account savedAccount = accountService.createPlain(account);

        Business business = new Business();
        business.setAccounts(Set.of(savedAccount));
        business.setName("Test Business");
        business.setBusinessUrl("https://testbusinessurl.com");
        business.setSubdomainUri("testdental" + Math.random());
        business.setEnabled(true);

        return businessService.create(business);
    }

    private Account createDefaultRoles(Account account) {
        Role adminRole = new Role();
        adminRole.setAccounts(List.of(account));
        adminRole.setName("ADMIN");
        account.setRoles(List.of(adminRole));
        return account;
    }

    @Test
    void givenNewBusiness_whenSave_thenHaveSameAccount() throws SubdomainExistsException {
        Business business = createDefaultBusiness();
        Optional<Business> foundBusiness = businessService.findById(business.getId());
        assertThat(foundBusiness.get().getAccounts()).isEqualTo(business.getAccounts());
    }

    @Test
    void givenNewBusiness_whenDisabled_thenSuccess() throws SubdomainExistsException {
        Business business = createDefaultBusiness();
        Optional<Business> foundBusiness = businessService.findById(business.getId());

        assertThat(foundBusiness.get().isEnabled()).isEqualTo(true);
        business.setAccounts(null); //Do not want to update business-account relation as well
        Business disabledBusiness = businessService.disable(business);
        assertThat(disabledBusiness.isEnabled()).isEqualTo(false);
    }
}
