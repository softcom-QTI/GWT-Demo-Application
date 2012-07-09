package pro.softcom.archetype.gwt.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CustomerSearchCriteriaModel implements IsSerializable {

	private String firstname;
	private String lastname;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
