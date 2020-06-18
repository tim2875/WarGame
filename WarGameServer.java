import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.io.*;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class WarGameServer extends UnicastRemoteObject {
	public int clientCnt=0;
	private static final int PORT = 2020;
	
	public WarGameServer(String server,String servName) throws Exception{
		super(PORT,
        new RMISSLClientSocketFactory(),
        new RMISSLServerSocketFactory());
		
	}
	public static void main(String[] args) throws UnknownHostException {
		String mServer="127.0.0.1";
		String mServName="WarGame";
		System.out.println("started at"+mServer+"and use default port(1099). service name: "+mServName);
		
      try {

          //SSL-based registry 생성
      	Registry registry = LocateRegistry.createRegistry(PORT, new RMISSLClientSocketFactory(), new RMISSLServerSocketFactory());

          //Create new impl object
      	WarGameImpl warGameImpl = new WarGameImpl();

          registry.bind("WarGame", warGameImpl);

          System.out.println("WarGame bound in registry");

      } catch (Exception e) {
          System.out.println("WarGame Error : " + e.getMessage());
          e.printStackTrace();
      }
		
		
	}
}
//public int whoClient(int clientID) {
//	for (int i = 0; i < clientCnt; i++)
//		if (client[i].getClientID() == clientID)
//			return i;
//	return -1;
//}
//public void putClient(int clientID, String inputLine) {
//	for (int i = 0; i < clientCnt; i++)
//		if (client[i].getClientID() == clientID) {
//			System.out.println("writer: "+clientID);
//		} else {
//			System.out.println("write: "+client[i].getClientID());
//			client[i].out.println(inputLine);
//		}
//}
//this is about chatting. so..
//public void addClient(ServerSocket serverSocket) {
//	Socket clientSocket = null;
//
//	if (clientCnt < client.length) { 
//		try {
//			clientSocket = serverSocket.accept();
//			clientSocket.setSoTimeout(40000); // 1000/sec
//		} catch (IOException i) {
//			System.out.println ("Accept() fail: "+i);
//		}
//		client[clientCnt] = new WarGameServerRunnable(this, clientSocket);
//		new Thread(client[clientCnt]).start();
//		clientCnt++;
//		System.out.println ("Client connected: " + clientSocket.getPort()
//		+", CurrentClient: " + clientCnt);
//	} else {
//		try {
//			Socket dummySocket = serverSocket.accept();
//			WarGameServerRunnable dummyRunnable = new WarGameServerRunnable(this, dummySocket);
//			new Thread(dummyRunnable);
//			dummyRunnable.out.println(dummySocket.getPort()
//					+ " < Sorry maximum user connected now");
//			System.out.println("Client refused: maximum connection "
//					+ client.length + " reached.");
//			dummyRunnable.close();
//		} catch (IOException i) {
//			System.out.println(i);
//		}	
//	}
//}
//public synchronized void delClient(int clientID) {
//	int pos = whoClient(clientID);
//	WarGameServerRunnable endClient = null;
//	if (pos >= 0) {
//		endClient = client[pos];
//		if (pos < clientCnt-1)
//			for (int i = pos+1; i < clientCnt; i++)
//				client[i-1] = client[i];
//		clientCnt--;
//		System.out.println("Client removed: " + clientID
//				+ " at client[" + pos +"], CurrentClient: " + clientCnt);
//		endClient.close();
//	}
//}
//}
//class WarGameServerRunnable implements Runnable{
//	protected WarGameServer warGameServer=null;
//	protected Socket clientSocket=null;
//	protected PrintWriter out=null;
//	protected BufferedReader in=null;
//	public int clientID=-1;
//
//	Game game=new Game();
//	ArrayList<Card> deck0=null;//내려놓는 곳 
//	ArrayList<Card> deck1;//p1 deck
//	ArrayList<Card> deck2;//p2 deck
//	int playerCardCnt[]=new int[2];//player의 카드 갯
//	int deck0Cnt;//내려놓는 곳 카드 수 
//	int turn=0;
//	public WarGameServerRunnable(WarGameServer server,Socket socket){
//		this.warGameServer=server;
//		this.clientSocket=socket;
//		clientID=clientSocket.getPort();
//		try{
//			out=new PrintWriter(clientSocket.getOutputStream(),true);
//			//	out=new BufferedWriter(clientSocket.getOutputStream());
//			in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//		}catch(IOException ioe){}
//	}
//	public void run() {
//		gameInit();
//	}
//	public void close() {
//		try {
//			if(in !=null) in.close();
//			if(out !=null) out.close();
//			if(clientSocket!=null)clientSocket.close();
//
//		}catch(IOException i) {
//			i.printStackTrace();
//		}
//	}
//	public int getClientID() {
//		return clientID;
//	}
//	public void gameInit() {
//
//		deck1=game.returnDeck1();//이미 셔플된 상태로 나온거라 따로 안해줘도 
//		deck2=game.returnDeck2();
//		playerCardCnt[0]=deck1.size();
//		playerCardCnt[1]=deck2.size();
//		deck0Cnt=0;
//
//	}
