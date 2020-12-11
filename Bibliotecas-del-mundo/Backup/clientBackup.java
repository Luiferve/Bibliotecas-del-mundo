package bibliotecaA;

import Z39.Z39;
import libro.Libro;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class BibliotecaA {

    private static Z39 look_up;

    private void connectServer() throws RemoteException{
        try{

            /*
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",55000); // Localhost: 127.0.0.1
            look_up = (Z39) registry.lookup("ServerA");
            List<String> response = look_up.getBook("J. K. Rowling");
            response.forEach((item) -> {
                System.out.println(item);
            });
            //Libro response = look_up.getAuthor("title","Don Quijote de la Mancha");
            //JOptionPane.showMessageDialog(null,response.getTitle() + " - " + response.getAuthor());

            registry = LocateRegistry.getRegistry("192.168.1.104",55500);
            look_up = (Z39) registry.lookup(("ServerB"));
            Libro response1 = look_up.getAuthor("title","El amor en los tiempos de colera");
            JOptionPane.showMessageDialog(null,response1.getTitle() + " - " + response1.getAuthor());

            registry = LocateRegistry.getRegistry("192.168.1.104",55555);
            look_up = (Z39) registry.lookup(("ServerC"));
            Libro response2 = look_up.getAuthor("title","Manifiesto del sere");
            JOptionPane.showMessageDialog(null,response2.getTitle() + " - " + response2.getAuthor());
            */
        }catch (Exception e){
            System.err.println("Server exception: "+ e.toString());
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
            throws MalformedURLException, RemoteException, NotBoundException {
        BibliotecaA biblioteca = new BibliotecaA();
        biblioteca.connectServer();
    }
}