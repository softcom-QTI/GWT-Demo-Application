package pro.softcom.archetype.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER")
public class Customer implements Serializable {

	private static final long serialVersionUID = 4002523319642036414L;

	@Id
	@GeneratedValue
	@Column(name = "CUSTOMER_ID", nullable = false, unique = true)
	private Long id;

	@Column(name = "LASTNAME", nullable = false)
	private String lastname;

	@Column(name = "FIRSTNAME", nullable = false)
	private String firstname;
	
	@Column(name = "BIRTHDATE", nullable = true)
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
