package assign.services;

import java.util.ArrayList;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import assign.domain.Meetings;
import assign.domain.Projects;

import java.io.IOException;



public class EavesdropService {
		
	public String getData() {
		return "Hello from Eavesdrop service.";
	}
	
	//Get all projects from the path ("/")
	public Projects getAllProjects() throws Exception {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://eavesdrop.openstack.org/meetings/").get();
		}
		catch (Exception exp) {
			exp.printStackTrace();
		}
		
		//Create a new Projects to have an arraylist
		Projects project = new Projects();
		project.setProjects(new ArrayList<String>());
		
		
		Elements links = doc.select("a");
		//Loop through to get project names
		if (doc != null){
			if (links != null) {
				ListIterator<Element> iter = links.listIterator();
				while(iter.hasNext()) {
		    		//Pick up project names
	    			Element element = (Element) iter.next();
	    			String itemName = element.text();
	    			if((!itemName.equals("Name")) && (!itemName.equals("Last modified")) && (!itemName.equals("Size")) && (!itemName.equals("Description"))
	    					&& (!itemName.equals("Parent Directory")))
	    			project.getProjects().add(itemName);
	    			}
		    }
		}
		return project;
	}
	
	
	//Get meeting times
	public Meetings getMeetingTimes(String project) throws Exception {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://eavesdrop.openstack.org/meetings/" + project).get();
		}
		catch (Exception exp) {
			exp.printStackTrace();
			return null;
		}
		
		//Create a new Projects to have an arraylist
		Meetings meeting = new Meetings();
		meeting.setTime(new ArrayList<String>());
		
		
		Elements links = doc.select("a");
		//Loop through to get project names
		if (doc != null){
			if (links != null) {
				ListIterator<Element> iter = links.listIterator();
				while(iter.hasNext()) {

	    			Element element = (Element) iter.next();
	    			String itemName = element.text();
	    			if((!itemName.equals("Name")) && (!itemName.equals("Last modified")) && (!itemName.equals("Size")) && (!itemName.equals("Description"))
	    					&& (!itemName.equals("Parent Directory")))
	    			meeting.getTime().add(itemName);
	    			}
		    }
		}
		return meeting;
	}
	
	
}
