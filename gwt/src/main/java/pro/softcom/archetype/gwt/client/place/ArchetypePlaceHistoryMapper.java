package pro.softcom.archetype.gwt.client.place;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

//@formatter:off
@WithTokenizers({ 
    SkillManagePlace.Tokenizer.class,
    CustomerSearchPlace.Tokenizer.class,
	CustomerEditPlace.Tokenizer.class 
})
//@formatter:on
public interface ArchetypePlaceHistoryMapper extends PlaceHistoryMapper {

}
