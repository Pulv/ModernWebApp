package assign.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import assign.domain.Project;

public class ProjectServiceImpl implements ProjectService {

	String dbURL = "";
	String dbUsername = "";
	String dbPassword = "";
	DataSource ds;

	// DB connection information would typically be read from a config file.
	public ProjectServiceImpl(String dbUrl, String username, String password) {
		this.dbURL = dbUrl;
		this.dbUsername = username;
		this.dbPassword = password;
		
		ds = setupDataSource();
	}
	
	public DataSource setupDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUsername(this.dbUsername);
        ds.setPassword(this.dbPassword);
        ds.setUrl(this.dbURL);
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        return ds;
    }
	
	public Project addProject(Project project) throws Exception {
		Connection conn = ds.getConnection();
		
		String insert = "INSERT INTO projects(name, description) VALUES(?, ?)";
		PreparedStatement stmt = conn.prepareStatement(insert,
                Statement.RETURN_GENERATED_KEYS);

		
		stmt.setString(1, project.getName());
		stmt.setString(2, project.getDescription());
		
		int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
        	return null;
            //throw new SQLException("Creating project failed, no rows affected.");
        }
        
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
        	project.setProjectId(generatedKeys.getInt(1));
        }
        else {
        	return null;
            //throw new SQLException("Creating project failed, no ID obtained.");
        }
        
        // Close the connection
        conn.close();
        
		return project;
	}



    public Project getProject(int project_id) throws Exception {
	String query = "select * from projects where project_id=?";
	Connection conn = ds.getConnection();
	PreparedStatement s = conn.prepareStatement(query);
	s.setString(1, String.valueOf(project_id));

	ResultSet r = s.executeQuery();
	
	if(r == null)
	{
		return null;
	}
	if (!r.next()) {
	    return null;
	}
	
	Project project = new Project();
	project.setDescription(r.getString("description"));
	project.setName(r.getString("name"));
	project.setProjectId(r.getInt("project_id"));
	return project;
    }
    
    
    public Project updateProject(int project_id, Project project) throws Exception {
	
    	String query = "select * from projects where project_id=?";
    	Connection conn = ds.getConnection();
    	PreparedStatement s = conn.prepareStatement(query);
    	s.setString(1, String.valueOf(project_id));
    	
    	ResultSet r = s.executeQuery();
    	if(r == null)
    	{
    		return null;
    	}
    	if (!r.next()) {
    	    return null;
    	}
    	
    	String updateCall = "UPDATE projects SET description=?,name=? WHERE project_id=?";
    	PreparedStatement update = conn.prepareStatement(updateCall);
    	update.setString(1, project.getDescription());
    	update.setString(2, project.getName());
    	update.setString(3, String.valueOf(project_id));
    	int command = update.executeUpdate();
    	
    	if(command == 0)
    	{
    		return null;
    	}
    	
    	project.setDescription(r.getString("description"));
    	project.setName(r.getString("name"));
    	//project.setProjectId(r.getInt("project_id"));
    	return project;
        }
    
    public void deleteProject(int project_id) throws Exception {
    	String query = "DELETE FROM projects WHERE project_id=?";
    	Connection conn = ds.getConnection();
    	PreparedStatement s = conn.prepareStatement(query);
    	s.setString(1, String.valueOf(project_id));
    	
    	int command = s.executeUpdate();
    	
    }


}
