package js;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.parsers.DocumentBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


@Path("/getSolum")
public class OpenStackResource {
	
	public OpenStackResource() {
		
	}
	
	@GET
	@Path("/solum_team_meeting")
	@Produces("text/xml")
	public String getSolumMeeting() {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://eavesdrop.openstack.org/meetings/solum_team_meeting").get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String output = "";
		int count = 0;
		Elements links = doc.select("a");
		//Loop through to get meeting years
		if (doc != null){
			if (links != null) {
				ListIterator<Element> iter = links.listIterator();
				while(iter.hasNext()) {
		    		//Pick up project names
	    			Element element = (Element) iter.next();
	    			String itemName = element.text();
	    			if((!itemName.equals("Name")) && (!itemName.equals("Last modified")) && (!itemName.equals("Size")) && (!itemName.equals("Description"))
	    					&& (!itemName.equals("Parent Directory")))
	    			{
	    				String year = itemName;
	    				output += itemName.substring(0, 5);
		    			try { 
		    				doc = Jsoup.connect("http://eavesdrop.openstack.org/meetings/solum_team_meeting/" + year).get();
		    				links = doc.select("a");
		    				}	    					
		    			catch (Exception exp) {
		    				exp.printStackTrace();
		    			}
		    			
		    			/* Now looping through to find the number of meetings held! */
						ListIterator<Element> numIter = links.listIterator();
						String firstName = null;
		    			while(numIter.hasNext()) {
			    			Element ele = (Element) numIter.next();
			    			String item = ele.html();
		    				if(item != null && firstName == null && (!item.equals("Name")) && (!item.equals("Last modified")) && 
			    					(!item.equals("Size")) && (!item.equals("Description")) && 
			    					(!item.equals("Parent Directory"))) {
				    				item = item.substring(0, 35);
				    				firstName = item;
				    				//count++;
				    				
				    				continue;
		    				}
		    				else if(item != null && (!item.equals("Name")) && (!item.equals("Last modified")) && 
			    					(!item.equals("Size")) && (!item.equals("Description")) && 
			    					(!item.equals("Parent Directory"))) {

		    					if(!numIter.hasNext())
		    					{
		    						break;
		    					}

		    					
			    				item = item.substring(19, 29);
			    				System.out.println(item);
				    			if (!item.equals(firstName)) { 
				    				count++;
				    				System.out.println(count);
				    				firstName = item;
			    				}		
			    			}
		    			}
		    			output += count + "/";
		    			count = 0;
		    			
	    			}
	    		}
		    }
		}
		return output;
	}
}
