package assign.services;


import assign.domain.Project;

public interface ProjectService {

	public Project addProject(Project p) throws Exception;
	
    public Project getProject(int courseId) throws Exception;
    
    public Project updateProject(int project_id, Project project) throws Exception;
    
    public void deleteProject(int project_id) throws Exception;

}
