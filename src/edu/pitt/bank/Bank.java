package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

public class Bank {
	private ArrayList<Account> accountList = new ArrayList<Account>();
	private ArrayList<Customer> customerList = new ArrayList<Customer>();
	
	/**
	 * This class constructor creates an instance of Bank and retrieves all accounts listed in the database and associates customer owners of those accounts
	 */
	public Bank(){
		loadAccounts();
		setAccountOwners();
	}
	/**
	 * This method loads a list of all accounts in the database
	 */
	private void loadAccounts(){
		String sql = "SELECT accountID, type, balance, interestRate, penalty, status, dateOpen FROM account;";
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			
			while(rs.next()){
				Account acct = new Account(rs.getString("accountID"));
				this.accountList.add(acct);
			}
		} catch (SQLException e) {
			ErrorLogger.log("Unable to load accounts from the database");
			ErrorLogger.log(e.getMessage());
		}
	}
	
	/**
	 * This method adds a account to the account list
	 * @param accountID
	 * @return
	 */
	private Account findAccount(String accountID){
		Account acct = new Account(accountID);
		if(accountList.contains(acct)){
			return acct;
		}
		return acct;
	}
	
	
	/**
	 * This method adds a customer to the customer list
	 * @param customerID
	 * @return
	 */
	private Customer findCustomer(String customerID){
		Customer cust = new Customer(customerID);
		if(customerList.contains(cust)){
			return cust;
		}
		return cust;
	}
	/**
	 * This method associates all accounts with their respective customer owners
	 */
	private void setAccountOwners(){
		String sql = "SELECT * ";
		sql += "FROM customer ";
		sql += "JOIN customer_account ON customerId = fk_customerId ";
		sql += "JOIN account ON fk_accountId = accountId;";
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				Account acct = new Account(rs.getString("accountID"));
				Customer cust = new Customer(rs.getString("customerID"));
				this.customerList.add(cust);
				acct.addAccountOwner(cust);
			}
		} catch (SQLException e) {
			ErrorLogger.log("Unable to match accounts with account owners");
			ErrorLogger.log(e.getMessage());
		}
	}
	/**
	 * This Method finds associated accounts from a customer object created using customerID
	 * @param customerID
	 * @return ArrayList<Account>
	 */
	public ArrayList<Account> findAccountsforCustomer(String customerID){
		String sql = "SELECT * FROM customer ";
		sql += "JOIN customer_account ON customerId = fk_customerId ";
		sql += "JOIN account ON fk_accountId = accountId ";
		sql += "WHERE customerId = '" + customerID + "';";
		DbUtilities db = new MySqlUtilities();
		ResultSet rs;
		ArrayList<Account> accounts = new ArrayList<Account>();
		try {
			rs = db.getResultSet(sql);
			if(rs.next()){
				Account acct = new Account(rs.getString("accountId"));
				accounts.add(acct);
			}
		} catch (SQLException e) {
			ErrorLogger.log("Unable to retreive account list for a specific customer");
			ErrorLogger.log(e.getMessage());
		}
		return accounts;
	}

	/**
	 * @return the accountList
	 */
	public ArrayList<Account> getAccountList() {
		return accountList;
	}

	/**
	 * @param accountList the accountList to set
	 */
	public void setAccountList(ArrayList<Account> accountList) {
		this.accountList = accountList;
	}

	/**
	 * @return the customerList
	 */
	public ArrayList<Customer> getCustomerList() {
		return customerList;
	}

	/**
	 * @param customerList the customerList to set
	 */
	public void setCustomerList(ArrayList<Customer> customerList) {
		this.customerList = customerList;
	}


	
	
	
}
