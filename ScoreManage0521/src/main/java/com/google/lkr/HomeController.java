package com.google.lkr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
	public String createTable(Locale locale, Model model) {
//		DBCommon <Student> db = new DBCommon<Student>("c:/tomcat/Score0521.db", "student");
//		db.createTable(new Student());
		UserDB db = new UserDB();
		db.createTable();
		model.addAttribute("m1", "테이블이 생성되었습니다.");
		return "message";
	}
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insertData(Locale locale, Model model) {		
		return "insert";
	}
	@RequestMapping(value = "/insertAction", method = RequestMethod.GET)
	public String insertAction(Locale locale, Model model,
			@RequestParam("student_name") String name,
			@RequestParam("middle_score") String midScore,
			@RequestParam("final_score") String finScore) {		
//		int middleScore = Integer.parseInt(midScore);
//		int finalScore = Integer.parseInt(finScore);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String now = sdf.format(Calendar.getInstance().getTime());
//		DBCommon<Student> db = new DBCommon<Student>("c:/tomcat/Score0521.db", "student");
		UserDB db = new UserDB();
		double middleScore = Double.parseDouble(midScore);
		double finalScore = Double.parseDouble(finScore);
		db.insertAction(name, middleScore, finalScore);
//		db.insertData(new Student(name, middleScore, finalScore, now));
		model.addAttribute("m1", "데이터가 입력되었습니다.");
		return "message";
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String show(Locale locale, Model model) {
		UserDB db = new UserDB();
//		htmlString변수에 selectData메소드로 받아낸 resultString을 담는다.
		String htmlString = db.selectData();
//		위에서 받은 문자열을 list view에 넣는다.
		model.addAttribute("list", htmlString);
//		그러면 list html이 펼쳐지면서 데이터 목록들이 보일것이다.
		return "list";
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Locale locale, Model model, 
			@RequestParam("idx") int idx) {
		DBCommon<Student> db = new DBCommon<Student>("c:/tomcat/Score0521.db", "student");
		Student selectStudent  = db.detailsData(new Student(), idx);
		db.deleteData(new Student(idx, selectStudent.name, selectStudent.middleScore, selectStudent.finalScore));
		model.addAttribute("m1", "데이터가 삭제되었습니다.");
		return "message";
	}
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Locale locale, Model model, 
			@RequestParam("idx") int idx) {
//		DBCommon<Student> db = new DBCommon<Student>("c:/tomcat/Score0521.db", "student");
//		Student selectStudent  = db.detailsData(new Student(), idx);
		UserDB db = new UserDB();
		Student selectStudent = db.detailsData(idx);
		if(selectStudent != null) {
			model.addAttribute("idx", selectStudent.idx);
			model.addAttribute("studentName", selectStudent.name);
			model.addAttribute("middleScore", selectStudent.middleScore);
			model.addAttribute("finalScore", selectStudent.finalScore);
		}
		return "update";
	}
	@RequestMapping(value = "/updateAction", method = RequestMethod.GET)
	public String updateAtion(Locale locale, Model model, 
			@RequestParam("idx") int idx,
			@RequestParam("student_name") String name,
			@RequestParam("middle_score") String middle_score,
			@RequestParam("final_score") String final_score) {
//		int midScore = Integer.parseInt(middle_score);
//		int finScore = Integer.parseInt(final_score);
//		DBCommon<Student> db = new DBCommon<Student>("c:/tomcat/Score0521.db", "student");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String now = sdf.format(Calendar.getInstance().getTime());
//		db.updateData(new Student(idx, name, midScore, finScore, now));
		UserDB db = new UserDB();
		double middleScore = Double.parseDouble(middle_score);
		double finalScore = Double.parseDouble(final_score);
		db.updateData(idx, name, middleScore, finalScore);
		model.addAttribute("m1", "데이터가 수정되었습니다.");
		return "message";
	}
	
}
