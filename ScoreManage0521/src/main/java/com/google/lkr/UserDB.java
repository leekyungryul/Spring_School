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
//			db open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db", config.toProperties());
//			use
//			쿼리문 실행
//			primary 키 잡는방법 확인
//			"CREATE TABLE " + this.tableName + "(" + query + ");";
			String query = "CREATE TABLE student(idx INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, middleScore REAL, finalScore REAL, now TEXT)";
//			sqlite는 정수는 integer, 실수는 real, 문자열은 text
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
//			close
			connection.close();
		} catch(Exception e) {
			
		}
	}
	
	public void insertAction(String name, double middleScore, double finalScore) {
		try {
//			db open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db", config.toProperties());
//			use
//			쿼리문 실행
//			자바에서 날짜만들어서 시간정보 넣어도 되고 db에서 제공하는 날짜함수 사용해도 된다.
//			아래는 날짜함수 사용(자바에서 현재날짜 생성하는것을 추천)
//			name은 문자형으로 들어가야하기 때문에 작은따옴표로 감싸주었다.
//			필드명은 객체생성시 입력한 변수명을 그대로 넣어야하고
//			밸류에는 위에 파라미터값에 들어간 변수명을 그대로 사용해야한다.
			String query = "INSERT INTO student (name, middleScore, finalScore, now) VALUES('"
					+ name + "'," + middleScore + ", " +finalScore + ", datetime('now'))";
//			sqlite는 정수는 integer, 실수는 real, 문자열은 text
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
//			close
			connection.close();
		} catch(Exception e) {
			
		}
	}
	
	public String selectData() {
		String resultString = "";
		try {
//			db open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db", config.toProperties());
//			use
			String query = "SELECT * FROM student";
//			String query = "SELECT * FROM student WHERE name LIKE ?";
//			아래는 쿼리로 연결한다는 그런거 같음
			PreparedStatement preparedStatement = connection.prepareStatement(query);
//			첫번째 물음표를 %홍%로 바꿔줘
//			preparedStatement.setString(1, "%홍%");
//			쿼리를 실행한 결과물이 Resultset이라는 형태로 나온다.
//			아래는 resultSet이라는 변수에 결과를 담았다.
			ResultSet resultSet = preparedStatement.executeQuery();
//			아래 next함수를 실행하면 커서가 한칸씩 이동한다.
			while(resultSet.next()) {
				int idx = resultSet.getInt("idx");
				String name = resultSet.getString("name");
				double middleScore = resultSet.getDouble("middleScore");
				double finalScore = resultSet.getDouble("finalScore");
				String now = resultSet.getString("now");
				resultString = resultString + "<tr>";
				resultString = resultString + "<td>" + idx + "</td><td>" + name + "</td><td>" + middleScore
						+ "</td><td>" + finalScore + "</td><td>" + now + "</td><td><a href='update?idx=" + idx + "'>수정하기</a></td>";
				resultString = resultString + "</tr>";
				
			}
			preparedStatement.close();
//			close
			connection.close();
		} catch(Exception e) {
		}
//		위에서 받아온 결과물을 반환한다.
		return resultString;
	}
	
	public Student detailsData(int idx) {
		Student resultData = new Student();
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "c:/tomcat/user.db", config.toProperties());			
//			이번에는 idx값을 가지고 데이터를 찾아낸다. 그러면 당연히 1개 데이터만 불러와질것이다.
			String query = "SELECT * FROM student WHERE idx=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
//			쿼리문에 첫번째 물음표에 해당하는 값은 idx으로 맵핑시킨다.
//			idx값은 위에서 파라미터에 들어가있는 idx이다.
			preparedStatement.setInt(1, idx);
//			위의 정보를 가지고 쿼리를 날린 결과물
			ResultSet resultSet = preparedStatement.executeQuery();
//			아래는 앞에서 작성한 selectData와 동일하다.
//			다만 selectData에서는 while문을 이용해서 다음 데이터가 있다면 계속 불러왔지만 지금은 하나의 데이터만 있을것이기 때문에
//			if문을 사용하면 된다.
			if(resultSet.next()) {
//				resultdata는 위에보면 새로운 Student객체를 생성한것이다.
//				그러므로 객체 생성시에 사용한 변수며을 그대로 맵핑시켜주어야 한다.
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
//			이번에는 ?가 다수인 상황이다. 쿼리문에 들어가는 각각의 값들을 아래에서 맵핑시켜주기 위함이다. 
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
