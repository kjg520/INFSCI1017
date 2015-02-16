package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

public class Account {
	private String accountID;
	private String type;
	private double balance;
	private double interestRate;
	private double penalty;
	private String status;
	private Date dateOpen;
	private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	private ArrayList<Customer> accountOwners = new ArrayList<Customer>();
	/**
	 * This class constructor retrieves account ID from the database, uses that ID to populate properties of the account class, retrieves data from the tansaction table in the database and uses that data to create transaction objects
	 * @param accountID
	 */
	public Account(String accountID){
		String sql = "SELECT * FROM account "; 
		sql += "WHERE accountID = '" + accountID + "';";
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			if(rs.next()){
				this.accountID = rs.getString("accountID");
				this.type = rs.getString("type");
				this.balance = rs.getDouble("balance");
				this.interestRate = rs.getDouble("interestRate");
				this.penalty = rs.getDouble("penalty");
				this.status = rs.getString("status");
				this.dateOpen = new Date();
			}
		} catch (SQLException e) {
			ErrorLogger.log("Unable to populate Account object");
			ErrorLogger.log(e.getMessage());
		}finally{
			db.closeDbConnection();
		}
		String sqlQuery = "SELECT * FROM transaction "; 
		sql += "WHERE accountID = '" + accountID + "'";
		MySqlUtilities database = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sqlQuery);
			while(rs.next()){
				this.accountID = rs.getString("accountID");
				String transactionID = rs.getString("transactionID");
				this.type = rs.getString("type");
				this.balance = rs.getDouble("balance");
				double amount = rs.getDouble("amount");
				createTransaction(this.accountID, this.type, amount, balance);
			}
		} catch (SQLException e) {
			ErrorLogger.log("Failed to retrieve Transaction List for Account");
			ErrorLogger.log(e.getMessage());
		}
	}
	/**
	 * This class constructor creates a new account using an initial balance and an account type and adds it to the database
	 * @param accountType
	 * @param initialBalance
	 */
	public Account(String accountType, double initialBalance){
		this.accountID = UUID.randomUUID().toString();
		this.type = accountType;
		this.balance = initialBalance;
		this.interestRate = 0;
		this.penalty = 0;
		this.status = "active";
		this.dateOpen = new Date();
		
		String sql = "INSERT INTO account ";
		sql += "(accountID,type,balance,interestRate,penalty,status,dateOpen) ";
		sql += " VALUES ";
		sql += "('" + this.accountID + "', ";
		sql += "'" + this.type + "', ";
		sql += this.balance + ", ";
		sql += this.interestRate + ", ";
		sql += this.penalty + ", ";
		sql += "'" + this.status + "', ";
		sql += "CURDATE());";
		
		DbUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
	}
	
	/**
	 * This method creates a transaction object, subtracts the requested amount to the account balance and updates the balance in the database
	 * @param amount
	 */
	public void withdraw(double amount){
		this.balance -= amount;
		createTransaction(this.accountID, this.type, amount, this.balance);
		updateDatabaseAccountBalance();
	}
	
	/**
	 * This method creates a transaction object, adds the requested amount to the account balance and updates the balance in the database
	 * @param amount
	 */
	public void deposit(double amount){
		this.balance += amount;
		createTransaction(this.accountID, this.type, amount, this.balance);
		updateDatabaseAccountBalance();
	}
	/**
	 * This method updates an account's balance in the database
	 */
	private void updateDatabaseAccountBalance(){
		String sql = "UPDATE account SET balance = " + this.balance + " ";
		sql += "WHERE accountID = '" + this.accountID + "';";
		
		DbUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
	}
	/**
	 * This method creates a transaction object and adds it to a list of transactions
	 * @param accountID
	 * @param type
	 * @param amount
	 * @param balance
	 * @return
	 */
	private Transaction createTransaction(String transactionID){
		Transaction t = new Transaction(transactionID);
		transactionList.add(t);
		return t;
	}
	/**
	 * This method creates a transaction object and adds it to a list of transactions
	 * @param accountID
	 * @param type
	 * @param amount
	 * @param balance
	 * @return
	 */
	private Transaction createTransaction(String accountID, String type, double amount, double balance){
		Transaction t = new Transaction(accountID, type, amount, balance);
		transactionList.add(t);
		return t;
	}
	/**
	 * This method associates a Customer with an Account
	 * @param accountOwner
	 */
	public void addAccountOwner(Customer accountOwner){
		this.accountOwners.add(accountOwner);
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the interestRate
	 */
	public double getInterestRate() {
		return interestRate;
	}

	/**
	 * @param interestRate the interestRate to set
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * @return the penalty
	 */
	public double getPenalty() {
		return penalty;
	}

	/**
	 * @param penalty the penalty to set
	 */
	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the dateOpen
	 */
	public Date getDateOpen() {
		return dateOpen;
	}

	/**
	 * @param dateOpen the dateOpen to set
	 */
	public void setDateOpen(Date dateOpen) {
		this.dateOpen = dateOpen;
	}

	/**
	 * @param accountID the accountID to set
	 */
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * @return the accountID
	 */
	public String getAccountID() {
		return accountID;
	}

	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @return the transactionList
	 */
	public ArrayList<Transaction> getTransactionList() {
		return transactionList;
	}

	/**
	 * @param transactionList the transactionList to set
	 */
	public void setTransactionList(ArrayList<Transaction> transactionList) {
		this.transactionList = transactionList;
	}

	/**
	 * @return the accountOwners
	 */
	public ArrayList<Customer> getAccountOwners() {
		return accountOwners;
	}

	/**
	 * @param accountOwners the accountOwners to set
	 */
	public void setAccountOwners(ArrayList<Customer> accountOwners) {
		this.accountOwners = accountOwners;
	}
	
	
	
	//@Override
	//private String toString(){
	//	return this.accountID;
	//}
	
}
