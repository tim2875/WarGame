import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.rmi.NotBoundException;



public class WarGameClient implements Runnable {//handle connection and use rmi function here from client side
//	private static final long serialVersionUID = 1L;
	private static final int PORT = 2019;
	
	private String id;
	boolean chkLog=false;
	private WarGame server;
	boolean boolean_ReadyBtnStatus;
	boolean boolean_HitBtnStatus;
	boolean boolean_DropBtnStatus;
	ArrayList<String> client;
	WarGameGUI gui;
	protected WarGameClient(WarGame wg,String id,String pw){
		boolean_ReadyBtnStatus=false;
		boolean_HitBtnStatus=false;
		boolean_DropBtnStatus=false;
		this.server=wg;
		this.id=id;
		try {
			chkLog=server.checkClientCredentials(wg, id, pw);
			if(chkLog==false) {
				System.out.println("wrong id or wrong passworld");
				System.exit(0);
			}


		}catch(RemoteException re) {
			System.out.println("RemoteException: "+re);

		}catch(java.lang.ArithmeticException ae) {
			System.out.println("java.lang.ArithmeticException: "+ae);
		}

	}
	void drop() {
		boolean_DropBtnStatus=gui.returnDropBtnStatus();
		try {
			server.doDrop(id);//id가 필요한지 모르겠음...
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	void hit() {
		boolean_HitBtnStatus=gui.returnHitBtnStatus();
		try {
			server.doHit(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void run() {
		if(chkLog) {
			System.out.println(id);
			gui=new WarGameGUI(id);
			gui.deactivatedHitDropBtn();
			try {
				while(server.checkAllReady()==false) {
					boolean_ReadyBtnStatus=gui.returnReadyBtnStatus();
					server.setReadyStatus(id, boolean_ReadyBtnStatus);
				}

			}catch(Exception e) {//디테일하게 예외 잡아 줘야 할 듯.. 예외처리 사이즈가
				e.printStackTrace();
			}
			gui.activatedHitDropBtn();
			System.out.println("game start");//this will be deleted in the end
			gui.setGameInfo("game start");
			gui.deactivateReadyBtn();
			try {//최종 참가자 리스트 추가
				client=server.getClientList();
				String tmp=client.get(0);
				String tmp2=client.get(1);
				gui.setPlayerList("Player1: "+tmp);
				gui.setPlayerList("Player2: "+tmp2);
			}catch(Exception e) {
				e.printStackTrace();
			}
//			try {//턴 돌아갈때 마다 올라가는 것으로 바꿔야 할것 같다. 1번에 국한되지 말고...
//				if(id.contentEquals(server.whosTurn())) {
//					String tmp=server.whosTurn();
//					sendMsgToAll(tmp+"'s turn\n");
//					
//				}
//					
////					System.out.println(tmp+"'s turn. good luck~!");
////					gui.setGameInfo(tmp+"'s turn. good luck~!");
////					String msg=receiveMsg();
////					gui.setMsgView(msg);
//				
//				
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
			try {
				while(server.checkEndingStatus()){//게임중... 
					while(id.equals(server.whosTurn())){//자신의 턴 동안...
						boolean_DropBtnStatus=gui.returnDropBtnStatus();
						boolean_HitBtnStatus=gui.returnHitBtnStatus();
						if(boolean_DropBtnStatus) {
							drop();
							gui.setMsgView("플레이어 "+ id+" 가 drop을 했습니다.");
//							server.doDrop(id);//id는 안쓰고, serverImpl에서 turn보고 알아서 판단하고 있음. 필요 없는거 확실해지면 그때 지우기.
							gui.setDropStatusFalse();//버튼 눌리면 true로 변하기 때문에 변경 안하면 항상 true인 효과 한번 버튼 누르면 다시 false로 변환 
							//server.changeTurn();
							gui.repaint();
						}else if(boolean_HitBtnStatus) {
							hit();
//							server.doHit(id);
							gui.setHitStatusFalse();

						}else {

						}
					}
					if(!id.equals(server.whosTurn())){//player가 잘못누르는 경우...
						boolean_DropBtnStatus=gui.returnDropBtnStatus();
						boolean_HitBtnStatus=gui.returnHitBtnStatus();
						if(boolean_DropBtnStatus){
							System.out.println("it's not your turn..please wait");
							gui.setGameInfo("it's not your turn..please wait");
							gui.setDropStatusFalse();
						}
						if(boolean_HitBtnStatus){
							System.out.println("it's not your turn..please wait");
							gui.setGameInfo("it's not your turn..please wait");
							gui.setHitStatusFalse();
						}
					}
				}
				
//				gui.deactivatedHitDropBtn();
			
			}catch(Exception e) {//디테일하게 예외 잡아 줘야 할 듯..예외처리 사이즈가 커
				e.printStackTrace();
			}
		}
	}
	public void sendMsgToAll(String str) {//행동 취하고 나면 반드시 해당함수를 써서 메시지를 보내야한다. 그리고 바로 receiveMSG도 작동해야한다.
		try {
			server.sendMsg(str);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
//	public String receiveMsg() {
//		String msg="";
//		try {
//			msg=server.receiveMsg();
////			gui.setMsgView(msg+"\n");
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//			
//		}
//		return msg;
//	}
	public static void main(String[] args) {
		String mServer="127.0.0.1";
		String mServName="WarGame";

		System.out.println("Remote Method Invocate to "+mServer+", service name: "+mServName);

		try {
			System.setProperty("javax.net.ssl.trustStore", "trustedcerts");
			System.setProperty("javax.net.ssl.trustStorePasssword", "123456");
			
			String id;
			String pw;
			Scanner sc=new Scanner(System.in);//this is for temporary
			System.out.print("enter id: ");
			id=sc.nextLine();
			System.out.print("enter pw: ");
			pw=sc.nextLine();


			Registry registry = LocateRegistry.getRegistry(
	                InetAddress.getLocalHost().getHostName(), PORT,
	                new RMISSLClientSocketFactory());	
			
			WarGame c=(WarGame)registry.lookup("WarGame"/*"rmi://"+mServer+":1099/"+mServName*/);
			new Thread(new WarGameClient(c,id,pw)).start();
		
		}catch(RemoteException re) {
			System.out.println("RemoteException: "+re);
		}catch(NotBoundException nbe) {
			System.out.println("NotBoundException: "+nbe);
		}catch(java.lang.ArithmeticException ae) {
			System.out.println("java.lang.ArithmeticException: "+ae);
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException: "+e);
		}
	}

}

