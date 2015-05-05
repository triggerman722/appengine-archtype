package com.openspection.service;

import com.openspection.model.Like;
import com.openspection.persistence.SystemDataAccess;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class LikeService
{

	//COMMENTS

	//GET ALL LIKES FOR AN ENTITY

	@RequestMapping( value = "likes/{id}",method = RequestMethod.GET )
	@ResponseBody
	public final List< Like > getLike( @PathVariable( "id" ) final Long id )
	{
        Object[][] tvoObject = new Object[1][2];
        tvoObject[0][0] = "entityid";
        tvoObject[0][1] = id;
		return SystemDataAccess.getWithParams("select p from Like p where p.entityid in (:entityid) ", tvoObject);
	}
	//POST
	@RequestMapping( value = "likes",method = RequestMethod.POST )
	@ResponseStatus( HttpStatus.CREATED )
	@ResponseBody	
	public final Like addLike( @RequestBody final Like p )
	{
		
		p.setId(null);
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
