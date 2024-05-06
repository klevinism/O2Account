package com.o2dent.lib.accounts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

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
        return account;
    }

    @Test
    void givenNewAccount_whenSave_thenSuccess() {
        Account account = createDefaultAccount();
        Account insertedAccount = accountRepository.save(account);
        Account foundAccount = entityManager.find(Account.class, insertedAccount.getId());
        assertThat(foundAccount).isEqualTo(account);
    }
}