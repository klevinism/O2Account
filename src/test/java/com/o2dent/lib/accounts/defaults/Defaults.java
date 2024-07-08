package com.o2dent.lib.accounts.defaults;

import com.o2dent.lib.accounts.TestConfiguration;
import com.o2dent.lib.accounts.persistence.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Set;

public final class Defaults {
    public static class Account{
        private com.o2dent.lib.accounts.entity.Account account;
        public Account(){this(true, true);}
        public Account(boolean isAccount, boolean isActive){
            account = new com.o2dent.lib.accounts.entity.Account();
            account.setName("TestName");
            account.setSurname("TestLastName");
            account.setAccount(isAccount);
            account.setActive(isActive);
            account.setEmail("test@test.com");
        }
        public com.o2dent.lib.accounts.entity.Account get(){
            return account;
        }
        public Account with(List<com.o2dent.lib.accounts.entity.Role> roles){
            account.setRoles(roles);
            roles.forEach(r -> r.addAccount(account));
            return this;
        }
        public Account with(Set<com.o2dent.lib.accounts.entity.Business> businesses){
            account.setBusinesses(businesses);
            businesses.forEach(b -> b.getAccounts().add(account));
            return this;
        }
    }

    public static class Business{
        private com.o2dent.lib.accounts.entity.Business business;
        public Business(){this(true);}
        public Business(boolean isEnabled){
            business = new com.o2dent.lib.accounts.entity.Business();
            business.setName("Test Business");
            business.setBusinessUrl("https://testbusinessurl.com");
            business.setSubdomainUri("testdental" + Math.random());
            business.setEnabled(isEnabled);
        }
        public com.o2dent.lib.accounts.entity.Business get(){
            return business;
        }
        public Business with(Set<com.o2dent.lib.accounts.entity.Account> accounts){
            business.setAccounts(accounts);
            return this;
        }
    }
    public static class Role{
        private com.o2dent.lib.accounts.entity.Role role;
        public Role(){
            role = new com.o2dent.lib.accounts.entity.Role();
        }
        public com.o2dent.lib.accounts.entity.Role get(){
            return this.get(Roles.ADMIN);
        }
        public com.o2dent.lib.accounts.entity.Role get(Roles picked){
            role.setName(picked.name());
            return role;
        }
        public Role with(List<com.o2dent.lib.accounts.entity.Account> accounts){
            role.setAccounts(accounts);
            accounts.forEach(a -> a.addRole(role));
            return this;
        }
    }
}
