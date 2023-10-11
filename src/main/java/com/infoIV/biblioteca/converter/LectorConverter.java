package com.infoIV.biblioteca.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.infoIV.biblioteca.model.Lector;
import com.infoIV.biblioteca.repository.Lectors;
import com.infoIV.biblioteca.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Lector.class)
public class LectorConverter implements Converter {

	//@Inject
	private Lectors lectors;
	
	public LectorConverter() {
		lectors = CDIServiceLocator.getBean(Lectors.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Lector retorno = null;
		
		if (value != null) {
			Long codigo = new Long(value);
			retorno = lectors.porId(codigo);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Lector lector = (Lector) value;
			return lector.getCodigo() == null ? null : lector.getCodigo().toString();
		}
		
		return "";
	}

}
