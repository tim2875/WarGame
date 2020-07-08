import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import java.util.Set;

public class WarGameImpl extends UnicastRemoteObject implements WarGame {
   private static final int PORT = 2020;
   private static final long serializedversion=1L;
   private ArrayList<String> clientID;
   private ArrayList<String> clientPassword;
   private ArrayList<Boolean> clientLogin;
//   private ArrayList<WarGame> clientList;
   private Game game;
   private boolean turn=true;
   private String msg;
   private ArrayList<Card> deck0,deck1,deck2;
   private int clientCnt=0;
   //private HashMap<String,Integer> clientStatus;//어떤 클라이언트의 순서인지 확인할 수 있다(이름,0)/(이름,1) -> 전자는 비활성화, 후자는 활성화
   private ArrayList<String> client;
   private HashMap<String,Integer> readyStatus;
   private boolean boolean_readyStatus[];
   private int attackCount=-1;
   
   private boolean boolean_dropStatus[];
   private boolean boolean_hitStatus[];
   
   private boolean isAttack = false;
   private int dropCount = 0;
   private int whoIsWin=0;
   
   protected WarGameImpl() throws RemoteException{
      super();
      //clientList=new ArrayList<WarGame>();
      msg="";
      game=new Game();
      deck1=game.returnDeck1();
      deck2=game.returnDeck2();
      deck0=game.returnDeck0();
      clientID = new ArrayList<String>();
      clientPassword = new ArrayList<String>();
      clientLogin = new ArrayList<Boolean>();
      readyStatus=new HashMap<String,Integer>();
      boolean_dropStatus = new boolean[2];
      boolean_hitStatus = new boolean[2];
      boolean_dropStatus[0] = false;
      boolean_dropStatus[1] = false;
      boolean_hitStatus[0] = false;
      boolean_hitStatus[1] = false;
      
      client=new ArrayList<String>();

   }

   public String findAnotherId(String id) throws RemoteException{
	   int num = -1;
	   String temp;
	   for(int i = 0; i < client.size(); i++) {
		   if(id.equals(client.get(i))) {
			   num = i;
			   break;
		   }
	   }
	   if(num == 0) {
		   temp = client.get(1);
	   }
	   else {
		   temp = client.get(0);
	   }
	   return temp;
   }
   
   public boolean checkBtnStatus(int whichBtn, String id) throws RemoteException{
	   int num = -1;
	   for(int i = 0; i < client.size(); i++) {
		   if(id.equals(client.get(i))) {
			   num = i;
			   break;
		   }
	   }
	   if(num == 0) {
		   num = 1;
	   }
	   else 
		   num = 0;
	   
	   
	   if(whichBtn == 0) {			//drop
		   return boolean_dropStatus[num];
	   }
	   else {						//hit
		   return boolean_hitStatus[num];
	   }
   }
   
   public void modifyBtnStatus(int whichBtn, String id) throws RemoteException{
	   int num = -1;
	   for(int i = 0; i < client.size(); i++) {
		   if(id.equals(client.get(i))) {
			   num = i;
			   break;
		   }
	   }
	   if(whichBtn == 0) {			//drop
		   boolean_dropStatus[num] = true;
	   }
	   else {		//hit
		   boolean_hitStatus[num] = true;
	   }
   }
   
   public void modifyAnotherBtnStatus(int whichBtn, String id) throws RemoteException{
	   int num = -1;
	   for(int i = 0; i < client.size(); i++) {
		   if(id.equals(client.get(i))) {
			   num = i;
			   break;
		   }
	   }
	   if(num == 0) 
		   num = 1;
	   else 
		   num = 0;
	   
	   if(whichBtn == 0) 		//drop
		   boolean_dropStatus[num] = false;
	   else 		//hit
		   boolean_hitStatus[num] = false;
	   
   }
   
   
   public boolean getDeckZeroStatus() throws RemoteException{
	   if(deck0.isEmpty())
		   return false;
	   else 
		   return true;
	   
   }
   public void refreshDeck(int who) throws RemoteException{
       if(who == 2) {
          for(int i = 0; i < deck0.size(); i++) {
             deck2.add(deck0.get(i));
          }
          deck0 = new ArrayList<Card>();
       }
       else if(who == 1) {
           for(int i = 0; i < deck0.size(); i++) {
              deck1.add(deck0.get(i));
           }
           deck0 = new ArrayList<Card>();
       }
       
    }
   public String getTopType() throws RemoteException{
	   try {
		   if(deck0.size()>0)
			   return deck0.get(deck0.size()-1).returnType();
		   else
			   return "";
	   }
	   catch(NullPointerException e) {
		   System.out.println(e);
		   return "";
	   }
   }
   public String getTopValue() throws RemoteException{
	   try {
		   if(deck0.size()>0)
			   return deck0.get(deck0.size()-1).returnValue();
		   else
			   return "";
	   }
	   catch(NullPointerException e) {
		   System.out.println(e);
		   return "";
	   }
   }
   public synchronized boolean checkClientCredentials(WarGame wg,String id,String password) throws RemoteException{   //id, pw 확인
      boolean checkLog=false;
      try {
    	  BufferedReader br=new BufferedReader(new FileReader("account.txt"));
    	  String s;
    	  while ((s = br.readLine()) != null) {
    		  String[] split = s.split("\t");
    		  clientID.add(split[0]);
    		  clientPassword.add(split[1]);
    		  clientLogin.add(false);
    	  }
      }catch(IOException e) {
    	  System.out.println(e);
      }
      
  
      for(int i=0;i<clientID.size();i++) {
    	  if(clientID.get(i).equals(id) && clientPassword.get(i).equals(password)) {
    		  if(clientLogin.get(i) == false) {
    			  clientLogin.set(i, true);
    			  checkLog=true;
    		  }
    		  else if(clientLogin.get(i) == true) {
    			  checkLog = false;
    			  break;
    		  }
    	  }
      }
      if(checkLog==false)
         System.out.println("no id or wrong password");
      return checkLog;
      
   }

   public int getDeckLength(int player) throws RemoteException{
	   if(player==0)
		   return deck1.size();
	   else
		   return deck2.size();
   }
   @Override
   public ArrayList<String> getClientList() throws RemoteException{   
      return client;
   }
   @Override
   public synchronized void sendMsg(String msg) throws RemoteException{
      this.msg=msg;
   }
   @Override
   public synchronized String receiveMsg() throws RemoteException{
      return msg;
   }
   @Override
    public int checkGameStatus() throws RemoteException {//drop을 해야할지 hit를 해야할지, 공격중인 상황인지 평시 상황인지 판단한담에 거기에 맞춰 int리턴
      // TODO Auto-generated method st
      int loc = deck0.size();
      String value=deck0.get(loc-1).returnValue();
      if(value=="K")//공격카드의 값만큼 리턴 
         return 3;
      else if(value.equals("Q"))
         return 2;
      else if(value.equals("J"))
         return 1;
      else if(value.equals("A"))
         return 3;
      else if(value.equals("2")) {
         return 2;
      }
      else {
         return -1;//평시 상태
      }
   }

   public boolean checkEndingStatus() throws RemoteException{   //deck이 비면 끝남
       if(deck1.isEmpty()==true || deck2.isEmpty()==true) {
          if(deck1.isEmpty()) {
             whoIsWin = 2;
          }
          else if(deck2.isEmpty()) {
             whoIsWin = 1;
          }
          return false;//game over
       }
       else 
          return true;
    }
   
    public String whosTurn() throws RemoteException{       //누구의 턴인지 반환
      if(turn==true)
         return client.get(0);
      else
         return client.get(1);
    }
    
    public void changeTurn() throws RemoteException{
       turn=!turn;
    }
    
    public synchronized void setReadyStatus(String id,boolean status) throws RemoteException{//플레이어 각각이 레디했는지 확인
       int tmp=0;
       if(status==true)
          tmp=1;
       else
          tmp=0;
       if(!readyStatus.containsKey(id))//결국엔 같은건데 구분할까 말까? 
          readyStatus.put(id, tmp);
       else {
          readyStatus.put(id,tmp);
         
       }
       
    }
    public synchronized boolean checkAllReady() throws RemoteException{//player 2명 전부 레디 했는지 확인 
       int cnt=0;
       Iterator<Entry<String, Integer>> itr=readyStatus.entrySet().iterator();
       Set<Entry<String,Integer>>set=readyStatus.entrySet();
       while(itr.hasNext()) {
          Map.Entry<String,Integer>e=(Map.Entry<String,Integer>)itr.next();
          if(e.getValue()==1) {
             cnt++;
          }
          
       }
       if(cnt==2) {
          client.addAll(readyStatus.keySet());
          return true;
       }
         
       else
          return false;
    }
    public int whosWin() throws RemoteException {
        return whoIsWin;
     }

    public synchronized void doDrop(String id) throws RemoteException{
        
        Card c=null;
        if(turn==true) {//player 1's drop
           if(isAttack == false) {
              System.out.println("player 1 drop!");
              if(deck1.isEmpty()) {
                 System.out.println("Player2 win!!");
                 return;
              }
              c=deck1.get(0);
              pop();   //가져온 카드 1개 제거

              deck0.add(c);
              
              attackCount = checkGameStatus();
              if(attackCount == -1) {
                 turn = !turn;
              }
              else {
                 isAttack = true;
                 turn = !turn;
              }
           }
           else if(isAttack == true) {      //공격 상태일 때
              System.out.println("player 1 drop! : Attack");
              if(deck1.isEmpty()) {
                 System.out.println("Player2 win!!");
                 return;
              }
              c=deck1.get(0);
              pop();   //가져온 카드 1개 제거

              deck0.add(c);
              if(deck1.isEmpty()) {
                 return;
              }
              if(checkGameStatus() == -1) {   //공격카드가 아닐 경우
                 dropCount++;   
                 
                 if(dropCount == attackCount) {   //낸 카드의 수가 조건을 넘어갔을 떄
                    dropCount = 0;
                    isAttack = false;
                    for(int i = 0; i < deck0.size(); i++) {
                       deck2.add(deck0.get(i));
                    }
                    deck0 = new ArrayList<Card>();
                    turn = !turn;
                 }
              }
              else {
                 isAttack = true;
                 dropCount = 0;
                 if(deck1.isEmpty()) {
                    return;
                 }
                 attackCount = checkGameStatus();
                 turn = !turn;
              }
           }
          
        }else {//player 2's drop
           if(isAttack == false) {
              System.out.println("player 2 drop!");
              if(deck2.isEmpty()) {
                 System.out.println("Player1 win!!");
                 return;
              }
              c=deck2.get(0);
              pop();   //가져온 카드 1개 제거

              deck0.add(c);
              attackCount = checkGameStatus();
              if(attackCount == -1) {
                 turn = !turn;
              }
              else {
                 isAttack = true;
                 turn = !turn;
              }
              
           }
           else if(isAttack == true) {      //공격 상태일 때
              System.out.println("player 2 drop! : Attack");
              if(deck2.isEmpty()) {
                 System.out.println("Player1 win!!");
                 return;
              }
              c=deck2.get(0);
              pop();
              
              deck0.add(c);
              
              if(checkGameStatus() == -1) {   //공격카드가 아닐 경우
                 dropCount++;      

                 if(dropCount == attackCount) {   //낸 카드의 수가 조건을 넘어갔을 떄
                    dropCount = 0;
                    isAttack = false;
                    for(int i = 0; i < deck0.size(); i++) {
                       deck1.add(deck0.get(i));
                    }
                    deck0 = new ArrayList<Card>();
                    turn = !turn;
                 }
              }
              else {
                 isAttack = true;
                 dropCount = 0;
                 if(deck2.isEmpty()) {
                    return;
                 }
                 attackCount = checkGameStatus();
                 turn = !turn;
              }
           }
        }
     }
     public synchronized void doHit(String id) throws RemoteException{
        int who = -1;
        for(int i = 0; i < client.size(); i++) {
           if(id.equals(client.get(i))) {
              who = i+1;
              break;
           }
        }
        Card []c=new Card[2];
        if(deck0.size() < 2) {            
           System.out.println("Hit Fail");
           if(who == 1){
              for(int j = 0; j < deck0.size();j++) {
                 deck2.add(deck0.get(j));
              }
              deck0 = new ArrayList<Card>();
              turn = false;
           }
           else if(who == 2){
              for(int j = 0; j < deck0.size();j++) {
                 deck1.add(deck0.get(j));
              }
              deck0 = new ArrayList<Card>();
              turn = true;
           }

        }
        else {
           c[0]=deck0.get(deck0.size()-2);
           c[1]=deck0.get(deck0.size()-1);
           if(c[0].returnValue().equals(c[1].returnValue())) {
              System.out.println("Hit Success");
              if(who == 1) {
                 for(int j = 0; j < deck0.size();j++) {
                    deck1.add(deck0.get(j));
                 }
                 deck0 = new ArrayList<Card>();
                 turn = true;
              }
              else if(who == 2) {
                 for(int j = 0; j < deck0.size();j++) {
                    deck2.add(deck0.get(j));
                 }
                 deck0 = new ArrayList<Card>();
                 turn = false;
              }
           }
           else {
              System.out.println("Hit Fail");
              if(who == 1){
                 for(int j = 0; j < deck0.size();j++) {
                    deck2.add(deck0.get(j));
                 }
                 deck0 = new ArrayList<Card>();
                 turn = false;
              }
              else if(who == 2){
                 for(int j = 0; j < deck0.size();j++) {
                    deck1.add(deck0.get(j));
                 }
                 deck0 = new ArrayList<Card>(); 
                 turn = true;
              }
           }     
        }
        dropCount = 0;
        isAttack = false;
     }
     
    public void pop() {
       if(turn==true) {
          deck1.remove(0);
       }else {
          deck2.remove(0);
       }
    }
    public void push() {
       if(turn==true) {
          
       }else {
          
       }
    }
    
}