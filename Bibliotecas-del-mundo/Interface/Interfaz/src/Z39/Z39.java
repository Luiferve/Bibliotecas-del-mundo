package Z39;

import libro.Libro;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Z39 extends Remote {

    public Libro getAuthor(String nodeName, String nodeValue) throws RemoteException;
    public Libro getBook(String nodeName, String nodeValue) throws RemoteException;
}
