package com.openspection.service;


import com.openspection.model.Award;
import com.openspection.persistence.SystemDataAccess;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AwardService
{

	//GET

	@RequestMapping( value = "awards",method = RequestMethod.GET )
	@ResponseBody
	public final List< Award > getAllAwards()
	{
		return SystemDataAccess.getAll("select p from Award p ");
	}

	@RequestMapping( value = "awards/{id}",method = RequestMethod.GET )
	@ResponseBody
	public final Award getAward( @PathVariable( "id" ) final Long id )
	{
		return (Award) SystemDataAccess.get(Award.class, id);
	}

	//POST
	
	@RequestMapping( value = "awards",method = RequestMethod.POST )
	@ResponseStatus( HttpStatus.CREATED )
	@ResponseBody	
	public final Award addAward( @RequestBody final Award p , HttpServletRequest request)
	{
		p.setId(null);
		return (Award) SystemDataAccess.add(p);
	}

	//PUT 

	@RequestMapping( value = "awards/{id}",method = RequestMethod.PUT )
	@ResponseStatus( HttpStatus.OK )
	@ResponseBody	
	public final Award setAward( @PathVariable( "id" ) final Long id, @RequestBody final Award p )
	{
		
		p.setId(id);
		return (Award) SystemDataAccess.set(Award.class, id, p);
	}

	//DELETE

	@RequestMapping( value = "awards/{id}",method = RequestMethod.DELETE )
	@ResponseStatus( HttpStatus.OK )
	public final void deleteAward( @PathVariable( "id" ) final Long id )
	{
		SystemDataAccess.delete(Award.class, id);
	}

}
