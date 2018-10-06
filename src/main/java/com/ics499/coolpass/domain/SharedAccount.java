package com.ics499.coolpass.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * SharedAccount entity.
 * Shared accounts reside on an
 * environment
 * @author Team2
 */
@ApiModel(description = "SharedAccount entity. Shared accounts reside on an environment @author Team2")
@Entity
@Table(name = "shared_account")
public class SharedAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "env_id")
    private Long envID;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("sharedAccounts")
    private Environment environment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public SharedAccount login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public SharedAccount password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getEnvID() {
        return envID;
    }

    public SharedAccount envID(Long envID) {
        this.envID = envID;
        return this;
    }

    public void setEnvID(Long envID) {
        this.envID = envID;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public SharedAccount environment(Environment environment) {
        this.environment = environment;
        return this;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
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
        SharedAccount sharedAccount = (SharedAccount) o;
        if (sharedAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sharedAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SharedAccount{" +
            "id=" + getId() +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", envID=" + getEnvID() +
            "}";
    }
}
