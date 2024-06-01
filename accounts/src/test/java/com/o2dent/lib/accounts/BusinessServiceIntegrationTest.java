package com.o2dent.lib.accounts;

import com.o2dent.lib.accounts.defaults.Defaults;
import com.o2dent.lib.accounts.entity.Account;
import com.o2dent.lib.accounts.entity.Business;
import com.o2dent.lib.accounts.entity.Role;
import com.o2dent.lib.accounts.helpers.exceptions.SubdomainExistsException;
import com.o2dent.lib.accounts.persistence.AccountService;
import com.o2dent.lib.accounts.persistence.BusinessService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration(classes = {TestConfiguration.class})
@DataJpaTest
public class BusinessServiceIntegrationTest {
    @Autowired
    BusinessService businessService;
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    private AccountService accountService;
    private Business createDefaultBusiness() throws SubdomainExistsException {
        Account account = new Defaults.Account()
                .with(List.of(new Defaults.Role().get()))
                .get();

        Account savedAccount = accountService.createPlain(account);
        Business business = new Defaults.Business().with(Set.of(savedAccount)).get();
        return businessService.create(business);
    }

    @Test
    void businessWithAccount_whenSave_thenThrowsBecauseNoAccountSaved() throws Exception {
        assertThrows(InvalidDataAccessApiUsageException.class,
                () -> businessService.create(
                        new Defaults.Business()
                                .with(Set.of(new Defaults.Account().get()))
                                .get()));
    }
    @Test
    void business_whenSave_thenHaveSameAccount() throws SubdomainExistsException {
        Business business = createDefaultBusiness();
        Optional<Business> foundBusiness = businessService.findById(business.getId());
        assertThat(foundBusiness.get().getAccounts()).isEqualTo(business.getAccounts());
    }
    @Test
    void business_whenDisabled_thenSuccess() throws SubdomainExistsException {
        Business business = createDefaultBusiness();
        Optional<Business> foundBusiness = businessService.findById(business.getId());

        assertThat(foundBusiness.get().isEnabled()).isEqualTo(true);
        business.setAccounts(null); //Do not want to update business-account relation as well
        Business disabledBusiness = businessService.disable(business);
        assertThat(disabledBusiness.isEnabled()).isEqualTo(false);
    }
}
