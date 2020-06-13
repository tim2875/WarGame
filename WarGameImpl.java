import java.io.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class WarGameImpl extends UnicastRemoteObject implements WarGame {
	
	 private static final int PORT = 2019;
	
	public WarGameImpl() throws Exception{
		super(/*PORT,
	          new RMISSLClientSocketFactory(),
	          new RMISSLServerSocketFactory()*/);
	}
	
	public void func() throws RemoteException{
		System.out.println("rmi function used");
	}
//	
//    public static void main(String args[]) {
//
//        try {
//    		System.setProperty("javax.net.ssl.trustStore", "trustedcerts");
//			System.setProperty("javax.net.ssl.trustStorePasssword", "123456");
//			
//        	
//            // Get SSL-based registry
//        	Registry registry = LocateRegistry.createRegistry(PORT, new RMISSLClientSocketFactory(), new RMISSLServerSocketFactory());
//
//            //Create new impl object
//        	WarGameImpl warGameImpl = new WarGameImpl();
//
//            registry.bind("WarGame", warGameImpl);
//
//            System.out.println("WarGame bound in registry");
//
//        } catch (Exception e) {
//            System.out.println("WarGame Error : " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
}
