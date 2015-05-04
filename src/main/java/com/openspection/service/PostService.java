package com.openspection.service;


import com.openspection.model.Post;
import com.openspection.persistence.SystemDataAccess;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PostService
{

	//GET

	@RequestMapping( value = "posts",method = RequestMethod.GET )
	@ResponseBody
	public final List< Post > getAllPosts()
	{
		return SystemDataAccess.getAll("select p from Post p ");
	}

	@RequestMapping( value = "posts/{id}",method = RequestMethod.GET )
	@ResponseBody
	public final Post getPost( @PathVariable( "id" ) final Long id )
	{
		return (Post) SystemDataAccess.get(Post.class, id);
	}

	//POST
	
	@RequestMapping( value = "posts",method = RequestMethod.POST )
	@ResponseStatus( HttpStatus.CREATED )
	@ResponseBody	
	public final Post addPost( @RequestBody final Post p , HttpServletRequest request)
	{
		PersonService tvoPersonService = new PersonService();
		//Long tvlPersonid=tvoPersonService.getLoggedInPersonId(request);
		//p.setCreatedby(tvlPersonid);
		p.setId(null);
		return (Post) SystemDataAccess.add(p);
	}

	//PUT 

	@RequestMapping( value = "posts/{id}",method = RequestMethod.PUT )
	@ResponseStatus( HttpStatus.OK )
	@ResponseBody	
	public final Post setPost( @PathVariable( "id" ) final Long id, @RequestBody final Post p )
	{
		
		p.setId(id);
		return (Post) SystemDataAccess.set(Post.class, id, p);
	}

	//DELETE

	@RequestMapping( value = "posts/{id}",method = RequestMethod.DELETE )
	@ResponseStatus( HttpStatus.OK )
	public final void deletePost( @PathVariable( "id" ) final Long id )
	{
		SystemDataAccess.delete(Post.class, id);
	}

}
