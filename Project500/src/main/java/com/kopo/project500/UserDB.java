package com.kopo.project500;

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
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db", config.toProperties());			
			// use
			String query = "CREATE TABLE student (idx INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, middleScore REAL, finalScore REAL, created TEXT)";
			// sqlite는 정수는 INTEGER, 실수는 REAL, 문자열 TEXT
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
			
			// close
			connection.close();
		} catch (Exception e) {
		}
	}
	
	public void insertData(String name, double middleScore, double finalScore) {
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db", config.toProperties());			

			String query = "INSERT INTO student (name, middleScore, finalScore, created) VALUES ('"
			+ name + "', " + middleScore + ", " + finalScore + ", datetime('now'))";
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
			
			// close
			connection.close();
		} catch (Exception e) {
		}
	}

	public String selectData() {
		String resultString = "";
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db", config.toProperties());			

			String query = "SELECT * FROM student";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int idx = resultSet.getInt("idx");
				String name = resultSet.getString("name");
				double middleScore = resultSet.getDouble("middleScore");
				double finalScore = resultSet.getDouble("finalScore");
				String created = resultSet.getString("created");
				resultString = resultString + "<tr>";
				resultString = resultString + "<td>" + idx + "</td><td>" + name + "</td><td>" + middleScore
						+ "</td><td>" + finalScore + "</td><td>" + created + "</td><td><a href='update?idx=" + idx + "'>수정하기</a></td>";
				resultString = resultString + "</tr>";
			}
			preparedStatement.close();
			
			// close
			connection.close();
		} catch (Exception e) {
		}
		return resultString;
	}

	public Student detailsData(int idx) {
		Student resultData = new Student();
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db", config.toProperties());			

			String query = "SELECT * FROM student WHERE idx=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, idx);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				resultData.idx = resultSet.getInt("idx");
				resultData.name = resultSet.getString("name");
				resultData.middleScore = resultSet.getDouble("middleScore");
				resultData.finalScore = resultSet.getDouble("finalScore");
				resultData.created = resultSet.getString("created");
			}
			preparedStatement.close();
			
			// close
			connection.close();
		} catch (Exception e) {
		}
		return resultData;
	}

	public void updateData(int idx, String name, double middleScore, double finalScore) {
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db", config.toProperties());			

			String query = "UPDATE student SET name=?, middleScore=?, finalScore=?, created=datetime('now') WHERE idx=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setDouble(2, middleScore);
			preparedStatement.setDouble(3, finalScore);
			preparedStatement.setInt(4, idx);
			int result = preparedStatement.executeUpdate();
			preparedStatement.close();
			
			// close
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
