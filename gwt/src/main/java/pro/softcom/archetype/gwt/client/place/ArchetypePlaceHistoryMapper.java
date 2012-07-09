package pro.softcom.archetype.gwt.client.place;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ CustomerSearchPlace.Tokenizer.class,
		CustomerEditPlace.Tokenizer.class })
public interface ArchetypePlaceHistoryMapper extends PlaceHistoryMapper {

}
