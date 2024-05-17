package com.o2dent.lib.accounts.persistence;

import com.o2dent.lib.accounts.entity.Account;
import com.o2dent.lib.accounts.helpers.exceptions.EmailExistsException;
import com.o2dent.lib.accounts.helpers.exceptions.PhoneNumberExistsException;
import com.o2dent.lib.accounts.helpers.exceptions.UsernameExistsException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
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
     * @param id
     * @param b
     * @param c
     * @param roles
     * @return
     */
    public List<Account> findAllByAccountBusinessIdAndActiveAndEnabledAndRoles_NameIn(Long id, boolean b, boolean c,
                                                                                      List<String> roles) {
        return this.accountRepository.findAllByBusinesses_IdAndActiveAndEnabledAndRoles_NameIn(id, b, c, roles);
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
     *
     * @param phone
     * @return
     */
    public boolean phoneNumberExists(Long phone) {
        return accountRepository.findByPhone(phone).isPresent();
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
     * @param name
     * @param surname
     * @param birthday
     * @return
     */
    public Optional<Account> findByNameAndSurnameAndBirthday(String name, String surname, Date birthday) {
        return this.accountRepository.findByNameIgnoreCaseAndSurnameIgnoreCaseAndBirthday(name, surname, birthday);
    }

    /**
     *
     * @param account
     */
    public void delete(Account account) {
        accountRepository.delete(account);
    }


    /**
     *
     * @param username
     * @return
     */
    public Optional<Account> findByUsernameOrEmail(String username) {
        return this.accountRepository.findByUsernameOrEmail(username, username);
    }

    /**
     *
     * @param account Account
     * @return Account the new user created
     */
    public Account createPlain(Account account) {
        return accountRepository.saveAndFlush(account);
    }

    /**
     *
     * @param newAccount
     * @return
     * @throws EmailExistsException
     * @throws UsernameExistsException
     * @throws PhoneNumberExistsException
     */
    public Account create(Account newAccount) throws EmailExistsException, UsernameExistsException, PhoneNumberExistsException {
        if(Strings.isNotEmpty(newAccount.getEmail()) && emailExists(newAccount.getEmail())) {
            throw new EmailExistsException();
        }else if(Strings.isNotEmpty(newAccount.getUsername()) && usernameExists(newAccount.getUsername())) {
            throw new UsernameExistsException();
        }else if(Objects.nonNull(newAccount.getPhone()) && phoneNumberExists(newAccount.getPhone())) {
            throw new PhoneNumberExistsException();
        }

        return createPlain(newAccount);
    }
}