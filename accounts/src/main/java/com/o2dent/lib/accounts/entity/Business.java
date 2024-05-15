package com.o2dent.lib.accounts.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import com.o2dent.lib.accounts.helpers.validators.ValidURL;
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
    @ManyToMany(cascade = CascadeType.ALL)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Business business = (Business) o;

        if (enabled != business.enabled) return false;
        if (!Objects.equals(id, business.id)) return false;
        if (!Objects.equals(name, business.name)) return false;
        if (!Objects.equals(subdomainUri, business.subdomainUri))
            return false;
        if (!Objects.equals(businessUrl, business.businessUrl))
            return false;
        return Objects.equals(accounts, business.accounts);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (subdomainUri != null ? subdomainUri.hashCode() : 0);
        result = 31 * result + (businessUrl != null ? businessUrl.hashCode() : 0);
        result = 31 * result + (accounts != null ? accounts.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        return result;
    }
}