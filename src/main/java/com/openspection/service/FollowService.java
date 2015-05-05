package com.openspection.service;

import com.openspection.model.Follow;
import com.openspection.persistence.SystemDataAccess;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class FollowService
{

	// Get a list of who follows me
	@RequestMapping( value = "follows/{id}",method = RequestMethod.GET )
	@ResponseBody
	public final List< Follow > getFollows( @PathVariable( "id" ) final Long id )
	{
        Object[][] tvoObject = new Object[1][2];
        tvoObject[0][0] = "entityid";
        tvoObject[0][1] = id;
		return SystemDataAccess.getWithParams("select p from Follow p where p.entityid in (:entityid) ", tvoObject);
	}

	// Get a list of who I am following
	@RequestMapping( value = "following/{id}",method = RequestMethod.GET )
	@ResponseBody
	public final List< Follow > getFollowing( @PathVariable( "id" ) final Long id )
	{
		Object[][] tvoObject = new Object[1][2];
		tvoObject[0][0] = "createdby";
		tvoObject[0][1] = id;
		return SystemDataAccess.getWithParams("select p from Follow p where p.createdby in (:createdby) ", tvoObject);
	}
	//POST
	@RequestMapping( value = "follow",method = RequestMethod.POST )
	@ResponseStatus( HttpStatus.CREATED )
	@ResponseBody	
	public final Follow addFollow( @RequestBody final Follow p )
	{
		
		p.setId(null);
		return (Follow) SystemDataAccess.add(p);
	}


	//DELETE

	@RequestMapping( value = "follow/{id}",method = RequestMethod.DELETE )
	@ResponseStatus( HttpStatus.OK )
	public final void deleteFollow( @PathVariable( "id" ) final Long id )
	{
		SystemDataAccess.delete(Follow.class, id);
	}
}
