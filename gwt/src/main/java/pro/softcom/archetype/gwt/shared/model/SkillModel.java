package pro.softcom.archetype.gwt.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Represents a skill (e.g. UML notation)
 */
public class SkillModel implements IsSerializable {

    private Long id;
    
	private String name;

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
