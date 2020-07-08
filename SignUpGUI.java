import java.awt.BorderLayout;
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

import javax.swing.*;


public class SignUpGUI extends JFrame implements ActionListener {
	JLabel label_id,label_pw,label_pw_again;
	JTextField tf_id;
	JPasswordField tf_pw,tf_pw_again;
	JButton signUp_btn;
	
	SignUpGUI(){
		label_id=new JLabel();
		label_pw=new JLabel();
		label_pw_again=new JLabel();
		tf_id=new JTextField();
		tf_pw=new JPasswordField();
		tf_pw_again=new JPasswordField();
		signUp_btn=new JButton();
		
		label_id.setText("id");
		label_pw.setText("password");
		label_pw_again.setText("re-passworld");
		
		signUp_btn.setText("Sign up");
		
		this.setLayout(new BorderLayout());
		JPanel panel1=new JPanel();
		panel1.setLayout(new GridLayout(3,2));
		panel1.add(label_id);
		panel1.add(tf_id);
		panel1.add(label_pw);
		panel1.add(tf_pw);
		panel1.add(label_pw_again);
		panel1.add(tf_pw_again);
		this.add(panel1,BorderLayout.CENTER);
		
		this.add(signUp_btn,BorderLayout.SOUTH);
		signUp_btn.addActionListener(this);
		
		this.setSize(200,140);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
	void addAccount(String id,char[] tf_pw) {
		FileWriter fw=null;
		BufferedWriter bw=null;
		File file=new File("account.txt");
		String pw="";
		for(char c : tf_pw){ 
			Character.toString(c);
			pw += (pw.equals("")) ? ""+c+"" : ""+c+"";
		}
		
		try {
			fw=new FileWriter(file,true);
			bw=new BufferedWriter(fw);
			bw.write(id+"\t"+pw+"\n");
			bw.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}

		this.dispose();
		
	}
	 public void actionPerformed(ActionEvent ae) {
		 try {
			 if(ae.getSource()==signUp_btn) {
				 String id=tf_id.getText();
				 char[] pw=tf_pw.getPassword();
				 char[] pw2=tf_pw_again.getPassword();
				 int len=pw.length;
				 if(len<pw2.length)
					 len=pw2.length;
				 
				 boolean same=true;
				 for(int i=0;i<len;i++) {
					 if(!(pw[i]==pw2[i])) {
						 System.out.println("비밀번호 불일치");
						 JOptionPane.showMessageDialog(null,"비밀번호가 일치하지 않습니다.");
						 same=false;
						 break;
					 }
				 }
				 if(same==false) {
					 tf_id.setText("");
					 tf_pw.setText("");
					 tf_pw_again.setText("");
				 }
				 else {
					 addAccount(id,pw);
					 JOptionPane.showMessageDialog(null,"계정 생성이 완료되었습니다.");
				 }
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
	
}