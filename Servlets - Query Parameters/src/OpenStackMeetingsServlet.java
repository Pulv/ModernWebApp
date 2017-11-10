import java.net.*;
import java.util.ArrayList;
import java.util.ListIterator;
import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * Servlet implementation class OpenStackMeetingsServlet
 */
@WebServlet("/openstackmeetings")
public class OpenStackMeetingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<String> URLS = new ArrayList<String>();
	ArrayList<String> DATA = new ArrayList<String>();
	int x = -1;
	boolean i = false;
	boolean wait = false;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OpenStackMeetingsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*URL webURL = new URL("http://eavesdrop.openstack.org/meetings/");
		BufferedReader input = new BufferedReader(new InputStreamReader(webURL.openStream()));
		
		
		String read; 
		while((read = input.readLine()) != null) {
			System.out.println(read);
		}
		input.close();
		response.getWriter().append("Served at:").append(request.getContextPath());
		response.getWriter().append("Test").append(request.getContextPath());*/
		PrintWriter writer = response.getWriter();
		//String project = request.getParameter("project");

		
		try {
			//String URL = "http://eavesdrop.openstack.org/meetings/" + project;
			String session = request.getParameter("session");
			String project = request.getParameter("project");
			String year = request.getParameter("year");
			String regex = "[0-9]+";


			Document docAll = Jsoup.connect("http://eavesdrop.openstack.org/meetings/").get();
			writer.println("Visited URLS:");
			//Session finding
			if (session != null) {
				if(session.toLowerCase().equals("start")) {
					//URLS.add(request.getRequestURL() + "?" + request.getQueryString());
					i = true;
				}
				if(session.toLowerCase().equals("end")) {
					for(int y = 0; y < URLS.size(); y++) {
						writer.println(URLS.get(y));
					}
					i = false;
					URLS.clear();
				}
				if (!(session.toLowerCase().equals("end")) && !(session.toLowerCase().equals("start"))){
					writer.println("Session parameter must either be \"start or end\"");
				}
			}
			if(i)
			{							
				for(int y = 0; y < URLS.size(); y++) {
					writer.println(URLS.get(y));
					
				}
				if(session != null)
				{
				URLS.add(request.getRequestURL() + "?" + request.getQueryString());
				}
			}
			if(year == null && project == null && session == null)
			{
				writer.println("URL Data:\n");
				return;
			}
			else if((year == null || project == null) && session == null) {
				if(year == null) {
					writer.println("Required parameter \"year\" is missing");
					return;
				}
				else if (project == null) {
					writer.println("Required parameter \"project\" is missing");
					return;
				}
			}
			
			Document doc = null;
			Elements links = null;
			//Checking if year is passable
			if(year != null) {
				if(!(year.matches(regex)) || year.length() != 4) {
					writer.println("Invalid year parameter. It must be only digits with length 4!");
					return;
				}
			}
			if(session == null) {
				try {
					Jsoup.connect("http://eavesdrop.openstack.org/meetings/" + project.toLowerCase()).get();
				}
				catch (Exception exp)
				{
					exp.printStackTrace();
					writer.println("Project with name <" + project + "> not found");
					return;
				}
				try { 
					
					doc = Jsoup.connect("http://eavesdrop.openstack.org/meetings/" + project.toLowerCase() + "/" + year + "/").get();
					if(i) {
						URLS.add(request.getRequestURL() + "?" + request.getQueryString());
					}
					links = doc.select("td");
					}
						
				catch (Exception exp) {
					exp.printStackTrace();
					writer.println("Invalid year <" + year + "> for project <" + project + ">");
					return;

				}
			}
				
					response.getWriter().println("URL Data:");
						if (doc != null){
							if (links != null) {
								ListIterator<Element> iter = links.listIterator();
								while(iter.hasNext()) {
						    		//Pick up project names
					    			Element element = (Element) iter.next();
					    			String itemName = element.text();
					  
					    			if (itemName != null && itemName.contains(project.toLowerCase()) && element.nextElementSibling() != null) {
					    				writer.println(itemName + " " + element.nextElementSibling().text());
					    			}
						    	}
							}
						}

			
		}
		catch (Exception exp) {
			exp.printStackTrace();
		}	


		
		
}
		
	
	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
