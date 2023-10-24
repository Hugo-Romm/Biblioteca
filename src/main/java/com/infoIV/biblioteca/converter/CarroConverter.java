package com.infoIV.biblioteca.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.infoIV.biblioteca.model.Carro;
import com.infoIV.biblioteca.repository.Carros;
import com.infoIV.biblioteca.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Carro.class)
public class CarroConverter implements Converter {

	//@Inject
	private Carros carros;
	
	public CarroConverter() {
		carros = CDIServiceLocator.getBean(Carros.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Carro retorno = null;
		
		if (value != null) {
			Long id = new Long(value);
			retorno = carros.porCodigo(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Carro carro = (Carro) value;
			return carro.getCodigo() == null ? null : carro.getCodigo().toString();
		}
		
		return "";
	}

}
