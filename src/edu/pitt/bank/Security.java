package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

public class Security {
	private String userID;
	private ArrayList<String> groups = new ArrayList<String>();
		
	/**
	 * This methods accepts a login name and password and checks to see if it matches a user in the database
	 * @param loginName
	 * @param pin
	 * @return
	 */
	public Customer validateLogin(String loginName, int pin){
		String sql = "SELECT * FROM customer WHERE loginName = '" + loginName + "' and pin = " + Integer.toString(pin) + ";";
		Customer cust = null;
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			if(rs.next()){
				cust = new Customer(rs.getString("customerID"));
			}
		} catch (SQLException e) {
			ErrorLogger.log("Unable to validate login information");
			ErrorLogger.log(e.getMessage());
		}
		return cust;
	}
	/**
	 * This method retrieves the user groups for a particular user
	 * @param userID
	 * @return
	 */
	public ArrayList<String> listUserGroups(String userID){
		String sql = "SELECT * FROM user_permissions ";
		sql += "JOIN groups WHERE user_permissions.groupID = groups.groupID ";
		sql += "AND groupOrUserID = '" + userID + "';";
		DbUtilities db = new MySqlUtilities();
		ResultSet rs;
		ArrayList<String> userGroups = new ArrayList<String>();	
		System.out.println(sql);
		try {
			rs = db.getResultSet(sql);	
			while(rs.next()){
				String userGroup = rs.getString("groupName");
				userGroups.add(userGroup);
			}
		} catch (SQLException e) {
			ErrorLogger.log("Unable to retrieve user groups for current user");
			ErrorLogger.log(e.getMessage());
		}
		return userGroups;
	}
}
