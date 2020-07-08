import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;


public class LoginGUI extends JFrame implements ActionListener{
//	JFrame frame;
	JLabel label_id,label_pw;
	JTextField tf_id;
	JPasswordField tf_pw;
	JButton logInBtn,signUpBtn;
	String id;
	String pw;
	boolean flag;
	ArrayList<String> clientID;
	ArrayList<String> clientPassword;
	ArrayList<Boolean> clientLogin;
	
	
	LoginGUI(){
		flag=false;
		clientID = new ArrayList<String>();
	    clientPassword = new ArrayList<String>();
	    clientLogin = new ArrayList<Boolean>();
			
		label_id=new JLabel();
		label_pw=new JLabel();
		tf_id=new JTextField();
		tf_pw=new JPasswordField();
		logInBtn=new JButton();
		signUpBtn=new JButton();
		
		label_id.setText("ID");
		label_id.setBounds(3,3,20,10);
		label_pw.setText("Password");
		label_pw.setBounds(3,6,20,10);
		logInBtn.setText("Login");
		signUpBtn.setText("Sign up");
		
		this.setLayout(new BorderLayout());
		
		JPanel panel1=new JPanel();
		panel1.setLayout(new GridLayout(2,2));
		panel1.add(label_id);
		panel1.add(tf_id);
		panel1.add(label_pw);
		panel1.add(tf_pw);
		
		JPanel panel2=new JPanel();
		panel2.setLayout(new GridLayout(1,2));
		panel2.add(logInBtn);
		panel2.add(signUpBtn);
		
		this.add(panel1,BorderLayout.CENTER);
		this.add(panel2,BorderLayout.SOUTH);
		
		logInBtn.addActionListener(this);
		signUpBtn.addActionListener(this);
		
		this.setSize(200,100);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
	 public void actionPerformed(ActionEvent ae) {
		 try {
			 if(ae.getSource()==logInBtn) {
				 String tmp_id;
				 String tmp_pw;
				 
				 id=tf_id.getText();
				 char[] tmp=tf_pw.getPassword();
				 pw="";
					for(char c : tmp){ 
						Character.toString(c);
						pw += (pw.equals("")) ? ""+c+"" : ""+c+"";
					}
				 	try {
				 		File f = new File("account.txt");
				 		if(!f.exists()) {
				 			FileWriter fw=null;
							BufferedWriter bw=null;
							File file=new File("account.txt");
							try {
								fw=new FileWriter(file,true);
								bw=new BufferedWriter(fw);
								bw.write("");
							}catch(IOException io) {
								io.printStackTrace();
							}
							
							JOptionPane.showMessageDialog(null, "계정이 없거나 비밀번호가 틀렸습니덜.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
			 				tf_id.setText("");
			 				tf_pw.setText("");
				 		}
				 		if(f.length() == 0) {
				 			JOptionPane.showMessageDialog(null, "계정이 없거나 비밀번호가 틀렸다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
			 				tf_id.setText("");
			 				tf_pw.setText("");
				 		}
				 			
				 			BufferedReader br=new BufferedReader(new FileReader("account.txt"));

				 			String s;
				 			while ((s = br.readLine()) != null) {
				 				String[] split = s.split("\t");
				 				clientID.add(split[0]);
				 				clientPassword.add(split[1]);
				 				clientLogin.add(false);
				 			}
				 			
				 		for(int i=0;i<clientID.size();i++) {
			    			if(id.equals(clientID.get(i)) && pw.equals(clientPassword.get(i))) {
			    				if(clientLogin.get(i) == false) {
			    					clientLogin.set(i, true);
			    					flag=true;
			    					this.dispose();
			    					break;
			    				}
			    				else if(clientLogin.get(i) == true) {
			    					flag = false;
			    					tf_id.setText("");
				    				tf_pw.setText("");
				    				break;
			    				}	
			    			}else {
			    				flag=false;
			    			}
			    		}
				 
				 		if(flag == true)
				 			JOptionPane.showMessageDialog(null,"로그인성공.");
				 		else {
				 			tf_id.setText("");
		    				tf_pw.setText("");
				 			JOptionPane.showMessageDialog(null, "계정이 없거나 비밀번호가 틀렸습니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
				 		}

				 	}catch(Exception e) {
				 		FileWriter fw=null;
						BufferedWriter bw=null;
						File file=new File("account.txt");
						try {
							fw=new FileWriter(file,true);
							bw=new BufferedWriter(fw);
							bw.write("");
						}catch(IOException io) {
							io.printStackTrace();
						}
				 		
				 		e.printStackTrace();
				 	}

	
			 }else if(ae.getSource()==signUpBtn) {
				 new SignUpGUI();
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
	 public String returnID() {
		 return id;
	 }
	 public String returnPW() {
		 return pw;
	 }
	 public boolean returnFlag() {
		 return flag;
	 }
	public static void main(String[] args) {
		new LoginGUI();
	}
}