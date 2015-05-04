package com.openspection.service;


import com.openspection.model.Post;
import com.openspection.model.Vote;
import com.openspection.persistence.SystemDataAccess;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class VoteService
{

	//GET

	@RequestMapping( value = "votes",method = RequestMethod.GET )
	@ResponseBody
	public final List< Vote > getAllVotes()
	{
		return SystemDataAccess.getAll("select p from Vote p ");
	}

	@RequestMapping( value = "votes/{id}",method = RequestMethod.GET )
	@ResponseBody
	public final Vote getVote( @PathVariable( "id" ) final Long id )
	{
		return (Vote) SystemDataAccess.get(Vote.class, id);
	}

	//POST
	
	@RequestMapping( value = "votes",method = RequestMethod.POST )
	@ResponseStatus( HttpStatus.CREATED )
	@ResponseBody	
	public final Vote addVote( @RequestBody final Vote p , HttpServletRequest request)
	{
		p.setId(null);
		return (Vote) SystemDataAccess.add(p);
	}

	//PUT 

	@RequestMapping( value = "votes/{id}",method = RequestMethod.PUT )
	@ResponseStatus( HttpStatus.OK )
	@ResponseBody	
	public final Vote setVote( @PathVariable( "id" ) final Long id, @RequestBody final Vote p )
	{
		
		p.setId(id);
		return (Vote) SystemDataAccess.set(Vote.class, id, p);
	}

	//DELETE

	@RequestMapping( value = "votes/{id}",method = RequestMethod.DELETE )
	@ResponseStatus( HttpStatus.OK )
	public final void deleteVote( @PathVariable( "id" ) final Long id )
	{
		SystemDataAccess.delete(Vote.class, id);
	}

}
