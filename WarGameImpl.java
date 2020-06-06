import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class WarGameImpl extends UnicastRemoteObject implements WarGame {
	public WarGameImpl() throws RemoteException{
		super();
	}
	public void func() throws RemoteException{
		System.out.println("rmi function");
	}
}
