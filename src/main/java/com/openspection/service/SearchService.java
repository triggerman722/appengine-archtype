package com.openspection.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.openspection.model.Post;

import com.openspection.persistence.SystemDataAccess;



@Controller
public class SearchService 
{

	@RequestMapping( value = "search/{query}",method = RequestMethod.GET )
	@ResponseBody
	public final List< Post > getByQuery(@PathVariable( "query" ) final String fvsQuery)
	{
		List< Post > tvlResult = new ArrayList< Post >();
		List< Post > tvlList = SystemDataAccess.getAll("select p from Post p ");
		for (Post tvoPost : tvlList) {
			if ((tvoPost.getDescription()==null) &&
					(tvoPost.getTitle()==null))
				continue;
			if (
					tvoPost.getDescription().toLowerCase().contains(fvsQuery.toLowerCase()) ||
					tvoPost.getTitle().toLowerCase().contains(fvsQuery.toLowerCase())
			)
			{
				tvlResult.add(tvoPost);
			}
		}
		return tvlResult;
	}

}
