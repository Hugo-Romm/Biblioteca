package com.infoIV.biblioteca.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.infoIV.biblioteca.model.Editor;
import com.infoIV.biblioteca.repository.Editors;
import com.infoIV.biblioteca.repository.filter.EditorFilter;
import com.infoIV.biblioteca.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaEditorsBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Editors editors;
	
	private EditorFilter filtro;
	private List<Editor> editorsFiltrados;
						 
	
	private Editor editorSelecionado;
	
	public PesquisaEditorsBean() {
		filtro = new EditorFilter();
	}
	
    public void excluir() {
		editors.remover(editorSelecionado);
		editorsFiltrados.remove(editorSelecionado);
		
		FacesUtil.addInfoMessage("Editor " + editorSelecionado.getCodigo()
				+ " eliminado con éxito.");
	}
	
	public void pesquisar() {
		editorsFiltrados = editors.filtrados(filtro);
	}
	
	public List<Editor> getEditorsFiltrados() {
		return editorsFiltrados;
	}

	public EditorFilter getFiltro() {
		return filtro;
	}

	public Editor getEditorSelecionado() {
		return editorSelecionado;
	}

	public void setEditorSelecionado(Editor editorSelecionado) {
		this.editorSelecionado = editorSelecionado;
	}
	

	public void posProcessarXls(Object documento) {
		HSSFWorkbook planilha = (HSSFWorkbook) documento;
		HSSFSheet folha = planilha.getSheetAt(0);
		HSSFRow cabecalho = folha.getRow(0);
		HSSFCellStyle estiloCelula = planilha.createCellStyle();
		Font fonteCabecalho = planilha.createFont();
		
		fonteCabecalho.setColor(IndexedColors.WHITE.getIndex());
		fonteCabecalho.setBold(true);
		fonteCabecalho.setFontHeightInPoints((short) 16);
		
		estiloCelula.setFont(fonteCabecalho);
		estiloCelula.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		estiloCelula.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		for (int i = 0; i < cabecalho.getPhysicalNumberOfCells(); i++) {
			cabecalho.getCell(i).setCellStyle(estiloCelula);
		}
	}
	
	
}