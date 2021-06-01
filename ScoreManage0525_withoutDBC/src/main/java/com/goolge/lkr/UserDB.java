package com.goolge.lkr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;

public class UserDB {
	public void createTable() {
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "CREATE TABLE student " + "(idx INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "name TEXT, middleScore REAL, finalScore REAL, date TEXT)";
			System.out.println(query);
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
//			close
			connection.close();
		} catch (Exception e) {
		}
	}
	public void createAction(String name) {
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "CREATE TABLE "+ name + " (idx INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "name TEXT, middleScore REAL, finalScore REAL, date TEXT)";
			System.out.println(query);
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
//			close
			connection.close();
		} catch (Exception e) {
		}
	}

	public void insertData(String className, String name, double middleScore, double finalScore) {
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db", config.toProperties());			

			String query = "INSERT INTO "+ className+ " (name, middleScore, finalScore, date) VALUES ('"
			+ name + "', " + middleScore + ", " + finalScore + ", datetime('now'))";
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			System.out.println(query);
			statement.close();
			
			// close
			connection.close();
		} catch (Exception e) {
		}
	}
	
	public String selectData(String className) {
		String resultString = "";
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "SELECT * FROM " + className;
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int idx = resultSet.getInt("idx");
				String name = resultSet.getString("name");
				int middleScore = resultSet.getInt("middleScore");
				int finalScore = resultSet.getInt("finalScore");
				String date = resultSet.getString("date");
				resultString += "<tr>";
				resultString += "<td>" + idx + "</td>" + "<td>" + name + "</td>" + "<td>" + middleScore + "</td>"
						+ "<td>" + finalScore + "</td>" +"<td>" + (middleScore+finalScore)/2 + "</td>" + "<td>" + date + "</td>" + "<td>" + "<a href='update?idx="	+ idx + "'>수정하기 </a></td>" + "<td>" + "<a href='delete?idx="	+ idx + "'>삭제하기 </a></td>";
				resultString += "</tr>";
			}
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
		}
		return resultString;
	}
	
	public Student detailsData(int idx) {
		Student resultData = new Student();
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "SELECT * FROM student WHERE idx=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, idx);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				resultData.idx = resultSet.getInt("idx");
				resultData.name = resultSet.getString("name");
				resultData.finalScore = resultSet.getDouble("finalScore");
				resultData.middleScore = resultSet.getDouble("middleScore");
				resultData.date = resultSet.getString("date");				
			}
			preparedStatement.close();
			connection.close();
		}catch (Exception e) {
		}
		return resultData;
	}
	public void updateData(int idx, String name, double middleScore, double finalScore) {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "UPDATE student SET name=?, middleScore=?, finalScore=?, date=datetime('now') WHERE idx=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setDouble(2, middleScore);
			preparedStatement.setDouble(3, finalScore);
			preparedStatement.setInt(4, idx);
			int result = preparedStatement.executeUpdate();
			System.out.println(result);
			preparedStatement.close();
			connection.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void deleteData(int idx) {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "DELETE FROM student WHERE idx=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, idx);
			int result = preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public String compare(String studentName) {
		String resultString = "";
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "SELECT * FROM student";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				if(name==studentName) {
					resultString = "동일한 이름을 가진 사람이 있습니다.";
//					return false;
				}
			}
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
		}
		return resultString;
	}
	public String sortMidScore(String className) {
		String resultString = "";
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "SELECT * , (finalScore+middleScore)/2 AS avg from "+className+" ORDER BY middleScore DESC";
//			String query = "DELETE FROM student WHERE idx=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int idx = resultSet.getInt("idx");
				String name = resultSet.getString("name");
				int middleScore = resultSet.getInt("middleScore");
				int finalScore = resultSet.getInt("finalScore");
				double avg = resultSet.getDouble("avg");
				String date = resultSet.getString("date");
				resultString += "<tr>";
				resultString += "<td>" + idx + "</td>" + "<td>" + name + "</td>" + "<td>" + middleScore + "</td>"
						+ "<td>" + finalScore + "</td>" + "<td>" + avg + "</td>" + "<td>" + date + "</td>" + "<td>" + "<a href='update?idx="	+ idx + "'>수정하기 </a></td>" + "<td>" + "<a href='delete?idx="	+ idx + "'>삭제하기 </a></td>";
				resultString += "<tr>";
			}
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
		}
		return resultString;
	}
	public String sortFinScore(String className) {
		String resultString = "";
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "SELECT * , (finalScore+middleScore)/2 AS avg from "+className+" ORDER BY finalScore DESC";
//			String query = "DELETE FROM student WHERE idx=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int idx = resultSet.getInt("idx");
				String name = resultSet.getString("name");
				int middleScore = resultSet.getInt("middleScore");
				int finalScore = resultSet.getInt("finalScore");
				double avg = resultSet.getDouble("avg");
				String date = resultSet.getString("date");
				resultString += "<tr>";
				resultString += "<td>" + idx + "</td>" + "<td>" + name + "</td>" + "<td>" + middleScore + "</td>"
						+ "<td>" + finalScore + "</td>" + "<td>" + avg + "</td>" + "<td>" + date + "</td>" + "<td>" + "<a href='update?idx="	+ idx + "'>수정하기 </a></td>" + "<td>" + "<a href='delete?idx="	+ idx + "'>삭제하기 </a></td>";
				resultString += "<tr>";
			}
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
		}
		return resultString;
	}
	public String sortAvgScore(String className) {
		String resultString = "";
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "SELECT * , (finalScore+middleScore)/2 AS avg from "+className+" ORDER BY avg DESC";
//			String query = "DELETE FROM student WHERE idx=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int idx = resultSet.getInt("idx");
				String name = resultSet.getString("name");
				int middleScore = resultSet.getInt("middleScore");
				int finalScore = resultSet.getInt("finalScore");
				double avg = resultSet.getDouble("avg");
				String date = resultSet.getString("date");
				resultString += "<tr>";
				resultString += "<td>" + idx + "</td>" + "<td>" + name + "</td>" + "<td>" + middleScore + "</td>"
						+ "<td>" + finalScore + "</td>" + "<td>" + avg + "</td>" + "<td>" + date + "</td>" + "<td>" + "<a href='update?idx="	+ idx + "'>수정하기 </a></td>" + "<td>" + "<a href='delete?idx="	+ idx + "'>삭제하기 </a></td>";
				resultString += "<tr>";
			}
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
		}
		return resultString;
	}
	public String findFinScore() {
		String resultString = "";
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "SELECT * , (finalScore+middleScore)/2 AS avg from student ORDER BY avg DESC";
//			String query = "SELECT * , (finalScore+middleScore)/2 AS avg from  "+ name + " student ORDER BY avg DESC";
//			String query = "DELETE FROM student WHERE idx=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.getRow() < 11) {
				int idx = resultSet.getInt("idx");
				String name = resultSet.getString("name");
				int middleScore = resultSet.getInt("middleScore");
				int finalScore = resultSet.getInt("finalScore");
				double avg = resultSet.getDouble("avg");
				String date = resultSet.getString("date");
				resultString += "<tr>";
				resultString += "<td>" + idx + "</td>" + "<td>" + name + "</td>" + "<td>" + middleScore + "</td>"
						+ "<td>" + finalScore + "</td>" +  "<td>" + avg + "</td>" +"<td>" + date + "</td>" + "<td>" + "<a href='update?idx="	+ idx + "'>수정하기 </a></td>" + "<td>" + "<a href='delete?idx="	+ idx + "'>삭제하기 </a></td>";
				resultString += "<tr>";
			}
			System.out.println(resultString);
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
		}
		return resultString;
		
	}
}
// 기말고사 평균점수가 몇점 이상인 사람을 출력
//select * ,AVG(finalScore) AS 기말평균 from student where 

//--6. 급여가 300만원이하이면서 2020년 08월 15일 이후에 입사한 사원의 
//--총인원수와 총급여합을 출력하시오
//SELECT COUNT(사원번호) AS 총인원, SUM(급여) AS 총급여
//FROM (
//SELECT * FROM 사원정보_입사년월일
//WHERE 1=1
//AND 급여 <= 3000000
//AND 입사_년 >= 2000
//AND 입사_월 >= 08
//AND 입사_일 >= 15
//)