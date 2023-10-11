package com.infoIV.biblioteca.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.repository.Prestamos;
import com.infoIV.biblioteca.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Prestamo.class)
public class PrestamoConverter implements Converter {

	//@Inject
	private Prestamos prestamos;

	
	public PrestamoConverter() {
		prestamos = CDIServiceLocator.getBean(Prestamos.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Prestamo retorno = null;
		
		if (value != null) {
			Long numero = new Long(value);
			retorno = prestamos.porId(numero);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Prestamo prestamo = (Prestamo) value;
			return prestamo.getNumero() == null ? null : prestamo.getNumero().toString();
		}
		
		return "";
	}

}
