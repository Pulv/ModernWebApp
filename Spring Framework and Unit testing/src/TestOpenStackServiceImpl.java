import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;

public class TestOpenStackServiceImpl {
	
	OpenStackService openStackService;
	
	@Before
	public void setup() {
		openStackService = new OpenStackServiceImpl();
	}
	
	@Test //1. Test when both project and year parameters are passed in, but year has no value ("year=")
	public void testifYearEmptyNothing() {
		String ret = "";
		if(!openStackService.ifYearEmpty("")) {
			ret = "Required parameter < year > missing";
		}
		assertEquals("Required parameter < year > missing", ret);
	}
	
	@Test //2. Test when both project and year parameters are passed in, but project has no value ("project=")
	public void testifProjectEmptyNothing() {
		String ret = "";
		if(!openStackService.ifProjectEmpty("")) {
			ret = "Required parameter < project > missing";
		}
		assertEquals("Required parameter < project > missing", ret);		
	}
	
	@Test //3. Test of the number of files present in project solum in 2015
	public void testProjectandYear() {
		String ret = openStackService.checkParameters("solum", "2015");
		assertEquals("Number of meeting files: 4", ret);		
	}
	
	@Test //4. Test that outputs that the year is not valid for solum project
	public void testProjectandYear2() {
		String ret = openStackService.checkParameters("solum", "2017");
		assertEquals("Invalid year < 2017 > for project < solum >", ret);		
	}
	
	@Test //5. Test that outputs that the year inputed is not valid
	public void testIsYear() {
		String ret = "";
		if(!openStackService.isYear("20005")) {
			ret = "Invalid year parameter. It must be only digits with length 4!";
		}
		assertEquals("Invalid year parameter. It must be only digits with length 4!", ret);		
	}
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	//Do not believe this test is working correctly. Checking if the output of the service is correct
	@Test //6.
	public void testProjectAndYearMock() {
		OpenStackService service = mock(OpenStackService.class);
		when(service.checkParameters("solum", "2014")).thenReturn("Number of meeting files: 4");
		verify(service);
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	}
	
	
}
