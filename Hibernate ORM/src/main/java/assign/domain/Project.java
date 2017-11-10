package assign.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "project")
@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class Project {
	
	private String name;
	
	private String description;
	
	

	
	@XmlTransient
	private long projectId;
	
	@XmlElementWrapper(name="meetings")
	private Set<Meeting> meeting = null;
	
	
	
	public Project() {
		
	}
	
	public Project(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	
	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	
	@Id
//	@GeneratedValue(generator="increment")
//	@GenericGenerator(name="increment", strategy ="increment")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@XmlAttribute(name = "id")
	public long getProjectId() {
		return projectId;
	}
	
	@OneToMany(mappedBy = "project",fetch = FetchType.EAGER)
	@Cascade({CascadeType.DELETE})
	public Set<Meeting> getMeetings() {
		return this.meeting;
	}
	
	public void setMeetings(Set<Meeting> meetings) {
		this.meeting = meetings;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setProjectId(long project_id) {
		this.projectId = project_id;
	}
	
	
//	@Column(name = "meetings")
//	public List<String> getLink() {
//        return link;
//    }
// 
//    public void setLink(List<String> link) {
//        this.link = link;
//    }
}
