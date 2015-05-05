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

	// does it actually matter if I check first to see if the entity exists?

	@RequestMapping( value = "votes/{id}",method = RequestMethod.GET )
	@ResponseBody
	public final List<Vote> getVote( @PathVariable( "id" ) final Long id )
	{
		Object[][] tvoObject = new Object[1][2];
		tvoObject[0][0] = "entityid";
		tvoObject[0][1] = id;
		return SystemDataAccess.getWithParams("select p from Vote p where p.entityid in (:entityid) ", tvoObject);
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


}
