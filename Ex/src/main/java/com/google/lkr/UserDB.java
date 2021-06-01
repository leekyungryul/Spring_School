package com.google.lkr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;

public class UserDB {
	public void createTable(String className) {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/0526.db",
					config.toProperties());
			String query = "CREATE TABLE " +className+ " (idx INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "name TEXT, middleScore REAL, finalScore REAL, date TEXT)";
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
//			close
			connection.close();
		}catch (Exception e) {
		}
	}
	public void insertData(String className, String name, double middleScore, double finalScore) {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/0526.db",
					config.toProperties());
			String query = "INSERT INTO "+ className+ " (name, middleScore, finalScore, date) VALUES ('"
			+ name + "', " + middleScore + ", " + finalScore + ", datetime('now'))";
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
			
			// close
			connection.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public StringBuilder selectData(String className) {
		StringBuilder sb = new StringBuilder();
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/0526.db",
					config.toProperties());
			String query = "SELECT * FROM " + className;
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int idx = resultSet.getInt("idx");
				String name = resultSet.getString("name");
				double middleScore = resultSet.getDouble("middleScore");
				double finalScore = resultSet.getDouble("middleScore");
				String date = resultSet.getString("date");
				sb.append("<tr>");
				sb.append("<td>");
				sb.append(idx);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(className);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(name);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(middleScore);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(finalScore);
				sb.append("</td>");
				sb.append("<td>");
				sb.append((middleScore+finalScore)/2);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(date);
				sb.append("</td>");
				sb.append("<td>");
				sb.append("<a href='update?idx="	+ idx + "'>수정하기 </a></td>");
				sb.append("</td>");
				sb.append("<td>");
				sb.append("<a href='delete?idx="	+ idx + "'>삭제하기 </a></td>");
				sb.append("<tr>");
			}
		}catch (Exception e) {
		}
		return sb;
	}
	public Student detailsData(String name, int idx) {
		Student resultData = new Student();
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/0526.db",
					config.toProperties());
			String query = "SELECT * FROM "+name+" WHERE idx=?";
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
}
