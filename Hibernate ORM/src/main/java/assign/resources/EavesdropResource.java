package assign.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.ws.rs.core.Response;


import org.xml.sax.InputSource;

import assign.domain.Meeting;
import assign.domain.Project;
import assign.services.DBLoader;
import assign.services.ProjectService;
import assign.services.ProjectServiceImpl;

@Path("/projects")
public class EavesdropResource {
	
	DBLoader dbLoad;
	ProjectService projectService;
	String password;
	String username;
	String dburl;	
	
	public EavesdropResource(@Context ServletContext servletContext) {
//		dburl =  "jdbc:mysql://" + servletContext.getInitParameter("DBHOST") + ":3306/" + servletContext.getInitParameter("DBNAME");
//		username = servletContext.getInitParameter("DBUSERNAME");
//		password = servletContext.getInitParameter("DBPASSWORD");
//		this.projectService = new ProjectServiceImpl(dburl, username, password);
		this.dbLoad = new DBLoader();
	}
	
	@GET
	@Path("/helloworld")
	@Produces("text/html")
	public String helloWorld() {
//		System.out.println("Inside helloworld");
//		System.out.println("DB creds are:");
//		System.out.println("DBURL:" + dburl);
//		System.out.println("DBUsername:" + username);
//		System.out.println("DBPassword:" + password);		
		return "Hello world";		
	}
		

	
	@GET
	@Path("/{project_id}")
	@Produces("application/xml")
	public Response getProjects(@PathParam("project_id") final String project_id) throws Exception {
		long proj_id = 0;
		try
		{
			proj_id = Integer.parseInt(project_id);
		}
		catch (Exception c)
		{
			c.printStackTrace();
			return Response.status(400).build();
		}
		
		final Project project = this.dbLoad.getProject(proj_id);
		
		if(project == null)
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		else
		{
		    StreamingOutput output = new StreamingOutput() {
		         public void write(OutputStream outputStream) throws IOException, WebApplicationException {
		        	 if(project != null)
		        	 {
//		        		Response.status(Response.Status.OK);
		 	            outputProject(outputStream, project);
		        	 }
		         }
		      };	
			return Response.ok(output).build();
		}    
	}
	
	@POST
	@Path("")
	@Consumes("application/xml")
	public Response createProject(Project project) throws Exception {
		Project added = null;
		if(!(project.getName().trim().length() > 0) || !(project.getDescription().trim().length() > 0))
		{
			return Response.status(400).build();
		}
		if(project.getName() == null || project.getDescription() == null)
		{
			return Response.status(400).build();
		}
		try 
		{
			//System.out.println("Im getting here!");
			added = this.dbLoad.addProject(project);
		}
		catch (Exception c)
		{
			c.printStackTrace();
			return Response.status(400).build();
			
		}
		if(added == null)
		{
			return Response.status(400).build();
		}
		
	    return Response.created(URI.create("/projects/" + added.getProjectId())).status(201).build();
	}
	
	/* Making a meeting for a project */
	@POST
	@Path("/{project_id}/meetings")
	@Consumes("application/xml")
	public Response addMeeting(@PathParam("project_id") final String project_id, Meeting meeting) throws Exception {
		Meeting added = null;
				
		int proj_id = -1;
		try
		{
			proj_id = Integer.parseInt(project_id);
		}
		catch (Exception c)
		{
			c.printStackTrace();
			return Response.status(400).build();
		}
		
		Project projectCheck = this.dbLoad.getProject(proj_id);
		if(projectCheck == null)
		{			return Response.status(400).build();
		}
		
		
		if(!(meeting.getName().trim().length() > 0) || !(meeting.getYear().trim().length() > 0))
		{
			return Response.status(400).build();
		}
		if(meeting.getName() == null || meeting.getYear() == null)
		{
			return Response.status(400).build();
		}
		try 
		{
			added = this.dbLoad.addMeeting(meeting, projectCheck);
		}
		catch (Exception c)
		{
			c.printStackTrace();
			return Response.status(400).build();
			
		}
		if(added == null)
		{
			return Response.status(400).build();
		}
		
		return Response.status(201).build();
	}
	
	
	
	
	
	
	
	@PUT
	@Path("/{project_id}/meetings/{meeting_id}")
	@Consumes("application/xml")
	public Response updateMeeting(@PathParam("project_id") final String project_id, @PathParam("meeting_id") final String meeting_id, Meeting meet) throws Exception {
		int proj_id = -1;
		int meet_id = -1;
		try
		{
			proj_id = Integer.parseInt(project_id);
			meet_id = Integer.parseInt(meeting_id);
		}
		catch (Exception c)
		{
			c.printStackTrace();
			return Response.status(400).build();
		}
		
		Project projectCheck = this.dbLoad.getProject(proj_id);
		if(projectCheck == null)
		{
			return Response.status(404).build();
		}
    	if(meet.getName() == null || meet.getYear() == null)
    	{
    		return Response.status(400).build();
    	}
    	if(!(meet.getName().trim().length() > 0) || !(meet.getYear().trim().length() > 0))
    	{
    		return Response.status(400).build();
    	}
    	
    	String regex = "[0-9]+";
		if(!(meet.getYear().matches(regex)) || meet.getYear().length() != 4) {

			return Response.status(400).build();
		}
		
		
		Meeting meeting = this.dbLoad.getMeeting(meet_id);
		if(meeting == null)
		{
			return Response.status(404).build();
		}
		Meeting updated = this.dbLoad.updateMeeting(meet, meet_id);
		if(updated == null)
		{
			return Response.status(400).build();
		}
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{project_id}")
	public Response deleteProject(@PathParam("project_id") final String project_id) throws Exception {
		int proj_id = -1;
		boolean delete = false;
		try
		{
			proj_id = Integer.parseInt(project_id);
		}
		catch (Exception c)
		{
			c.printStackTrace();
			return Response.status(400).build();
		}
		Project projectCheck = this.dbLoad.getProject(proj_id);
		if(projectCheck == null)
		{
			return Response.status(404).build();
		}
		else
		{
			delete = this.dbLoad.deleteProject(proj_id);
		}
		/* Added boolean to check if it successfully deleted! */
		if(delete) {
			return Response.ok().build();

		}
		else {
			return Response.status(400).build();
		}
		
	}
	
	protected void outputProject(OutputStream os, Project project) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(Project.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(project, os);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}	

}
