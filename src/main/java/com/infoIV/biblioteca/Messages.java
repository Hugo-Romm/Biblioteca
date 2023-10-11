package com.infoIV.biblioteca;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class Messages {

	public void adicionarMensagemErro() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, 
				"Resumen del mensaje de error", "Mensaje del error completo");
		
		context.addMessage("destinoErro", msg);
	}
	
	public void adicionarAvisoFlutuante() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
				"Resumen del aviso", "Aviso flutuante completo");
		
		context.addMessage("destinoAviso", msg);
	}
	
}