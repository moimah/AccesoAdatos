package aed.javafx.accesoAdatos;


import java.util.ArrayList;
import java.util.Observable;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View extends TabPane {
	
	
	private Label lbl_NombreAlumno;
	
	private Label lbl_RutaActual;
	private TextField txt_RutaActual;
	
	private Button btn_Crear;
	private Button btn_Eliminar;
	private Button btn_Mover;	
	private Button btn_copiar;
	
	private RadioButton rd_EsCarpeta;
	private RadioButton rd_EsFichero;
	private final ToggleGroup group;
	
	private TextField txt_fichero = new TextField();
	
	private Button btn_VerFicherosCarpetas;
	
	private ListView lv_Listado;
	
	
	private Button btn_VerContenido;
	private Button btn_ModificarContenido;
	private TextArea txt_Contenido;
	
	public View() {
		super();
		

		//Hbox 0
		
		lbl_NombreAlumno = new Label("Moisés Abreu Hernández");		
		HBox hbox_0 = new HBox(20, lbl_NombreAlumno);
		hbox_0.setAlignment(Pos.CENTER);
		
		//Hbox 1
		
		lbl_RutaActual = new Label("Ruta Actual");
		lbl_RutaActual.setMinWidth(80); //A񡤩do como mejora
		
		txt_RutaActual = new TextField();
		
	
		txt_RutaActual.setPromptText("Carpeta o fichero a crear, eliminar o destino a mover");
		HBox hbox_1 = new HBox(20, lbl_RutaActual, txt_RutaActual);
		hbox_1.setHgrow(txt_RutaActual, Priority.ALWAYS);
		
		
		
		
		//Hbox 2
		
		btn_Crear = new Button("Crear");
		btn_Crear.setPrefWidth(65);
		btn_Eliminar = new Button("Eliminar");
		btn_Eliminar.setPrefWidth(65);
		btn_Mover = new Button("Mover");
		btn_Mover.setPrefWidth(65);
		btn_copiar = new Button("Copiar");
		btn_copiar.setPrefWidth(65);
		
		group = new ToggleGroup(); //Grupo para los radioButton
		rd_EsCarpeta = new RadioButton("Es carpeta");	
		rd_EsCarpeta.setToggleGroup(group);	
		rd_EsFichero = new RadioButton("Es fichero");	
		rd_EsFichero.setToggleGroup(group);
		rd_EsFichero.setSelected(true);
		
		
		
		
		
			HBox hbox_radio = new HBox(rd_EsCarpeta, rd_EsFichero);
			hbox_radio.setAlignment(Pos.BASELINE_RIGHT);
			hbox_radio.setSpacing(30);
			hbox_radio.setPadding(new Insets(10));
		HBox hbox_2 = new HBox(20, btn_Crear, btn_Eliminar, btn_Mover, btn_copiar, hbox_radio);
		hbox_2.setHgrow(hbox_radio, Priority.ALWAYS);
		hbox_2.setPadding(new Insets(10));
		
		
		
		//Hbox 3
		Label lbl_nuevo = new Label("Archivo"); //A񡤩do para mejorar interfaz
		lbl_nuevo.setMinWidth(80);
		
		
		txt_fichero = new TextField();
		txt_fichero.setPromptText("Carpeta o fichero a: crear, eliminar o destino a mover");
		HBox hbox_3 = new HBox(20, lbl_nuevo, txt_fichero); // Se a񡤥 label como mejora
		hbox_3.setHgrow(txt_fichero, Priority.ALWAYS);
		
		//Hbox 4
		
		btn_VerFicherosCarpetas =  new Button("Ver ficheros y carpetas");
		btn_VerFicherosCarpetas.setPrefWidth(160);
		HBox hbox_4 = new HBox(20, btn_VerFicherosCarpetas);
		hbox_4.setPadding(new Insets(10));
		
		//Hbox 5
		lv_Listado = new ListView();
		HBox hbox_5 = new HBox(20, lv_Listado);
		HBox.setHgrow(lv_Listado, Priority.ALWAYS);
		
		//Hbox 6
		
		btn_VerContenido = new Button("Ver contenido");
		btn_VerContenido.setPrefWidth(160);
		btn_ModificarContenido = new Button("Modificar contenido");
		btn_ModificarContenido.setPrefWidth(160);
		VBox vbox_1 = new VBox(btn_VerContenido, btn_ModificarContenido);
		vbox_1.setSpacing(30);
		vbox_1.setPadding(new Insets(10));
		
		txt_Contenido = new TextArea();
		HBox hbox_6 = new HBox(20, vbox_1, txt_Contenido);
		hbox_6.setHgrow(txt_Contenido, Priority.ALWAYS);
		
		
		GridPane gridpane = new GridPane();
		gridpane.setPadding(new Insets(15)); //Separacion de los bordes de la ventana
		gridpane.setHgap(18);
		gridpane.setVgap(18);
		//gridpane.setGridLinesVisible(true);
		
		gridpane.addRow(0, hbox_0);
		gridpane.addRow(1, hbox_1);
		gridpane.addRow(2, hbox_3);//Intercambio el orden porque queda mejor
		gridpane.addRow(3, hbox_2);// Intercambiada
		gridpane.addRow(4, hbox_4);
		gridpane.addRow(5, hbox_5);
		gridpane.addRow(6, hbox_6);
		
		ColumnConstraints []cols = {
				new ColumnConstraints()
			
		};
		
		gridpane.getColumnConstraints().setAll(cols);
		
		
		cols[0].setHgrow(Priority.ALWAYS);
		
		
		//Creamos las tabs y las agregamos al tabpane

			
		Tab tab_AccesoFicheros = new Tab("Acceso a ficheros");		
		tab_AccesoFicheros.setContent(gridpane);
		
		Tab tab_AccesoAleatorio = new Tab("Acceso aleatorio");		
		Tab tab_AccesoXML = new Tab("Acceso XML");
		
		this.getTabs().add(tab_AccesoFicheros);
		this.getTabs().add(tab_AccesoAleatorio);
		this.getTabs().add(tab_AccesoXML);
		
		
		
		
		
		
			
		
//		primaryStage.setScene(scene);
//		primaryStage.setTitle("Acceso a datos");
//		primaryStage.show();
		
		
	}
	

	


	public Label getLbl_NombreAlumno() {
		return lbl_NombreAlumno;
	}



	public Label getLbl_RutaActual() {
		return lbl_RutaActual;
	}



	public TextField getTxt_RutaActual() {
		return txt_RutaActual;
	}



	public Button getBtn_Crear() {
		return btn_Crear;
	}



	public Button getBtn_Eliminar() {
		return btn_Eliminar;
	}



	public Button getBtn_Mover() {
		return btn_Mover;
	}



	public Button getBtn_copiar() {
		return btn_copiar;
	}





	public RadioButton getRd_EsCarpeta() {
		return rd_EsCarpeta;
	}



	public RadioButton getRd_EsFichero() {
		return rd_EsFichero;
	}



	public TextField getTxt_fichero() {
		return txt_fichero;
	}
	
	


	public ToggleGroup getGroup() {
		return group;
	}





	public Button getBtn_VerFicherosCarpetas() {
		return btn_VerFicherosCarpetas;
	}



	public ListView getLv_Listado() {
		return lv_Listado;
	}



	public Button getBtn_VerContenido() {
		return btn_VerContenido;
	}



	public Button getBtn_ModificarContenido() {
		return btn_ModificarContenido;
	}



	public TextArea getTxt_Contenido() {
		return txt_Contenido;
	}





	public void setLv_Listado(ObservableList obList) {
		
		
		this.lv_Listado.setItems(obList);
		
		
		
		
	}
	


	

}
