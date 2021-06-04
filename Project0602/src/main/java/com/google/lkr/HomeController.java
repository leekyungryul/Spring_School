package com.google.lkr;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "main";
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Locale locale, Model model) {	
		UserDB db = new UserDB();
		boolean isSucess = db.createTable();
		if(isSucess) {
			model.addAttribute("m1", "테이블이 생성되었습니다.");
		}else {
			model.addAttribute("m1", "DB error");
		}
		return "message";
	}
	@RequestMapping(value = "/insertData", method = RequestMethod.GET)
	public String insert(Locale locale, Model model
			, @RequestParam("id") String id, @RequestParam("pwd") String pwd
			, @RequestParam("name") String name, @RequestParam("birthday") String birthday
			, @RequestParam("address") String address) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(Calendar.getInstance().getTime());
		People people = new People(id, pwd, name, birthday, address, now, now);
		UserDB db = new UserDB();
		boolean isSucess = db.insertDB(people);
		if(isSucess) {
			model.addAttribute("m1", "데이터가 입력되었습니다.");
		}else {
			model.addAttribute("m1", "DB error");
		}
		return "message";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Locale locale, Model model) {
		UserDB db = new UserDB();
		StringBuilder result = db.selectData();
		model.addAttribute("list", result);
		return "list";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {		
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Locale locale, Model model) {		
		return "logout";
	}
	
	@RequestMapping(value = "/login_action", method = RequestMethod.POST)
	public String loginAction(HttpServletRequest request,Locale locale, Model model) {
		try {
			request.setCharacterEncoding("utf-8");
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		html에서 사용자가 입력한 id
		String id = request.getParameter("id");
//		html에서 사용자가 입력한 pwd
		String pwd = request.getParameter("pwd");
		UserDB db = new UserDB();
		boolean isSuccess = db.loginDB(id,pwd);
		if(isSuccess) {
			HttpSession session = request.getSession();
			session.setAttribute("is_login", true);
//			로그인에 성공하면 main페이지로 이동하고
			return "logout";
		}
//		로그인에 실패하면 다시 login.jsp가 return된다.
		return "redirect:/login";
	}
	
}
