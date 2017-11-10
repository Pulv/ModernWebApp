package assign.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "meetings")
@XmlAccessorType(XmlAccessType.FIELD)
public class Meetings {
		
	List<String> year = null;

	
	public List<String> getTime() {
        return year;
    }
 
    public void setTime(List<String> link) {
        this.year = link;
    }
}
