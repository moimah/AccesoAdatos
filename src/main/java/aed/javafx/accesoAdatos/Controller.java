package aed.javafx.accesoAdatos;


import java.io.BufferedReader;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Optional;

public class Controller {

	private Model model = new Model();
	private View view = new View();

	// Aqui hacer bindeos y manejo de eventos
	public Controller() {

		// Bindings

		model.sp_RutaTextProperty().bind(view.getTxt_RutaActual().textProperty());
		model.sp_FicheroTextProperty().bind(view.getTxt_fichero().textProperty());
		view.getLv_Listado().itemsProperty().bindBidirectional(model.nombresProperty());
		
		model.seleccionadoProperty().bind(view.getLv_Listado().getSelectionModel().selectedItemProperty());
		model.sp_contenidoTextProperty().bindBidirectional(view.getTxt_Contenido().textProperty());
		
		 
		// To do (Bindeo al radioButton)

		// Listeners
		view.getTxt_RutaActual().setOnMouseClicked(e -> onPulsarRutaActionEvent(e)); // Al pulsar el texto de ruta
		view.getBtn_Crear().setOnAction(e -> onCrearActionEvent(e)); // Al pulsar el botón crear
		view.getBtn_Eliminar().setOnAction(e -> onEliminarActionEvent(e));
		view.getBtn_Mover().setOnAction(e -> onMoverActionEvent(e));
		view.getBtn_VerFicherosCarpetas().setOnAction(e -> onVerFicherosCarpetas(e));
		view.getBtn_VerContenido().setOnAction(e -> onVerContenido(e));
		view.getBtn_ModificarContenido().setOnAction(e->onModificarContenido(e));
		view.getBtn_copiar().setOnAction(e -> onCopiar(e));
		
		model.getNombres().addListener(new ListChangeListener<String>() {
			public void onChanged(Change<? extends String> c) {
				onNombresChanged(c);
			}
		});
		
	}

		

	// Captura el evento de click de ratón sobre la ruta actual
	// Completa el campo automaticamente con la ruta seleccionada

	private void onPulsarRutaActionEvent(MouseEvent e) {

		String ruta = buscarDirectorio();
		view.getTxt_RutaActual().textProperty().set(ruta);

	}

	private void onCrearActionEvent(ActionEvent e) {

		// Obtener el radio seleccionado en un toogle group///Reutilizame que llevo
		// horas descubrirlo

		RadioButton selectedRadioButton = (RadioButton) view.getGroup().getSelectedToggle();
		String toogleGroupValue = selectedRadioButton.getText();

		if (toogleGroupValue.equals("Es carpeta")) {

			crearCarpeta();

		} else if (toogleGroupValue.equals("Es fichero")) {
			crearFichero();

		}

	}
	
	private void onEliminarActionEvent(ActionEvent e) {
		
		// Obtener el radio seleccionado en un toogle group ///Reutilizame que llevo
				// horas descubrirlo

		
			eliminarFichero();
		
		
	}
	
private void onMoverActionEvent(ActionEvent e) {
		
	
		MoverFichero();
		
		
	}

private void onCopiar(ActionEvent e) {
	copiarArchivo();
	
	
	
}

private void onVerFicherosCarpetas(ActionEvent e) {
	
	verFichero();
	
}

private void onVerContenido(ActionEvent e) {
	
	mostrarContenido();
	
}

private void onModificarContenido(ActionEvent e) {
	
	modificarContenido();
}

private void onNombresChanged(Change<? extends String> c) {
	
	System.out.println("Se ha detectdo un cambio");
	while (c.next()) {
		
		for (String nuevo : c.getAddedSubList()) {
			System.out.println("se ha añadido " + nuevo);
		}

		for (String quitado : c.getRemoved()) {
			System.out.println("se ha quitado " + quitado);
		}
		
	}
}



/**
* Metodo que permite crear carpeta
* a partir de un string ruta "obtenido de" txt_rutaActual y txt_fichero
* Comprueba que la ruta tenga un tamaño aceptable por windows
* Si no existe crea carpeta
*/

	public void crearCarpeta() {

		//String con la ruta de la nueva carpeta
		
		String ruta = model.getSp_RutaText().concat("\\").concat(model.getSp_FicheroText()); 
		
		 // Si la ruta es mayor a 2 (minimo Windows)
		
				
			
		File carpeta = new File(ruta); // Crar objeto File
		
		//Si no existe la carpeta crear directorio y enviar mensaje
		
		if (!carpeta.exists() && ruta.length()>2) {	// Comprobamos que exista el fichero y que la ruta sea absoluta	
			carpeta.mkdir();
			System.out.println("Se creado la carpeta");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Acceso a datos");
			alert.setHeaderText("Enhorabuena");
			alert.setContentText("Se ha creado la carpeta");
			alert.showAndWait();
		
		} else { //Si no mensaje de que existe			
			System.out.println("No se ha podido crear la carpeta");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Acceso a datos");
			alert.setHeaderText("Ups, ha ocurrido un problema");
			alert.setContentText("No se ha podido crear la carpeta");
			alert.showAndWait();
		}

		
	}
	
	/**
	* Metodo que permite crear fichero
	* a partir de un string ruta "obtenido de" txt_rutaActual y txt_fichero
	* Comprueba que la ruta tenga un tamaño aceptable por windows
	* Si no existe crea fichero
	*/
	
	public void crearFichero() {

		//String con la ruta de nuevo fichero
		
		String ruta = model.getSp_RutaText().concat("\\").concat(model.getSp_FicheroText()); 
		
		//Si la ruta  es mayor a 2 (minimo windows)
		
		
		
		File fichero = new File(ruta); //Crear objeto File
		
		//Si no existe el fichero  crear nuevo y  enviar mensaje

		if (!fichero.exists() && ruta.length()>2) { // Comprobamos que exista el fichero y que la ruta sea absoluta
			
			try {
				
				fichero.createNewFile();
				System.out.println("Se creado la el fichero");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Acceso a datos");
				alert.setHeaderText("Enhorabuena");
				alert.setContentText("Se ha creado el fichero");
				alert.showAndWait();
				
			} catch (IOException e) {		// Si no mensaje de que el fichero no se ha podido crear
				
				System.out.println("No se ha podido crear el fichero");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Acceso a datos");
				alert.setHeaderText("Ups, ha ocurrido un problema");
				alert.setContentText("No se ha podido crear el fichero");
				alert.showAndWait();
			}
			
			
		} else { // Si no mensaje de que el fichero ya existe
			
			System.out.println("No se ha podido crear el fichero");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Acceso a datos");
			alert.setHeaderText("Ups, ha ocurrido un problema");
			alert.setContentText("No se ha podido crear el fichero");
			alert.showAndWait();
		}

		
	}
	
	/**
	* Metodo que permite crear fichero
	* a partir de un string ruta "obtenido de" txt_rutaActual y txt_fichero
	* Comprueba que exista el fichero y lo elimina	
	*/
	
	public void eliminarFichero() {

		// String con la ruta de nuevo fichero

		String ruta = model.getSp_RutaText().concat("\\").concat(model.getSp_FicheroText());

		if(model.getSp_RutaText().length()<1 || model.getSp_FicheroText().length()<1) {
			
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Acceso a datos");
			alerta.setHeaderText("Ups, ha ocurrido un problema");
			//alerta.setContentText("No se ha movido");
			alerta.setContentText("Completa ambos campos");
			alerta.showAndWait();
						
			}else { //Si no ejecutar codigo	

			File fileDel = new File(ruta); // Archivo file con la ruta

			if (fileDel.exists() && ruta.length() > 2) { // Comprobamos que exista el fichero y que la ruta sea absoluta

				Alert alerta = new Alert(AlertType.CONFIRMATION);
				alerta.setTitle("Acceso a datos");
				alerta.setHeaderText("BORRAR FICHERO");
				alerta.setContentText("Esta seguro de borrar: " + ruta);
				alerta.showAndWait();		
				
				Optional<ButtonType> result = alerta.showAndWait(); //Almacena el resultado de un boton
				

				// Si se ha borrado, mostrar mensaje
				
				if(result.get() == ButtonType.OK) {
					if (borrarFileRecursivamente(ruta)) {

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Acceso a datos");
						alert.setHeaderText("Enhorabuena");
						alert.setContentText("Se ha eliminado");
						alert.showAndWait();
					} else { // Si no mostrar mensaje de problema

						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Acceso a datos");
						alert.setHeaderText("Ups, ha ocurrido un problema");
						alert.setContentText("No existe el archivo");
						alert.showAndWait();
					}
				}
				
			} else { // Si no mostrar mensaje de que no se ha borrado

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Acceso a datos");
				alert.setHeaderText("Ups, ha ocurrido un problema");
				alert.setContentText("No se ha borrado");
				alert.showAndWait();
			}

		}
	}
		
	
	/**
	* Metodo que permite mover ficheros
	* a partir de un string origen "obtenido de" txt_rutaActual 
	* y string destino obtenido de  txt_fichero
	* se apoya del metodo moverFile	
	*/

	public void MoverFichero() {

		// Strings que almacenan las rutas de origen y destino

		
		if(model.getSp_RutaText().length()<1 || model.getSp_FicheroText().length()<1) {
		
		Alert alerta = new Alert(AlertType.ERROR);
		alerta.setTitle("Acceso a datos");
		alerta.setHeaderText("Ups, ha ocurrido un problema");
		//alerta.setContentText("No se ha movido");
		alerta.setContentText("Completa ambos campos");
		alerta.showAndWait();
		}else { //Si no ejecutar codigo		
		
		String origen = model.getSp_RutaText();
		String destino = model.getSp_FicheroText();

		if (origen.length() > 2 && destino.length() > 2) { // Si el origen y el destino son mayores a 2 (minimo Windows)
			
			if (moverFile(origen, destino)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Acceso a datos");
				alert.setHeaderText("Enhorabuena");
				alert.setContentText("Se ha movido el fichero");
				alert.showAndWait();

			} else {// Llamar método mover

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Acceso a datos");
				alert.setHeaderText("Ups, ha ocurrido un problema");
				alert.setContentText("No se ha movido");
				alert.showAndWait();
			}
		}
		
		}
	}
	
	
    /**
     * Metodo que ejecuta el borrado y envia avisos 
     *  de si se ha borrado o no, se apoya del método
     *  copiadoRecursivo para realizar la operación	     
     */
  
    
    private void copiarArchivo() {
   
    	String path_a = model.getSp_RutaText();
    	File origen = new File(path_a);
    	
    	String path_b = model.getSp_FicheroText();
    	String path_destino = path_b.concat("\\").concat(origen.getName());
    	File destino = new File(path_destino);
    	
    	if(origen.exists() && !destino.exists()) {
    		if(copiadoRecursivo(origen, destino)) {
    			Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Acceso a datos");
				alert.setHeaderText("Enhorabuena");
				alert.setContentText("Se ha copiado el fichero");
				alert.showAndWait();    			
    		}else {
    			Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Acceso a datos");
				alert.setHeaderText("Ups, ha ocurrido un problema");
				alert.setContentText("No se ha copiado");
				alert.showAndWait();
    		}
    		}else {
    		
        			Alert alert = new Alert(AlertType.ERROR);
    				alert.setTitle("Acceso a datos");
    				alert.setHeaderText("Ups, ha ocurrido un problema");
    				alert.setContentText("No se ha copiado");
    				alert.showAndWait();
    		}
    	
    	
    
    }
    
	
	/**
	* Metodo que permite ver ficheros en un listView
	* a partir de un string ruta  obtenido de txt_ruta actual y  txt_fichero
	* se apoya del metodo leerFicherosYcarpetas
	*/
	
	public void verFichero() {
		
		//String que contiene la ruta 
		
		String ruta = model.getSp_RutaText().concat("\\"); 	
		
		if(ruta.length()>2) { //Si la ruta es mayor a 2 (Minimo windows) 	
			
		listarFicherosYcarpetas(ruta); //Llamar al método listarFicherosYCarpetas
		}
		
	}
	
	
	/**
	* Metodo que permite  mostrar el contenido de un elemento de listview 
	* solo si tiene extensión ."extension"
	* a partir de un string ruta  obtenido de txt_ruta actual y  txt_fichero
	* se apoya del metodo leerFichero y almacena en un Strin el resultado de lectura
	*/
	
	
	public void  mostrarContenido() {
		
		//Creamos un path concatenando contenido de ruta con fichero
		
				
		String ruta = model.getSp_RutaText();
		
		//Obtenemos el elemento seleccionado de la lista
		
		String seleccionado = model.seleccionadoProperty().get();
		
				
		 //Si se ha seleccionado un archivo y el archivo no es carpeta contiene "." - tiene extensión
		
		if(seleccionado!=null && seleccionado.contains(".")) {
			
			//Concatenamos el nombre del atchivo seleccionado a la ruta
			
			ruta = ruta.concat("\\" + seleccionado);
			
			String lectura =  leerFichero(ruta); //Almacenamos en string la lectura llamando al método leer fichero
			
			model.setSp_contenidoText(lectura);	//Mostramos en el txtContenido la lectura recibida 	
			
		}else { //Mostramos mensaje de error
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Acceso a datos");
			alert.setHeaderText("Ups, ha ocurrido un problema");
			alert.setContentText("Asegurate, que has seleccionado un archivo con extensión");
			alert.showAndWait();			
		}
		

		
	}
	
	
	/**
	* Metodo que permite  modificar el contenido de un elemento de listview 
	* solo si tiene extensión ."extension"
	* a partir de un string ruta  obtenido de txt_ruta actual y  txt_fichero
	* se apoya del metodo escribir contenido fichero que se encarga de sobreescribir
	*/
	
	
	private void modificarContenido() {

		// Creamos un path concatenando contenido de ruta con fichero

		String ruta = model.getSp_RutaText();

		// Obtenemos el elemento seleccionado de la lista

		String seleccionado = model.seleccionadoProperty().get();

		// Si se ha seleccionado un archivo y el archivo no es carpeta contiene "." -tiene extensión

		 
		if (seleccionado != null && seleccionado.contains(".")) {

			// ruta = ruta.concat(seleccionado);
			
			ruta = ruta.concat("\\" + seleccionado);

			// Si se ha escrito correctamente enviar mensaje

			if (escribirContenidoFichero(ruta)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Acceso a datos");
				alert.setHeaderText("Enhorabuena");
				alert.setContentText("Se ha modificado el archivo");
				alert.showAndWait();
			} else { // Si no enviar mensaje de error

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Acceso a datos");
				alert.setHeaderText("Ups, ha ocurrido un problema");
				alert.setContentText("No se ha podido modificar el archivo");
				alert.showAndWait();
			}

		} else { // Si no enviar mensaje de error
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Acceso a datos");
			alert.setHeaderText("Ups, ha ocurrido un problema");
			alert.setContentText("No se ha podido modificar el archivo");
			alert.showAndWait();
		}
	
	
		
	}
	
	
	
	
	/**
	 * Metodo para borrar una carpeta recursivamente Recibe por parametro path de
	 * File a eliminar Realiza borado recursivo en caso de que tenga más carpetas
	 * @param fileDel
	 * @return exito
	 */
	private boolean borrarFileRecursivamente(String path) {

		boolean exito = false;

		File fileDel = new File(path);

		System.out.println("La ruta a borrar es " + path);
		if (fileDel.isDirectory()) {

			// Si no tiene contenido borrar
			if (fileDel.list().length == 0) {
				fileDel.delete();
				exito = true;
			} else { // Si tiene contenido borrado recursivo

				for (String temp : fileDel.list()) {
					File fileDelete = new File(fileDel, temp);
					// Borardo recursivo llamando al propio metodo
					borrarFileRecursivamente(fileDelete.getPath());
					exito = true;
				}

				// Comprobar de nuevo el directorio, si esta vacio eliminar

				if (fileDel.list().length == 0)
					fileDel.delete();
				exito = true;

			}

		} else { // Si es archivo borrar

			fileDel.delete();
			exito = true;
		}

		return exito;
	}
	    
	    
	    	/**
		   * Metodo para mover carpetas y ficheros
		   * Recibe por parametro la ruta de origen y la de destino
		   * Devuelve true si se ha movido y false si no
		   * @param fromFile
		   * @param toFile
		   * @return exito 
		   */
	    
	    private boolean moverFile(String fromFile, String toFile) {
			
	    	boolean exito = false;
	    	//Se crean los Objetos File origen y destino con las rutas recibida por parametros
	    	File origen = new File(fromFile);
	    	File destino = new File(toFile);    	
	    	
	    	try {
	    	 origen.renameTo(new File(destino, origen.getName())); //Rename de encarga de mover
	    	 exito = true; 
	    	}catch (Exception e) {
				System.out.println("No se ha podido mover");
			}
	    	return exito;
	    		   
	    		         
	    }
	    
	    
	    

	/**
	 * Metodo que permite listar permite listar contenido de un directorio
	 * Recibe un String con la ruta del fichero
	 * Devuelve true si el fichero tiene contenido
	 * @param path
	 * @return exito
	 */

	private Boolean listarFicherosYcarpetas(String path) {

		Boolean exito = false; // Variable de return
		
		model.getNombres().clear();

		File directorio = new File(path); // Obj File a partir del path

		// Almacenamos en un String array los ficheros que hay dentro de la ruta
		String[] ficheros = directorio.list();

		if (ficheros != null) { // Si hay ficheros
			for (int i = 0; i < ficheros.length; i++) {
				model.getNombres().add(ficheros[i]); // Añadimos los ficheros a la lista
				System.out.println(ficheros[i]);
			}

			exito = true;
		}

		return exito;

	}
    

	    
		/**
		 * Metodo que permite leer fichero de texto y
		 * almacenar su contenido en un String
		 * Recibe un String con la ruta del fichero
		 * Devuelve String con contenido del fichero
		 * @param path
		 * @return lectura
		 */
	    
	    
	    private String leerFichero(String path) {
	    	
	    	String lectura = ""; //Variable de retorno
	    	
	    	File fich = new File(path); //Objeto file de la ruta
	    	try {
	    		//Streams
				FileReader fr = new FileReader(fich); 
				BufferedReader br = new BufferedReader(fr);
				
				String linea  = br.readLine();	//Almaceamos primera linea		
				//Almacenamos contenido en lectura incluimos salto de linea para concevar formato
				lectura = lectura + linea + "\n"; 
				while(linea!=null) { //Mientras haya contenido					
					linea = br.readLine();
					if(linea!=null) { //Si hay linea
					lectura = lectura + linea + "\n"; //Almacenamos
					}					
					
					//model.setSp_contenidoText(lectura);
				}
				//Cerrar Streams
				fr.close();
				br.close();
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	    	
	    	return lectura;
	    	
	    }
	    
	    /**
		 * Metodo escribe en fichero de texto, 		
		 * el contenido de un String
		 * Devuelve true si se ha escrito correctamente
		 * @param path
		 * @return exito
		 */   
	    
	    private boolean escribirContenidoFichero(String path) {
	    	
	    	boolean exito = false;
	    	
	    	String modificado = model.getSp_contenidoText();
	    	File fich = new File(path);
	    	
	    	try {
				FileWriter fw = new FileWriter(fich);
				fw.write(modificado);
				fw.close();
				exito = true; 				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	    	

	    	return exito; 
	    	
	    }
	    
	    /**
		 * Metodo que abre un cuadro de dialogo , 		
		 * explorador de windows
		 * Devuelve un String con el fichero seleccionado		 * 
		 * @return fichero
		 */ 
	    
		public String buscarDirectorio() {

			String fichero = "";
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selectedDirectory = directoryChooser.showDialog(null);

			if (selectedDirectory != null) {
				System.out.println("Se ha seleccionado fichero");
				fichero = selectedDirectory.getPath().toString();
				System.out.println(fichero);

			} else {
				System.out.println("No se ha seleccionado el fichero");
			}

			return fichero;

		}
		
	/**
	 * Metodo que realiza el copiado recursivo de ficheros y diectorios
	 * Recibe un parametro File de origen y otro File de destino
	 * Verifica que no exista el directorio o fichero
	 * Realizad el copiado o copiado recursivo según el caso
	 * Devuelve true si se ha copiado y false si no se ha podido
	 * @param d1
	 * @param d2
	 * @return fichero
	 */

	private boolean copiadoRecursivo(File d1, File d2) {

		boolean exito = false;

		// Parte del copiado recursivo de directorio
		if (d1.isDirectory()) { // Si el origen es un directorio
			// Recorremos recursivamente el directorio
			if (!d2.exists()) { // Y no existe
				d2.mkdir(); // Crear
				System.out.println("Creando directorio " + d2.toString());
				String[] ficheros = d1.list(); // Lista de ficheros que contiene el directorio
				for (int x = 0; x < ficheros.length; x++) { // Bucle de copiado recursivo llamando al propio metodo
					copiadoRecursivo(new File(d1, ficheros[x]), new File(d2, ficheros[x]));
				}
				exito = true;
			}
		} else {

			if (d1.exists() && !d2.exists()) { // Si existe el fichero y no existe en el origen

				try {
					// Streams de E/S

					InputStream in = new FileInputStream(d1);
					OutputStream out = new FileOutputStream(d2);

					byte[] buf = new byte[1024];
					int len;

					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}

					in.close();
					out.close();
					exito = true;

				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}

		return exito;
	}
		
		
		
		public View getRoot() {
			return view;
			
		}
		
    
   
	  



}
