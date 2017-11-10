package assign.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class Project {

	String name;
	String description;
	
	@XmlTransient
	int project_id;
		
	List<String> link = null;

	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	@XmlAttribute(name = "project_id")
	public int getProjectId() {
		return project_id;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setProjectId(int project_id) {
		this.project_id = project_id;
	}
	
	public List<String> getLink() {
        return link;
    }
 
    public void setLink(List<String> link) {
        this.link = link;
    }
}
