package com.o2dent.lib.accounts;

import com.o2dent.lib.accounts.defaults.Defaults;
import com.o2dent.lib.accounts.entity.Account;
import com.o2dent.lib.accounts.entity.Business;
import com.o2dent.lib.accounts.persistence.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = {TestConfiguration.class})
@DataJpaTest
public class AccountServiceIntegrationTest {
    @Autowired
    AccountService accountService;
    private Account createDefaultAccount(){
        Account account = new Defaults.Account()
                .with(List.of(new Defaults.Role().get()))
                .get();
        return accountService.createPlain(account);
    }
    @Test
    void givenAccountWithBusiness_whenSave_thenSuccess(){
        Business defaultBusiness = new Defaults.Business().get();
        Account defaultAccount = new Defaults.Account()
                .with(Set.of(defaultBusiness))
                .get();
        Account newAccount = accountService.createPlain(defaultAccount);
        assertEquals(newAccount.getBusinesses(), Set.of(defaultBusiness));
    }
    @Test
    void givenNewAccount_whenSave_thenSuccess() {
        Account account = createDefaultAccount();
        Optional<Account> foundAccount = accountService.findById(account.getId());
        assertEquals(account, foundAccount.get());
    }
    @Test
    void givenNewAccount_whenSaveWithRoleAdmin_thenSuccessAndRole_isAdmin() {
        Account account = createDefaultAccount();
        Optional<Account> foundAccount = accountService.findById(account.getId());
        assertEquals(foundAccount.get().getRoles(), account.getRoles());
    }
}