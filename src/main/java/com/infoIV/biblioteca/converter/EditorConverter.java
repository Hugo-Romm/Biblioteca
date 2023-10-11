package com.infoIV.biblioteca.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.infoIV.biblioteca.model.Editor;
import com.infoIV.biblioteca.repository.Editors;
import com.infoIV.biblioteca.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Editor.class)
public class EditorConverter implements Converter {

	// @Inject
	private Editors editors;

	public EditorConverter() {
		editors = CDIServiceLocator.getBean(Editors.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Editor retorno = null;

		if (value != null) {
			Long codigo = new Long(value);
			retorno = editors.porId(codigo);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
		if (value != null) {
			Editor editor = (Editor) value;
			return editor.getCodigo() == null ? null : editor.getCodigo().toString();
		//	return ((Editor)value).getCodigo().toString();
		}

		return "";
	}

}
