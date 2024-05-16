package com.o2dent.lib.accounts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author delimeta
 *
 */
@Entity
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACC_SEQ")
    @SequenceGenerator(sequenceName = "account_seq", allocationSize = 1, name = "ACC_SEQ")
    private Long id;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "accounts", cascade = CascadeType.ALL)
    private Set<Business> businesses = new HashSet<Business>();

    @NotNull(message = "{alert.fieldEmpty}")
    @NotEmpty(message = "{alert.fieldEmpty}")
    private String name;

    @NotNull(message = "{alert.fieldEmpty}")
    @NotEmpty(message = "{alert.fieldEmpty}")
    private String surname;

    private int age;

    private String gender;

    @Nullable
    private String email;

    @Nullable
    private Long phone;

    private String username;

    private String password;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthday;

    @Nullable
    private String image;

    @Nullable
    private String address;

    @Nullable
    private String city;

    @Nullable
    private String country;

    private boolean isAccount;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "authority",
            joinColumns = @JoinColumn(name = "accountid"),
            inverseJoinColumns = @JoinColumn(name = "roleid"))
    private List<Role> roles  = new ArrayList<>();
    private boolean enabled;

    private boolean active;



    /**
     * @param username String
     * @param password String
     * @param roles List<String>
     * @param enabled boolean
     * @param active boolean
     */
    public Account(@NotBlank(message = "Username is mandatory") String username,
                   @NotBlank(message = "Password is mandatory") String password, List<Role> roles, boolean enabled, boolean active) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.enabled = enabled;
        this.active = active;
    }

    /**
     * @param account Account
     */
    public Account(Account account) {
        this.id = account.id;
        this.name = account.name;
        this.surname = account.surname;
        this.username = account.username;
        this.password = account.password;
        this.address = account.address;
        this.age = account.age;
        this.birthday = account.birthday;
        this.city = account.city;
        this.country = account.country;
        this.email = account.email;
        this.gender = account.gender;
        this.roles = account.roles;
        this.image = account.image;
        this.phone = account.phone;
        this.businesses = account.businesses;
        this.enabled = account.enabled;
        this.active = account.active;
        this.isAccount = account.isAccount;
    }

    /**
     * Default constructor needed for entity
     */
    public Account() {
    }

    /**
     * @param id Long
     */
    public void setId(Long id){
        this.id = id;
    }
    /**
     * @return
     */
    public Long getId() {
        return id;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public Long getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(Long phone) {
        this.phone = phone;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return active boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday String the registerdate to set
     */
    public void setBirthday(String birthday) {
        try {
            this.birthday = new SimpleDateFormat("dd-MMM-yyyy").parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return
     */
    public Set<Business> getBusinesses() {
        return businesses;
    }

    /**
     * @param businesses
     */
    public void setBusinesses(Set<Business> businesses) {
        this.businesses = businesses;
    }

    /**
     *
     * @param currentLoggedInBusiness
     */
    public void addBusiness(Business currentLoggedInBusiness) {
        this.businesses.add(currentLoggedInBusiness);
        currentLoggedInBusiness.getAccounts().add(this);
    }

    /**
     *
     * @return
     */
    public boolean isAccount() {
        return isAccount;
    }

    /**
     *
     * @param isAccount
     */
    public void setAccount(boolean isAccount) {
        this.isAccount = isAccount;
    }

    /**
     *
     * @return
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     *
     * @param roles
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     *
     * @param role
     */
    public void addRole(Role role) {
        this.roles.add(role);
        role.getAccounts().add(this);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Account [id=" + id + ", name=" + name + ", surname=" + surname + ", age=" + age + ", gender=" + gender
                + ", email=" + email + ", phone=" + phone + ", username=" + username + ", password=" + password
                + ", birthday=" + birthday + ", image=" + image + ", address=" + address + ", city=" + city
                + ", country=" + country + ", enabled=" + enabled + ", active=" + active + "]";
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (age != account.age) return false;
        if (isAccount != account.isAccount) return false;
        if (enabled != account.enabled) return false;
        if (active != account.active) return false;
        if (!id.equals(account.id)) return false;
        if (!Objects.equals(name, account.name)) return false;
        if (!Objects.equals(surname, account.surname)) return false;
        if (!Objects.equals(gender, account.gender)) return false;
        if (!Objects.equals(email, account.email)) return false;
        if (!Objects.equals(phone, account.phone)) return false;
        if (!username.equals(account.username)) return false;
        if (!Objects.equals(password, account.password)) return false;
        if (!Objects.equals(birthday, account.birthday)) return false;
        if (!Objects.equals(image, account.image)) return false;
        if (!Objects.equals(address, account.address)) return false;
        if (!Objects.equals(roles, account.roles)) return false;
        if (!Objects.equals(city, account.city)) return false;
        return Objects.equals(country, account.country);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (isAccount ? 1 : 0);
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }
}