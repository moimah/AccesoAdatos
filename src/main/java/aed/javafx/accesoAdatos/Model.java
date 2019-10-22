package aed.javafx.accesoAdatos;

import java.io.File;
import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {
	
	private StringProperty sp_RutaText = new SimpleStringProperty();
	private StringProperty sp_FicheroText = new SimpleStringProperty();
	private StringProperty sp_listadoText = new SimpleStringProperty();
	private StringProperty sp_contenidoText = new SimpleStringProperty();
	private ListProperty<String>  nombres = new SimpleListProperty<String>(FXCollections.observableArrayList());
	private StringProperty seleccionado = new SimpleStringProperty();

	
	public final StringProperty sp_RutaTextProperty() {
		return this.sp_RutaText;
	}
	
	public final String getSp_RutaText() {
		return this.sp_RutaTextProperty().get();
	}
	
	public final void setSp_RutaText(final String sp_RutaText) {
		this.sp_RutaTextProperty().set(sp_RutaText);
	}
	
	public final StringProperty sp_FicheroTextProperty() {
		return this.sp_FicheroText;
	}
	
	public final String getSp_FicheroText() {
		return this.sp_FicheroTextProperty().get();
	}
	
	public final void setSp_FicheroText(final String sp_FicheroText) {
		this.sp_FicheroTextProperty().set(sp_FicheroText);
	}
	
	public final StringProperty sp_listadoTextProperty() {
		return this.sp_listadoText;
	}
	
	public final String getSp_listadoText() {
		return this.sp_listadoTextProperty().get();
	}
	
	public final void setSp_listadoText(final String sp_listadoText) {
		this.sp_listadoTextProperty().set(sp_listadoText);
	}

	public final StringProperty sp_contenidoTextProperty() {
		return this.sp_contenidoText;
	}
	

	public final String getSp_contenidoText() {
		return this.sp_contenidoTextProperty().get();
	}
	

	public final void setSp_contenidoText(final String sp_contenidoText) {
		this.sp_contenidoTextProperty().set(sp_contenidoText);
	}

	public final ListProperty<String> nombresProperty() {
		return this.nombres;
	}
	

	public final ObservableList<String> getNombres() {
		return this.nombresProperty().get();
	}
	

	public final void setNombres(final ObservableList<String> nombres) {
		this.nombresProperty().set(nombres);
	}
	

	public final StringProperty seleccionadoProperty() {
		return this.seleccionado;
	}
	

	public final String getSeleccionado() {
		return this.seleccionadoProperty().get();
	}
	

	public final void setSeleccionado(final String seleccionado) {
		this.seleccionadoProperty().set(seleccionado);
	}
	

	
	

}
