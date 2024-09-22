package com.labtest.LabTest.dao;

import java.sql.SQLException;

import com.labtest.LabTest.entity.User;

public interface UserDao {
	/*
	 * fetch using id
	 * user login
	 */
	 
	User getUserById(int userId) throws SQLException;
	User login(String username, String password)  throws SQLException;
	
}
