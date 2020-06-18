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
	private static final int PORT = 2019;
	
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

