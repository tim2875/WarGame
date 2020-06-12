import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
public class WarGameClient implements Runnable {//handle connection and use rmi function here from client side
//	private static final long serialVersionUID = 1L;
	private String id;
	boolean chkLog=false;
	private WarGame server;
	boolean boolean_ReadyBtnStatus;
	boolean boolean_HitBtnStatus;
	boolean boolean_DropBtnStatus;
	
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

	public void run() {
		if(chkLog) {
			System.out.println(id);
			WarGameGUI gui=new WarGameGUI(id);
			try {
				while(server.checkAllReady()==false) {
					boolean_ReadyBtnStatus=gui.returnReadyBtnStatus();
					server.setReadyStatus(id, boolean_ReadyBtnStatus);	
				}
				
			}catch(Exception e) {//디테일하게 예외 잡아 줘야 할 듯.. 예외처리 사이즈가 
				e.printStackTrace();
			}
			System.out.println("game start");//this will be deleted in the end
			gui.setGameInfo("game start");
			gui.deactivateReadyBtn();
			
//			try {
//				do{//게임중... do while인 이유는 마지막 카드가 공격이어서 기회가 남아 있을수도 있다..
//					////////////////////
//				}while(server.checkStatus());
//			}catch(Exception e) {//디테일하게 예외 잡아 줘야 할 듯..예외처리 사이즈가 커 
//				e.printStackTrace();
//			}
		}
	}
	public static void main(String[] args) {
		String mServer="127.0.0.1";
//		String mServer=args[0];
		String mServName="WarGame";
		
		System.out.println("Remote Method Invocate to "+mServer+", service name: "+mServName);
		
		try {
			
			String id;
			String pw;
			Scanner sc=new Scanner(System.in);//this is for temporary
			System.out.println("enter id: ");
			id=sc.nextLine();
			System.out.println("enter pw: ");
			pw=sc.nextLine();
			
			WarGame wg=(WarGame)Naming.lookup("rmi://"+mServer+":1099/"+mServName);
			new Thread(new WarGameClient(wg,id,pw)).start();
			
		}catch(MalformedURLException mue) {
			System.out.println("MalformedURLException: "+mue);
		}catch(RemoteException re) {
			System.out.println("RemoteException: "+re);
		}catch(NotBoundException nbe) {
			System.out.println("NotBoundException: "+nbe);
		}catch(java.lang.ArithmeticException ae) {
			System.out.println("java.lang.ArithmeticException: "+ae);
		}
	}
	
}
