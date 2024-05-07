package com.o2dent.lib.accounts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class AccountRepositoryIntegrationTest {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TestEntityManager entityManager;

    private Account createDefaultAccount(){
        Account account = new Account();
        account.setName("TestName");
        account.setAccount(true);
        account.setActive(true);
        account.setEmail("test@test.com");
        Role adminRole = new Role();
        adminRole.setAccounts(List.of(account));
        adminRole.setName("ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(adminRole);
        account.setRoles(List.of());
        return account;
    }

    @Test
    void givenNewAccount_whenSave_thenSuccess() {
        Account account = createDefaultAccount();
        Account insertedAccount = accountRepository.save(account);
        Account foundAccount = entityManager.find(Account.class, insertedAccount.getId());
        assertThat(foundAccount).isEqualTo(account);
    }

    @Test
    void givenNewAccount_whenSave_thenRole_isAdmin() {
        Account account = createDefaultAccount();
        Account insertedAccount = accountRepository.save(account);
        Account foundAccount = entityManager.find(Account.class, insertedAccount.getId());
        assertTrue(foundAccount.getRoles().equals(insertedAccount.getRoles()));
    }
}