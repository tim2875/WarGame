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
	CardGUI []cardGUI;
	public WarGameBoard() {
		setLayout(null);

		postedCard=new ImageIcon();
		playerLabel=new JLabel[2];
		cardNumLabel=new JLabel[2];
		userName=new String[2];
		frontSideCard=new ImageIcon[52];
		String address="img/trumpCard/";
		String tmp=null;

		cardGUI=new CardGUI[52];
		for(int i=1;i<10;i++) {
			cardGUI[13*0+i]=new CardGUI("S",i);
			cardGUI[13*1+i]=new CardGUI("D",i);
			cardGUI[13*2+i]=new CardGUI("H",i);
			cardGUI[13*3+i]=new CardGUI("C",i);
		}
		cardGUI[13*0+0]=new CardGUI("S","A");//type,value
		cardGUI[13*1+0]=new CardGUI("D","A");//type,value
		cardGUI[13*2+0]=new CardGUI("H","A");//type,value
		cardGUI[13*3+0]=new CardGUI("C","A");//type,value

		cardGUI[13*0+10]=new CardGUI("S","J");//type,value
		cardGUI[13*1+10]=new CardGUI("D","J");//type,value
		cardGUI[13*2+10]=new CardGUI("H","J");//type,value
		cardGUI[13*3+10]=new CardGUI("C","J");//type,value

		cardGUI[13*0+11]=new CardGUI("S","Q");
		cardGUI[13*1+11]=new CardGUI("D","Q");
		cardGUI[13*2+11]=new CardGUI("H","Q");
		cardGUI[13*3+11]=new CardGUI("C","Q");

		cardGUI[13*0+12]=new CardGUI("S","K");
		cardGUI[13*1+12]=new CardGUI("D","K");
		cardGUI[13*2+12]=new CardGUI("H","K");
		cardGUI[13*3+12]=new CardGUI("C","K");

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
	void updateCardImg(String type,String value) {
		playerCard=findImg(type,value);
		repaint();
		
	}
	void callRepaint() {
		repaint();
	}
	void updateCardImg() {
		playerCard=new ImageIcon("img/trumpCard/red_back.png");
		repaint();
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		playerCard.paintIcon(this,g,200,50);
	}
	void setCardNumLabel(int p,int num) {
		cardNumLabel[p].setText(""+num);
		
	}
	ImageIcon findImg(String type,String value) {
		for(int i=0;i<cardGUI.length;i++) {
			if(cardGUI[i].returnType().equals(type) && cardGUI[i].returnValue().equals(value))
				return cardGUI[i].returnImg();
		}
			return new ImageIcon("img/trumpCard/red_back.png");
	}
	
}
public class WarGameGUI extends JFrame implements ActionListener{
	JTextArea ta_msgView;
	JScrollPane scPane;

	DefaultListModel model=new DefaultListModel();
	JList li_Player=new JList(model);
	
	JButton readyBtn=new JButton("ready");
	JLabel la_GameInfo=new JLabel("<information>");
	JLabel la_playerInfo=new JLabel("<participants>");

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

		this.id=id;
		boolean_ReadyBtn=false;
		boolean_HitBtn=false;
		boolean_DropBtn=false;

		Container ct=getContentPane();
		ct.setLayout(null);

		model=(DefaultListModel)li_Player.getModel();
		hitBtn=new JButton();
		hitBtn.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        hitBtn.setText("Hit");
        hitBtn.setName("hitBtn");

        hitBtn.setBounds(120,560,157,82);
        ct.add(hitBtn);
        hitBtn.addActionListener(this);


        dropBtn=new JButton();
        dropBtn.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        dropBtn.setText("Drop");
        dropBtn.setName("dropBtn");

        dropBtn.setBounds(300,560,157,82);
        ct.add(dropBtn);
        dropBtn.addActionListener(this);

		ta_msgView=new JTextArea(1,1);
		scPane=new JScrollPane(ta_msgView);


		ta_msgView.setEditable(false);
		la_GameInfo.setBounds(10,10,480,30);
		ct.add(la_GameInfo);
		board.setBounds(5,50,600,500);
		board.setBorder(new LineBorder(Color.BLACK,1));//will be deleted soon
		ct.add(board);


		JPanel connectPanel=new JPanel();
		connectPanel.setLayout(new GridLayout(1,1));

		readyBtn.setFont(new java.awt.Font("Lucida Grande", 0, 24));;
		connectPanel.add(readyBtn);

		connectPanel.setBounds(620,50,250,70);
		ct.add(connectPanel);

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

		infoPanel.setBounds(620,360,250,200);
		ct.add(infoPanel);

		ta_msgView.append("press ready to play game\n");
		
		this.setSize(880,690);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void callRepaint() {
		board.callRepaint();
	}
		//playerLabel[0],[1]
	public void setCardNumLabel(int p,int num) {
		board.setCardNumLabel(p, num);
	}
	public void updateCardImg(String type,String value) {
		board.updateCardImg(type, value);
	}
	public void updateCardImg() {
		board.updateCardImg();
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
	public void deactivatedHitDropBtn() {
		hitBtn.setEnabled(false);
		dropBtn.setEnabled(false);
	}
	public void activatedHitDropBtn() {
		hitBtn.setEnabled(true);
		dropBtn.setEnabled(true);
	}
	public void getMessageBox(String Gid) {
        JOptionPane.showMessageDialog(null, Gid+"가 승리하였습니다.");
  }
    public void actionPerformed(ActionEvent ae) {
    	try {
	    	if(ae.getSource()==dropBtn) {
	    		boolean_DropBtn=true;
	    	}
	    	else if(ae.getSource()==hitBtn) {
	    		boolean_HitBtn=true;
	    	}

    		else if(ae.getSource()==readyBtn) {
	    		if(boolean_ReadyBtn==false) {
	    			boolean_ReadyBtn=true;
	    			readyBtn.setText("not ready");//레디 박은 상
	    		}
	    		else {
	    			boolean_ReadyBtn=false;
	    			readyBtn.setText("ready");//레디 안했을 경우 
	    		}
	    	}

    	}catch(Exception e) {

    	}
    }
	
}
class CardGUI{
	String type;
	String value;
	ImageIcon img;
	String address="img/trumpCard/";
	String tmp=null;
	CardGUI(String type,String value){
		this.type=type;
		this.value=value;
		tmp=address+value+type+".png";
		img=new ImageIcon(tmp);

	}
	CardGUI(String type,int value){
		this.type=type;
		value=value+1;
		this.value=Integer.toString(value);
		
		tmp=address+value+type+".png";
		img=new ImageIcon(tmp);
	}
	String returnType() {
		return type;
	}
	String returnValue() {
		return value;
	}
	ImageIcon returnImg() {
		return img;
	}
}