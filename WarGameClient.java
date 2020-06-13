import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.NotBoundException;

public class WarGameClient  {//handle connection and use rmi function here from client side
	private static final int PORT = 2019;
	
	
	public WarGameClient() {
		new WarGameGUI();
	}
	public static void main(String[] args) {
		String mServer="localhost";
		String mServName="WarGame";
		
		System.out.println("Remote Method Invocate to "+mServer+", service name: "+mServName);
		
		try { 	
			System.setProperty("javax.net.ssl.trustStore", "trustedcerts");
			System.setProperty("javax.net.ssl.trustStorePasssword", "123456");
			
			
			Registry registry = LocateRegistry.getRegistry(
	                InetAddress.getLocalHost().getHostName(), PORT,
	                new RMISSLClientSocketFactory());	
			
			WarGame c=(WarGame)registry.lookup("WarGame"/*"rmi://"+mServer+":1099/"+mServName*/);
			
			c.func();
			new WarGameClient();
			System.out.print("실행함 : : "); c.func();
			
			
			System.out.println("????");
		}catch(IOException e) {
			System.out.println("IOException :" +e);
		}catch(NotBoundException nbe) {
			System.out.println("NotBoundException: "+nbe);
		}catch(java.lang.ArithmeticException ae) {
			System.out.println("java.lang.ArithmeticException: "+ae);
		}
	
	}
}
