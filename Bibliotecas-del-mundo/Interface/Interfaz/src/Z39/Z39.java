package Z39;

import libro.Libro;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Z39 extends Remote {

    public Libro getAuthor(String nodeName, String nodeValue) throws RemoteException;
    public List<String> getBook(String nodeValue) throws RemoteException;
}
