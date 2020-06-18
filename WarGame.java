import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
 public interface WarGame extends Remote{
	 //public void func() throws RemoteException;
	 boolean checkClientCredentials(WarGame wg,String id,String password) throws RemoteException;
//	 void broadcastMsg(String name, String message) throws RemoteException;//useless
//	 void sendMsgToClient(String message) throws RemoteException;//useless
	 void doDrop(String id) throws RemoteException;
	 void doHit(String id) throws RemoteException;
	 
	 boolean checkAllReady() throws RemoteException;//player 2명 전부 레디 했는지 확인 
	 void setReadyStatus(String id,boolean status) throws RemoteException;//플레이어 각각이 레디했는지 확인. 아이디로 key, status value 임.
	 boolean checkEndingStatus() throws RemoteException;//게임 종료시점인지 확인..p1나 p2의 카드가 제로인 경우 게임 끝..
	 int checkGameStatus() throws RemoteException;//drop을 해야할지 hit를 해야할지, 공격중인 상황인지 평시 상황인지 판단한담에 거기에 맞춰 int리턴 해주기..대신 #define방식으로 해서
	 //가독성을 높이
	 String whosTurn() throws RemoteException;//누구턴인지 알려줌 
	 void changeTurn() throws RemoteException;
	 void sendMsg(String msg) throws RemoteException;//client가 서버에게 메시지를 보내는 역할을 한다.
	 String receiveMsg() throws RemoteException;//client입장에서 서버로부터 메지시를 받는 역할을 한다.
	 ArrayList<String> getClientList() throws RemoteException;
	 int getDeckLength(int player) throws RemoteException;
	 int whosWin() throws RemoteException;
	 void refreshDeck(int who) throws RemoteException;
}