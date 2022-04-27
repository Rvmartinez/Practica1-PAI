package com.example.practica1;

import javafx.scene.transform.Transform;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import java.util.ArrayList;




public class HelloApplication extends Application {

    private final int XESTANTERIA = 20;
    private Group grupo3D;
    // Colecci�n de elementos gr�ficos que se van a representar en 3D

    private Group grupoPalets;
    // Colecci�n de elementos gr�ficos que se van a representar a los ejes de
    // coordenadas mediante paralelep�pedos de la clase Box

    private Group[] grupoEstanterias;

    private PerspectiveCamera camara;
    // C�mara utilizada en la escena 3D

    private double ratonX, ratonY;
    // Posici�n del rat�n en la ventana

    private double ratonXVentanaAntes, ratonYVentanaAntes;
    // Para recordar la posici�n anterior del rat�n en la ventana

    ArrayList<Palet> Palets = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        readXML();
        grupoEstanterias = new Group[4];
        for(int i = 0; i < 4 ; i++){
            grupoEstanterias[i] = new Group();
        }
        grupo3D = new Group();

        for(int i = 0; i < 8; i++){
            crearBaldas(i);
        }
        grupo3D.getChildren().addAll(grupoEstanterias);
        grupoPalets = new Group();
        crearPalets(grupoPalets);
        grupo3D.getChildren().add(grupoPalets);

        Color colorRojo = new Color(1, 0, 0, 1);
        // Objeto para representar al color rojo (componentes R=1, G=0, B=0 y alpha=1).
        // Todos los valores son reales entre 0 y 1.

        Color colorMagenta = new Color(1, 0, 1, 1);
        // Color magenta, combinando rojo y azul

        SubScene subEscena3D = new SubScene(grupo3D, 0, 0, true,
                SceneAntialiasing.BALANCED);


        // A�ade el grupo a la escena 3D,
        // donde se realiza un renderizado con antialiasing

        subEscena3D.setFill(new Color(0.2, 0.2, 0.2, 1));
        // Establece un color gris de fondo

        camara = new PerspectiveCamera(true);
        // Crea una c�mara para realizar la proyecci�n

        camara.setNearClip(0.1);
        camara.setFarClip(10000.0);
        // Se van a representar en pantalla todos los objetos situados desde una distancia
        // de 0.1 unidades de la c�mara y hasta una distancia de 100000 unidades

        Rotate rotacionXCamara = new Rotate(160, Rotate.X_AXIS);
        Rotate rotacionYCamara = new Rotate(-30, Rotate.Y_AXIS);
        Translate traslacionCamara = new Translate(0, 0, -100);//Alejar o arcercar camara cambiando v2
        camara.getTransforms().addAll(rotacionXCamara, rotacionYCamara, traslacionCamara);
        // A la c�mara se le aplican las rotaciones y traslaci�n indicadas por par�metro para
        // situarla en una posici�n inicial. Primero una traslaci�n de -3000 unidades en el
        // eje Z, luego una rotaci�n de -30� en el eje Y y finalmente una rotaci�n de 160�
        // en el eje X

        subEscena3D.setCamera(camara);
        // Establece la c�mara para la escena 3D



        PointLight luzPuntual = new PointLight(new Color(0.6, 0.6, 0.6, 1));
        luzPuntual.setTranslateX(10000);
        luzPuntual.setTranslateY(20000);
        luzPuntual.setTranslateZ(30000);
        grupo3D.getChildren().add(luzPuntual);
        // Se define en la escena una luz puntual situada en (10000, 20000, 30000) con un
        // color gris definido por sus componentes roja = verde = azul = 0.6

        grupo3D.getChildren().add(new AmbientLight(new Color(0.3, 0.3, 0.3, 1)));
        // A�ade una luz difusa de color gris formada por rojo = verde = azul = 0.3


        // ---------------- Parte superior de la ventana en 2D -------------------------

        Label etiquetaResultado = new Label("");
        // Etiqueta para mostrar un texto inicialmente vac�o

        etiquetaResultado.setPadding(new Insets(5));  // M�rgenes de 5 puntos alrededor

        ArrayList<String> opcionesComboBox = new ArrayList<String>();
        opcionesComboBox.add("Todos");
        opcionesComboBox.add("TensoActivo");
        opcionesComboBox.add("Secuestrante");
        opcionesComboBox.add("Rindente");
        opcionesComboBox.add("Remojo");
        opcionesComboBox.add("Engrase");
        opcionesComboBox.add("Desencalante");
        opcionesComboBox.add("Conservante");
        opcionesComboBox.add("Calero");
        opcionesComboBox.add("Cera");
        opcionesComboBox.add("Hidrofugante");
        opcionesComboBox.add("Resina");
        opcionesComboBox.add("Curtición");
        // ArrayList de cadenas con el texto de las opciones que aparecen en el ComboBox

        ComboBox comboProductos = new ComboBox(
                FXCollections.observableArrayList(opcionesComboBox));
        // Crea un ComboBox para mostrar esas opciones

        comboProductos.setValue("Todos");
        // Establece la opci�n seleccionada inicialmente

        comboProductos.setOnAction(evento -> {
            // Al m�todo setOnAction se le pasa una expresi�n Lambda que se ejecutar�
            // cuando cambie la opci�n elegida en el ComboBox

            etiquetaResultado.setText("Producto: " +
                    comboProductos.getSelectionModel().getSelectedItem());
            // Muestra la opci�n elegida en la etiqueta
        });

        CheckBox checkEst1 = new CheckBox("Estanteria 1");  // CheckBox con el texto "Esfera 1"
        checkEst1.setPadding(new Insets(5));  // Establece m�rgenes de 5 puntos alrededor
        checkEst1.setSelected(true);  // Inicialmente el CheckBox est� seleccionado


        checkEst1.setOnAction(evento -> {
            // Al m�todo setOnAction se le pasa una expresi�n Lambda que se ejecutar�
            // cuando cambie el estado de selecci�n del CheckBox

            grupoEstanterias[0].setVisible(checkEst1.isSelected());
            // La esfera 1 ser� visible si el CheckBox est� seleccionado
        });


        CheckBox checkEst2 = new CheckBox("Estanteria 2");
        checkEst2.setPadding(new Insets(5));
        checkEst2.setSelected(true);
        checkEst2.setOnAction(evento -> {
            grupoEstanterias[1].setVisible(checkEst2.isSelected());
        });

        CheckBox checkEst3 = new CheckBox("Estanteria 3");  // CheckBox con el texto "Esfera 1"
        checkEst3.setPadding(new Insets(5));  // Establece m�rgenes de 5 puntos alrededor
        checkEst3.setSelected(true);  // Inicialmente el CheckBox est� seleccionado


        checkEst3.setOnAction(evento -> {
            // Al m�todo setOnAction se le pasa una expresi�n Lambda que se ejecutar�
            // cuando cambie el estado de selecci�n del CheckBox

            grupoEstanterias[2].setVisible(checkEst3.isSelected());
            // La esfera 1 ser� visible si el CheckBox est� seleccionado
        });

        CheckBox checkEst4 = new CheckBox("Estanteria 4");  // CheckBox con el texto "Esfera 1"
        checkEst4.setPadding(new Insets(5));  // Establece m�rgenes de 5 puntos alrededor
        checkEst4.setSelected(true);  // Inicialmente el CheckBox est� seleccionado


        checkEst4.setOnAction(evento -> {
            // Al m�todo setOnAction se le pasa una expresi�n Lambda que se ejecutar�
            // cuando cambie el estado de selecci�n del CheckBox

            grupoEstanterias[3].setVisible(checkEst4.isSelected());
            // La esfera 1 ser� visible si el CheckBox est� seleccionado
        });

        CheckBox checkBaldas = new CheckBox("Baldas");  // CheckBox con el texto "Esfera 1"
        checkBaldas.setPadding(new Insets(5));  // Establece m�rgenes de 5 puntos alrededor
        checkBaldas.setSelected(true);  // Inicialmente el CheckBox est� seleccionado


        checkBaldas.setOnAction(evento -> {
            // Al m�todo setOnAction se le pasa una expresi�n Lambda que se ejecutar�
            // cuando cambie el estado de selecci�n del CheckBox
            for(Group g : grupoEstanterias ){
                grupoPalets.setVisible(checkBaldas.isSelected());
                g.setVisible(checkBaldas.isSelected());
            }
        });




        ToolBar barraSuperior = new ToolBar();
        barraSuperior.getItems().add(checkEst1);
        barraSuperior.getItems().add(checkEst2);
        barraSuperior.getItems().add(checkEst3);
        barraSuperior.getItems().add(checkEst4);
        barraSuperior.getItems().add(checkBaldas);
        //barraSuperior.getItems().add(checkEsfera2);
        barraSuperior.getItems().add(comboProductos);
        barraSuperior.getItems().add(etiquetaResultado);
        barraSuperior.setOrientation(Orientation.HORIZONTAL);
        // Barra superior horizontal donde se colocan los CheckBox, ComboBox y Label. Es una
        // colecci�n donde se guardan objetos de esas clases, que son derivadas de la clase
        // base Node


        // -------------------------- Ventana --------------------------

        // Contenido de la ventana
        BorderPane contenidoVentana = new BorderPane();
        contenidoVentana.setCenter(subEscena3D);
        contenidoVentana.setTop(barraSuperior);
        // Crea el contenido de la ventana, situando la subescena 3D en el centro y la barra
        // superior arriba

        subEscena3D.heightProperty().bind(contenidoVentana.heightProperty());
        subEscena3D.widthProperty().bind(contenidoVentana.widthProperty());
        // El tama�o de la subescena 3D se adapta a cambios de tama�o en la ventana

        Scene escena = new Scene(contenidoVentana);
        // Crea una escena a partir del contenido a mostrar


        escena.setOnMousePressed((MouseEvent evento) -> {
            ratonXVentanaAntes = evento.getSceneX();
            ratonYVentanaAntes = evento.getSceneY();
        });
        // Define mediante una expresi�n Lambda el c�digo que se ejecuta cuando se produce
        // el evento de rat�n consistente en la pulsaci�n de un bot�n del rat�n.
        // Guarda en las variables ratonXVentanaAntes y ratonYVentanaAntes las coordenadas
        // X e Y donde se puls� el rat�n.

        escena.setOnScroll((ScrollEvent evento) -> {
            // Define mediante una expresi�n Lambda el c�digo que se ejecuta cuando el
            // usuario gira la rueda del rat�n.

            double factor = 0.02;
            camara.setRotationAxis(Rotate.Z_AXIS);
            double roll = camara.getRotate() + evento.getDeltaY() * factor;
            // Con el m�todo getDeltaY se recoge la cantidad de movimiento en la rueda del
            // rat�n, que se multiplica por un factor y se a�ade al �ngulo de rotaci�n en el
            // eje Z de la c�mara, que es el eje de visi�n

            Rotate rotacion = new Rotate(roll, Rotate.Z_AXIS);
            camara.getTransforms().addAll(rotacion);
            // Modifica la rotaci�n de la c�mara en su eje Z
        });


        escena.setOnKeyPressed(event -> {
            // Define la expresi�n Lambda que se ejecuta cuando se detecta la pulsaci�n
            // (�nica o repetida) de una tecla

            double desplazamiento = 10;
            if (event.isShiftDown()) {
                desplazamiento = 60;
            }
            // Cantidad de espacio que se desplaza la c�mara. Con may�sculas se desplaza
            // el doble

            KeyCode tecla = event.getCode();  // Tecla pulsada

            if (tecla == KeyCode.S) {
                // Si tecla S, desplazamiento hacia atr�s, en eje Z de la c�mara, que es su
                // eje de visi�n
                Translate traslacion = new Translate(0, 0, -desplazamiento);
                camara.getTransforms().addAll(traslacion);
            }
            if (tecla == KeyCode.W) {  // Desplazamiento hacia delante
                Translate traslacion = new Translate(0, 0, desplazamiento);
                camara.getTransforms().addAll(traslacion);
            }
            if (tecla == KeyCode.A) {  // Desplazamiento hacia la izquierda
                Translate traslacion = new Translate(-desplazamiento, 0, 0);
                camara.getTransforms().addAll(traslacion);
            }
            if (tecla == KeyCode.D) {  // Desplazamiento hacia la derecha
                Translate traslacion = new Translate(desplazamiento, 0, 0);
                camara.getTransforms().addAll(traslacion);
            }
            if (tecla == KeyCode.E) {  // Desplazamiento hacia arriba
                Translate traslacion = new Translate(0, -desplazamiento, 0);
                camara.getTransforms().addAll(traslacion);
            }
            if (tecla == KeyCode.C) {  // Desplazamiento hacia abajo
                Translate traslacion = new Translate(0, desplazamiento, 0);
                camara.getTransforms().addAll(traslacion);
            }
        });


        escena.setOnMouseDragged(evento -> {
            // Expresi�n Lambda que se ejecuta mientras se arrastra el rat�n con alg�n
            // bot�n pulsado

            double limitePitch = 90;  // �ngulo m�ximo de cabeceo, positivo o negativo

            ratonY = evento.getSceneY();
            ratonX = evento.getSceneX();
            // Obtiene la nueva posici�n del rat�n

            double movimientoRatonY = ratonY - ratonYVentanaAntes;
            double movimientoRatonX = ratonX - ratonXVentanaAntes;
            // Cantidad de movimiento del rat�n desde la anterior posici�n

            double factor = 0.1;
            if (evento.isShiftDown())
                factor = 0.3;
            // Factor por el que se multiplican los �ngulos de giro, mayor si se pulsa
            // la tecla Shift

            if (evento.isPrimaryButtonDown()) {
                // Se est� pulsando el bot�n izquierdo del rat�n ...

                if (ratonY != ratonYVentanaAntes) {
                    // Si hubo movimiento vertical del rat�n en la ventana ...

                    camara.setRotationAxis(Rotate.X_AXIS);
                    double pitch = -movimientoRatonY * factor;
                    // �ngulo de cabeceo � pitch en funci�n del movimiento del rat�n

                    if (pitch > limitePitch)
                        pitch = limitePitch;
                    if (pitch < -limitePitch)
                        pitch = -limitePitch;
                    // Limita el pitch

                    camara.getTransforms().addAll(new Rotate(pitch, Rotate.X_AXIS));
                    // Rota la c�mara con ese �ngulo con respecto a su eje X
                }

                if (ratonX != ratonXVentanaAntes) {  // Hubo movimiento horizontal del rat�n

                    camara.setRotationAxis(Rotate.Y_AXIS);
                    double yaw = movimientoRatonX * factor;
                    // Calcula el �ngulo yaw

                    camara.getTransforms().addAll(new Rotate(yaw, Rotate.Y_AXIS));
                    // Rota la c�mara con ese �ngulo con respecto al eje Y de la c�mara
                }

            }

            ratonXVentanaAntes = ratonX;
            ratonYVentanaAntes = ratonY;
            // Recuerda la posici�n del rat�n para el siguiente evento
        });


        escena.setOnMouseClicked((MouseEvent evento) -> {
            // Trata con una expresi�n Lambda el evento producido cuando se hace click
            // con el rat�n

            Node captura = evento.getPickResult().getIntersectedNode();
            // Cuando se hace click sobre un elemento gr�fico, se obtiene una referencia
            // al objeto correspondiente. Es una referencia de la clase Node, que es clase
            // base para Box, Sphere, etc

            if (captura instanceof Sphere) {
                // Si es una referencia a un objeto de la clase Sphere ...

                Sphere esferaSeleccionada = (Sphere) captura;
                // La convierte a una referencia de la clase Sphere
/*
                if (esferaSeleccionada == esfera1)
                    etiquetaResultado.setText("Click en esfera 1");
                else if (esferaSeleccionada == esfera2)
                    etiquetaResultado.setText("Click en esfera 2");

 */
                // Muestra un mensaje en la etiqueta indicando sobre qu� esfera se hizo click
            }
        });


        stage.setTitle("Escena 3D");  // Establece el t�tulo de la ventana
        stage.setScene(escena);  // Establece la escena que se muestra en la ventana
        stage.setWidth(800);  // Establece ancho inicial de la ventana
        stage.setHeight(600);  // Establece alto inicial de la ventana
        stage.show();
    }  // Fin del m�todo start

    private void readXML() {

        Document archivo_xml = null;
        DocumentBuilderFactory dbf;
        DocumentBuilder db;
        dbf = DocumentBuilderFactory.newInstance();

        try {
            db = dbf.newDocumentBuilder();
            archivo_xml = db.parse("./src/datos.xml");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }

        Element nodoRaiz = archivo_xml.getDocumentElement();
        NodeList listaGrupoEstanterias = nodoRaiz.getElementsByTagName("estanterias");
        int nGrupoEstanterias =  listaGrupoEstanterias.getLength();


        for (int i = 0; i < nGrupoEstanterias; i++){
            Element estanterias = (Element) listaGrupoEstanterias.item(i);

            NodeList listaEstanterias = estanterias.getElementsByTagName("estanteria");
            int nEstanterias = listaEstanterias.getLength();
            for(int j = 0 ; j < nEstanterias;j++){
                Element estanteria = (Element) listaEstanterias.item(j);
                NodeList listaBaldas = estanteria.getElementsByTagName("balda");
                int nBaldas = listaBaldas.getLength();
                for (int k = 0; k < nBaldas;k++ ){
                    Element balda = (Element) listaBaldas.item(k);
                    int alturaBalda = Integer.parseInt(balda.getAttribute("altura"));
                    NodeList listaPalets = balda.getElementsByTagName("palet");

                    int nPalets = listaPalets.getLength();
                    for(int l = 0; l < nPalets;l++ ){

                        Element palet = (Element) listaPalets.item(l);

                        double ancho = Double.parseDouble(palet.getAttribute("ancho"));
                        double alto = Double.parseDouble(palet.getAttribute("alto"));
                        double largo = Double.parseDouble(palet.getAttribute("largo"));
                        String idProducto = palet.getAttribute("idProducto");
                        int nEstanteria = j;

                        int altura = alturaBalda;
                        int posicion = Integer.parseInt(palet.getAttribute("posicion"));
                        int idPalet = Integer.parseInt(palet.getAttribute("idPalet"));
                        int cantidad = Integer.parseInt(palet.getAttribute("cantidadProducto"));
                        boolean delante = Boolean.parseBoolean(palet.getAttribute("delante"));
                        Palets.add(new Palet(ancho,alto,largo,"",idProducto,nEstanteria,altura,posicion,idPalet,cantidad,null,delante,null));
                    }
                }
            }
        }
    }
    private void crearBaldas(int altura){
        double anchoBalda = 3.3;
        int anchoPasillo = 2;


        ArrayList<Box> children = new ArrayList<Box>();
        Box balda1=new Box(3.3,0.1,36.3);
        Box balda2=new Box(3.3,0.1,36.3);
        Box balda3=new Box(3.3,0.1,36.3);
        Box balda4=new Box(3.3,0.1,36.3);
        balda1.getTransforms().addAll(new Translate(0,altura*2,0));
        balda2.getTransforms().addAll(new Translate(anchoBalda+anchoPasillo,altura*2,0));
        balda3.getTransforms().addAll(new Translate(anchoBalda*2+anchoPasillo,altura*2,0));
        balda4.getTransforms().addAll(new Translate(anchoBalda*3+anchoPasillo*2,altura*2,0));

        children.add(balda1);
        children.add(balda2);
        children.add(balda3);
        children.add(balda4);
        balda1.setMaterial(new PhongMaterial(Color.color(1,1,0,1)));
        balda2.setMaterial(new PhongMaterial(Color.color(1,1,0,1)));
        balda3.setMaterial(new PhongMaterial(Color.color(1,1,0,1)));
        balda4.setMaterial(new PhongMaterial(Color.color(1,1,0,1)));

        grupoEstanterias[0].getChildren().addAll(balda1);
        grupoEstanterias[1].getChildren().addAll(balda2);
        grupoEstanterias[2].getChildren().addAll(balda3);
        grupoEstanterias[3].getChildren().addAll(balda4);
    }

    private void crearPalets(Group grupo){
        double anchoBalda = 3.3;
        int anchoPasillo = 2;
        double despEstanteria = 0;
        for (Palet p: Palets) {

            switch(p.nEsteria){
                case 0:
                    despEstanteria = 0;
                    break;
                case 1:
                    despEstanteria = anchoBalda+anchoPasillo;
                    break;
                case 2: despEstanteria = anchoBalda*2+anchoPasillo;
                break;
                case 3: despEstanteria = anchoBalda*3+anchoPasillo*2;
                break;

            }
            for(int i = 0 ; i < 24;i++){
                createPalet(despEstanteria,grupo,i,p.altura-1);
            }
        }
        grupo.getTransforms().add(new Translate(0,0,-17.4));
    }

    private void createPalet(double despEstanteria,Group grupo ,int i,int altura){
        Box aux = new Box(1.2,0.2,1.2);
        if(altura == 0)
            aux.getTransforms().addAll(new Translate(despEstanteria,0.1,1.5*i));
        else
            aux.getTransforms().addAll(new Translate(despEstanteria,(0.1+2)*altura,1.5*i));

        aux.setMaterial(new PhongMaterial(Color.color(1,0,1,1)));
        grupo.getChildren().add(aux);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
