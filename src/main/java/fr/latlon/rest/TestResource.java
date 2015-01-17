package fr.latlon.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Component
@Path("/test")
public class TestResource {

	@Autowired
	private SomeService someService;
	
	@GET
	@Path("/")
	@Produces("application/json")
	public Response test() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("muie", someService.doSth());
		
		return Response.status(200).entity(map).build();
	}
}
