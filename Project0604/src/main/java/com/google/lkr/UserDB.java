package com.google.lkr;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.sqlite.SQLiteConfig;

public class UserDB {
	public boolean createTable() {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/0604.db",
					config.toProperties());
			String query = "CREATE TABLE users (idx INTEGER PRIMARY KEY AUTOINCREMENT,"
					+" id TEXT, pwd TEXT, name TEXT, address TEXT, birthday TEXT, created TEXT, updated TEXT)";
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
	
//	조영철 교수가 한 방법 
	public boolean insertData2(People people) {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/0604.db",
					config.toProperties());
//			id가 동일한 데이터가 있는지를 확인하는 로직
			String query1 = "SELECT * FROM users WHERE id=?";
			PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
//			파라미터로 들어온 객체의 id값을 쿼리문에 대입해준다.
			preparedStatement1.setString(1, people.id);
//			쿼리의 실행결과를 resultSet에 담아준다.
			ResultSet resultSet = preparedStatement1.executeQuery();
//			결과물이 있다면 동일한 id를 가진 객체가 있다는 뜻이므로 false값을 반환하여준다.
			if(resultSet.next()) {
				preparedStatement1.close();
				connection.close();
				return false;
			}
			preparedStatement1.close();
//			결과물이 없다면 데이터 주입 절차를 정상적으로 진행한다.
			String query2 = "INSERT INTO users (id, pwd, name, address, birthday, created, updated)"+
					" VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
			preparedStatement2.setString(1, people.id);
			preparedStatement2.setString(2, people.pwd);
			preparedStatement2.setString(3, people.name);
			preparedStatement2.setString(4, people.address);
			preparedStatement2.setString(5, people.birthday);
			preparedStatement2.setString(6, people.created);
			preparedStatement2.setString(7, people.updated);
//			실행결과가 정상이면 숫자1이 담아진다
			int result = preparedStatement2.executeUpdate();
//			결과가 1이 아니면 false를 반환
			if(result < 1) {
				preparedStatement2.close();
				connection.close();
				return false;
			}
			preparedStatement2.close();
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

//	내가 한 방법
	public boolean loginCheck(People people) {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
//		 	테이블의 모든 데이터를 불러오는 쿼리
			String query = "SELECT * FROM users";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
//			쿼리를 실행한 결과를 resultSet에 담고
			ResultSet resultSet = preparedStatement.executeQuery();
//			한줄씩 확인하면서 
			while (resultSet.next()) {
				int idx = resultSet.getInt("idx");
				String id = resultSet.getString("id");
				String pwd = resultSet.getString("pwd");
				String name = resultSet.getString("name");
//				해당 행의 id,name과 파라미터에 있는 객체의 id,name이 같으면 false반환
				if(id.equals(people.id) || name.equals(people.name)){
					return false;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean insertData(People people) {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/0604.db",
					config.toProperties());
			String query = "INSERT INTO users (id, pwd, name, address, birthday, created, updated)"+
					" VALUES (?, ?, ?, ?, ?, ?, ?)";
			people.pwd = sha256(people.pwd);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, people.id);
			preparedStatement.setString(2, people.pwd);
			preparedStatement.setString(3, people.name);
			preparedStatement.setString(4, people.address);
			preparedStatement.setString(5, people.birthday);
			preparedStatement.setString(6, people.created);
			preparedStatement.setString(7, people.updated);
			int result = preparedStatement.executeUpdate();
			if(result < 1) {
				return false;
			}
			preparedStatement.close();
			connection.close();
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	
//	id와 pwd를 파라미터로 받아서 동일한 id와 pwd를 가지는 데이터가 존재하는지를 확인하는 로직
	public boolean loginDB(String id, String pwd) {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/0604.db",
					config.toProperties());
//			사용자에게 입력받은 pwd는 hash처리한다.
			pwd = this.sha256(pwd);
//			사용자가 입력한 id와 사용자가 입력한 pwd를 hash처리된 pwd를가지는 데이터를 불러오는 쿼리 
			String query = "SELECT * FROM users WHERE id=? AND pwd=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, pwd);
//			쿼리를 실행한 결과를 resultSet변수에 담아준다.
			ResultSet resultSet = preparedStatement.executeQuery();
//			만약 결과물이 있다면 true반환(id,pwd가 확인되었음)
			if(resultSet.next()) {
				preparedStatement.close();
				connection.close();
				return true;
//				결과물이 없다면 false반환
			}else {
				preparedStatement.close();
				connection.close();
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public StringBuilder listData() {
		StringBuilder sb = new StringBuilder(); 
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/0604.db",
					config.toProperties());
			String query = "SELECT * FROM users";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int idx = resultSet.getInt("idx");
				String id = resultSet.getString("id");
				String pwd = resultSet.getString("pwd");
				String name = resultSet.getString("name");
				String address = resultSet.getString("address");
				String birthday = resultSet.getString("birthday");
				String created = resultSet.getString("created");
				String updated = resultSet.getString("updated");				
				sb.append("<tr>");
				sb.append("<td>");
				sb.append(idx);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(id);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(name);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(birthday);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(address);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(created);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(updated);
				sb.append("</td>");
				sb.append("<td>");
				sb.append("<a href='update?idx="+idx+"'>수정하기</a></td>");
				sb.append("</td>");
				sb.append("<td>");
				sb.append("<a href='delete?idx="+idx+"'>삭제하기</a></td>");
				sb.append("</td>");
				sb.append("</tr>");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sb;
	}
}
