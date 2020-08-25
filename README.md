# CBIR-with-RoI
Repositorio con todo el material usado y creado en el desarrollo de sistemas de recuperaci�n de im�genes basados en regiones. En dicho repositorio se recoge tanto el sistema realizado, creado para ello una biblioteca con todas las funcionalidades, como una aplicaci�n de interfaz de usuario que permita a estos utilizar mi sistema. Adem�s se incluye el material utilizado para la realizaci�n de pruebas.

## Introducci�n

El acceso a contenidos multimedia, y en particular a las bases de datos de im�genes, est� adquiriendo cada vez mayor importancia. 
En este contexto, alcanzan gran relevancia los sistemas de recuperaci�n de informaci�n, basados fundamentalmente en descriptores de bajo nivel 
(color, textura, etc.) obtenidos directamente a partir de la imagen. En estos sistemas, denominados **CBIR** por sus siglas en ingl�s, las im�genes 
se representan como vectores de descriptores, las consultas se definen utilizando una imagen o boceto, y la b�squeda de correspondencia entre 
ambas se realiza en base a una medida de similitud entre vectores.

En estos sistemas, los descriptores suelen ser de car�cter global, es decir, calculados para la imagen entendida como un todo. Una mejora 
a este enfoque cl�sico consiste en la incorporaci�n de **Regiones de Inter�s** (*Regions of Interest*) en el proceso de descripci�n y b�squeda. En las soluciones actuales, las aproximaciones locales se basan en enfoques orientados a cuadr�culas (grids) de tama�o fijo, no tanto a regiones de inter�s; en este �ltimo caso, adem�s de la selecci�n de dichas regiones, surge el problema de (1) c�mo calcular el descriptor asociado a una regi�n de forma variable y (2) c�mo calcular la semejanza entre dos im�genes con conjuntos de RoIs diferentes. En este contexto, el objetivo general de este proyecto ha sido el desarrollo de m�dulos integrados en la **JMR** (*Java Multimedia Retrieval �*) para la descripci�n de im�genes basadas en **Regiones de Inter�s** (*RoI*), as� como m�tricas que permitan realizar consultas basadas en dichos descriptores.

## Algunas pruebas

En el programa se da la posibilidad de elegir el tipo de comparadores para usar la b�squeda de im�genes similares a la actual, adem�s del tipo de descriptores y atendiendo o no a la posici�n de los objetos a buscar. Aqu� solo se recogen algunos ejemplos:

##### Buscando im�genes en las que al menos haya una regi�n en com�n sin tener en cuenta su posici�n

<img src="/others/md/test_1_scalable.PNG" alt="B�squeda de la pelota en la base de datos" style="zoom:67%;" />

En este ejemplo se encuentran fotograf�as que contengan la pelota amarilla. Puede estar ella sola, como en el primer resultado, o acompa�ada de m�s objetos, como se puede observar en las fotograf�as obtenidas (est� utilizando el Comparador de im�genes en las que haya al menos una similitud entre ellas, en este caso, el de la pelota de tenis). La primera imagen, la m�s parecida solo tiene una pelota, mientras que el resto aparece la pelota junto a m�s elementos y en otras localizaciones.



<img src="/others/md/test_2_s_np.PNG" alt="B�squeda de im�genes que tengan el estuche y/o la pelota" style="zoom: 50%;" />

Este otro ejemplo demuestra que no ha de estar solo un objeto, que se pueden buscar varios objetos utilizando el mismo comparador del ejemplo anterior. En este caso los resultados muestran im�genes donde est� uno o los dos objetos a buscar (escuche y pelota).

##### Buscando im�genes en las que al menos haya una regi�n en com�n teniendo en cuenta su posici�n

<img src="/others/md/test_1_s_p.PNG" alt="Ahora se tiene en cuenta la posici�n de la pelota" style="zoom:67%;" />

Utilizando de nuevo el ejemplo de la pelota, pero esta vez, teniendo en cuenta la posici�n. Se devuelven im�genes donde la pelota est� situada o cuyo centro est� situado alrededor de la que se us� como criterio de b�squeda. En el resultado se contemplan im�genes que tienen una pelota alrededor de la posici�n de donde est� la de la imagen usada como b�squeda.

##### Buscando im�genes en las que est�n todas las regiones en com�n sin tener en cuenta su posici�n

<img src="/others/md/test_6_s_p.PNG" alt="B�squeda de im�genes que tengan la pelota y el estuche" style="zoom: 50%;" />

Esta �ltima comparaci�n buscar� im�genes que tengan tanto la pelota como el estuche de color rojo (sin tener en cuenta su posici�n). Por ello devuelve im�genes que contienen los dos objetos s� o s�, ya sean ellos dos solos o acompa�ados de otros objetos.

Se pueden utilizar este m�todo de nuevo para buscar dos objetos pero teniendo en cuenta su posici�n. Adicionalmente, se pueden utilizar otro tipo de comparadores y m�tricas. Todas ellas est�n comentadas en la documentaci�n del programa.

## Funcionamiento del programa

A continuaci�n se presenta un peque�o manual describiendo los distintos elementos del programa para su utilizaci�n.

#### Men�s superiores

<img src="/others/md/inter1.PNG" alt="Men�s superiores" style="zoom:200%;" />

Hay tres tipos de men� en la parte superior:

* **Men� *File***: Se encarga de abrir o guardar una imagen con o sin figuras. Se explicar� en la secci�n *Abrir/Guardar* c�mo se usan estos men�s.
* **Men� *View***: Se encarga de la ocultaci�n y visualizaci�n de paneles de la aplicaci�n.
* **Men� *Tools***: Ofrece un par de herramientas adicionales, como son:
  * **A�adir un directo**: A�ade a la base de datos un directorio de im�genes con o sin regi�n. Leer� el fichero de la imagen y a partir de ella, si tiene alguno asociado, el fichero *csv* con las figuras. 
  * **Mostrar base de datos:** Si hay una base de datos con im�genes, al pulsar mostrar� un listado con las im�genes que hay en dicha base de datos. Hay que tener cuidado, pues con bases de datos muy grandes, el programa colapsa.

#### Abrir/Guardar

<img src="others/md/inter2.PNG" alt="Botones Abrir y Guardar" style="zoom:150%;" />


Son dos botones principalmente:

* **Bot�n Abrir:** Al pulsarlo se abrir� un men� mostrando directorios de nuestro ordenador. Desde �l buscaremos archivos que sean im�genes y al seleccionarlo se abrir� una nueva ventana en nuestro programa con la imagen. Si exist�a un fichero *csv* asociado a la imagen, se mostrar�n tambi�n las figuras que conten�a. Se pueden abrir varias im�genes a la vez, manteniendo el bot�n *Control* pulsado y seleccionando varios ficheros con el rat�n o teclado. 
* **Bot�n Guardar:** En caso de que hayamos dibujado formas y queramos guardarlas, al pulsar sobre el bot�n guardar se abrir� un men� que nos permitir� guardar en un archivo *csv* las figuras dibujadas.

#### Dibujo de formas


![Botonera de dibujo de formas](others/md/inter3.PNG)



Herramientas para el dibujado de figuras sobre la imagen para delimitar o indicar la regi�n.

* **Herramienta cuadrado:** Permite dibujar cuadrados sobre la imagen. Se dejar� pulsado el rat�n sobre el lienzo y se mover� a la vez para indicar el tama�o que tendr�. Al soltarlo, se quedar� dibujada dicha figura.
* **Herramienta c�rculo:** Si la seleccionamos, pasaremos a poder dibujar c�rculos. El proceso de dibujado es similar al del cuadrado.
* **Herramienta trazo libre:** Herramienta para el dibujado de figuras aleatorias. Al soltar el rat�n, esta se cerrar�.
* **Selecci�n de color:** Permite cambiar el color del trazo de las figuras. Por defecto est� como color rosa. Al pulsar sobre �l, se abrir� una venta que muestra muchos tipos de colores diferentes. Al seleccionar, se cambiar�n todas las figuras a ese nuevo color.
* **Eliminar figuras:** Este bot�n permite eliminar todas las figuras dibujadas sobre el lienzo actual.

#### Base de datos

<img src="others/md/inter4.PNG" alt="Botonera de la base de datos" style="zoom:150%;" />

Se muestra una botonera sobre las distintas operaciones con la base de datos. De izquierda a derecha:

* **Crear una nueva base de datos:** Se crea una nueva base de datos vac�a. Si se pulsa con el bot�n derecho del raz�n sobre el icono, mostrar� un men� desplegable para seleccionar el tipo de descriptor que se usar� para crear la base de datos.
* **Abrir una base de datos:** En caso de que tengamos nuestra base de datos guardada en un fichero, se ofrece la posibilidad de poder usar el archivo para crear la base de datos, es decir, abrirla. Se abrir� un men� para buscar la base de datos. Se buscan solo archivos *.dat*, que es la extensi�n que tienen las bases de datos.
* **Guardar base de datos:** en caso de que haya una base de datos en uso en el programa, existe la posibilidad de que se pueda guardar. Al pulsar sobre el bot�n, se abrir� una ventana para guardar dicha base de datos. Las bases de datos se guardan, como comentaba, con la extensi�n *.dat*.
* **Eliminar base de datos:** Con este bot�n se elimina la base de datos, completamente. No se trata de un borrado de contenido, sino que no habr� ninguna base de datos en el programa.
* **A�adir elemento actual a la base de datos:** Se a�adir� el contenido de la ventana seleccionada a la base de datos al pulsar sobre el bot�n.
* **Buscar elemento actual:** Este bot�n permite la b�squeda de la ventana actual, de la imagen con o sin figuras, en la base de datos. El resultado depender� del tipo de descriptor usado y los comparadores y m�tricas. Se mostrar� una nueva ventana con los resultados, donde con doble clic se podr�n agrandar para verse mejor.


#### Comparador de escritorio

<img src="others/md/inter5.PNG" alt="Bot�n comparador de escritorio" style="zoom:150%;" />

Este �nico bot�n permite hacer una comparativa de todas las im�genes abiertas en pantalla con la que est� seleccionada. Para cambiar el tipo de descriptor que se quiera usar, basta con pulsar con el bot�n izquierdo sobre el icono. A partir de ese descriptor se calcular� la distancia con el resto de ventanas y se abrir� una nueva ventana con el resultado ordenado de menor a mayor. En la parte inferior, se mostrar� por testo la comparativa de una imagen con la otra utilizando el descriptor y m�tricas seleccionados.

#### Herramientas de comparadores y m�tricas

<img src="others/md/inter6.PNG" alt="Botonera con las distintas opciones de las m�tricas" style="zoom:150%;" />

Son varios los elementos a destacar en este apartado. Dichos elementos son:

* **Selector del tipo de comparador:** Es de tres tipos, desde al menos una regi�n en com�n, el mayor n�mero y la media.
* **Bot�n Incursi�n Simple:** Si est� seleccionado, el comparador tendr� en cuenta que ser� incursi�n simple la m�trica usada para las b�squedas. Por defecto est� seleccionada.
* **Bot�n Igualdad:** Al seleccionarlo, cambiar� el tipo de comparador usado para usar la igualdad. 
* **Bot�n Posici�n:** Si se selecciona, se tendr� adem�s en cuenta la posici�n de las regiones a la hora de realizas las b�squedas en la base de datos.
* **Bot�n Sin Repeticiones:** Si se selecciona, evitar� que varias regiones apunten a una misma regi�n, haciendo un c�lculo m�s realista.

#### Muestra de resultados

<img src="others/md/inter7.PNG" alt="Una prueba mostrando el comparador de escritorio para una imagen" style="zoom:67%;" />

En el escritorio del programa se mostrar�n tanto los lienzos que se abran (uno o varios). Abajo aparecer� una ventana con un listado de im�genes con las que la actual imagen tiene similitudes, dependiendo del comparador y descriptor usados.

#### Barra inferior

La barra inferior del programa muestra tres tipos de informaci�n:

* Informaci�n sobre el punto actual donde est� el cursor. Muestra:
  * Coordenadas del p�xel donde est� el cursor situado.
  * Informaci�n de los colores de dicho pixel (Rojo, azul, verde y, si existe, valor alfa).
* Informaci�n de la base de datos que hay actualmente en uso. Indicar� el n�mero de entradas y el tipo de descriptor en uso. Si no hay ninguna utiliz�ndose, mostrar� un mensaje que indica que no hay nada.

## Material disponible

En este repositorio est� disponible el siguiente material:

 - **JMR.RSG** es una extensi�n de la biblioteca **JMR** enfocada a la recuperaci�n de im�genes por contenido, y en funci�n a sus regiones de inter�s. Dicha biblioteca **JMR** puede ser consultada en [https://github.com/jesuschamorro/JMR](https://github.com/jesuschamorro/JMR).
 - **JMR.Application** es la interfaz de usuario que permite a cualquier persona utilizar el sistema desarrollado. Ofrece una interfaz gr�fica en la que se puede abrir im�genes, seleccionar las regiones de �sta con herramientas de dibujo, manipular la base de datos, etc.
 - Material adicional, como im�genes con su archivo .csv correspondiente que guarda las regiones de dichas figuras.

Se acompa�a el c�digo de la biblioteca y aplicaci�n con su documentaci�n *javadocs*, accesible desde *dist/javadocs*. Para la utilizaci�n del programa, recomiendo el uso de un IDE. Yo recomiendo [Netbeans](https://netbeans.org/).

