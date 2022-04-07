package config;

public class Config {
    public String getUrl() {
        return "jdbc:mariadb://emp.fulgentcorp.com:3306/scott?allowPublicKeyRetrieval=true&useSSL=false";
    }

    public String getUser() {
        return "scott";
    }

    public String getPassword() {
        return "SZ8RzSZcNQCs2anS";
    }
}
