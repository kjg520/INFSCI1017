package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;
/**
 * 
 * @author Kevin J. Grande
 * This class represents transactions done for a bank account
 *
 */
public class Transaction {
	private String transactionID;
	private String accountID;
	private String type;
	private double amount;
	private double balance;
	private Date transactionDate; 
	/**
	 * 
	 * @param transactionID
	 * This Constructor retrieves data provided a transaction ID and update properties for the transaction class
	 * 
	 */
	public Transaction(String transactionID){
		String sql = "SELECT * FROM transaction "; 
		sql += "WHERE transactionID = '" + transactionID + "'";
		System.out.println(sql);
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.transactionID = rs.getString("transactionID");
				this.accountID = rs.getString("accountID");
				this.type = rs.getString("type");
				this.amount = rs.getDouble("amount");
				this.balance = rs.getDouble("balance");
				this.transactionDate = new Date();
			}
		} catch (SQLException e) {
			ErrorLogger.log("Unable to populate transaction information from database");
			ErrorLogger.log(e.getMessage());
		}
	}
	/**
	 * 
	 * @param accountID
	 * @param type
	 * @param amount
	 * @param balance
	 * 
	 * This is a class constructor that generates a transaction ID, updates properties of the Transaction class and inserts a record into the transaction table of the MySQL database
	 */
	public Transaction(String accountID, String type, double amount, double balance){
		this.transactionID = UUID.randomUUID().toString();
		this.type = type;
		this.amount = amount;
		this.accountID = accountID;
		this.balance = balance;
		
		String sql = "INSERT INTO transaction ";
		sql += "(transactionID, accountID, amount, transactionDate, type, balance) ";
		sql += " VALUES ";
		sql += "('" + this.transactionID + "', ";
		sql += "'" + this.accountID + "', ";
		sql += amount + ", ";
		sql += "CURDATE(), ";
		sql += "'" + this.type + "', ";
		sql += balance + ");";
		
		//System.out.println(sql);
		
		DbUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
	}
}
