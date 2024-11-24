package business;

import javax.persistence.Entity;
import java.util.Date;


@Entity
public class Customer extends Person {
    private String googleLogin;
    private String status;

    public Customer() {

    }

    public Customer(String name, Date birthDate, Address address, String email, String password, String phone, byte[] avatar, String googleLogin, String status) {
        super(name, birthDate, address, email, password, phone, avatar);
        this.googleLogin = googleLogin;
        this.status = status;
    }

    public String getGoogleLogin() {
        return googleLogin;
    }

    public void setGoogleLogin(String googleLogin) {
        this.googleLogin = googleLogin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
