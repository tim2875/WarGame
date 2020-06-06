import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;

public class WarGameClient {//handle connection and use rmi function here from client side
	public WarGameClient() {
		new WarGameGUI();
	}
	public static void main(String[] args) {
		String mServer="localhost";
		String mServName="WarGame";
		
		System.out.println("Remote Method Invocate to "+mServer+", service name: "+mServName);
		new WarGameClient();
		try {
			WarGame c=(WarGame)Naming.lookup("rmi://"+mServer+"."+mServName);
			c.func();
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
