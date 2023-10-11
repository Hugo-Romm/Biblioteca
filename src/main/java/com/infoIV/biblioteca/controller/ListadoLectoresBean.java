package com.infoIV.biblioteca.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.hibernate.Session;

import com.infoIV.biblioteca.model.Lector;
import com.infoIV.biblioteca.repository.Lectors;
import com.infoIV.biblioteca.repository.filter.LectorFilter;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jsf.FacesUtil;
import com.infoIV.biblioteca.util.report.ExecutorRelatorio;

@Named
@ViewScoped
public class ListadoLectoresBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Lectors lectores ;
	@Inject
	private Lector lector;
	
	private LectorFilter filtro;
	private List<Lector> lectoresFiltrados;
	

	private Long codigoInicio;
	private Long codigoFin;
	
	private String descriInicio;
	private String descriFin;
	
	private Integer console;
	
	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;
/***********************   INFORME DETALLADO Editoriales.PDF   ****************************/
	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("codigo_inicio", this.codigoInicio);
		parametros.put("codigo_fin", this.codigoFin);
		parametros.put("descri_inicio", this.descriInicio);
		parametros.put("descri_fin", this.descriFin);
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/relatorio_pedidos_emitidos.jasper",
				this.response, parametros, "Lectores registradas.pdf");
		
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("El rango no tiene datos");
		}
	}
	/******************************************************************/
	
	public ListadoLectoresBean() {
		filtro = new LectorFilter();
	}
	
	/*****************   PARA LISTADO DE EDITORIALES ********************/
	public void pesquisar() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("codigo_inicio", this.codigoInicio);
		parametros.put("codigo_fin", this.codigoFin);
		parametros.put("descri_inicio", this.descriInicio);
		parametros.put("descri_fin", this.descriFin);
				
		codigoInicio = this.codigoInicio;
		codigoFin = this.codigoFin;
		descriInicio = this.descriInicio;
		descriFin = this.descriFin;
		console = this.console;
		
		lectoresFiltrados = lectores.filtradosReportesLectorTeste2(lector, codigoInicio, codigoFin, descriInicio, descriFin,console);
		System.out.println("paso por aca");
		if (this.lectoresFiltrados.isEmpty() ){
		       System.out.println("Si está vacio");
		       throw new NegocioException("No hay datos en este intervalo");  
		      
		}else {
			System.out.println("entra en else");
			getLectoresFiltrados();
		}
		
	}
	
	public List<Lector> getLectoresFiltrados() {
		return lectoresFiltrados;
	}
	
	public LectorFilter getFiltro() {
		return filtro;
	}
/**************   SETTERS AND GETTERS   *************************/
	public Long getCodigoInicio() {
		return codigoInicio;
	}
	public void setCodigoInicio(Long codigoInicio) {
		this.codigoInicio = codigoInicio;
	}
	public Long getCodigoFin() {
		return codigoFin;
	}
	public void setCodigoFin(Long codigoFin) {
		this.codigoFin = codigoFin;
	}
	public String getDescriInicio() {
		return descriInicio;
	}
	public void setDescriInicio(String descriInicio) {
		this.descriInicio = descriInicio;
	}
	public String getDescriFin() {
		return descriFin;
	}
	public void setDescriFin(String descriFin) {
		this.descriFin = descriFin;
	}
	
	public Integer getConsole() {
		return console;
	}

	public void setConsole(Integer console) {
		this.console = console;
	}

	/*******************************************************/
	public void posProcessarXls(Object documento) {
		HSSFWorkbook planilha = (HSSFWorkbook) documento;
		HSSFSheet folha = planilha.getSheetAt(0);
		HSSFRow cabecalho = folha.getRow(0);
		HSSFCellStyle estiloCelula = planilha.createCellStyle();
		HSSFFont fonteCabecalho = planilha.createFont();
		
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
	
	
