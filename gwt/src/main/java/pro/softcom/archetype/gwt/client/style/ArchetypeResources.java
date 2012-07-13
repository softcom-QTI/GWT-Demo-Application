package pro.softcom.archetype.gwt.client.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class ArchetypeResources {

    public interface Resources extends ClientBundle {
        @Source("logo.png")
        ImageResource logo();
    }

    private static Resources resources;

    static {
        resources = GWT.create(Resources.class);
    }

    public static Resources resources() {
        return resources;
    }
}
