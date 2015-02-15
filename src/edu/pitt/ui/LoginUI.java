package edu.pitt.ui;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;

import javax.swing.JTextField;
import javax.swing.JButton;

import edu.pitt.bank.Account;
import edu.pitt.bank.Bank;
import edu.pitt.bank.Customer;
import edu.pitt.bank.Security;
import edu.pitt.utilities.ErrorLogger;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class LoginUI {

	private JFrame frmBankLogin;
	private JTextField txtLoginName;
	private JTextField txtPassword;
	private JComboBox cboAccountList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI window = new LoginUI();
					window.frmBankLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBankLogin = new JFrame();
		frmBankLogin.setTitle("Bank 1017 Login");
		frmBankLogin.setBounds(100, 100, 360, 271);
		frmBankLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBankLogin.getContentPane().setLayout(null);
		
		JLabel lblLoginName = new JLabel("Login Name");
		lblLoginName.setBounds(10, 25, 151, 34);
		frmBankLogin.getContentPane().add(lblLoginName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 103, 151, 14);
		frmBankLogin.getContentPane().add(lblPassword);
		
		txtLoginName = new JTextField();
		txtLoginName.setBounds(10, 63, 310, 28);
		frmBankLogin.getContentPane().add(txtLoginName);
		txtLoginName.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(10, 128, 310, 28);
		frmBankLogin.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		cboAccountList = new JComboBox();
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Security sec = new Security();
				String password = txtPassword.getText();
				String loginName = txtLoginName.getText();
				try{
					Customer cust = sec.validateLogin(loginName, Integer.parseInt(password));
					if (cust != null){
						frmBankLogin.setVisible(false);
						AccountDetailsUI acctDetails = new AccountDetailsUI(cust);
					}else{
						JOptionPane.showMessageDialog(null, "You have entered an incorrect Login Name or Pin.");
					}
				}catch(NumberFormatException nfe){
					JOptionPane.showMessageDialog(null, "Your Password must be a four-digit integer.");
					ErrorLogger.log("User entered a non integer password");
					ErrorLogger.log(nfe.getMessage());
				}
			}
		});
		btnLogin.setBounds(231, 189, 89, 23);
		frmBankLogin.getContentPane().add(btnLogin);
	}
}
