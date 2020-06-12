import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

class WarGameBoard extends JPanel{
	ImageIcon postedCard;
	ImageIcon backSideCard;
	ImageIcon frontSideCard[];
	JLabel playerLabel[];
	JLabel cardNumLabel[];
	String userName[];
	ImageIcon playerCard;
	BufferedImage img;
	JLabel cardLabel;
	ImageIcon icon;
	JButton dropBtn;
	JButton hitBtn;
 
	public WarGameBoard() {
		setLayout(null);

		postedCard=new ImageIcon();
		playerLabel=new JLabel[2];
		cardNumLabel=new JLabel[2];
		userName=new String[2];
		frontSideCard=new ImageIcon[52];
		String address="img/trumpCard/";
		String tmp=null;
		for(int i=1;i<10;i++) {
			tmp=address+(i+1)+"S"+".png";
			frontSideCard[13*0+i]=new ImageIcon(tmp);
			tmp=address+(i+1)+"D"+".png";
			frontSideCard[13*1+i]=new ImageIcon(tmp);
			tmp=address+(i+1)+"H"+".png";
			frontSideCard[13*2+i]=new ImageIcon(tmp);
			tmp=address+(i+1)+"C"+".png";
			frontSideCard[13*3+i]=new ImageIcon(tmp);
		}
		frontSideCard[13*0+0]=new ImageIcon("img/trumpCard/AS.png");
		frontSideCard[13*1+0]=new ImageIcon("img/trumpCard/AD.png");
		frontSideCard[13*2+0]=new ImageIcon("img/trumpCard/AH.png");
		frontSideCard[13*3+0]=new ImageIcon("img/trumpCard/AC.png");

		frontSideCard[13*0+10]=new ImageIcon("img/trumpCard/JS.png");
		frontSideCard[13*1+10]=new ImageIcon("img/trumpCard/JD.png");
		frontSideCard[13*2+10]=new ImageIcon("img/trumpCard/JH.png");
		frontSideCard[13*3+10]=new ImageIcon("img/trumpCard/JC.png");

		frontSideCard[13*0+11]=new ImageIcon("img/trumpCard/QS.png");
		frontSideCard[13*1+11]=new ImageIcon("img/trumpCard/QD.png");
		frontSideCard[13*2+11]=new ImageIcon("img/trumpCard/QH.png");
		frontSideCard[13*3+11]=new ImageIcon("img/trumpCard/QC.png");

		frontSideCard[13*0+12]=new ImageIcon("img/trumpCard/KS.png");
		frontSideCard[13*1+12]=new ImageIcon("img/trumpCard/KD.png");
		frontSideCard[13*2+12]=new ImageIcon("img/trumpCard/KH.png");
		frontSideCard[13*3+12]=new ImageIcon("img/trumpCard/KC.png");



		backSideCard=new ImageIcon("img/trumpCard/red_back.png");

		playerLabel[0]=new JLabel("player 1:");
		playerLabel[0].setFont(new Font("Lucida Grande", 0, 24));
		playerLabel[0].setBounds(430,460,110,30);
		cardNumLabel[0]=new JLabel("00");
		cardNumLabel[0].setFont(new Font("Lucida Grande", 0, 24));
		cardNumLabel[0].setBounds(540,460,100,30);

		playerLabel[1]=new JLabel("player 2:");
		playerLabel[1].setFont(new Font("Lucida Grande", 0, 24));
		playerLabel[1].setBounds(10,5,110,30);//x,y,width,height
		cardNumLabel[1]=new JLabel("00");
		cardNumLabel[1].setFont(new Font("Lucida Grande", 0, 24));
		cardNumLabel[1].setBounds(120,5,100,30);



		add(playerLabel[0]);
		add(cardNumLabel[0]);
		add(playerLabel[1]);
		add(cardNumLabel[1]);

		playerCard=backSideCard;


	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		playerCard.paintIcon(this,g,200,50);
	}
}
public class WarGameGUI extends JFrame implements ActionListener{
	JTextArea ta_msgView;
	JScrollPane scPane;
//	JTextField tf_Send=new JTextField("");
//	JTextField tf_Name=new JTextField();
//	JTextField tf_IPAddress=new JTextField();
	DefaultListModel model=new DefaultListModel();
	JList li_Player=new JList(model);
	
//	JButton connectBtn=new JButton("connect");
	JButton readyBtn=new JButton("ready");
	JLabel la_GameInfo=new JLabel("<information>");
	JLabel la_playerInfo=new JLabel("<participants>");
//	boolean ready=false;
	boolean boolean_ReadyBtn;
	boolean boolean_HitBtn;
	boolean boolean_DropBtn;
	private String id;
	WarGameBoard board=new WarGameBoard();
	BufferedReader reader;
	PrintWriter writer;

	String userName=null;

	JButton hitBtn=new JButton();
	JButton dropBtn=new JButton();


	public WarGameGUI(String id) {
		super("War Game");
//		WarGameClient client=new WarGameClient();
		this.id=id;
		boolean_ReadyBtn=false;
		boolean_HitBtn=false;
		boolean_DropBtn=false;
		//it was in main() at first.but need to change to use rmi...
		Container ct=getContentPane();
		ct.setLayout(null);

		model=(DefaultListModel)li_Player.getModel();
		hitBtn=new JButton();
		hitBtn.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        hitBtn.setText("Hit");
        hitBtn.setName("hitBtn");

        hitBtn.setBounds(120,420,157,82);
        ct.add(hitBtn);
        hitBtn.addActionListener(this);


        dropBtn=new JButton();
        dropBtn.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        dropBtn.setText("Drop");
        dropBtn.setName("dropBtn");

        dropBtn.setBounds(300,420,157,82);
        ct.add(dropBtn);
        dropBtn.addActionListener(this);

		ta_msgView=new JTextArea(1,1);
		scPane=new JScrollPane(ta_msgView);

//		EtchedBorder eborder = new EtchedBorder(EtchedBorder.RAISED);
		ta_msgView.setEditable(false);
		la_GameInfo.setBounds(10,10,480,30);
		ct.add(la_GameInfo);
		board.setBounds(5,50,600,500);
		board.setBorder(new LineBorder(Color.BLACK,1));//will be deleted soon
		ct.add(board);


		JPanel connectPanel=new JPanel();
		connectPanel.setLayout(new GridLayout(1,1));
//		connectPanel.add(new Label("IP Address: ",2));
//		connectPanel.add(tf_IPAddress);
//		connectPanel.add(new Label("name : ",2));
//		connectPanel.add(tf_Name);
//		connectPanel.add(connectBtn)
		readyBtn.setFont(new java.awt.Font("Lucida Grande", 0, 24));;
		connectPanel.add(readyBtn);
//		readyBtn.setEnabled(false);
		connectPanel.setBounds(620,50,250,70);
		ct.add(connectPanel);
//		connectBtn.addActionListener(this);
		readyBtn.addActionListener(this);

		JPanel participantsPanel=new JPanel();
		participantsPanel.setLayout(new BorderLayout());
		participantsPanel.add(la_playerInfo,"North");
		participantsPanel.add(li_Player,"Center");
		participantsPanel.setBounds(620,120,250,230);
		ct.add(participantsPanel);

		JPanel infoPanel=new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoPanel.add(scPane,"Center");
//		infoPanel.add(tf_Send,"South");
		infoPanel.setBounds(620,360,250,200);
		ct.add(infoPanel);
//		tf_Send.addActionListener(this);
		ta_msgView.append("press ready to play game\n");
		
		this.setSize(880,650);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public String returnID() {
		return id;
	}
	public boolean returnReadyBtnStatus() {
		return boolean_ReadyBtn;
	}
	public boolean returnHitBtnStatus() {
		return boolean_HitBtn;
	}
	public void setHitStatusFalse() {
		boolean_HitBtn=false;
	}
	public void setDropStatusFalse() {
		boolean_DropBtn=false;
	}
	public boolean returnDropBtnStatus() {
		return boolean_DropBtn;
	}
	public void setGameInfo(String str) {//상단에 <information> 부분으로 누구 차례인지, 뭘 해야 하는지 등을 알려줌 
		la_GameInfo.setText("<information> "+str);
	}
	public void setMsgView(String str) {//행동의 결과를 알려줌. hit를 했다, drop을 했다등 
		ta_msgView.append(str+"\n");
	}
	public void setPlayerList(String str) {//참가자 리스트를 보여줌
		model.addElement(str);
	}
	public void deactivateReadyBtn() {
		readyBtn.setEnabled(false);
	}
    public void actionPerformed(ActionEvent ae) {
    	try {
	    	if(ae.getSource()==dropBtn) {
//	    		System.out.println("drop");
	    		boolean_DropBtn=true;
	    		
	    		
	    	}
	    	else if(ae.getSource()==hitBtn) {
	    		System.out.println("hit");
	    	}
//	    	else if(ae.getSource()==tf_Send) {
//	    		String msg=tf_Send.getText();
//	    		if(msg.length()==0)
//	    			return ;
//	    		if(msg.length()>=30) {
//	    			msg=msg.substring(0,30);
//	    		}
////	    		writer.println("[MSG]: ",msg);
//	    		System.out.println("[MSG]:"+ msg);
//	    		tf_Send.setText("");
//	    	}
//	    	else if(ae.getSource()==connectBtn) {
//	    		System.out.println("[Connect]: button");
//	    	}
    		else if(ae.getSource()==readyBtn) {
//	    		System.out.println("[Ready]: button");
	    		if(boolean_ReadyBtn==false) {
	    			boolean_ReadyBtn=true;
	    			readyBtn.setText("not ready");//레디 박은 상
	    			System.out.println(boolean_ReadyBtn);
	    		}
	    		else {
	    			boolean_ReadyBtn=false;
	    			readyBtn.setText("ready");//레디 안했을 경우 
	    			System.out.println(boolean_ReadyBtn);
	    		}
	    	}

    	}catch(Exception e) {

    	}
    }
	
}
