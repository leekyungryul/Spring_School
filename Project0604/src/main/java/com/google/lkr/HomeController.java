package com.google.lkr;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
//	비밀번호 암호화를 위한 로직
	public String sha256(String msg) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(msg.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : md.digest()) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "main";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Locale locale, Model model) {
		UserDB db = new UserDB();
		boolean isSuccess = db.createTable();
		if(isSuccess) {
			model.addAttribute("m1", "테이블이 생성되었습니다.");
		}else{
			model.addAttribute("m1", "DB error");
		}
		return "message";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insert(Locale locale, Model model) {
		return "insert";
	}
	
//	이름과 아이디 
	@RequestMapping(value = "/insert_action", method = RequestMethod.POST)
	public String insert_action(HttpServletRequest request,Locale locale, Model model) {
		try {
			request.setCharacterEncoding("UTF-8");
		}catch (Exception e) {
			e.printStackTrace();
		}
//		html에 있는 각 입력값을 받아온다.
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String birthday = request.getParameter("birthday");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(Calendar.getInstance().getTime());
		People people = new People(id, pwd, name,address,birthday,now,now);
		UserDB db = new UserDB();
		boolean loginSuccess = db.loginCheck(people);
		if(loginSuccess) {
			boolean isSuccess = db.insertData(people);
			if(isSuccess) {
				model.addAttribute("m1", "데이터가 입력되었습니다.");
			}else {
				model.addAttribute("m1", "DB error");
			}
		}else {
			model.addAttribute("m1", "동일한 데이터가 있습니다.");
		}
		return "message";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Locale locale, Model model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			boolean isLogin = (Boolean) session.getAttribute("is_login");
			if(isLogin) {
				UserDB db = new UserDB();
				StringBuilder result = db.listData();
				model.addAttribute("list", result);
				return "list";
			}else {
				model.addAttribute("m1", "로그인이 필요합니다.");
				return "message";
			}
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("m1", "로그인이 필요합니다.");
			return "message";
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {
		return "login";
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
			return "redirect:/";
		}
//		로그인에 실패하면 다시 login.jsp가 return된다.
		return "redirect:/login";
	}
}
