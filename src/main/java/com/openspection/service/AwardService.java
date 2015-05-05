package com.openspection.service;


import com.openspection.model.Award;
import com.openspection.model.Like;
import com.openspection.persistence.SystemDataAccess;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AwardService
{

	// Get a list of awards that apply to a person.
	@RequestMapping( value = "awards/{id}",method = RequestMethod.GET )
	@ResponseBody
	public final List<Award> getAwards( @PathVariable( "id" ) final Long id )
	{
		Object[][] tvoObject = new Object[1][2];
		tvoObject[0][0] = "personid";
		tvoObject[0][1] = id;
		return SystemDataAccess.getWithParams("select p from Award p where p.personid in (:personid) ", tvoObject);
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



	//DELETE

	@RequestMapping( value = "awards/{id}",method = RequestMethod.DELETE )
	@ResponseStatus( HttpStatus.OK )
	public final void deleteAward( @PathVariable( "id" ) final Long id )
	{
		SystemDataAccess.delete(Award.class, id);
	}

}
