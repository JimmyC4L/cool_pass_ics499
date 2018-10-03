package com.ics499.coolpass.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Request entity.
 * Users enter request to get
 * access to a role.
 * Business Owners or Admins can
 * approve or reject a role
 * @author Team 2.
 */
@ApiModel(description = "Request entity. Users enter request to get access to a role. Business Owners or Admins can approve or reject a role @author Team 2.")
@Entity
@Table(name = "request")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "requester_id")
    private Long requesterId;

    @Column(name = "authority_name")
    private String authorityName;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Request description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRequesterId() {
        return requesterId;
    }

    public Request requesterId(Long requesterId) {
        this.requesterId = requesterId;
        return this;
    }

    public void setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public Request authorityName(String authorityName) {
        this.authorityName = authorityName;
        return this;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getStatus() {
        return status;
    }

    public Request status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
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
        Request request = (Request) o;
        if (request.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), request.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Request{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", requesterId=" + getRequesterId() +
            ", authorityName='" + getAuthorityName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
