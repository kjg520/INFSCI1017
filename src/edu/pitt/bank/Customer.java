package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

public class Customer {
	private String customerID;
	private String firstName;
	private String lastName;
	private String ssn;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String loginName;
	private String pin;
	/**
	 * This class constructor retrieves data from the database for a customer to be used by the Bank application
	 * @param customerID
	 */
	public Customer(String customerID){
		String sql = "SELECT * FROM customer "; 
		sql += "WHERE customerID = '" + customerID + "';";
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			if(rs.next()){
				this.customerID = rs.getString("customerID");
				this.firstName = rs.getString("firstName");
				this.lastName = rs.getString("lastName");
				this.ssn = rs.getString("ssn");
				this.streetAddress = rs.getString("streetAddress");
				this.city = rs.getString("city");
				this.state = rs.getString("state");
				this.zip = rs.getString("zip");
				this.loginName = rs.getString("loginName");
				this.pin = rs.getString("pin");
			}
		} catch (SQLException e) {
			ErrorLogger.log("Unable to populate Customer object");
			ErrorLogger.log(e.getMessage());
		}
	}
	/**
	 * This class constructor creates a new customer and adds it to the database
	 * @param lastName
	 * @param firstName
	 * @param ssn
	 * @param loginName
	 * @param pin
	 * @param streetAddress
	 * @param city
	 * @param state
	 * @param zip
	 */
	public Customer(String lastName, String firstName, String ssn, String loginName, int pin, String streetAddress, String city, String state, String zip){
		this.customerID = java.util.UUID.randomUUID().toString();
		this.firstName = firstName;
		this.lastName = lastName;
		this.ssn = ssn;
		this.loginName = loginName;
		this.pin = Integer.toString(pin);
		this.city = city;
		this.streetAddress = streetAddress;
		this.state = state;
		this.zip = zip;
		
		String sql = "INSERT INTO customer(customerID, firstName, lastName, ssn, loginName, pin) VALUES (customerID, firstName, lastName, ssn, loginName, pin)";
		DbUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
	}

	/**
	 * @return the customerID
	 */
	public String getCustomerID() {
		return customerID;
	}

	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * @param streetAddress the streetAddress to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * @param pin the pin to set
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}
}
