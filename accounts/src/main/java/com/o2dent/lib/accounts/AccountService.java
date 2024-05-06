package com.o2dent.lib.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    /**
     * @param accountRepository AccountRepository
     */
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * @param id Long
     * @return
     */
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    /**
     * @param email
     * @return
     */
    public Optional<Account> findByEmail(String email) {
        return this.accountRepository.findByEmail(email);
    }

    /**
     * @param phoneNr
     * @return
     */
    public Optional<Account> findByPhoneNr(Long phoneNr) {
        return this.accountRepository.findByPhone(phoneNr);
    }

    /**
     *
     * @param username String
     * @return Optional<Account>
     */
    public Optional<Account> findByUsernameOrEmailOrPhoneNumber(String username) {
        try{
            return this.accountRepository.findByPhone(Long.valueOf(username));
        }catch(NumberFormatException exception) {
            return this.accountRepository.findByUsernameOrEmail(username, username);
        }
    }
}