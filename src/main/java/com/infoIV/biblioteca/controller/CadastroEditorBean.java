package com.infoIV.biblioteca.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.infoIV.biblioteca.model.Editor;
import com.infoIV.biblioteca.repository.Editors;
import com.infoIV.biblioteca.repository.filter.EditorFilter;
import com.infoIV.biblioteca.service.CadastroEditorService;
import com.infoIV.biblioteca.util.jsf.FacesUtil;
@ManagedBean
@Named
@ViewScoped
public class CadastroEditorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	

	@Inject
	private CadastroEditorService cadastroEditorService;

	private Editor editor;
	
	@Inject
	private Editors editors;
	
	private Editor editorSelecionado;
	

	private EditorFilter filtro;
	

	private List<Editor> editorsFiltrados;
		

	public CadastroEditorBean() {
		limpar();
	}
	
	
	
	public void setEditorSelecionado(Editor editorSelecionado) {
		this.editorSelecionado = editorSelecionado;
	}


	
	public void setFiltro(EditorFilter filtro) {
		this.filtro = filtro;
	}

	public EditorFilter getFiltro() {
		return filtro;
	}

	public Editor getEditorSelecionado() {
		return editorSelecionado;
	}

	public Editors getEditors() {
		return editors;
	}

	public void setEditors(Editors editors) {
		this.editors = editors;
	}

	public List<Editor> getEditorsFiltrados() {
		return editorsFiltrados;
	}


	public void inicializar() {
		/* System.out.println("Inicializando..."); */

		if (FacesUtil.isNotPostback()) {
			
		}
	}

	

	private void limpar() {		
		editor = new Editor();
		filtro = new EditorFilter();
	}

	public void salvar() {
		this.editor = (Editor) cadastroEditorService.salvar(this.editor);
		limpar();
		FacesUtil.addInfoMessage("Editorial se agregó correctamente!");
	}

	public Editor getEditor() {
		return editor;
	}

	public void setEditore(Editor editor) {
		this.editor = editor;
		
	}

	
	
	public boolean isEditando() {
		return this.editor.getCodigo() != null;
	}

	public void setEditor(Editor editor) {
		this.editor = editor;
		
	}
	
	

    public void excluir() {
		editors.remover(editorSelecionado);
		editorsFiltrados.remove(editorSelecionado);    		
		
		FacesUtil.addInfoMessage("Editor " + editorSelecionado.getCodigo()
				+ " eliminado con éxito.");
	}	
	

	public void pesquisar() {
		
		if (filtro.getDescri().isEmpty()) {			
			editorsFiltrados = editors.lista();
		}else {
			editorsFiltrados = editors.filtrados(filtro);		
		}

	}
	
	public int getCantidad() {
		int i = 0;
		if (editorsFiltrados != null) {
			i = editorsFiltrados.size();
		}
		return i;
	}
	
	

}