import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class RMISSLServerSocketFactory implements RMIServerSocketFactory{
	private static SSLServerSocketFactory ssf = null;

	final String runRoot = "C:/eclipse/java/WarGame/bin/";
	String ksName = runRoot + ".keystore/SSLSocketServerKey";
	
	public RMISSLServerSocketFactory() throws Exception{
		try {
			KeyStore ks;
			KeyManagerFactory kmf;
			SSLContext sc ;
			
				
			char[] keyStorePass = "123456".toCharArray();
			char[] keyPass = "123456".toCharArray();
			
			ks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream(ksName), keyStorePass);
			kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, keyPass);
			
			sc = SSLContext.getInstance("TLS");
			sc.init(kmf.getKeyManagers(), null, null);
			ssf = sc.getServerSocketFactory();
		
		} catch(Exception e) {
			 e.printStackTrace();
			 throw e;
		}
	}
	
	
	public ServerSocket createServerSocket(int port) throws IOException {
		 return ssf.createServerSocket(port);
	}
	
	public int hashCode() {
		return getClass().hashCode();
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		return true;
	}

	
}
