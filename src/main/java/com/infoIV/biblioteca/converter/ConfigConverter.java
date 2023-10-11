package com.infoIV.biblioteca.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.infoIV.biblioteca.model.Config;
import com.infoIV.biblioteca.repository.Configs;
import com.infoIV.biblioteca.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Config.class)
public class ConfigConverter implements Converter {

	//@Inject
	private Configs configs;
	
	public ConfigConverter() {
		configs = CDIServiceLocator.getBean(Configs.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Config retorno = null;
		
		if (value != null) {
			Long codigo = new Long(value);
			retorno = configs.porCodigo(codigo);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Config config = (Config) value;
			return config.getCodigo() == null ? null : config.getCodigo().toString();
		}
		
		return "";
	}

}
