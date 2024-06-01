package com.o2dent.lib.accounts.persistence;

import com.o2dent.lib.accounts.entity.Account;
import com.o2dent.lib.accounts.helpers.exceptions.EmailExistsException;
import com.o2dent.lib.accounts.helpers.exceptions.PhoneNumberExistsException;
import com.o2dent.lib.accounts.helpers.exceptions.UsernameExistsException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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

    public Account disable(Account personnel) {
        personnel.setEnabled(false);
        return this.accountRepository.saveAndFlush(personnel);
    }

    public Account disableById(Long id) {
        Optional<Account> account = this.findById(id);
        if(account.isPresent()) return this.accountRepository.saveAndFlush(account.get());
        return null;
    }

    /**
     *
     * @param businessId
     * @return
     */
    public List<Account> findAllByBusinessId(Long businessId) {
        return this.accountRepository.findAllByBusinesses_Id(businessId);
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

    public List<Account> findAllByBusinessIdAndRoles_NameIn(Long businessId, List<String> roleNames) {
        return this.accountRepository.findAllByBusinesses_IdAndRoles_NameIn(businessId, roleNames);
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
     * @param businessId
     * @param role
     * @return
     */
    public List<Account> findAllByBusinessIdAndRoles_Name(Long businessId, String role){
        return this.accountRepository.findAllByBusinesses_IdAndRoles_Name(businessId, role);
    }

    /**
     *
     * @param enabled
     * @param active
     * @param role
     * @return
     */
    public List<Account> findAllByEnabledAndActiveAndRoles_Name(boolean enabled, boolean active, String role) {
        return this.accountRepository.findAllByActiveAndEnabledAndRoles_Name(enabled, active, role);
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
     * @param currentBusinessId
     * @param enabled
     * @param active
     * @param ids
     * @return
     */
    public Integer countByBusinesses_IdAndEnabledAndActiveAndIdIn(Long currentBusinessId, boolean enabled, boolean active, List<Long> ids) {
        return this.accountRepository.countByBusinesses_IdAndEnabledAndActiveAndIdIn(currentBusinessId, enabled, active, ids);
    }

    /**
     *
     * @param currentBusinessId
     * @param ids
     * @return
     */
    public List<Account> findByBusinesses_IdAndIdIn(Long currentBusinessId, List<Long> ids) {
        return this.accountRepository.findByBusinesses_IdAndIdIn(currentBusinessId, ids);
    }
    /**
     *
     * @param enabled
     * @param active
     * @param ids
     * @return
     */
    public Integer countByEnabledAndActiveAndIdIn(boolean enabled, boolean active, List<Long> ids) {
        return this.accountRepository.countByEnabledAndActiveAndIdIn(enabled, active, ids);
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