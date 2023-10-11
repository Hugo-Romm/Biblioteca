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

import com.infoIV.biblioteca.model.Config;
import com.infoIV.biblioteca.repository.Configs;
import com.infoIV.biblioteca.repository.filter.ConfigFilter;

@Named
@ViewScoped
public class PesquisaConfigsBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Configs configs;
	
	private ConfigFilter filtro;
	private List<Config> configsFiltrados;
						 
	
	private Config configSelecionado;
	
	public PesquisaConfigsBean() {
		filtro = new ConfigFilter();
	}
	
/*    public void excluir() {
		configs.remover(configSelecionado);
		configsFiltrados.remove(configSelecionado);
		
		FacesUtil.addInfoMessage("Config " + configSelecionado.getCodigo()
				+ " eliminado con éxito.");
	}*/
	
	public void pesquisar() {
		configsFiltrados = configs.filtrados(filtro);
	}
	
	public List<Config> getConfigsFiltrados() {
		return configsFiltrados;
	}

	public ConfigFilter getFiltro() {
		return filtro;
	}

	public Config getConfigSelecionado() {
		return configSelecionado;
	}

	public void setConfigSelecionado(Config configSelecionado) {
		this.configSelecionado = configSelecionado;
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