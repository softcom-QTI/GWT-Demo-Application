package pro.softcom.archetype.gwt.shared.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Represents a topic containing skills. 
 * 
 * Topics are organized in a parent-child hierarchy
 */
public class TopicModel implements IsSerializable {

    private Long id;
    
	private String name;
	
	private List<TopicModel> childTopics = new ArrayList<TopicModel>();
	
	private List<SkillModel> skills = new ArrayList<SkillModel>();

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

	public List<TopicModel> getChildTopics() {
		return childTopics;
	}

	public void setChildTopics(List<TopicModel> childTopics) {
		this.childTopics = childTopics;
	}

	public List<SkillModel> getSkills() {
		return skills;
	}

	public void setSkills(List<SkillModel> skills) {
		this.skills = skills;
	}
	
	
}
