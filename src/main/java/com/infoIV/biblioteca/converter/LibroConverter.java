package com.infoIV.biblioteca.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.infoIV.biblioteca.model.Libro;
import com.infoIV.biblioteca.repository.Libros;
import com.infoIV.biblioteca.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Libro.class)
public class LibroConverter implements Converter {

	//@Inject
	private Libros libros;
	
	public LibroConverter() {
		libros = CDIServiceLocator.getBean(Libros.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Libro retorno = null;
		
		if (value != null) {
			Long codigo = new Long(value);
			retorno = libros.porId(codigo);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Libro libro = (Libro) value;
			return libro.getCodigo() == null ? null : libro.getCodigo().toString();
		}
		
		return "";
	}

}
