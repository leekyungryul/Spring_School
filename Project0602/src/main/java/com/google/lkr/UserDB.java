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
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/0602.db",
					config.toProperties());
			String query = "CREATE TABLE users (idx INTEGER PRIMARY KEY AUTOINCREMENT"
					+ ", id TEXT, pwd TEXT, name TEXT, birthday TEXT, " + "address TEXT, created TEXT, updated TEXT)";
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
			connection.close();
		} catch (Exception e) {
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

	public boolean insertDB(People people) {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/0602.db",
					config.toProperties());
			people.pwd = sha256(people.pwd);
			String query = "INSERT INTO users (id, pwd, name, birthday, address, created, updated)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, people.id);
			preparedStatement.setString(2, people.pwd);
			preparedStatement.setString(3, people.name);
			preparedStatement.setString(4, people.birthday);
			preparedStatement.setString(5, people.address);
			preparedStatement.setString(6, people.created);
			preparedStatement.setString(7, people.updated);
			int result = preparedStatement.executeUpdate();
			if (result < 1) {
				return false;
			}
			connection.close();
			preparedStatement.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public StringBuilder selectData() {
		StringBuilder sb = new StringBuilder();
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/0602.db",
					config.toProperties());
			String query = "SELECT * FROM users";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int idx = resultSet.getInt("idx");
				String id = resultSet.getString("id");
				String pwd = resultSet.getString("pwd");
				String name = resultSet.getString("name");
				String birthday = resultSet.getString("birthday");
				String address = resultSet.getString("address");
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
				sb.append("<a href='update?idx=");
				sb.append(idx);
				sb.append("'>수정하기</a></td>");
				sb.append("</td>");
				sb.append("<td>");
				sb.append("<a href='delete?idx=");
				sb.append(idx);
				sb.append("'>삭제하기</a></td>");
				sb.append("</td>");
				sb.append("</tr>");
			}
		} catch (Exception e) {
		}
		return sb;
	}
	
//	id와 pwd를 파라미터로 받아서 동일한 id와 pwd를 가지는 데이터가 존재하는지를 확인하는 로직
	public boolean loginDB(String id, String pwd) {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/0602.db",
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
}