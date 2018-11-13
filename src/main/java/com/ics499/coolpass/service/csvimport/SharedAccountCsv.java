package com.ics499.coolpass.service.csvimport;

import com.opencsv.bean.CsvBindByName;

public class SharedAccountCsv {
    @CsvBindByName(column = "login")
    private String login;
    @CsvBindByName(column = "jhi_password")
    private String password;
    @CsvBindByName(column = "environment_id")
    private Long environmentId;
    @CsvBindByName(column = "id")
    private Long id;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
