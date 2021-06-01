package com.google.lkr;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

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
	@RequestMapping(value = "/create_table", method = RequestMethod.GET)
	public String createTable(Locale locale, Model model) {
		UserDB db = new UserDB();
		db.createTable();
		model.addAttribute("m1", "테이블이 생성되었습니다.");
		return "message";
	}
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insert(Locale locale, Model model) {
		return "insert";
	}
	@RequestMapping(value = "/insert_action", method = RequestMethod.GET)
	public String insertAction(Locale locale, Model model, 
			@RequestParam("name") String name,
			@RequestParam("sex") String sex,
			@RequestParam("adress") String adress, 
			@RequestParam("part") String part) {
		UserDB db = new UserDB();
		db.insertData(name, sex, adress, part);
		model.addAttribute("m1", "데이터가 입력되었습니다.");
		return "message";
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String showTable(Locale locale, Model model) {
		UserDB db = new UserDB();
		StringBuilder result = db.selectData();
		model.addAttribute("list", result);
		return "list";
	}
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Locale locale, Model model, @RequestParam("idx") int idx) {
		UserDB db = new UserDB();
		Employ selectEmploy = db.detailsData(idx);
		if (selectEmploy != null) {
			model.addAttribute("idx", selectEmploy.idx);
			model.addAttribute("name", selectEmploy.name);
			model.addAttribute("sex", selectEmploy.sex);
			model.addAttribute("adress", selectEmploy.adress);
			model.addAttribute("part", selectEmploy.part);
		}
		return "update";
	}
	@RequestMapping(value = "/update_action", method = RequestMethod.GET)
	public String updateAction(Locale locale, Model model, @RequestParam("idx") int idx,
			@RequestParam("name") String name, @RequestParam("sex") String sex,
			@RequestParam("adress") String adress, @RequestParam("part") String part) {
		UserDB db = new UserDB();
		db.updateData(idx, name, sex, adress, part);
		model.addAttribute("m1", "수정되었습니다.");
		return "message";
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Locale locale, Model model, @RequestParam("idx") int idx) {
		UserDB db = new UserDB();
		Employ selectEmploy = db.detailsData(idx);
		db.deleteData(idx);
		model.addAttribute("m1", "삭제되었습니다.");
		return "message";
	}
	@RequestMapping(value = "/searchselect", method = RequestMethod.GET)
	public String search(Locale locale, Model model) {
		return "search";
	}
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(Locale locale, Model model, @RequestParam("name") String name) {
		UserDB db = new UserDB();
		Employ selectEmploy = db.searchData(name);
		if (selectEmploy != null) {
			model.addAttribute("idx", selectEmploy.idx);
			model.addAttribute("name", selectEmploy.name);
			model.addAttribute("sex", selectEmploy.sex);
			model.addAttribute("adress", selectEmploy.adress);
			model.addAttribute("part", selectEmploy.part);
		}
		return "searchlist";
	}
}
