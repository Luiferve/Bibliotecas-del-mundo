package bibliotecaB;

import Z39.Z39;
import libro.Libro;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class BibliotecaB {

    private static Z39 look_up;

    private void ClientConnectServer() throws RemoteException{
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int cont = 1;
            String library;
            String title;
            String author;

            while (cont == 1) {
                System.out.println("Indique el comando a usar: (Ex1: Buscar Título; Ex2: Buscar Autor)");
                String comando = reader.readLine();
                Registry registry;

                switch (comando) {
                    case "Buscar Título":
                        System.out.println("Introduzca la biblioteca a solicitar: ");
                        library = reader.readLine();

                        switch (library) {
                            /*  PORTS
                                Biblioteca A = 55000
                                Biblioteca B = 55500
                                Biblioteca C = 55555
                             */
                            default:
                                // Conecta con la biblioteca local, que en este caso es la Biblioteca B.
                                System.out.println("Introduzca titulo del libro: ");
                                title = reader.readLine();
                                registry = LocateRegistry.getRegistry("127.0.0.1",55000); // Localhost: 127.0.0.1
                                look_up = (Z39) registry.lookup("ServerB");
                                Libro response = look_up.getAuthor("title", title);
                                if(response == null){  // Si no encuentra en la biblioteca B, intenta con biblioteca C.
                                    registry = LocateRegistry.getRegistry("127.0.0.1",55500); // Localhost: 127.0.0.1
                                    look_up = (Z39) registry.lookup("ServerC");
                                    Libro response1 = look_up.getAuthor("title", title);
                                    if(response1 == null) {  // Si no encuentra en la Biblioteca C, intenta con la biblioteca A.
                                        registry = LocateRegistry.getRegistry("127.0.0.1", 55555); // Localhost: 127.0.0.1
                                        look_up = (Z39) registry.lookup("ServerA");
                                        Libro response2 = look_up.getAuthor("title", title);
                                        if (response2 == null){
                                            System.out.println("getAuthor: Book title not found!");
                                        } else{
                                            System.out.println("Libro: " + response2.getTitle() + " - " + "Autor: " + response2.getAuthor());
                                        }
                                    } else{
                                        System.out.println("Libro: " + response1.getTitle() + " - " + "Autor: " + response1.getAuthor());
                                    }
                                } else {
                                    System.out.println("Libro: " + response.getTitle() + " - " + "Autor: " + response.getAuthor());
                                }
                                cont = 0;
                                break;
                            case "B":
                                // Conecta con la biblioteca B
                                System.out.println("Introduzca titulo del libro: ");
                                title = reader.readLine();

                                registry = LocateRegistry.getRegistry("127.0.0.1",55000); // Localhost: 127.0.0.1
                                look_up = (Z39) registry.lookup("ServerB");
                                Libro response3 = look_up.getAuthor("title", title);
                                if(response3 == null){  // Si no encuentra en la biblioteca B, intenta con la biblioteca A.
                                    registry = LocateRegistry.getRegistry("127.0.0.1",55500); // Localhost: 127.0.0.1
                                    look_up = (Z39) registry.lookup("ServerA");
                                    Libro response4 = look_up.getAuthor("title", title);
                                    if(response4 == null){ // Si no encuentra en la Biblioteca A, intenta con la biblioteca C.
                                        registry = LocateRegistry.getRegistry("127.0.0.1",55555); // Localhost: 127.0.0.1
                                        look_up = (Z39) registry.lookup("ServerC");
                                        Libro response5 = look_up.getAuthor("title", title);
                                        if (response5 == null){
                                            System.out.println("getAuthor: Book title not found!");
                                        } else {
                                            System.out.println("Libro: " + response5.getTitle() + " - " + "Autor: " + response5.getAuthor());
                                        }
                                    } else{
                                        System.out.println("Libro: " + response4.getTitle() + " - " + "Autor: " + response4.getAuthor());
                                    }
                                } else {
                                    System.out.println("Libro: " + response3.getTitle() + " - " + "Autor: " + response3.getAuthor());
                                }
                                cont = 0;
                                break;
                            case "C":
                                //Conecta con la biblioteca local. En este caso C
                                System.out.println("Introduzca titulo del libro: ");
                                title = reader.readLine();
                                registry = LocateRegistry.getRegistry("127.0.0.1",55000); // Localhost: 127.0.0.1
                                look_up = (Z39) registry.lookup("ServerC");
                                Libro response6 = look_up.getAuthor("title", title);
                                if(response6 == null){  //Si no encuentra en la biblioteca C, intenta con la biblioteca B.
                                    registry = LocateRegistry.getRegistry("127.0.0.1",55500); // Localhost: 127.0.0.1
                                    look_up = (Z39) registry.lookup("ServerB");
                                    Libro response7 = look_up.getAuthor("title", title);
                                    if(response7 == null){  // Si no encuentra en la Biblioteca B, intenta con la biblioteca A.
                                        registry = LocateRegistry.getRegistry("127.0.0.1",55555); // Localhost: 127.0.0.1
                                        look_up = (Z39) registry.lookup("ServerA");
                                        Libro response8 = look_up.getAuthor("title", title);
                                        if (response8 == null){
                                            System.out.println("getAuthor: Book title not found!");
                                        } else {
                                            System.out.println("Libro: " + response8.getTitle() + " - " + "Autor: " + response8.getAuthor());
                                        }
                                    } else {
                                        System.out.println("Libro: " + response7.getTitle() + " - " + "Autor: " + response7.getAuthor());
                                    }
                                } else {
                                    System.out.println("Libro: " + response6.getTitle() + " - " + "Autor: " + response6.getAuthor());
                                }
                                cont = 0;
                                break;
                        }
                        break;
                    case "Buscar Autor":
                        System.out.println("Introduzca la biblioteca a solicitar: ");
                        library = reader.readLine();

                        switch (library) {
                            default:
                                //Conecta con la biblioteca Local. En este caso la biblioteca B.
                                System.out.println("Introduzca el autor a buscar: ");
                                author = reader.readLine();
                                registry = LocateRegistry.getRegistry("127.0.0.1",55000); // Localhost: 127.0.0.1
                                look_up = (Z39) registry.lookup("ServerB");
                                List<String> response = look_up.getBook(author);
                                if(response == null){ // Si no encuentra en la biblioteca B, intenta con la biblioteca C.
                                    registry = LocateRegistry.getRegistry("127.0.0.1",55500); // Localhost: 127.0.0.1
                                    look_up = (Z39) registry.lookup("ServerC");
                                    List<String> response1 = look_up.getBook(author);
                                    if (response1 == null){ //Si no encuentra en la biblioteca C, intenta con la biblioteca A.
                                        registry = LocateRegistry.getRegistry("127.0.0.1",55555); // Localhost: 127.0.0.1
                                        look_up = (Z39) registry.lookup("ServerA");
                                        List<String> response2 = look_up.getBook(author);
                                        if(response2 == null){
                                            System.out.println("getBook: Author not found!");
                                        } else {
                                            response2.forEach((item) -> {
                                                System.out.println(item);
                                            });
                                        }
                                    } else {
                                        response1.forEach((item) -> {
                                            System.out.println(item);
                                        });
                                    }
                                } else {
                                    response.forEach((item) -> {
                                        System.out.println(item);
                                    });
                                }
                                cont = 0;
                                break;
                            case "B":
                                //Conecta con la biblioteca Local. En este caso B
                                System.out.println("Introduzca el autor a buscar: ");
                                author = reader.readLine();
                                registry = LocateRegistry.getRegistry("127.0.0.1",55000); // Localhost: 127.0.0.1
                                look_up = (Z39) registry.lookup("ServerB");
                                List<String> response3 = look_up.getBook(author);
                                if(response3 == null){  // Si no encuentra en la biblioteca B, intenta con la biblioteca C.
                                    registry = LocateRegistry.getRegistry("127.0.0.1",55500); // Localhost: 127.0.0.1
                                    look_up = (Z39) registry.lookup("ServerC");
                                    List<String> response4 = look_up.getBook(author);
                                    if (response4 == null){ // Si no encuentra en la biblioteca C, intenta con la biblioteca A.
                                        registry = LocateRegistry.getRegistry("127.0.0.1",55555); // Localhost: 127.0.0.1
                                        look_up = (Z39) registry.lookup("ServerA");
                                        List<String> response5 = look_up.getBook(author);
                                        if (response5 == null){
                                            System.out.println("getBook: Author not found!");
                                        } else {
                                            response5.forEach((item) -> {
                                                System.out.println(item);
                                            });
                                        }
                                    } else {
                                        response4.forEach((item) -> {
                                            System.out.println(item);
                                        });
                                    }
                                } else {
                                    response3.forEach((item) -> {
                                        System.out.println(item);
                                    });
                                }
                                cont = 0;
                                break;
                            case "C":
                                //Conecta con la biblioteca Local. En este caso C
                                System.out.println("Introduzca el autor a buscar: ");
                                author = reader.readLine();
                                registry = LocateRegistry.getRegistry("127.0.0.1",55000); // Localhost: 127.0.0.1
                                look_up = (Z39) registry.lookup("ServerC");
                                List<String> response6 = look_up.getBook(author);
                                if(response6 == null){  // Si no encuentra en la biblioteca C, intenta con la biblioteca A.
                                    registry = LocateRegistry.getRegistry("127.0.0.1",55500); // Localhost: 127.0.0.1
                                    look_up = (Z39) registry.lookup("ServerA");
                                    List<String> response7 = look_up.getBook(author);
                                    if (response7 == null){ // Si no encuentra en la biblioteca A, intenta con la biblioteca B.
                                        registry = LocateRegistry.getRegistry("127.0.0.1",55555); // Localhost: 127.0.0.1
                                        look_up = (Z39) registry.lookup("ServerB");
                                        List<String> response8 = look_up.getBook(author);
                                        if (response8 == null){
                                            System.out.println("getBook: Author not found!");
                                        } else {
                                            response8.forEach((item) -> {
                                                System.out.println(item);
                                            });
                                        }
                                    } else {
                                        response7.forEach((item) -> {
                                            System.out.println(item);
                                        });
                                    }
                                } else {
                                    response6.forEach((item) -> {
                                        System.out.println(item);
                                    });
                                }
                                cont = 0;
                                break;
                        }
                        break;
                    default:
                        System.out.println("No se reconoce el comando!");
                        break;
                }
            }

        }catch (Exception e){
            System.err.println("Server exception: "+ e.toString());
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
            throws MalformedURLException, RemoteException, NotBoundException {
        BibliotecaB biblioteca = new BibliotecaB();
        biblioteca.ClientConnectServer();
    }
}
