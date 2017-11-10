package assign.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import assign.domain.Project;
import assign.resources.DBInterface;
import assign.domain.Meeting;

import java.util.logging.*;
public class DBLoader implements DBInterface{

	private SessionFactory sessionFactory;
	private ServiceRegistry registry;
	
	Logger log;
	
	@SuppressWarnings("deprecation")
	public DBLoader() {
        sessionFactory = new Configuration().configure().buildSessionFactory(); 
        log = Logger.getLogger("EavesdropReader");
        
//		Configuration config = new Configuration().configure();
//		registry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();     
//		log = Logger.getLogger("DBLoader.class");
//	    sessionFactory = config.buildSessionFactory(registry);

	}
	
	public Project addProject(Project project) throws Exception {
		
		Session session = sessionFactory.openSession();
		Transaction t = null;
		Project proj = null;
		long projId = -1;
		
		try {
			t = session.beginTransaction();
			
			String projectName = project.getName();
			String projectDescription = project.getDescription();
			proj = new Project(projectName, projectDescription);
			projId = proj.getProjectId();
			proj.setProjectId(projId);
			session.save(proj);
			
			t.commit();
		}
		catch (Exception e) {
			
			if(t != null)
			{
				t.rollback();
				e.printStackTrace();
			}
			
		}
		finally {
			session.close();
		}
		
		return proj;
	}
	
	public Project getProject(long project_id) throws Exception {
		Session session = sessionFactory.openSession();
		
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Project.class).add(Restrictions.eq("projectId", project_id));
			if(criteria == null) {
				session.close();
				return null;
			}
			List<Project> projects = criteria.list();
			if(projects.size() > 0) {
				Hibernate.initialize(projects.get(0).getMeetings());
				session.close();
				return projects.get(0);
			}
			else {
				session.close();
				return null;
			}		
		
	}
	
	
	
	public Meeting updateMeeting(Meeting meeting, long meetingId) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction t = null;
		Meeting meet = null;
		try {
			t = session.beginTransaction();
			meet = (Meeting) session.get(Meeting.class, meetingId);
			String meetName = meeting.getName();
			String meetYear = meeting.getYear();
			meet.setName(meetName);
			meet.setYear(meetYear);
			session.update(meet);
			t.commit();
		}
		catch (Exception e) {
			if(t != null)
			{
				t.rollback();
				e.printStackTrace();
			}
		}
		finally {
			session.close();
		}
		return meet;

	}
	
	
	public boolean deleteProject(long projectId) throws Exception {
		
		Session session = sessionFactory.openSession();		
		session.beginTransaction();
		String query = "delete from project project where project.projectId = :projectId";		
				
		Project project = (Project) session.createCriteria(Project.class).add(Restrictions.eq("projectId", projectId)).uniqueResult();
		if(project == null)
		{
			return false;
		}
		session.delete(project);
		//Project proj = (Project) session.createQuery(query).setParameter("projectId", projectId).list().get(0);
		//session.createQuery(query).setString("projectId", projectId).executeUpdate();
		
        //session.delete(proj);

        session.getTransaction().commit();
        session.close();
        return true;
	}
	
	public Meeting addMeeting(Meeting meeting, Project project) throws Exception {
		
		Session session = sessionFactory.openSession();
		Transaction t = null;
		Meeting meet = null;
		long meetId = -1;
		
		try {
			t = session.beginTransaction();
			
			String meetingName = meeting.getName();
			String meetingYear = meeting.getYear();
			meet = new Meeting(meetingName, meetingYear);
			project.getMeetings().add(meeting);
			meetId = meet.getMeetingId();
			meet.setMeetingId(meetId);
			meet.setProject(project);
			session.save(meet);
			
			t.commit();
		}
		catch (Exception e) {
			
			if(t != null)
			{
				t.rollback();
				e.printStackTrace();
			}
			
		}
		finally {
			session.close();
		}
		
		return meet;
	}
	public Meeting getMeeting(long meeting_id) throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Meeting.class).add(Restrictions.eq("meetingId", meeting_id));
		
		List<Meeting> meetings = criteria.list();
		
		session.close();
		
		if (meetings.size() > 0) {
			return meetings.get(0);			
		} else {
			return null;
		}
	}
}
