package bibliotecaB;

import Z39.Z39;
import libro.Libro;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BibliotecaB {

    private static Z39 look_up;

    private void wlog(String log){
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            String path = System.getProperty("user.home");
            // File file = new File("C:/Users/User/Desktop/Bibliotecas-del-mundo/log.txt"); // Localhost
            File file = new File(path+"/Desktop/ClienteBLog.txt");
            if (!file.exists()) {  // Crea el archivo en caso que no exista.
                file.createNewFile();
            }
            Date objDate = new Date();
            String strDateFormat = "hh: mm: ss a dd-MMM-aaaa";
            SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(log + " " + objSDF.format(objDate));
            bw.write("\n");
            System.out.println("Informacion Registrada!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    };

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
                    case "Buscar Titulo":
                        System.out.println("Introduzca la biblioteca a solicitar: ");
                        library = reader.readLine();

                        switch (library) {
                            /*  IP              PORTS
                                192.168.1.104   Biblioteca A = 55000
                                192.168.1.106   Biblioteca B = 55500
                                192.168.1.104   Biblioteca C = 55555
                             */
                            default:
                                // Conecta con la biblioteca local, que en este caso es la Biblioteca B.
                                System.out.println("Introduzca titulo del libro: ");
                                title = reader.readLine();
                                registry = LocateRegistry.getRegistry("192.168.1.106",55500); // Localhost: 127.0.0.1
                                look_up = (Z39) registry.lookup("ServerB");
                                Libro response = look_up.getAuthor("title", title);
                                if(response == null){  // Si no encuentra en la biblioteca B, intenta con biblioteca C.
                                    registry = LocateRegistry.getRegistry("192.168.1.104",55555); // Localhost: 127.0.0.1
                                    look_up = (Z39) registry.lookup("ServerC");
                                    Libro response1 = look_up.getAuthor("title", title);
                                    wlog("getAuthor: Book title not found in biblioteca B!");
                                    if(response1 == null) {  // Si no encuentra en la Biblioteca C, intenta con la biblioteca A.
                                        registry = LocateRegistry.getRegistry("192.168.1.104", 55000); // Localhost: 127.0.0.1
                                        look_up = (Z39) registry.lookup("ServerA");
                                        Libro response2 = look_up.getAuthor("title", title);
                                        wlog("getAuthor: Book title not found in biblioteca C!");
                                        if (response2 == null){
                                            wlog("getAuthor: Book title not found in biblioteca A!");
                                            System.out.println("\"getAuthor: Book title not found in biblioteca A!");
                                        } else{
                                            System.out.println("Libro: " + response2.getTitle() + " - " + "Autor: " + response2.getAuthor());
                                            wlog("Libro: " + response2.getTitle() + " - " + "Autor: " + response2.getAuthor() + "Found in Biblioteca A");
                                        }
                                    } else{
                                        System.out.println("Libro: " + response1.getTitle() + " - " + "Autor: " + response1.getAuthor());
                                        wlog("Libro: " + response1.getTitle() + " - " + "Autor: " + response1.getAuthor() + "Found in Biblioteca C");
                                    }
                                } else {
                                    System.out.println("Libro: " + response.getTitle() + " - " + "Autor: " + response.getAuthor());
                                    wlog("Libro: " + response.getTitle() + " - " + "Autor: " + response.getAuthor() + "Found in Biblioteca B");
                                }
                                cont = 0;
                                break;
                            case "A":
                                // Conecta con la biblioteca A
                                System.out.println("Introduzca titulo del libro: ");
                                title = reader.readLine();

                                registry = LocateRegistry.getRegistry("192.168.1.104",55000); // Localhost: 127.0.0.1
                                look_up = (Z39) registry.lookup("ServerA");
                                Libro response3 = look_up.getAuthor("title", title);
                                if(response3 == null){  // Si no encuentra en la biblioteca A, intenta con la biblioteca C.
                                    registry = LocateRegistry.getRegistry("192.168.1.104",55555); // Localhost: 127.0.0.1
                                    look_up = (Z39) registry.lookup("ServerC");
                                    Libro response4 = look_up.getAuthor("title", title);
                                    wlog("getAuthor: Book title not found in biblioteca A!");
                                    if(response4 == null){ // Si no encuentra en la Biblioteca C, intenta con la biblioteca B.
                                        registry = LocateRegistry.getRegistry("192.168.1.106",55500); // Localhost: 127.0.0.1
                                        look_up = (Z39) registry.lookup("ServerB");
                                        Libro response5 = look_up.getAuthor("title", title);
                                        wlog("getAuthor: Book title not found in biblioteca C!");
                                        if (response5 == null){
                                            wlog("getAuthor: Book title not found in biblioteca B!");
                                            System.out.println("getAuthor: Book title not found in biblioteca B!");
                                        } else {
                                            System.out.println("Libro: " + response5.getTitle() + " - " + "Autor: " + response5.getAuthor());
                                            wlog("Libro: " + response5.getTitle() + " - " + "Autor: " + response5.getAuthor() + "Found in Biblioteca B! ");
                                        }
                                    } else{
                                        System.out.println("Libro: " + response4.getTitle() + " - " + "Autor: " + response4.getAuthor());
                                        wlog("Libro: " + response4.getTitle() + " - " + "Autor: " + response4.getAuthor() + "Found in Biblioteca C! ");
                                    }
                                } else {
                                    System.out.println("Libro: " + response3.getTitle() + " - " + "Autor: " + response3.getAuthor());
                                    wlog("Libro: " + response3.getTitle() + " - " + "Autor: " + response3.getAuthor() + "Found in Biblioteca A! ");
                                }
                                cont = 0;
                                break;
                            case "C":
                                //Conecta con la biblioteca local. En este caso C
                                System.out.println("Introduzca titulo del libro: ");
                                title = reader.readLine();
                                registry = LocateRegistry.getRegistry("192.168.1.104",55555); // Localhost: 127.0.0.1
                                look_up = (Z39) registry.lookup("ServerC");
                                Libro response6 = look_up.getAuthor("title", title);
                                if(response6 == null){  //Si no encuentra en la biblioteca C, intenta con la biblioteca A.
                                    registry = LocateRegistry.getRegistry("192.168.1.104",55000); // Localhost: 127.0.0.1
                                    look_up = (Z39) registry.lookup("ServerA");
                                    Libro response7 = look_up.getAuthor("title", title);
                                    wlog("getAuthor: Book title not found in biblioteca C!");
                                    if(response7 == null){  // Si no encuentra en la Biblioteca A, intenta con la biblioteca B.
                                        registry = LocateRegistry.getRegistry("192.168.1.106",55500); // Localhost: 127.0.0.1
                                        look_up = (Z39) registry.lookup("ServerB");
                                        Libro response8 = look_up.getAuthor("title", title);
                                        wlog("getAuthor: Book title not found in biblioteca A!");
                                        if (response8 == null){
                                            wlog("getAuthor: Book title not found in Biblioteca B!");
                                            System.out.println("getAuthor: Book title not found in Biblioteca B!");
                                        } else {
                                            wlog("Libro: " + response8.getTitle() + " - " + "Autor: " + response8.getAuthor() + "Found in Biblioteca B ");
                                            System.out.println("Libro: " + response8.getTitle() + " - " + "Autor: " + response8.getAuthor());
                                        }
                                    } else {
                                        System.out.println("Libro: " + response7.getTitle() + " - " + "Autor: " + response7.getAuthor());
                                        wlog("Libro: " + response7.getTitle() + " - " + "Autor: " + response7.getAuthor() + "Found in Biblioteca A ");
                                    }
                                } else {
                                    System.out.println("Libro: " + response6.getTitle() + " - " + "Autor: " + response6.getAuthor());
                                    wlog("Libro: " + response6.getTitle() + " - " + "Autor: " + response6.getAuthor() + "Found in Biblioteca C ");
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
                                registry = LocateRegistry.getRegistry("192.168.1.106",55500); // Localhost: 127.0.0.1
                                look_up = (Z39) registry.lookup("ServerB");
                                List<String> response = look_up.getBook(author);
                                if(response == null){ // Si no encuentra en la biblioteca B, intenta con la biblioteca C.
                                    registry = LocateRegistry.getRegistry("192.168.1.104",55555); // Localhost: 127.0.0.1
                                    look_up = (Z39) registry.lookup("ServerC");
                                    List<String> response1 = look_up.getBook(author);
                                    wlog("getBook: Author not found in Biblioteca B!");
                                    if (response1 == null){ //Si no encuentra en la biblioteca C, intenta con la biblioteca A.
                                        registry = LocateRegistry.getRegistry("192.168.1.104",55000); // Localhost: 127.0.0.1
                                        look_up = (Z39) registry.lookup("ServerA");
                                        List<String> response2 = look_up.getBook(author);
                                        wlog("getBook: Author not found in Biblioteca C!");
                                        if(response2 == null){
                                            wlog("getBook: Author not found in Biblioteca A!");
                                            System.out.println("getBook: Author not found in Biblioteca A!");
                                        } else {
                                            response2.forEach((item) -> {
                                                System.out.println(item);
                                                wlog(item + "Found in Biblioteca A! ");
                                            });
                                        }
                                    } else {
                                        response1.forEach((item) -> {
                                            System.out.println(item);
                                            wlog(item + "Found in Biblioteca C! ");
                                        });
                                    }
                                } else {
                                    response.forEach((item) -> {
                                        System.out.println(item);
                                        wlog(item + "Found in Biblioteca B! ");
                                    });
                                }
                                cont = 0;
                                break;
                            case "A":
                                //Conecta con la biblioteca A
                                System.out.println("Introduzca el autor a buscar: ");
                                author = reader.readLine();
                                registry = LocateRegistry.getRegistry("192.168.1.104",55000); // Localhost: 127.0.0.1
                                look_up = (Z39) registry.lookup("ServerA");
                                List<String> response3 = look_up.getBook(author);
                                if(response3 == null){  // Si no encuentra en la biblioteca A, intenta con la biblioteca C.
                                    registry = LocateRegistry.getRegistry("192.168.1.104",55555); // Localhost: 127.0.0.1
                                    look_up = (Z39) registry.lookup("ServerC");
                                    List<String> response4 = look_up.getBook(author);
                                    wlog("getBook: Author not found in Biblioteca A!");
                                    if (response4 == null){ // Si no encuentra en la biblioteca C, intenta con la biblioteca B.
                                        registry = LocateRegistry.getRegistry("192.168.1.106",55500); // Localhost: 127.0.0.1
                                        look_up = (Z39) registry.lookup("ServerB");
                                        List<String> response5 = look_up.getBook(author);
                                        wlog("getBook: Author not found in Biblioteca C!");
                                        if (response5 == null){
                                            wlog("getBook: Author not found in Biblioteca B!");
                                            System.out.println("getBook: Author not found in Biblioteca B!");
                                        } else {
                                            response5.forEach((item) -> {
                                                System.out.println(item);
                                                wlog(item + "Found in Biblioteca B! ");
                                            });
                                        }
                                    } else {
                                        response4.forEach((item) -> {
                                            System.out.println(item);
                                            wlog(item + "Found in Biblioteca C! ");
                                        });
                                    }
                                } else {
                                    response3.forEach((item) -> {
                                        System.out.println(item);
                                        wlog(item + "Found in Biblioteca A! ");
                                    });
                                }
                                cont = 0;
                                break;
                            case "C":
                                //Conecta con la biblioteca Local. En este caso C
                                System.out.println("Introduzca el autor a buscar: ");
                                author = reader.readLine();
                                registry = LocateRegistry.getRegistry("192.168.1.104",55555); // Localhost: 127.0.0.1
                                look_up = (Z39) registry.lookup("ServerC");
                                List<String> response6 = look_up.getBook(author);
                                if(response6 == null){  // Si no encuentra en la biblioteca C, intenta con la biblioteca A.
                                    registry = LocateRegistry.getRegistry("192.168.1.104",55000); // Localhost: 127.0.0.1
                                    look_up = (Z39) registry.lookup("ServerA");
                                    List<String> response7 = look_up.getBook(author);
                                    wlog("getBook: Author not found in Biblioteca C!");
                                    if (response7 == null){ // Si no encuentra en la biblioteca A, intenta con la biblioteca B.
                                        registry = LocateRegistry.getRegistry("192.168.1.106",55500); // Localhost: 127.0.0.1
                                        look_up = (Z39) registry.lookup("ServerB");
                                        List<String> response8 = look_up.getBook(author);
                                        wlog("getBook: Author not found in Biblioteca A!");
                                        if (response8 == null){
                                            wlog("getBook: Author not found in Biblioteca B!");
                                            System.out.println("getBook: Author not found in Biblioteca B!");
                                        } else {
                                            response8.forEach((item) -> {
                                                System.out.println(item);
                                                wlog(item + "Found in Biblioteca B! ");
                                            });
                                        }
                                    } else {
                                        response7.forEach((item) -> {
                                            System.out.println(item);
                                            wlog(item + "Found in Biblioteca A! ");
                                        });
                                    }
                                } else {
                                    response6.forEach((item) -> {
                                        System.out.println(item);
                                        wlog(item + "Found in Biblioteca C! ");
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
