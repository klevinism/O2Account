package com.o2dent.lib.accounts;

import com.o2dent.lib.accounts.entity.Account;
import com.o2dent.lib.accounts.entity.Role;
import com.o2dent.lib.accounts.persistence.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {Configurations.class, AccountService.class})
@DataJpaTest
@EntityScan
public class AccountServiceIntegrationTest {
    @Autowired
    AccountService accountService;

    @Autowired
    TestEntityManager entityManager;

    private Account createDefaultAccount(){
        Account account = new Account();
        account.setName("TestName");
        account.setSurname("TestLastName");
        account.setAccount(true);
        account.setActive(true);
        account.setEmail("test@test.com");
        Role adminRole = new Role();
        adminRole.setAccounts(List.of(account));
        adminRole.setName("ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(adminRole);
        account.setRoles(List.of());
        return accountService.createPlain(account);
    }

    @Test
    void givenNewAccount_whenSave_thenSuccess() {
        Account account = createDefaultAccount();
        Optional<Account> foundAccount = accountService.findById(account.getId());
        assertThat(foundAccount.get()).isEqualTo(account);
    }

    @Test
    void givenNewAccount_whenSave_thenRole_isAdmin() {
        Account account = createDefaultAccount();
        Optional<Account> foundAccount = accountService.findById(account.getId());
        assertEquals(foundAccount.get().getRoles(), account.getRoles());
    }
}