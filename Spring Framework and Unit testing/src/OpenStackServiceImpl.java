import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class OpenStackServiceImpl implements OpenStackService {

	public boolean ifProjectEmpty(String project) {
		if(project == "")
		{
			return false;
		}
		
		return true;
	}
	
	public boolean ifYearEmpty(String year) {
		if(year == "")
		{
			return false;
		}
		return true;
	}
	
	public boolean isYear(String year) {
		String regex = "[0-9]+";
		if(!(year.matches(regex)) || year.length() != 4) {

			return false;
		}
		return true;
	}
	
	public String checkParameters(String project, String year) {
		
		Document doc = null;
		Elements links = null;
		int count = -1;
		
		try {
			Jsoup.connect("http://eavesdrop.openstack.org/meetings/" + project.toLowerCase()).get();
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			return "Project with name < " + project + " > not found";
		}
		
		//Now connect to year
		try { 
			doc = Jsoup.connect("http://eavesdrop.openstack.org/meetings/" + project.toLowerCase() + "/" + year + "/").get();
			links = doc.select("td");
			}
				
		catch (Exception exp) {
			exp.printStackTrace();
			return "Invalid year < " + year + " > for project < " + project + " >";
		}
		if (doc != null){
			if (links != null) {
				ListIterator<Element> iter = links.listIterator();
				while(iter.hasNext()) {
		    		//Pick up project names
	    			Element element = (Element) iter.next();
	    			String itemName = element.html();
	    			if (itemName != null && itemName.contains(project.toLowerCase())) {
	    				count++;
	    			}
		    	}
			}
		}
		
		return "Number of meeting files: " + count;
	}
	 
}
