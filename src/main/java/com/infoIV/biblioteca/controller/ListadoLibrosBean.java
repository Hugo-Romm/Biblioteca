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

import com.infoIV.biblioteca.model.Libro;
import com.infoIV.biblioteca.model.Editor;
import com.infoIV.biblioteca.repository.Libros;
import com.infoIV.biblioteca.repository.filter.LibroFilter;
import com.infoIV.biblioteca.repository.filter.PrestamoFilter;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jsf.FacesUtil;
import com.infoIV.biblioteca.util.report.ExecutorRelatorio;

@Named
@ViewScoped
public class ListadoLibrosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Libros libros;
	@Inject
	private Libro libro;

	private LibroFilter filtro;
	private List<Libro> librosFiltrados;

	private Long codigoInicio;
	private Long codigoFin;

	private String descriInicio;
	private String descriFin;

	private String editoInicio;
	private String editoFin;

	private Integer console;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	/*********************** INFORME DETALLADO LIBROS.PDF ****************************/
	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("codigo_inicio", this.codigoInicio);
		parametros.put("codigo_fin", this.codigoFin);
		parametros.put("descri_inicio", this.descriInicio);
		parametros.put("descri_fin", this.descriFin);
		parametros.put("edito_inicio", this.editoInicio);
		parametros.put("edito_fin", this.editoFin);
		ExecutorRelatorio executor = new ExecutorRelatorio(
				"/relatorios/relatorio_pedidos_emitidos.jasper", this.response,
				parametros, "Prestamos emitidos.pdf");

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("El rango no tiene datos");
		}
	}

	/******************************************************************/

	public ListadoLibrosBean() {
		filtro = new LibroFilter();
	}

	/***************** PARA LISTADO DE LIBROS ********************/
	public void pesquisar() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("codigo_inicio", this.codigoInicio);
		parametros.put("codigo_fin", this.codigoFin);
		parametros.put("descri_inicio", this.descriInicio);
		parametros.put("descri_fin", this.descriFin);
		parametros.put("edito_inicio", this.editoInicio);
		parametros.put("edito_fin", this.editoFin);

		codigoInicio = this.codigoInicio;
		codigoFin = this.codigoFin;
		descriInicio = this.descriInicio;
		descriFin = this.descriFin;
		editoInicio = this.editoInicio;
		editoFin = this.editoFin;
		console = this.console;
		System.out.println("console desde bean : "+ console);
		librosFiltrados = libros.filtradosReportesLibroTeste2(libro,
				codigoInicio, codigoFin, descriInicio, descriFin, editoInicio,
				editoFin,console);
		System.out.println("paso por aca");
		if (this.librosFiltrados.isEmpty()) {
			System.out.println("Si está vacio");
			throw new NegocioException("No hay datos en este intervalo");

		} else {
			System.out.println("entra en else");
			getLibrosFiltrados();
		}

	}

	public List<Libro> getLibrosFiltrados() {
		return librosFiltrados;
	}

	public LibroFilter getFiltro() {
		return filtro;
	}

	/************** SETTERS AND GETTERS *************************/
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

	public String getEditoInicio() {
		return editoInicio;
	}

	public void setEditoInicio(String editoInicio) {
		this.editoInicio = editoInicio;
	}

	public String getEditoFin() {
		return editoFin;
	}

	public void setEditoFin(String editoFin) {
		this.editoFin = editoFin;
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
