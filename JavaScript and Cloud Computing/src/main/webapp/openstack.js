/**
 * 
 */


function myFunction() {
    var text;
    var request = new XMLHttpRequest();
    var url = "/js/getSolum/solum_team_meeting/";
    	
    	
    request.open("GET", url, false);
    request.send();

    text = request.response;
    var stringOutput = text;
    var res = stringOutput.split("/");
    document.getElementById("demo").border=1;
    document.getElementById("demo").innerHTML += "<tr><th> Year </th>" + "<th> Number of Meetings </th></tr>"; 
    var row = 1;
    for(i = 0; i <= res.length - 1; i += 2)
    {
    	if(res[i] == "") {
    		break;
    	}
    	
    	if(i + 1 != "") {
    		document.getElementById("demo").innerHTML += "<td border>" + res[i] + "</td>" + "<td>" + res[i+1] + "</td>";
    	}
    }
    

    console.log(request.status);
    console.log(request.statusText);

    
}