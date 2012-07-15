package pro.softcom.archetype.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SkillManagePlace extends Place {
    private static final String PLACE_TOKEN_NAME = "skillManage";

    public static class Tokenizer implements PlaceTokenizer<SkillManagePlace> {

        public String getToken(SkillManagePlace place) {
            return PLACE_TOKEN_NAME;
        }

        public SkillManagePlace getPlace(String token) {
            return new SkillManagePlace();
        }

    }

}
