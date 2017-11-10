import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class OpenStackMeetingsController {

	private OpenStackService openStackService;

	public OpenStackMeetingsController() {
		
	}
	
	
	public OpenStackMeetingsController(OpenStackService OpenStackService) {
		this.openStackService = OpenStackService;
	}
	
	
	@ResponseBody
    @RequestMapping(value = "/")
    public String assignment2Open()
    {
        return "Opened assignment2!";
        
    }
	@ResponseBody
    @RequestMapping(value = "/openstackmeetings")
    public String assignment2Greeting()
    {
        return "Welcome to OpenStack meeting statistics calculation page. Please provide project and year as query parameters.";
    }
	
	@ResponseBody
    @RequestMapping(value = "/openstackmeetings", params = {"project"},  method=RequestMethod.GET)
    public String getProj(@RequestParam("project") String project) {
		if(project == "") {
			return "Required parameter < project > is empty!";
		}
		
			return "Required parameter < year > missing";

	}
	
	@ResponseBody
    @RequestMapping(value = "/openstackmeetings", params = {"year"},  method=RequestMethod.GET)
    public String getYear(@RequestParam("year") String year) {
		if(year == "") {
			return "Required parameter < year > is empty!";
		}
			return "Required parameter < project > missing";
	}
	
	//If both passed through are not empty, check it
	@ResponseBody
    @RequestMapping(value = "/openstackmeetings", params = {"project", "year"},  method=RequestMethod.GET)
    public String getURI(@RequestParam("project") String project, @RequestParam("year") String year)  {
		
		String projectName = project;
		String yearM = year;
		
		if(!openStackService.ifProjectEmpty(project)) {
			return "Required parameter < project > missing";
		}
		if(!openStackService.ifYearEmpty(year)) {
			return "Required parameter < year > missing";
		}
		
		if(!openStackService.isYear(yearM)) {
			return "Invalid year parameter. It must be only digits with length 4!";
		}
		
		return openStackService.checkParameters(project, year);
		 
		
		
    }
}