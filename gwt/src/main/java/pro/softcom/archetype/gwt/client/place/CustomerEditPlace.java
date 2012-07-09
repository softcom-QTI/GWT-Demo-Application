package pro.softcom.archetype.gwt.client.place;

import pro.softcom.archetype.gwt.shared.model.CustomerModel;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CustomerEditPlace extends Place {
	private static final String PLACE_TOKEN_NAME = "customerEdit";

	private final CustomerModel customer;
	
	public CustomerEditPlace(CustomerModel customer){
		this.customer = customer;
	}
	
	public static class Tokenizer implements PlaceTokenizer<CustomerEditPlace> {
		public String getToken(CustomerEditPlace place) {
			return PLACE_TOKEN_NAME;
		}

		public CustomerEditPlace getPlace(String token) {
			return new CustomerEditPlace(new CustomerModel());
		}
	}
	
	public CustomerModel getCustomer(){
		return customer;
	}
}
