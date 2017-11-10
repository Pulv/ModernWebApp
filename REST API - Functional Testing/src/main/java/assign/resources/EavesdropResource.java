package assign.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import assign.domain.Errors;
import assign.domain.Meetings;
import assign.domain.Projects;
import assign.services.EavesdropService;

@Path("/projects")
public class EavesdropResource {
	
	EavesdropService eavesdropService;
	
	public EavesdropResource() {
		this.eavesdropService = new EavesdropService();
	}

	@GET
	@Path("/helloworld")
	@Produces("text/html")
	public String helloWorld() {
		return "Hello world";		
	}
	
	@GET
	@Path("/helloeavesdrop")
	@Produces("text/html")
	public String helloEavesdrop() {
		return this.eavesdropService.getData();		
	}	
	
	
	@GET
	@Path("/")
	@Produces("application/xml")
	public StreamingOutput getProjects() throws Exception {
		
		final Projects project = this.eavesdropService.getAllProjects();
		
		
		return new StreamingOutput() {
	         public void write(OutputStream outputStream) throws IOException, WebApplicationException {
	            outputProjects(outputStream, project);
	         }
	      };	
	}
	//Output the projects from above 
	protected void outputProjects(OutputStream os, Projects projects) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(Projects.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(projects, os);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}	
	
	
	
	
	
	@GET
	@Path("/{project}/meetings")
	@Produces("application/xml")
	public StreamingOutput getMeeting(@PathParam("project") final String project) throws Exception {
		
		//System.out.println(project);
		final Meetings meetingTimes = this.eavesdropService.getMeetingTimes(project);
		
		
		return new StreamingOutput() {
	         public void write(OutputStream outputStream) throws IOException, WebApplicationException {
	     		if(meetingTimes == null) {
	    			final Errors error = new Errors();
	    			error.setError("Project " + project + " does not exist");
	    			outputError(outputStream, error);
	    		}
	     		else {
		            outputMeetings(outputStream, meetingTimes);
	     		}
	         }
	      };	
	}
	
	protected void outputMeetings(OutputStream os, Meetings meetingTimes) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(Meetings.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(meetingTimes, os);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}
	protected void outputError(OutputStream os, Errors err) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(Errors.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(err, os);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}	
}