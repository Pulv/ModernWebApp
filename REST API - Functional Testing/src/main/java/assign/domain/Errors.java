package assign.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "output")
@XmlAccessorType(XmlAccessType.FIELD)
public class Errors {
		
	String error = null;

	
	public String getError() {
        return error;
    }
	
    public void setError(String err) {
        this.error = err;
    }
}
