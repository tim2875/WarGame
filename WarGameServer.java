import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.io.*;

import java.rmi.Naming;

public class WarGameServer {
	public int clientCnt=0;
	private WarGameServerRunnable client[]=new WarGameServerRunnable[2];
	public WarGameServer(String server,String servName) {
		try {
			WarGame wg=new WarGameImpl();
			Naming.rebind("rmi://"+server+":1099/"+servName, wg);
		}catch(Exception e) {
			System.out.println("Trouble: "+e);
		}
	}
	public static void main(String[] args) throws UnknownHostException {
		String mServer=InetAddress.getLocalHost().getHostName();
		String mServName="WarGame";
		System.out.println("started at"+mServer+"and use default port(1099). service name: "+mServName);
		new WarGameServer(mServer,mServName);
		////////////////
		//기존의 6장 서버코드랑 rmi코드랑 합친건데.. 이거 같은 경우엔 포트가 1099로 정해져 있어서..
		//rmi랑 같이 못쓰는거 같은데.... 그냥 끔찍한 혼종이야.. rmi base로 개발할 생각해!!!
		
	}
	public int whoClient(int clientID) {
		for (int i = 0; i < clientCnt; i++)
			if (client[i].getClientID() == clientID)
				return i;
		return -1;
	}
//	public void putClient(int clientID, String inputLine) {
//		for (int i = 0; i < clientCnt; i++)
//			if (client[i].getClientID() == clientID) {
//				System.out.println("writer: "+clientID);
//			} else {
//				System.out.println("write: "+client[i].getClientID());
//				client[i].out.println(inputLine);
//			}
//	}
	//this is about chatting. so..
	public void addClient(ServerSocket serverSocket) {
		Socket clientSocket = null;
		
		if (clientCnt < client.length) { 
			try {
				clientSocket = serverSocket.accept();
				clientSocket.setSoTimeout(40000); // 1000/sec
			} catch (IOException i) {
				System.out.println ("Accept() fail: "+i);
			}
			client[clientCnt] = new WarGameServerRunnable(this, clientSocket);
			new Thread(client[clientCnt]).start();
			clientCnt++;
			System.out.println ("Client connected: " + clientSocket.getPort()
					+", CurrentClient: " + clientCnt);
		} else {
			try {
				Socket dummySocket = serverSocket.accept();
				WarGameServerRunnable dummyRunnable = new WarGameServerRunnable(this, dummySocket);
				new Thread(dummyRunnable);
				dummyRunnable.out.println(dummySocket.getPort()
						+ " < Sorry maximum user connected now");
				System.out.println("Client refused: maximum connection "
						+ client.length + " reached.");
				dummyRunnable.close();
			} catch (IOException i) {
				System.out.println(i);
			}	
		}
	}
	public synchronized void delClient(int clientID) {
		int pos = whoClient(clientID);
		WarGameServerRunnable endClient = null;
	      if (pos >= 0) {
	    	   endClient = client[pos];
	    	  if (pos < clientCnt-1)
	    		  for (int i = pos+1; i < clientCnt; i++)
	    			  client[i-1] = client[i];
	    	  clientCnt--;
	    	  System.out.println("Client removed: " + clientID
	    			  + " at client[" + pos +"], CurrentClient: " + clientCnt);
	    	  endClient.close();
	      }
	}
}
class WarGameServerRunnable implements Runnable{
	protected WarGameServer warGameServer=null;
	protected Socket clientSocket=null;
	protected PrintWriter out=null;
	protected BufferedReader in=null;
	public int clientID=-1;
	
	Game game=new Game();
	ArrayList<Card> deck0=null;//내려놓는 곳 
	ArrayList<Card> deck1;//p1 deck
	ArrayList<Card> deck2;//p2 deck
	int playerCardCnt[]=new int[2];//player의 카드 갯
	int deck0Cnt;//내려놓는 곳 카드 수 
	int turn=0;
	public WarGameServerRunnable(WarGameServer server,Socket socket){
		this.warGameServer=server;
		this.clientSocket=socket;
		clientID=clientSocket.getPort();
		try{
			out=new PrintWriter(clientSocket.getOutputStream(),true);
//			out=new BufferedWriter(clientSocket.getOutputStream());
			in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}catch(IOException ioe){}
	}
	public void run() {
		gameInit();
	}
	public void close() {
		try {
			if(in !=null) in.close();
			if(out !=null) out.close();
			if(clientSocket!=null)clientSocket.close();
			
		}catch(IOException i) {
			i.printStackTrace();
		}
	}
	public int getClientID() {
		return clientID;
	}
	public void gameInit() {

		deck1=game.returnDeck1();//이미 셔플된 상태로 나온거라 따로 안해줘도 
		deck2=game.returnDeck2();
		playerCardCnt[0]=deck1.size();
		playerCardCnt[1]=deck2.size();
		deck0Cnt=0;
		
	}
	public void updateCardNum() {
		
	}
	public int nextTurn() {
		if(turn==0)
			turn=1;
		else
			turn=0;
		return turn;
			
	}
	public void dropCard(int turn) {
		Card c=null;
		if(turn==0) {
			c=deck1.get(0);
			deck1.remove(0);
			deck0.add(c);
		}
		else {
			c=deck2.get(0);
			deck2.remove(0);
			deck0.add(c);
		}
			
	}
	public boolean checkEndGame() {
		if(playerCardCnt[0]==0 || playerCardCnt[1]==0)
			return true;//if gameover return true
		else
			return false;
	}
	public void hitSuccess(int turn) {
		if(turn==0) {
			deck1.addAll(deck0);
		}
		else
			deck2.addAll(deck0);
		if(turn==0)
			System.out.println("hit success.player1 gets all");
		else
			System.out.println("hit success. player2 gets all");
		//this has to posted in client's info panel
			
	}
	public void hitFail(int turn) {
		if(turn==0)
			deck2.addAll(deck0);
		else
			deck1.addAll(deck0);
		if(turn==0)
			System.out.println("hit fail.player2 gets all");
		else
			System.out.println("hit fail.player1 gets all");
	}
}
