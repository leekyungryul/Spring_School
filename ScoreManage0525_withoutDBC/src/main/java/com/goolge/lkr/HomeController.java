package com.goolge.lkr;

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
//	테이블명 입력받기
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Locale locale, Model model) {
		return "create";
	}
//	반별로 테이블 생성
	@RequestMapping(value = "/create_action", method = RequestMethod.GET)
	public String createTable2(Locale locale, Model model, @RequestParam("class_name") String name) {
		UserDB db = new UserDB();
		db.createAction(name);
		model.addAttribute("m1", "테이블이 생성되었습니다.");
		return "message";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insert(Locale locale, Model model) {
		return "insert";
	}
//	반명을 입력받아서 데이터 입력
	@RequestMapping(value = "/insert_action", method = RequestMethod.GET)
	public String insertAction(Locale locale, Model model, @RequestParam("student_name") String name,
			@RequestParam("class_name") String className,
			@RequestParam("middle_score") String middleScore, @RequestParam("final_score") String finalScore) {
		UserDB db = new UserDB();
		double midScore = Double.parseDouble(middleScore);
		double finScore = Double.parseDouble(finalScore);
		db.insertData(className, name, midScore, finScore);
		model.addAttribute("m1", "데이터가 입력되었습니다.");
		return "message";
	}
//	반을 선택하는 페이지 로드
	@RequestMapping(value = "/listSelect", method = RequestMethod.GET)
	public String listSelect(Locale locale, Model model) {
		UserDB db = new UserDB();
		return "listSelect";
	}
	@RequestMapping(value = "/listSelect_midSort", method = RequestMethod.GET)
	public String midSort(Locale locale, Model model) {
		UserDB db = new UserDB();
		return "listSelect_midSort";
	}
	@RequestMapping(value = "/listSelect_finSort", method = RequestMethod.GET)
	public String finSort(Locale locale, Model model) {
		UserDB db = new UserDB();
		return "listSelect_finSort";
	}
	@RequestMapping(value = "/listSelect_avgSort", method = RequestMethod.GET)
	public String avgSort(Locale locale, Model model) {
		UserDB db = new UserDB();
		return "listSelect_avgSort";
	}
//	위에서 선택한 반번호를 기준으로 class값을 물고가서 해당 테이블의 모든 리스트들을 출력
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String showTable(Locale locale, Model model,
			@RequestParam("class") String className) {
		UserDB db = new UserDB();
		String result = db.selectData(className);
		model.addAttribute("list", result);
		return "list";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Locale locale, Model model, @RequestParam("idx") int idx) {
		UserDB db = new UserDB();
		Student selectStudent = db.detailsData(idx);
		if (selectStudent != null) {
			model.addAttribute("idx", selectStudent.idx);
			model.addAttribute("studentName", selectStudent.name);
			model.addAttribute("middleScore", selectStudent.middleScore);
			model.addAttribute("finalScore", selectStudent.finalScore);
		}
		return "update";
	}
	@RequestMapping(value = "/update_action", method = RequestMethod.GET)
	public String updateAction(Locale locale, Model model, @RequestParam("idx") int idx,
			@RequestParam("student_name") String name, @RequestParam("middle_score") String midScore,
			@RequestParam("final_score") String finScore) {
		UserDB db = new UserDB();
		double middleScore = Double.parseDouble(midScore);
		double finalScore = Double.parseDouble(finScore);
		db.updateData(idx, name, middleScore, finalScore);
		model.addAttribute("m1", "수정되었습니다.");
		return "message";
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Locale locale, Model model, @RequestParam("idx") int idx) {
		UserDB db = new UserDB();
		Student selectStudent = db.detailsData(idx);
		db.deleteData(idx);
		model.addAttribute("m1", "삭제되었습니다.");
		return "message";
	}
//	중간고사 성적순으로 정렬
	@RequestMapping(value = "/midScore_sort", method = RequestMethod.GET)
	public String sortingMidScore(Locale locale, Model model,
			@RequestParam("class") String className) {
		UserDB db = new UserDB();
		String result = db.sortMidScore(className);
		model.addAttribute("list", result);
		return "list";
	}
//	기말고사 성적순으로 정렬
	@RequestMapping(value = "/finScore_sort", method = RequestMethod.GET)
	public String sortingFinScore(Locale locale, Model model,
			@RequestParam("class") String className) {
		UserDB db = new UserDB();
		String result = db.sortFinScore(className);
		model.addAttribute("list", result);
		return "list";
	}
//	평균점수 순으로 정렬
	@RequestMapping(value = "/avgScore_sort", method = RequestMethod.GET)
	public String sortAvgScore(Locale locale, Model model,
			@RequestParam("class") String className) {
		UserDB db = new UserDB();
		String result = db.sortAvgScore(className);
		model.addAttribute("list", result);
		return "list";
	}
}
