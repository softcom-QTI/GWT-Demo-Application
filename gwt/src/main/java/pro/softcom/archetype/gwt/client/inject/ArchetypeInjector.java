package pro.softcom.archetype.gwt.client.inject;

import pro.softcom.archetype.gwt.client.base.ArchetypeApp;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(ArchetypeModule.class)
public interface ArchetypeInjector extends Ginjector {
    ArchetypeApp getArchetypeApp();
}
