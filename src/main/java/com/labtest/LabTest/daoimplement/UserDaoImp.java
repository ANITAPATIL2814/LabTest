//business logic
package com.labtest.LabTest.daoimplement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.labtest.LabTest.dao.UserDao;
import com.labtest.LabTest.entity.User;
import com.labtest.LabTest.helper.DatabaseConnection;

public class UserDaoImp implements UserDao{

	@Override
	public User getUserById(int userId) throws SQLException {
		Connection connection=DatabaseConnection.getConnection();
		String query="Select*from User where userId=? ";
		PreparedStatement statement= connection.prepareStatement(query);//to call sql statment
		statement.setInt(1, userId);
		ResultSet rs= statement.executeQuery();
		User user=null;
		if(rs.next()) {
			user=new User();
			user.setUserId(rs.getInt("userId"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setRole(rs.getString("role"));
		}
		rs.close();
		statement.close();
		connection.close();
		return user;
	}	

	@Override
	public User login(String username, String password) throws SQLException {
		Connection connection=DatabaseConnection.getConnection();
		String query="Select*from User where username=? && password= ? ";
		PreparedStatement statement= connection.prepareStatement(query);//to call sql statment
		statement.setString(1, username);
		statement.setString(2, password);
		ResultSet rs= statement.executeQuery();
		User user=null;
		if(rs.next()) {
			user=new User();
			user.setUserId(rs.getInt("userId"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setRole(rs.getString("role"));
		}
		rs.close();
		statement.close();
		connection.close();
		return user;
	}
	
}
