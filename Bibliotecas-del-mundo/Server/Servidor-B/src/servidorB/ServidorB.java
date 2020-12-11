package servidorB;

import Z39.Z39;
import libro.Libro;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServidorB extends UnicastRemoteObject implements Z39 {

    protected ServidorB() throws RemoteException {
        super();
    }

    // Retorna el Autor del titulo de un libro dado.
    @Override
    public synchronized Libro getAuthor(String nodeName, String nodeValue) throws RemoteException{
        String bookTitle = "", bookAuthor = "";
        BufferedWriter bw = null;
        FileWriter fw = null;
        String log = "";

        // Obtenemos el Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Obtenemos el documento XML
            Document doc = builder.parse(new File("BibliotecaB.xml"));

            // Normalizar la estructura del XML
            doc.getDocumentElement().normalize();

            // Obtener los elementos
            NodeList bookNodes = doc.getElementsByTagName("Libro");
            for(int i=0; i<bookNodes.getLength(); i++)
            {
                Node bNode = bookNodes.item(i);
                if(bNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element bookElement = (Element) bNode;
                    NodeList textNodes = bookElement.getElementsByTagName(nodeName);
                    if(textNodes.getLength() > 0)
                    {
                        if(textNodes.item(0).getTextContent().equalsIgnoreCase(nodeValue))
                        {
                            bookTitle = textNodes.item(0).getTextContent();
                            bookAuthor = bookElement.getElementsByTagName("author").item(0).getTextContent();
                            System.out.println("getAuthor: "+ bookAuthor +" in BibliotecaB!");

                            try {
                                log = "getAuthor: "+ bookAuthor +" in BibliotecaB!/n";
                                // File file = new File("C:/Users/User/Desktop/Bibliotecas-del-mundo/log.txt"); // Localhost
                                File file = new File("E:/Home/Kali/log.txt"); // Linux
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
                                try {  // Cierra instancias de FileWriter y BufferedWriter
                                    if (bw != null)
                                        bw.close();
                                    if (fw != null)
                                        fw.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }

                            return new Libro(bookTitle, bookAuthor);
                        }
                    }
                }
            }
        } catch(ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("getAuthor: Book title not found in BibliotecaB!");

        try {
            log = "getAuthor: Book title not found in BibliotecaB!/n";
            // File file = new File("C:/Users/User/Desktop/Bibliotecas-del-mundo/log.txt"); // Localhost
            File file = new File("E:/Home/Kali/log.txt"); // Linux
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
            try {  // Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    // Retorna una lista de los libros de un autor dado.
    @Override
    public synchronized List<String> getBook(String nodeValue) throws RemoteException {
        String bookTitle = "", bookAuthor = "";
        BufferedWriter bw = null;
        FileWriter fw = null;
        String log = "";

        // Obtenemos el Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            File archivo = new File("BibliotecaB.xml");
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document doc = documentBuilder.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList listaLibros = doc.getElementsByTagName("Libro");
            List<String> libros = new ArrayList<String>();

            for (int temp = 0; temp < listaLibros.getLength(); temp++) {
                Node nodeBook = listaLibros.item(temp);
                if (nodeBook.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodeBook;

                    if (element.getElementsByTagName("author").item(0).getTextContent().equals(nodeValue)) {
                        libros.add(element.getElementsByTagName("title").item(0).getTextContent());
                    }
                }
            }
            if (!libros.isEmpty()) {
                System.out.println("getBook: Book found in BibliotecaB!");
                try {
                    log = "getBook: Book found in BibliotecaB!/n";
                    // File file = new File("C:/Users/User/Desktop/Bibliotecas-del-mundo/log.txt"); // Localhost
                    File file = new File("E:/Home/Kali/log.txt"); // Linux
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
                    try {  // Cierra instancias de FileWriter y BufferedWriter
                        if (bw != null)
                            bw.close();
                        if (fw != null)
                            fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                return libros;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("getBook: Author not found in BibliotecaB!");

        try {
            log = "getBook: Author not found in BibliotecaB!/n";
            // File file = new File("C:/Users/User/Desktop/Bibliotecas-del-mundo/log.txt"); // Localhost
            File file = new File("E:/Home/Kali/log.txt"); // Linux
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
            try {  // Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(55500); // PORT 55500
            registry.rebind("ServerB", new ServidorB()); //Crea un nuevo objeto de tipo ServidorB al rmiregistry.
            System.err.println("Server B ready!");
        } catch (Exception e) {
            System.err.println("Server exception: "+ e.toString());
            e.printStackTrace();
        }
    }
}

