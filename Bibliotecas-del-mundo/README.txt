Bibliotecas del mundo.

Se obtiene una primera carpeta principal llamada "Bibliotecas-del-mundo" que contiene todas las
aplicaciones. Estas son:

 - Tres aplicaciones Clientes: Biblioteca-A, Biblioteca-B, Biblioteca-C.
 - Una aplicación Interfaz: Interfaz.
 - Tres aplicaciones Servidor: Servidor-A, Servidor-B, Servidor-C.
 - Una aplicacion RMI, que contiene las clases de todos las aplicaciones de las que el cada
Cliente extraerá la información: rmi.

El rmiregistry es un servicio que va a estar ejecutandose siempre en una carpeta y va a estar
leyendo las clases que esten en ella. A su vez, todas las clases en dicha carpeta, estaran
disponibles para todas las aplicaciones que la consuman. Es una carpeta compartida.

CÓMO COMPILAR LAS APLICACIONES?

NOTA: Todos los archivos de las clases van guardados y compilados en la carpeta "rmi".

 1. Abrir una ventana de comando CMD.
 2. Pararse en la carpeta "Bibliotecas-del-mundo".
 3. Ejecutar de la manera: javac -d rmi Interface\Interfaz-A\src\interfazA\InterfazA.java
 
 NOTA: -d identifica la carpeta donde se guardara el archivo compilado.
 NOTA: La direccion suministrada es la del archivo modificado, es decir, la que esta fuera de 
la carpeta "rmi".
 
 4. Ir al proyecto y agregar la libreria "rmi".
 5. Si anteriormente, ya fue implementada la libreria, se debera ejecutar de la siguiente manera:
 javac -d rmi -cp "rmi" Server\Servidor-A\src\servidorA
 
 NOTA: -cp "rmi" es un comando que especifica al compilador que esta usando esa libreria.
 NOTA: Las compilaciones por consolas, solo son necesarias para los Servidores e Interfaces,
ya que los Clientes no ocupan clases que seran usadas por los otros dos.


CÓMO EJECUTAR LAS APLICACIONES?

NOTA: Para que el rmi funcione, debemos tener un servicio ejecutándose. Ese servicio es 
"rmiregistry" y siempre debe estar ejecutandose para que se puedan compartir las clases.

 1. Abrir una ventana de comando CMD.
 2. Pararse en la carpeta Bibliotecas-del-mundo\rmi

 NOTA: El "rmiregistry" siempre se debe ejecutar en la carpeta compartida.

 3. Ejecutar el comando: start rmiregistry
 4. Luego, ejecutar el Servidor que queremos ejecutar con: java servidorA.ServidorA
 
 NOTA: Tras ejecutar la linea 4. deberiamos tener una respuesta del tipo: "Servidor x Ready".

 5. Finalmente, pasamos a ejecutar el Cliente que puede ser ejecutado desde la IDE.

