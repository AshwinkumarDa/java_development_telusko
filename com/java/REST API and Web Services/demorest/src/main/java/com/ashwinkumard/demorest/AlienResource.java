package com.ashwinkumard.demorest;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("aliens")
public class AlienResource {
	// /usr/local/tomcat11/bin/shutdown.sh
	
	// Open URL - http://localhost:8080/demorest/webapi/aliens
	
	AlienRepository repo = new AlienRepository();
	
//	@GET
//	@Produces(MediaType.APPLICATION_XML)
//	public Alien getAlien() {
//		Alien alien = new Alien();
//		alien.setName("Navin");
//		alien.setPoints(60);
//		return alien;
//	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Alien> getAliens() {
		System.out.println("getAliens called...");
		
		return repo.getAliens();
	}
	
	@GET
	@Path("alien/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Alien getAlien(@PathParam("id") int id) {
		return repo.getAlien(id);
	}
	
	@POST
	@Path("alien")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Alien createAlien(Alien alien) {
		System.out.println("createAlien: "+ alien);
		repo.create(alien);
		return alien;
	}
	
	@PUT
	@Path("alien")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Alien updateAlien(Alien alien) {
		if(repo.getAlien(alien.getId()) == null) {
			System.out.println("alien not found, new alien is created");
			repo.create(alien);
		} else { 
			System.out.println("updated aliean: "+ alien);
			repo.update(alien);
		}

		return alien;
	}
	
	@DELETE
	@Path("alien/{id}")
	public Alien killAlien(@PathParam("id") int id) {
		
		Alien alien = repo.getAlien(id);
		
		if(alien != null) {
			boolean isDeleted = repo.delete(id);
			if (isDeleted) {
				return alien;
			}
		}
		return null;
	}
	
}
