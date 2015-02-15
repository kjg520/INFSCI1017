package edu.pitt.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import edu.pitt.bank.Bank;
import edu.pitt.bank.Account;
import edu.pitt.bank.Customer;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class AccountDetailsUI {

	private Customer accountOwner;
	private Account selectedAccount;
	
	private JFrame accountDetails;
	private JComboBox cboAccountList;
	private JTextField txtAmount;
	private JTextPane txtpnWelcomeMessage;
	private JLabel lblYourAccounts;
	private JLabel lblAccountType;
	private JLabel lblBalance;
	private JLabel lblInterestRate;
	private JLabel lblPenalty; 
	private JLabel lblAmount;
	private JButton btnDeposit;
	private JButton btnWithdraw;
	private JButton btnShowTransactions;
	private JButton btnExit;


	/**
	 * Create the application.
	 */
	public AccountDetailsUI(Customer cust) {
		this.accountOwner = cust;
		initialize();
		accountDetails.setVisible(true);
	}

	/**
	 * Initialize the contents of the Account Details frame.
	 */
	private void initialize() {
		accountDetails = new JFrame();
		accountDetails.setTitle("Account Details");
		accountDetails.setBounds(100, 100, 450, 300);
		accountDetails.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		accountDetails.getContentPane().setLayout(null);
		
		cboAccountList = new JComboBox();
		cboAccountList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent item) {
				String selectedAccountID = cboAccountList.getSelectedItem().toString();
				selectedAccount = new Account(selectedAccountID);
				
				lblAccountType = new JLabel("Account Type: " + selectedAccount.getType());
				lblAccountType.setBounds(10, 117, 150, 20);
				accountDetails.getContentPane().add(lblAccountType);
				
				lblBalance = new JLabel("Balance: " + selectedAccount.getBalance());
				lblBalance.setBounds(10, 139, 150, 20);
				accountDetails.getContentPane().add(lblBalance);
				
				lblInterestRate = new JLabel("Interest Rate: "  + selectedAccount.getInterestRate());
				lblInterestRate.setBounds(10, 163, 150, 14);
				accountDetails.getContentPane().add(lblInterestRate);
				
				lblPenalty = new JLabel("Penalty: " + selectedAccount.getPenalty());
				lblPenalty.setBounds(10, 181, 150, 20);
				accountDetails.getContentPane().add(lblPenalty);
			}
		});
		cboAccountList.setBounds(120, 69, 304, 20);
		Bank bank = new Bank();
		ArrayList<Account> customerAccounts= bank.findAccountsforCustomer(accountOwner.getCustomerID());
		for(Account acct: customerAccounts){
			cboAccountList.addItem(acct.getAccountID());
		}
		accountDetails.getContentPane().add(cboAccountList);
		
		lblYourAccounts = new JLabel("Your Accounts");
		lblYourAccounts.setBounds(10, 72, 100, 14);
		accountDetails.getContentPane().add(lblYourAccounts);
		
		txtpnWelcomeMessage = new JTextPane();
		txtpnWelcomeMessage.setBackground(SystemColor.control);
		txtpnWelcomeMessage.setText("Welcome to the 1017 Bank. You have the following permissions in this system: Administrator, Branch Manager, Customer");
		txtpnWelcomeMessage.setBounds(10, 11, 414, 46);
		accountDetails.getContentPane().add(txtpnWelcomeMessage);
		
		txtAmount = new JTextField();
		txtAmount.setBounds(279, 117, 132, 20);
		accountDetails.getContentPane().add(txtAmount);
		txtAmount.setColumns(10);
		
		lblAmount = new JLabel("Amount:");
		lblAmount.setBounds(194, 120, 75, 14);
		accountDetails.getContentPane().add(lblAmount);
		
		btnDeposit = new JButton("Deposit");
		btnDeposit.setBounds(206, 159, 89, 23);
		accountDetails.getContentPane().add(btnDeposit);
		
		btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnWithdraw.setBounds(305, 159, 106, 23);
		accountDetails.getContentPane().add(btnWithdraw);
		
		btnShowTransactions = new JButton("Show Transactions");
		btnShowTransactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedAccount = new Account(cboAccountList.getSelectedItem().toString());
				TransactionListUI transList = new TransactionListUI(selectedAccount);
				Window frmTransList = transList.getAccountTransactions();
				frmTransList.setVisible(true);
			}
		});
		btnShowTransactions.setBounds(158, 215, 181, 23);
		accountDetails.getContentPane().add(btnShowTransactions);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountDetails.setVisible(false);
			}
		});
		btnExit.setBounds(349, 215, 75, 23);
		accountDetails.getContentPane().add(btnExit);
	}

	/**
	 * @return the accountDetails
	 */
	public JFrame getAccountDetails() {
		return accountDetails;
	}

	/**
	 * @param accountDetails the accountDetails to set
	 */
	public void setAccountDetails(JFrame accountDetails) {
		this.accountDetails = accountDetails;
	}

	/**
	 * @return the cboAccountList
	 */
	public JComboBox getCboAccountList() {
		return cboAccountList;
	}

	/**
	 * @param cboAccountList the cboAccountList to set
	 */
	public void setCboAccountList(JComboBox cboAccountList) {
		this.cboAccountList = cboAccountList;
	}

	/**
	 * @return the txtAmount
	 */
	public JTextField getTxtAmount() {
		return txtAmount;
	}

	/**
	 * @param txtAmount the txtAmount to set
	 */
	public void setTxtAmount(JTextField txtAmount) {
		this.txtAmount = txtAmount;
	}

	/**
	 * @return the txtpnWelcomeMessage
	 */
	public JTextPane getTxtpnWelcomeMessage() {
		return txtpnWelcomeMessage;
	}

	/**
	 * @param txtpnWelcomeMessage the txtpnWelcomeMessage to set
	 */
	public void setTxtpnWelcomeMessage(JTextPane txtpnWelcomeMessage) {
		this.txtpnWelcomeMessage = txtpnWelcomeMessage;
	}

	/**
	 * @return the lblYourAccounts
	 */
	public JLabel getLblYourAccounts() {
		return lblYourAccounts;
	}

	/**
	 * @param lblYourAccounts the lblYourAccounts to set
	 */
	public void setLblYourAccounts(JLabel lblYourAccounts) {
		this.lblYourAccounts = lblYourAccounts;
	}

	/**
	 * @return the lblAccountType
	 */
	public JLabel getLblAccountType() {
		return lblAccountType;
	}

	/**
	 * @param lblAccountType the lblAccountType to set
	 */
	public void setLblAccountType(JLabel lblAccountType) {
		this.lblAccountType = lblAccountType;
	}

	/**
	 * @return the lblBalance
	 */
	public JLabel getLblBalance() {
		return lblBalance;
	}

	/**
	 * @param lblBalance the lblBalance to set
	 */
	public void setLblBalance(JLabel lblBalance) {
		this.lblBalance = lblBalance;
	}

	/**
	 * @return the lblInterestRate
	 */
	public JLabel getLblInterestRate() {
		return lblInterestRate;
	}

	/**
	 * @param lblInterestRate the lblInterestRate to set
	 */
	public void setLblInterestRate(JLabel lblInterestRate) {
		this.lblInterestRate = lblInterestRate;
	}

	/**
	 * @return the lblPenalty
	 */
	public JLabel getLblPenalty() {
		return lblPenalty;
	}

	/**
	 * @param lblPenalty the lblPenalty to set
	 */
	public void setLblPenalty(JLabel lblPenalty) {
		this.lblPenalty = lblPenalty;
	}

	/**
	 * @return the lblAmount
	 */
	public JLabel getLblAmount() {
		return lblAmount;
	}

	/**
	 * @param lblAmount the lblAmount to set
	 */
	public void setLblAmount(JLabel lblAmount) {
		this.lblAmount = lblAmount;
	}

	/**
	 * @return the btnDeposit
	 */
	public JButton getBtnDeposit() {
		return btnDeposit;
	}

	/**
	 * @param btnDeposit the btnDeposit to set
	 */
	public void setBtnDeposit(JButton btnDeposit) {
		this.btnDeposit = btnDeposit;
	}

	/**
	 * @return the btnWithdraw
	 */
	public JButton getBtnWithdraw() {
		return btnWithdraw;
	}

	/**
	 * @param btnWithdraw the btnWithdraw to set
	 */
	public void setBtnWithdraw(JButton btnWithdraw) {
		this.btnWithdraw = btnWithdraw;
	}

	/**
	 * @return the btnShowTransactions
	 */
	public JButton getBtnShowTransactions() {
		return btnShowTransactions;
	}

	/**
	 * @param btnShowTransactions the btnShowTransactions to set
	 */
	public void setBtnShowTransactions(JButton btnShowTransactions) {
		this.btnShowTransactions = btnShowTransactions;
	}

	/**
	 * @return the btnExit
	 */
	public JButton getBtnExit() {
		return btnExit;
	}

	/**
	 * @param btnExit the btnExit to set
	 */
	public void setBtnExit(JButton btnExit) {
		this.btnExit = btnExit;
	}

	
}
