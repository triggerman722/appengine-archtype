package com.openspection.service;

import com.openspection.model.Like;
import com.openspection.model.Person;
import com.openspection.persistence.SystemDataAccess;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Date;
import java.util.List;


@Controller
public class LikeService
{

	//COMMENTS

	//GET ALL LIKES FOR AN ENTITY

	@RequestMapping( value = "likes/{id}",method = RequestMethod.GET )
	@ResponseBody
	public final List< Like > getLike( @PathVariable( "id" ) final Long id, HttpServletResponse response)
	{
        Object[][] tvoObject = new Object[1][2];
        tvoObject[0][0] = "entityid";
        tvoObject[0][1] = id;
		List< Like > tvlLikes = List< Like > SystemDataAccess.getWithParams("select p from Like p where p.entityid in (:entityid) ", tvoObject);
		if (tvlLikes.isEmpty()) {
			response.setStatus(HttpStatus.NOT_FOUND);
		}
		return tvlLikes;

	}
	//POST
	@RequestMapping( value = "likes",method = RequestMethod.POST )
	@ResponseStatus( HttpStatus.CREATED )
	@ResponseBody	
	public final Like addLike( @RequestBody final Like p, Principal fvoPrincipal)
	{
		
		p.setId(null);
		p.setDatecreated(new Date());
		PersonService tvoPersonService = new PersonService();
		Person tvoPerson = tvoPersonService.getPersonByEmail(fvoPrincipal.getName());
		p.setCreatedby(tvoPerson.getId());
		return (Like) SystemDataAccess.add(p);
	}


	//DELETE

	@RequestMapping( value = "likes/{id}",method = RequestMethod.DELETE )
	@ResponseStatus( HttpStatus.OK )
	public final void deleteLike( @PathVariable( "id" ) final Long id )
	{
		SystemDataAccess.delete(Like.class, id);
	}
}
