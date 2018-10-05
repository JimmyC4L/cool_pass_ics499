package com.ics499.coolpass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Environment entity.
 * @author Team 2.
 */
@ApiModel(description = "Environment entity. @author Team 2.")
@Entity
@Table(name = "environment")
public class Environment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "server")
    private String server;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "environment")
    private Set<SharedAccount> sharedAccounts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServer() {
        return server;
    }

    public Environment server(String server) {
        this.server = server;
        return this;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getName() {
        return name;
    }

    public Environment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SharedAccount> getSharedAccounts() {
        return sharedAccounts;
    }

    public Environment sharedAccounts(Set<SharedAccount> sharedAccounts) {
        this.sharedAccounts = sharedAccounts;
        return this;
    }

    public Environment addSharedAccount(SharedAccount sharedAccount) {
        this.sharedAccounts.add(sharedAccount);
        sharedAccount.setEnvironment(this);
        return this;
    }

    public Environment removeSharedAccount(SharedAccount sharedAccount) {
        this.sharedAccounts.remove(sharedAccount);
        sharedAccount.setEnvironment(null);
        return this;
    }

    public void setSharedAccounts(Set<SharedAccount> sharedAccounts) {
        this.sharedAccounts = sharedAccounts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Environment environment = (Environment) o;
        if (environment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), environment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Environment{" +
            "id=" + getId() +
            ", server='" + getServer() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
