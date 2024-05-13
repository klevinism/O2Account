package com.o2dent.lib.accounts;

import java.io.Serializable;
import java.util.Set;

import com.o2dent.lib.accounts.helpers.ValidURL;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * @author delimeta
 *
 */
@Entity
public class Business implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUS_SEQ")
    @SequenceGenerator(sequenceName = "business_seq", allocationSize = 1, name = "BUS_SEQ")
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Column(name = "subdomain_uri")
    private String subdomainUri;

    @ValidURL
    @Column(name = "business_url")
    private String businessUrl;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(name = "business_accounts",
            joinColumns = @JoinColumn(name = "businessid"),
            inverseJoinColumns = @JoinColumn(name = "accountid"))
    private Set<Account> accounts;

    private boolean enabled;

    /**
     * @return id Long
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return name String
     */
    public String getName() {
        return name;
    }

    /**
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return businessUrl String
     */
    public String getBusinessUrl() {
        return businessUrl;
    }

    /**
     * @param businessUrl String
     */
    public void setBusinessUrl(String businessUrl) {
        this.businessUrl = businessUrl;
    }

    /**
     * @return
     */
    public Set<Account> getAccounts() {
        return accounts;
    }

    /**
     * @param accounts
     */
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }


    /**
     * @return enabled boolean
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled boolean
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return subdomainUri String
     */
    public String getSubdomainUri() {
        return subdomainUri;
    }

    /**
     * @param subdomainUri String
     */
    public void setSubdomainUri(String subdomainUri) {
        this.subdomainUri = subdomainUri;
    }

}