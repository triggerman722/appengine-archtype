package com.openspection.service;

import com.openspection.model.Feedback;
import com.openspection.persistence.SystemDataAccess;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Controller
public class FeedbackService
{

	//COMMENTS

	//GET ALL FEEDBACK FOR A USER

	@RequestMapping( value = "feedback/{id}",method = RequestMethod.GET )
	@ResponseBody
	public final List< Feedback > getFeedback( @PathVariable( "id" ) final Long id )
	{
        Object[][] tvoObject = new Object[1][2];
        tvoObject[0][0] = "userid";
        tvoObject[0][1] = id;
		return SystemDataAccess.getWithParams("select p from Feedback p where p.userid in (:userid) ", tvoObject);
	}
	//POST
	@RequestMapping( value = "feedback",method = RequestMethod.POST )
	@ResponseStatus( HttpStatus.CREATED )
	@ResponseBody	
	public final Feedback addFeedback( @RequestBody final Feedback p )
	{
		
		p.setId(null);
		p.setDatecreated(new Date());
		return (Feedback) SystemDataAccess.add(p);
	}


	//DELETE

	@RequestMapping( value = "feedback/{id}",method = RequestMethod.DELETE )
	@ResponseStatus( HttpStatus.OK )
	public final void deleteFeedback( @PathVariable( "id" ) final Long id )
	{
		SystemDataAccess.delete(Feedback.class, id);
	}
}
