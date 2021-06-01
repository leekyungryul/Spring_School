package com.google.lkr;

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
			String query = "CREATE TABLE Employ " + "(idx INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "name TEXT, sex TEXT, adress TEXT, part TEXT, date TEXT)";
			System.out.println(query);
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
			connection.close();
		} catch (Exception e) {
		}
	}

	public void insertData(String name, String sex, String adress, String part) {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db", config.toProperties());
			String query = "INSERT INTO Employ (name, sex, adress, part, date) VALUES ('"
					+ name + "', '" + sex + "', '" + adress + "', '" +part+"', datetime('now'))";
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
			System.out.println(query);
			connection.close();
		} catch (Exception e) {
		}
	}
	
	public StringBuilder selectData() {
		StringBuilder sb = new StringBuilder();
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "SELECT * FROM Employ";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int idx = resultSet.getInt("idx");
				String name = resultSet.getString("name");
				String sex = resultSet.getString("sex");
				String adress = resultSet.getString("adress");
				String part = resultSet.getString("part");
				String date = resultSet.getString("date");
				sb.append("<tr>");
				sb.append("<td>");
				sb.append(idx);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(name);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(sex);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(adress);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(part);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(date);
				sb.append("</td>");
				sb.append("<td>");
				sb.append("<a href='update?idx=" + idx + "'>수정하기 </a></td>");
				sb.append("</td>");
				sb.append("<td>");
				sb.append("<a href='delete?idx=" + idx + "'>삭제하기 </a></td>");
				sb.append("<tr>");
			}
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
		}
		return sb;
	}
	
	public Employ detailsData(int idx) {
		Employ resultData = new Employ();
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "SELECT * FROM Employ WHERE idx=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, idx);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				resultData.idx = resultSet.getInt("idx");
				resultData.name = resultSet.getString("name");
				resultData.sex = resultSet.getString("sex");
				resultData.adress = resultSet.getString("adress");
				resultData.part = resultSet.getString("part");
				resultData.date = resultSet.getString("date");				
			}
			preparedStatement.close();
			connection.close();
		}catch (Exception e) {
		}
		return resultData;
	}
	public Employ searchData(String name) {
		Employ resultData = new Employ();
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "SELECT * FROM Employ WHERE name=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				resultData.idx = resultSet.getInt("idx");
				resultData.name = resultSet.getString("name");
				resultData.sex = resultSet.getString("sex");
				resultData.adress = resultSet.getString("adress");
				resultData.part = resultSet.getString("part");
				resultData.date = resultSet.getString("date");				
			}
			preparedStatement.close();
			connection.close();
		}catch (Exception e) {
		}
		return resultData;
	}
	public void updateData(int idx, String name, String sex, String adress, String part) {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "UPDATE employ SET name=?, sex=?, adress=?, part=?, date=datetime('now') WHERE idx=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, sex);
			preparedStatement.setString(3, adress);
			preparedStatement.setString(4, part);
			preparedStatement.setInt(5, idx);
			int result = preparedStatement.executeUpdate();
			System.out.println(result);
			preparedStatement.close();
			connection.close();
		}catch (Exception e) {
		}
	}
	public void deleteData(int idx) {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db",
					config.toProperties());
			String query = "DELETE FROM Employ WHERE idx=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, idx);
			int result = preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
		}catch (Exception e) {
		}
	}
}