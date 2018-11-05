package com.ics499.coolpass.service.csvimport;

import com.opencsv.bean.CsvBindByName;

public class SharedAccountCsv {
    @CsvBindByName(column = "login")
    private String login;
    @CsvBindByName(column = "jhi_password")
    private String password;
    @CsvBindByName(column = "environment_id")
    private Long environmentId;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Long getEnvironmentId() {
        return environmentId;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }
}
