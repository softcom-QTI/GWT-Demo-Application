package pro.softcom.archetype.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CustomerSearchPlace extends Place {
	private static final String PLACE_TOKEN_NAME = "customerSearch";

	public static class Tokenizer implements
			PlaceTokenizer<CustomerSearchPlace> {

		public String getToken(CustomerSearchPlace place) {
			return PLACE_TOKEN_NAME;
		}

		public CustomerSearchPlace getPlace(String token) {
			return new CustomerSearchPlace();
		}

	}
}
