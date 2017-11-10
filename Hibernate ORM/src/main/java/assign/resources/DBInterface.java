package assign.resources;

import assign.domain.Meeting;
import assign.domain.Project;

public interface DBInterface {
	
	public Project addProject(Project project) throws Exception;
	
	public Project getProject(long project_id) throws Exception;
	
	public Meeting updateMeeting(Meeting meeting, long meetingId) throws Exception;
	
	public boolean deleteProject(long projectId) throws Exception;
	
	public Meeting addMeeting(Meeting meeting, Project project) throws Exception;
	
	public Meeting getMeeting(long meeting_id) throws Exception;
}
