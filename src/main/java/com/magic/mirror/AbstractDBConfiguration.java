package com.magic.mirror;

public abstract class AbstractDBConfiguration {

    private String url;
    private String username;
    private String password;
    private String ddlAuto;
    private String dialect;
    private String showSql;
    private String driverClassName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDdlAuto() {
        return ddlAuto;
    }

    public void setDdlAuto(String ddlAuto) {
        this.ddlAuto = ddlAuto;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getShowSql() {
        return showSql;
    }

    public void setShowSql(String showSql) {
        this.showSql = showSql;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    @Override
    public String toString() {
        return "AbstractDBConfiguration{" +
                "url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ddlAuto='" + ddlAuto + '\'' +
                ", dialect='" + dialect + '\'' +
                ", showSql='" + showSql + '\'' +
                ", driverClassName='" + driverClassName + '\'' +
                '}';
    }
}
