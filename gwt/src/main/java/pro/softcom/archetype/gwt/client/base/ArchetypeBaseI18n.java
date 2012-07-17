package pro.softcom.archetype.gwt.client.base;

import com.google.gwt.i18n.client.Constants;

/**
 * This interface contains the texts that are used in the base structure of the application, like the titles, menus, etc.
 */
public interface ArchetypeBaseI18n extends Constants {

    String title();

    String subtitle();

    String menuSearch();
    String menuSearchCollaborators();

    String menuManage();
    String menuManageEmployees();
    String menuManageProjects();
    String menuManageSkills();
    String menuManageGroups();
    String menuManageFunctions();

    String menuHr();

    String menuAdmin();

	String menuHome();


}
