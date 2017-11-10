package assign.resources;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.junit.Test;
import org.xml.sax.InputSource;

import static org.junit.Assert.*;

import java.io.StringReader;


public class TestEavesdropResources {

	
	@Test
	public void testEavesdropServiceProject() throws Exception {
		
	    Client client = ClientBuilder.newClient();
	    try
	    {    	
	    	Response response = client.target("http://localhost:8080/assignment3/myeavesdrop/projects/").request(MediaType.APPLICATION_XML).get();
	    	String output = response.readEntity(String.class);
	    	
	    	DocumentBuilder build = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    	InputSource source = new InputSource();
	    	source.setCharacterStream(new StringReader(output));
	    	org.w3c.dom.Document doc = build.parse(source);
  	
	    	
	    	//Testing first 6 projects
	    	String project1 = doc.getElementsByTagName("project").item(0).getTextContent();
	    	assertEquals("3rd_party_ci/", project1);
	    	
	    	String project2 = doc.getElementsByTagName("project").item(1).getTextContent();
	    	assertEquals("17_12_2015/", project2);
	    	
	    	String project3 = doc.getElementsByTagName("project").item(2).getTextContent();
	    	assertEquals("2015_09_17/", project3);
	    	
	    	String project4 = doc.getElementsByTagName("project").item(3).getTextContent();
	    	assertEquals("2015_10_15/", project4);
	    	
	    	String project5 = doc.getElementsByTagName("project").item(4).getTextContent();
	    	assertEquals("2015_10_29/", project5);
	    	
	    	String project6 = doc.getElementsByTagName("project").item(5).getTextContent();
	    	assertEquals("____freezer_meeting_23_07_2015____/", project6);
	    	
	    	String project7 = doc.getElementsByTagName("project").item(6).getTextContent();
	    	assertEquals("___endmeeting/", project7);
	    	
	    	String project8 = doc.getElementsByTagName("project").item(7).getTextContent();
	    	assertEquals("__ironic_neutron/", project8);
	    	
	    	String project9 = doc.getElementsByTagName("project").item(8).getTextContent();
	    	assertEquals("__networking_l2gw/", project9);
	    	
	    	String project10 = doc.getElementsByTagName("project").item(9).getTextContent();
	    	assertEquals("__poppy/", project10);
	    	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	    	
	    	
	    	
	    	
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    }

		
	}
	
	@Test
	public void getProjectSolumYears() throws Exception
	{
		Client client = ClientBuilder.newClient();
	    try
	    {    	
	    	Response response = client.target("http://localhost:8080/assignment3/myeavesdrop/projects/solum_team_meeting/meetings")
	    			.request(MediaType.APPLICATION_XML).get();
	    	String output = response.readEntity(String.class);
	    	
	    	DocumentBuilder build = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    	InputSource source = new InputSource();
	    	source.setCharacterStream(new StringReader(output));
	    	org.w3c.dom.Document doc = build.parse(source);
	    	
	    	
	    	//Testing the years for solum_team_meetings
	    	String project1 = doc.getElementsByTagName("year").item(0).getTextContent();
	    	//System.out.println(project1);
	    	assertEquals("2013/", project1);
	    	
	    	String project2 = doc.getElementsByTagName("year").item(1).getTextContent();
	    	//System.out.println(project2);
	    	assertEquals("2014/", project2);
	    	
	    	String project3 = doc.getElementsByTagName("year").item(2).getTextContent();
	    	//System.out.println(project3);
	    	assertEquals("2015/", project3);
	    	
	    	String project4 = doc.getElementsByTagName("year").item(3).getTextContent();
	    	//System.out.println(project4);
	    	assertEquals("2016/", project4);
	    	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    }

	}
	
	@Test
	public void getProjectCityYears() throws Exception
	{
		Client client = ClientBuilder.newClient();
	    try
	    {    	
	    	Response response = client.target("http://localhost:8080/assignment3/myeavesdrop/projects/3rd_party_ci/meetings")
	    			.request(MediaType.APPLICATION_XML).get();
	    	String output = response.readEntity(String.class);
	    	
	    	DocumentBuilder build = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    	InputSource source = new InputSource();
	    	source.setCharacterStream(new StringReader(output));
	    	org.w3c.dom.Document doc = build.parse(source);
	    	
	    	
	    	//Testing the years for 3rd_party_ci
	    	String project1 = doc.getElementsByTagName("year").item(0).getTextContent();
	    	//System.out.println(project1);
	    	assertEquals("2014/", project1);
	    	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    }

	}
	
	@Test
	public void getNonProject() throws Exception
	{
		Client client = ClientBuilder.newClient();
	    try
	    {    	
	    	Response response = client.target("http://localhost:8080/assignment3/myeavesdrop/projects/non-existent-project/meetings")
	    			.request(MediaType.APPLICATION_XML).get();
	    	String output = response.readEntity(String.class);
	    	
	    	DocumentBuilder build = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    	InputSource source = new InputSource();
	    	source.setCharacterStream(new StringReader(output));
	    	org.w3c.dom.Document doc = build.parse(source);
	    	
	    	
	    	//Testing a target of a non-project url
	    	String project1 = doc.getElementsByTagName("error").item(0).getTextContent();
	    	//System.out.println(project1);
	    	assertEquals("Project non-existent-project does not exist", project1);
	    	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    }

	}
	
}
