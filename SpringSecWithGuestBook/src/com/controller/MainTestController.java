package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainTestController {
	@RequestMapping("/tiles")
	public String tiles()
	{
		
		return "tiles";
	}
	
	@RequestMapping("/newfile")
	public String newfile()
	{
		
		return "TILES/NewFile";
	}
	
	@RequestMapping("/test/newfile")
	public String test()
	{
		
		return "TILES/test/NewFile";
	}
}
