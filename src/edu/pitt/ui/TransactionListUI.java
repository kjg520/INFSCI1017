package edu.pitt.ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.pitt.bank.Account;
import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class TransactionListUI {

	private Account selectedAccount;
	
	private JFrame accountTransactions;
	private JScrollPane transactionPane;
	private JTable tblTransactions;

	/**
	 * Create the application.
	 */
	public TransactionListUI(Account acct) {
		this.selectedAccount = acct;
		initialize();
		accountTransactions.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		accountTransactions = new JFrame();
		accountTransactions.setTitle("Account Transactions");
		accountTransactions.setBounds(100, 100, 450, 300);
		accountTransactions.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		accountTransactions.getContentPane().setLayout(null);
		
		transactionPane = new JScrollPane();
		transactionPane.setSize(415, 200);
		transactionPane.setLocation(10, 22);
		accountTransactions.getContentPane().add(transactionPane);
		String[] cols = {"Transaction ID", "Account ID", "Amount", "Date", "Type", "Balance"}; 
		DbUtilities db = new MySqlUtilities();
		String sql = "SELECT * FROM transaction WHERE AccountId = '" + this.selectedAccount.getAccountID() + "';";
		try {
			DefaultTableModel transactionList = db.getDataTable(sql, cols);
			tblTransactions = new JTable(transactionList);
			tblTransactions.setFillsViewportHeight(true);
			tblTransactions.setShowGrid(true);
			tblTransactions.setGridColor(Color.BLACK);
			transactionPane.setViewportView(tblTransactions);
		} catch (SQLException e1) {
			ErrorLogger.log("Failed to retrieve Transaction List");
			ErrorLogger.log(e1.getMessage());
		}
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountTransactions.setVisible(false);
			}
		});
		btnClose.setBounds(335, 230, 89, 23);
		accountTransactions.getContentPane().add(btnClose);
	}

	public JFrame getAccountTransactions() {
		return accountTransactions;
	}
}
