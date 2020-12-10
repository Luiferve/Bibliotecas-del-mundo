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
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServidorB extends UnicastRemoteObject implements Z39 {

    protected ServidorB() throws RemoteException {
        super();
    }

    // Retorna el Autor del titulo de un libro dado.
    @Override
    public synchronized Libro getAuthor(String nodeName, String nodeValue) throws RemoteException{
        String bookTitle = "", bookAuthor = "";

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
                            System.out.println("getAuthor: "+ bookAuthor+" in BibliotecaB!");
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
        return null;
    }

    // Retorna una lista de los libros de un autor dado.
    @Override
    public synchronized Libro getBook(String nodeName, String nodeValue) throws RemoteException {
        String bookTitle = "", bookAuthor = "";

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
                            bookAuthor = bookElement.getElementsByTagName("title").item(0).getTextContent();
                            System.out.println("getBook: "+ bookTitle+" in BibliotecaB!");
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
        System.out.println("getBook: Author not found in BibliotecaB!");
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

