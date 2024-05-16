package com.o2dent.lib.accounts.persistence;

import com.o2dent.lib.accounts.entity.Account;
import com.o2dent.lib.accounts.helpers.exceptions.EmailExistsException;
import com.o2dent.lib.accounts.helpers.exceptions.UsernameExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
     * Finds all accounts
     * @return account List
     */
    public List<Account> findAll() {
        return this.accountRepository.findAll();
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
     * @param newAccount
     * @return
     * @throws EmailExistsException
     * @throws UsernameExistsException
     */
    public Account update(Account newAccount) throws EmailExistsException, UsernameExistsException {
        if(emailExists(newAccount.getEmail())) {
            throw new EmailExistsException();
        }else if(usernameExists(newAccount.getUsername())) {
            throw new UsernameExistsException();
        }

        return createPlain(newAccount);
    }

    /**
     *
     * @param email
     * @return
     */
    public boolean emailExists(String email) {
        return accountRepository.findByEmail(email).isPresent();
    }

    /**
     *
     * @param username
     * @return
     */
    public boolean usernameExists(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }

    /**
     * @param id
     * @param currentBusinessId
     * @return
     */
    public Optional<Account> findByIdAndBusinesses_Id(Long id, long currentBusinessId) {
        return this.accountRepository.findByIdAndBusinesses_Id(id, currentBusinessId);
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

    /**
     *
     * @param account Account
     * @return Account the new user created
     */
    public Account createPlain(Account account) {
        return accountRepository.saveAndFlush(account);
    }
}