# Working plan

## Week 1 (8 h)

* Study the contents of the Unit, complementing them with the following notes and videos.  
  * [Generación de Interfaces de Usuario](https://fpadistancia.caib.es/pluginfile.php/214797/mod_resource/content/4/index.html)  
    * Section 1\. If you have time read also the article [https\://en.wikipedia.org/wiki/Graphical\_user\_interface](https://en.wikipedia.org/wiki/Graphical_user_interface)  
    * Expand the contents of ‘2.1.- Bibliotecas de componentes.’. Search or ask any LLM which GUI component libraries are the most used nowadays for desktop and mobile applications.  
    * Section 3.1. Watch video:  
      * [Create a Maven project with NetBeans 15 and JDK 17](https://youtu.be/RuoZTKGEE84)  
        * There is no difference between NetBeans 15 and 31 for this purpose.  
        * You can also find how to create a Maven Swing project in the last part of the video [From Console To GUI](https://youtu.be/qbudYyF4Jcw?t=4181).  
    * Section 4\. Watch videos:  
      * [Contenedores](https://youtu.be/dnq8chO52oA)  
        * JFrame and JDialog  
      * [https\://youtu.be/JLnld38AzUE?si=xgls1k1XCVuJkkIi](https://youtu.be/JLnld38AzUE?si=xgls1k1XCVuJkkIi)  
        * JPanel  
    * Section 5\. Componentes de la interfaz.  
      * Java swing components naming convention  
        * [https\://stackoverflow.com/questions/4770174/name-convention-on-java-swing-componentsprefix](https://stackoverflow.com/questions/4770174/name-convention-on-java-swing-componentsprefix)  
        * You can also use the naming convention used in Pablo Gonzalez videos.  
      * [https\://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html](https://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html)  
    * Section 5.1 Ejercicio resuelto  
      * [Tabbed Pane](https://www.youtube.com/watch?v=-5tvXIC_9nk)  
    * Section 5.2 Creación de la GUI Encuesta  
      * [Creación de la GUI Dialog Encuesta](https://youtu.be/a_WEjAee6x0)  
    * Section 5.3 Ejercicio resuelto añadir funcionalidad  
      * [https\://youtu.be/SzOFUQbgnwU](https://youtu.be/SzOFUQbgnwU)  
    * Section 5.4.- Ubicación y alineamiento de los componentes.  
      * Read only. No need to do the exercise now. We will come back and work with layouts in future lessons. In this first unit we will be using the null layout and positioning the components ‘by hand’.  
    * **DO NOT study** Section 5.5.- Enlace de componentes a orígenes de datos. We will be accessing data in a Database in a more programmatically approach explained later.  
    * Section 7.- Diálogos modales y no modales.  
      *  Read the section but leave the exercises for later.  
      * You will practise showing and hiding modal dialogs (JDialog) and **communicating and sharing data** between a JFrame and a JDialog in both directions watching the first videos of the playlist [https\://www\.youtube.com/playlist?list=PLIfP1vJ2qakli4Z\_-yVZV-rq\_hQeHQzUb](https://www.youtube.com/playlist?list=PLIfP1vJ2qakli4Z_-yVZV-rq_hQeHQzUb) (videos 1 a 8 de la PlayList).  
      * We will be using the JOptionPane and the JFileChooser. They will be introduced as needed.  
        * Ver video 11 de la anterior playlist para JOptionPane.  
    * Read sections 8, 9 and 10\.

## Week 2 (6h)

* Read the following article and watch carefully the video:  
  * Read [https\://www\.cs.ubc.ca/\~laks/cpsc304/Swing/jdbc\_swing.html](https://www.cs.ubc.ca/~laks/cpsc304/Swing/jdbc_swing.html) (hasta el apartado ‘Before you move on’)  
  * [From Console To GUI](https://youtu.be/qbudYyF4Jcw?t=4181)  
* Practice exercises to learn the most common GUI components and their properties and events  
  * JFrame, JLabel, JButton, JTextField, JTextArea, JCheckBox, JRadioButton, ButtonGroup, JSlider, JProgressBar, JSpinner, JOptionPane, JFileChooser, JMenuBar, JMenu, JMenuItem.  
* Read the Enunciado tarea para DI01. Make up a cool name for your application.  
* Tarea para DI01  
  * Create the project and github repo. Post the link of the repository to the moodle assignment.

## Week 3 (6h)

* JList and JComboBox  
  * [Tutoria 2022-11-11 JList and JComboBox Of Objects (different than String)](https://youtu.be/m_61rb8tW8k)  
* JTable  
  * See the rest of the videos of the playlist [https\://www\.youtube.com/playlist?list=PLIfP1vJ2qakli4Z\_-yVZV-rq\_hQeHQzUb](https://www.youtube.com/playlist?list=PLIfP1vJ2qakli4Z_-yVZV-rq_hQeHQzUb) (videos 9 to 20, except videos number 13 and 17 (ValidationApi)).

## Week 4 (4h)

* Finish Tarea for DI01  
  * Entrega 10-11-2026

# Contents

GUI

- [https\://en.wikipedia.org/wiki/Graphical\_user\_interface](https://en.wikipedia.org/wiki/Graphical_user_interface)

NetBeans IDE

- Design / Source  
- Palette  
- Properties

Components

- Swing Windows: JFrame, JDialog, JOptionPane, JFileChooser  
  - [https\://docs.oracle.com/javase/tutorial/uiswing/components/toplevel.html](https://docs.oracle.com/javase/tutorial/uiswing/components/toplevel.html)  
- Swing Containers: JPanel, JScrollPane  
- Swing Controls: JLabel, JButton, JTextField, JTextArea, JCheckBox, JRadioButton, ButtonGroup, JSlider, JProgressBar, JSpinner, JList, JComboBox, JTable  
- Swing Menus: JMenuBar, JMenu, JMenuItem

Events

- Listeners  
- Event handler methods  
- actionEvent, windowOpened, windowClosing, focusGained, focusLost, keyPressed, mouseClicked, mouseEntered, …

Layout

- **Null layout**  
- Absolute layout (avoid, don’t use)  
- Border layout  
- Box layout (X-axis, Y-axis)  
- Flow layout  
  - Wrap layout  
- Grid layout   
- Free design (Netbeans)  
- …

# Additional Resources

* Watch (and do practise) videos 1 to 12 from playlist:  
  * [https\://www\.youtube.com/playlist?list=PLIfP1vJ2qakli4Z\_-yVZV-rq\_hQeHQzU](https://www.youtube.com/playlist?list=PLIfP1vJ2qakli4Z_-yVZV-rq_hQeHQzUb)  
* OOP  
  * Inheritance and composition  
  * Abstract classes & Interfaces  
    - [https\://www\.youtube.com/watch?v=HvPlEJ3LHgE](https://www.youtube.com/watch?v=HvPlEJ3LHgE)  
* Learn how to include static images or icons in your GUIs.  
  * [Including images in a Java Swing Maven application](https://youtu.be/U_9qrcVq7T4)

