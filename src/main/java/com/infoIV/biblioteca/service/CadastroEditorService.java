 package com.infoIV.biblioteca.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.infoIV.biblioteca.model.Editor;
import com.infoIV.biblioteca.repository.Editors;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class CadastroEditorService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Editors editors;
	
	@Transactional
	public Editor salvar(Editor editor) {
		Editor editorExistente = editors.porNome(editor.getDescri());
		
	if (editorExistente != null && !editorExistente.equals(editor)){
			throw new NegocioException("Ya existe um editor con nombre informado.");
		}
		
		
		return editors.guardar(editor);
	}

	
}
