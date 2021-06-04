package com.kopo.projecta;

import java.lang.module.ModuleDescriptor.Builder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.sqlite.SQLiteConfig;

public class UserDB {
	public boolean createDB() {
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			// use
			String query = "CREATE TABLE users (idx INTEGER PRIMARY KEY AUTOINCREMENT"
					+ ", id TEXT, pwd TEXT, name TEXT, birthday TEXT, address TEXT, created TEXT, updated TEXT)";
			// sqlite는 정수는 INTEGER, 실수는 REAL, 문자열 TEXT
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();

			// close
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean insertDB(People people) {
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());

//			String query = "INSERT INTO users (id, pwd, name, birthday, address, created, updated) VALUES ('"
//					+ people.id + "', '"
//					+ people.pwd + "', '"
//					+ people.name + "', '"
//					+ people.birthday + "', '"
//					+ people.address + "', '"
//					+ people.created + "', '"
//					+ people.updated + "'"
//					+ ")";
//			Statement statement = connection.createStatement();
//			int result = statement.executeUpdate(query);

//			password sha256
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
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
//	패스워드 암호화를 위한 로직
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
	public StringBuilder selectData() {
		StringBuilder sb = new StringBuilder();
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
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
		}catch (Exception e) {
		}
		return sb;
	}
	
	public People detailsData(int idx) {
		People resultData = new People();
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "SELECT * FROM users WHERE idx=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, idx);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				resultData.idx = resultSet.getInt("idx");
				resultData.id = resultSet.getString("id");
				resultData.pwd = resultSet.getString("pwd");
				resultData.name = resultSet.getString("name");
				resultData.birthday = resultSet.getString("birthday");
				resultData.address = resultSet.getString("address");
				resultData.created = resultSet.getString("created");
				resultData.updated = resultSet.getString("updated");
			}
			preparedStatement.close();
			connection.close();
		}catch (Exception e) {
		}
		return resultData;
	}

	public void updateData(People people) {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(Calendar.getInstance().getTime());
			String query = "UPDATE users SET name=?, birthday=?, address=?, created=?, updated=? WHERE idx=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, people.name);
			preparedStatement.setString(2, people.birthday);
			preparedStatement.setString(3, people.address);
			preparedStatement.setString(4, people.created);
			preparedStatement.setString(5, now);
			preparedStatement.setInt(6, people.idx);
			System.out.println(query);
			int result = preparedStatement.executeUpdate();
			System.out.println(result);
			System.out.println(query);
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
			String query = "DELETE FROM users WHERE idx=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, idx);
			int result = preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}