package pro.softcom.archetype.gwt.shared.model;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CustomerModel implements IsSerializable {

    private Long id;
    private String lastname;
    private String firstname;
    private Date birthdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

}
