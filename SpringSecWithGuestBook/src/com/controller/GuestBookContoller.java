package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dto.GuestBookDto;
import com.dto.GuestbookPageDto;
import com.google.gson.Gson;
import com.service.ListService;
import com.service.UpdateFormService;
import com.service.UpdateService;
import com.service.WriteService;

@Controller
public class GuestBookContoller {

	//private static int GC_CallCnt = 0;
	
	@Autowired
	private ListService listService;
	@Autowired
	private WriteService writeService;
	@Autowired
	private UpdateFormService updateFormService;
	@Autowired
	private UpdateService updateService;
	
//	//HttpServletRequest req 로 안하고 @RequestParam 으로도 가능.
//	@RequestMapping("/list")
//	public String list2(Model model,
//			@RequestParam(value = "num", required = false) String param) {
//		model.addAttribute("data", listService.getList(param));
//		return "list";
//	}
	
	//@RequestMapping("/main")
	@RequestMapping("/list")
	public String list(Model model, HttpServletRequest req) {
		
		//GC_CallCnt++;
		//System.out.println();
		//System.out.println("in GuestBookController GC_CallCnt : " +GC_CallCnt);
		//System.out.println("in GuestBookController req.num : " +req.getParameter("num"));

		model.addAttribute("data", listService.getList(req.getParameter("num")));

		//test - 나의 착각 - 질문거리에 해결로 글달아놓음.
//		int num =0;
//		if(req.getParameter("num") == null){
//			return "list";
//		}
//		else {
//			num =  Integer.valueOf(req.getParameter("num"));
//			System.out.println("in GuestBookController num : " +num);
//			return "list?num="+num;
//		}
		
		//return "list";
		return "TILES/list";
	}
	
//	@RequestMapping("/writeForm")
//	public String writeForm() {
//		//return "writeForm";
//		return "TILES/writeForm";
//	}
	
	@PostMapping("/write")
	public String write(Model model, GuestBookDto reqDto) {
		model.addAttribute("isSuccess", writeService.write(reqDto));
		//return "write";
		return "TILES/write";
	}
	
	@RequestMapping("/updateForm")
	public String updateForm(Model model,
			@RequestParam(value = "id", required = true) int param) {
		model.addAttribute("data",updateFormService.getUpdateFormData(param));
		//return "updateForm";
		return "TILES/updateForm";
	}
	
	//HttpServletRequest와 command 객체를 동시에 사용 할 수 도 있다.
	@PostMapping("/update")
	public String update(Model model, HttpServletRequest req, GuestBookDto reqDto) {
		//System.out.println("");
		//System.out.println("in GuestBookController reqDto - guestbookId : " + reqDto.getGuestbookId());
		//System.out.println("in GuestBookController reqDto - name : " + reqDto.getName());
		//System.out.println("in GuestBookController req - guestbookId : " + req.getParameter("guestbookId"));
		
		model.addAttribute("isSuccess",updateService.update(reqDto));
		//return "update";
		return "TILES/update";
	}
	
	////////////security/////////////
	//String 으로 리턴하는 방법도있고, ModelAndView로 하는방법도있다. 이번엔 ModelAndView 써볼것임.
	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		
		return mav;
	}
	
	
	///////////////test//////////////
	@GetMapping("/hello")
	public String hello(Model model,
			@RequestParam(value = "name", required = false) String name) {
		model.addAttribute("greeting", "안녕하세요, " + name);
		return "hello";
	}
	
	@RequestMapping("/test1")
	public String ajaxTestController1() {
		return "hello";
	}
	
	@RequestMapping("/test2")
	@ResponseBody
	public String ajaxTestController2(String arg) {
		System.out.println(arg);
		return "helloAjax222222222222";
	}
	
	//test 용
	@RequestMapping("/list1")
	@ResponseBody
	public String AjaxlistController(String rslt) {
		System.out.println("in GC - rslt : " + rslt);
		
		return "Ajaxlist1";
	}
	
	//실제 list.jsp ajax 처리
	@RequestMapping(value="/list2",produces="application/json")
	@ResponseBody
	public String AjaxlistController2(String rslt) {
		//System.out.println("in GC - rslt : " + rslt);
		
		Gson gson = new Gson();
		String json = gson.toJson(listService.getList(rslt));
		
		return json;
	}
	
	//실제 update.jsp ajax 처리 
	@RequestMapping(value="/update2",produces="application/json")
	@ResponseBody
	public String AjaxUpdateController(@ModelAttribute GuestBookDto book) {
		System.out.println("in GC - guestbookId : " + book.getGuestbookId());
		Gson gson = new Gson();
		String json = gson.toJson(updateService.update(book));
		System.out.println("in GC - json : " + json);
		return json;
	}
	
	//실제 update.jsp ajax 처리 
	//@RequestParm 이나 기타 파라미터 값을 받아올때 Null 이거나, Type이 맞지 않는 경우 Required int parameter 'id' is not present 와같은 에러가 발생된다.
	//@RequestParam(value="id", required = false)
		@RequestMapping(value="/update3",produces="application/json")
		@ResponseBody
		public String AjaxUpdateController2(String id,
				String password,String name, String email, String content){
			//int GuestbookId = id;
			int GuestbookId = Integer.valueOf(id);
			System.out.println("in GC - guestbookId : " + id);
			System.out.println("in GC - password : " + password);
			System.out.println("in GC - name : " + name);
			System.out.println("in GC - email : " + email);
			System.out.println("in GC - content : " + content);
			
			//boolean rslt = updateService.update(GuestBookDto.builder().guestbookId(GuestbookId)
			//		.password(password).name(name).email(email).content(content).build());
			
			//return rslt;
			
			Gson gson = new Gson();
			String json = gson.toJson(
					updateService.update(GuestBookDto.builder().guestbookId(GuestbookId)
							.password(password).name(name).email(email).content(content).build()));
			System.out.println("in GC - json : " + json);
			return json;
		}
		
	// 실제 update.jsp ajax 처리
	@RequestMapping(value = "/update4")
	@ResponseBody
	public String AjaxUpdateController3(String password) {
		String test = password;
		System.out.println("in GC - test : " + test);
		System.out.println("in GC - password : " + password);
		return "AjaxUpdateController3";
	}
	
	// 실제 write.jsp ajax 처리
	@RequestMapping(value = "/write2" ,produces="application/json")
	@ResponseBody
	public String AjaxWriteController(String name,String pwd, String email, String text) {
		System.out.println("in GC - password : " + pwd);
		System.out.println("in GC - name : " + name);
		System.out.println("in GC - email : " + email);
		System.out.println("in GC - content : " + text);
		Gson gson = new Gson();
		String json = gson.toJson(
				writeService.write(GuestBookDto.builder().password(pwd).name(name).email(email).content(text).build()));
		return json;
	}
}
