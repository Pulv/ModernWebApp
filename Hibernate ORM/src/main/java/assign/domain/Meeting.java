package assign.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "meeting")
@XmlRootElement(name = "meeting")
@XmlAccessorType(XmlAccessType.FIELD)
public class Meeting {

	 
	private String name;
	private String year;
	
	@XmlAttribute
	private long id;
	
	@XmlTransient
	private Project project;

		
	//List<String> link = null;
	
	public Meeting() {
		
	}
	
	public Meeting(String name, String year) {
		this.name = name;
		this.year = year;
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	
	@Column(name = "year")
	public String getYear() {
		return year;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	public Long getMeetingId() {
		return id;
	}
	
	public void setMeetingId(Long id) {
		this.id = id;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setProject(Project proj) {
		this.project = proj;
	}
	
	@ManyToOne
	@JoinColumn(name = "projectId")
	public Project getProject() {
		return this.project;
	}
	
//	public List<String> getLink() {
//        return link;
//    }
// 
//    public void setLink(List<String> link) {
//        this.link = link;
//    }
}
