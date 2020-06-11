package com.hxzy.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GongfuController {

	// level1/** : 需要有 学徒 角色

	@PreAuthorize("hasRole('学徒') and hasAuthority('罗汉拳')")
	@GetMapping("/level1/1")
	public String leve1Page(){
		return "/level1/1";
	}
	@PreAuthorize("hasAnyAuthority('ROLE_学徒','武当长拳')")
	@GetMapping("/level1/2")
	public String leve1Page2(){
		return "/level1/2";
	}
	@PreAuthorize("hasRole('学徒')")
	@GetMapping("/level1/3")
	public String leve1Page3(){
		return "/level1/3";
	}

	@PreAuthorize("hasAnyAuthority('ROLE_大师','太极拳')")
	@GetMapping("/level2/1")
	public String leve2Page(){
		return "/level2/1";
	}

	@PreAuthorize("hasRole('大师')")
	@GetMapping("/level2/2")
	public String leve2Page2(){
		return "/level2/2";
	}

	@PreAuthorize("hasRole('大师')")
	@GetMapping("/level2/3")
	public String leve2Page3(){
		return "/level2/3";
	}

	@PreAuthorize("hasRole('宗师')")
	@GetMapping("/level3/{path}")
	public String leve3Page(@PathVariable("path")String path){
		return "/level3/"+path;
	}

}
