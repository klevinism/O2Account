package com.o2dent.lib.accounts;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {Configurations.class,AccountRepository.class})
@DataJpaTest
@EntityScan
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
        assertEquals(foundAccount.getRoles(),insertedAccount.getRoles());
    }
}