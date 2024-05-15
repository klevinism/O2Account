package com.o2dent.lib.accounts.persistance;

import com.o2dent.lib.accounts.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * @param username String
     * @return Optional<Account>
     */
    Optional<Account> findByUsername(String username);

    /**
     * @param id Long
     * @return Long
     */
    void deleteById(Long id);

    /**
     * @param name String
     * @return Long
     */
    Long deleteByName(String name);

    /**
     * Custom JPA query
     * Finds {@link Account} by Username or Email
     * @param username
     * @param email
     * @return Optional<{@link Account}>
     */
    @Query("select t from Account t where t.username = ?1 or t.email = ?2")
    Optional<Account> findByUsernameOrEmail(String username, String email);

    /**
     * @param b
     * @param c
     * @param name
     * @return
     */
    public List<Account> findAllByActiveAndEnabledAndRoles_Name(boolean b, boolean c, String name);

    /**
     * @param b
     * @param c
     * @param beginMonthDate
     * @param endMonthDate
     * @return
     */
//    public Integer countByEnabledAndActiveAndCustomer_RegisterdateBetween(boolean b, boolean c, Date beginMonthDate,
//                                                                          Date endMonthDate);

    /**
     * @param email
     * @return
     */
    Optional<Account> findByEmail(String email);

    /**
     * @param name
     * @param surname
     * @param birthday
     * @return
     */
    Optional<Account> findByNameIgnoreCaseAndSurnameIgnoreCaseAndBirthday(String name, String surname,
                                                                                 Date birthday);


    /**
     * @param id
     * @param currentBusinessId
     * @return
     */
    public Optional<Account> findByIdAndBusinesses_Id(Long id, long currentBusinessId);

    /**
     * @param currentBusinessId
     * @param b
     * @param b2
     * @param name
     * @return
     */
    public List<Account> findAllByBusinesses_IdAndActiveAndEnabledAndRoles_Name(Long currentBusinessId, boolean enabled,
                                                                                boolean active, String name);


    /**
     * @param currentBusinessId
     * @param enabled
     * @param active
     * @param beginMonthDate
     * @param endMonthDate
     * @return
     */
//    public Integer countByBusinesses_IdAndEnabledAndActiveAndCustomer_RegisterdateBetween(Long currentBusinessId,
//                                                                                          boolean enabled, boolean active, Date beginMonthDate, Date endMonthDate);

    /**
     * @param id
     * @param b
     * @param c
     * @param roles
     * @return
     */
    public List<Account> findAllByBusinesses_IdAndActiveAndEnabledAndRoles_NameIn(Long id, boolean b, boolean c,
                                                                                  List<String> roles);

    /**
     * @param phone Long phone number
     * @return
     */
    Optional<Account> findByPhone(Long phone);

}
