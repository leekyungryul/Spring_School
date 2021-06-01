package com.kopo.projecta;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "main";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Locale locale, Model model) {
		UserDB userDB = new UserDB();
		boolean isSuccess = userDB.createDB();
		if (isSuccess) {
			model.addAttribute("m1", "테이블이 생성되었습니다.");
		} else {
			model.addAttribute("m1", "DB Error");
		}
		return "message";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insert(Locale locale, Model model) {
		return "insert";
	}

	@RequestMapping(value = "/insert_action", method = RequestMethod.GET)
	public String insertAction(Locale locale, Model model
			, @RequestParam("id") String id, @RequestParam("pwd") String pwd
			, @RequestParam("name") String name, @RequestParam("birthday") String birthday
			, @RequestParam("address") String address) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(Calendar.getInstance().getTime());
		
		People people = new People(id, pwd, name, birthday, address, now, now);
		UserDB userDB = new UserDB();
		boolean isSuccess = userDB.insertDB(people);
		if (isSuccess) {
			model.addAttribute("m1", "데이터가 입력되었습니다.");
		} else {
			model.addAttribute("m1", "DB Error");
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
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Locale locale, Model model,
			@RequestParam("idx") int idx) {
		UserDB db = new UserDB();
		People selectPeople = db.detailsData(idx);
		if(selectPeople != null) {
			model.addAttribute("idx", selectPeople.idx);
			model.addAttribute("id", selectPeople.id);
//			model.addAttribute("pwd", selectPeople.pwd);
			model.addAttribute("name", selectPeople.name);
			model.addAttribute("birthday", selectPeople.birthday);
			model.addAttribute("address", selectPeople.address);
			model.addAttribute("created", selectPeople.created);
		}
		return "update";
	}
	
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
	
	@RequestMapping(value = "/update_action", method = RequestMethod.GET)
	public String updateAction(Locale locale, Model model,
			@RequestParam("idx") int idx, @RequestParam("id") String id
			, @RequestParam("pwd") String pwd, @RequestParam("name") String name
			, @RequestParam("birthday") String birthday, @RequestParam("address") String address
			, @RequestParam("created") String created) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(Calendar.getInstance().getTime());
		People people = new People(idx,id, pwd, name, birthday, address, created, now);
		UserDB db = new UserDB();
		People selectPeople = db.detailsData(idx);
		if(selectPeople.id.equals(id) && selectPeople.pwd.equals(sha256(pwd))) {
			db.updateData(people);
			model.addAttribute("m1", "데이터가 수정되었습니다.");
		}else {
			model.addAttribute("m1", "ID와 비번을 확인하세요");
		}
		return "message";
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Locale locale, Model model,
			@RequestParam("idx") int idx) {
		UserDB db = new UserDB();
		db.deleteData(idx);
		model.addAttribute("m1", "삭제되었습니다.");
		return "message";
	}
}
