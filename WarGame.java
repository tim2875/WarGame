import java.rmi.Remote;
import java.rmi.RemoteException;
 public interface WarGame extends Remote{
	 //public void func() throws RemoteException;
	 boolean checkClientCredentials(WarGame wg,String id,String password) throws RemoteException;
	 void broadcastMsg(String name, String message) throws RemoteException;//useless
	 void sendMsgToClient(String message) throws RemoteException;//useless
	 void doDrop() throws RemoteException;
	 void doHit() throws RemoteException;
	 
	 boolean checkAllReady() throws RemoteException;//player 2명 전부 레디 했는지 확인 
	 void setReadyStatus(String id,boolean status) throws RemoteException;//플레이어 각각이 레디했는지 확인. 아이디로 key, status value 임.
	 boolean checkStatus() throws RemoteException;//게임 종료시점인지 확인..p1나 p2의 카드가 제로인 경우 게임 끝..
}
