package com.openspection.service;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.openspection.model.Comment;

import com.openspection.persistence.SystemDataAccess;


@Controller
public class CommentService 
{

	//COMMENTS

	//GET

	@RequestMapping( value = "comments/{id}",method = RequestMethod.GET )
	@ResponseBody
	public final List< Comment > getComment( @PathVariable( "id" ) final Long id )
	{
        Object[][] tvoObject = new Object[1][2];
        tvoObject[0][0] = "entityid";
        tvoObject[0][1] = id;
		return SystemDataAccess.getWithParams("select p from Comment p where p.entityid in (:entityid) ", tvoObject);
	}
	//POST
	@RequestMapping( value = "comments",method = RequestMethod.POST )
	@ResponseStatus( HttpStatus.CREATED )
	@ResponseBody	
	public final Comment addComment( @RequestBody final Comment p )
	{
		
		p.setId(null);
		p.setDatecreated(new Date());
		return (Comment) SystemDataAccess.add(p);
	}

	//PUT 

	@RequestMapping( value = "comments/{id}",method = RequestMethod.PUT )
	@ResponseStatus( HttpStatus.OK )
	@ResponseBody	
	public final Comment setComment( @PathVariable( "id" ) final Long id, @RequestBody final Comment p )
	{
		
		p.setId(id);
		return (Comment) SystemDataAccess.set(Comment.class, id, p);
	}

	//DELETE

	@RequestMapping( value = "comments/{id}",method = RequestMethod.DELETE )
	@ResponseStatus( HttpStatus.OK )
	public final void deleteComment( @PathVariable( "id" ) final Long id )
	{
		SystemDataAccess.delete(Comment.class, id);
	}
}
