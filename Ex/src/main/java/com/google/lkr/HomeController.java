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
	@RequestMapping(value = "/createSelect", method = RequestMethod.GET)
	public String createSel(Locale locale, Model model) {
		return "create";
	}
	@RequestMapping(value = "/createTable", method = RequestMethod.GET)
	public String createTable(Locale locale, Model model,
			@RequestParam("class_name")String className) {
		UserDB db = new UserDB();
		db.createTable(className);
		model.addAttribute("m1", "테이블이 생성되었습니다.");
		return "message";
	}
	@RequestMapping(value = "/insertSelect", method = RequestMethod.GET)
	public String insertSelect(Locale locale, Model model) {
		return "insertSelect";
	}
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insert(Locale locale, Model model,
			@RequestParam("class") String className) {
		model.addAttribute("className", className);
		return "insert";
	}
	@RequestMapping(value = "/insert_action", method = RequestMethod.GET)
	public String insertAction(Locale locale, Model model,
			@RequestParam("class_name") String className,
			@RequestParam("student_name") String name,
			@RequestParam("middle_score") String midScore,
			@RequestParam("final_score") String finScore) {
		double middleScore = Double.parseDouble(midScore);
		double finalScore = Double.parseDouble(finScore);
		UserDB db = new UserDB();
		db.insertData(className, name, middleScore, finalScore);
		model.addAttribute("m1", "데이터가 입력되었습니다.");
		return "message";
	}
	@RequestMapping(value = "/listSelect", method = RequestMethod.GET)
	public String listSelect(Locale locale, Model model) {
		UserDB db = new UserDB();
		return "listSelect";
	}
//	위에서 선택한 반번호를 기준으로 class값을 물고가서 해당 테이블의 모든 리스트들을 출력
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String showTable(Locale locale, Model model,
			@RequestParam("class") String className) {
		UserDB db = new UserDB();
		StringBuilder result = db.selectData(className);
		model.addAttribute("list", result);
		return "list";
	}
//	미완성....
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Locale locale, Model model, @RequestParam("className") String className,
			@RequestParam("idx") int idx) {
		UserDB db = new UserDB();
		Student selectStudent = db.detailsData(className, idx);
		if (selectStudent != null) {
			model.addAttribute("className", className);
			model.addAttribute("idx", selectStudent.idx);
			model.addAttribute("studentName", selectStudent.name);
			model.addAttribute("middleScore", selectStudent.middleScore);
			model.addAttribute("finalScore", selectStudent.finalScore);
		}
		return "update";
	}
}
