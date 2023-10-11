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

import com.infoIV.biblioteca.model.Deuda;
import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.repository.Deudas;
import com.infoIV.biblioteca.repository.filter.DeudaFilter;
import com.infoIV.biblioteca.repository.filter.LibroFilter;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jsf.FacesUtil;
import com.infoIV.biblioteca.util.report.ExecutorRelatorio;

@Named
@ViewScoped
public class ListadoDeudasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Deudas deudas;
	@Inject
	private Deuda deuda;

	private DeudaFilter filtro;
	private List<Deuda> deudasFiltrados;

	private Long codigoInicio;
	private Long codigoFin;

	private String descriInicio;
	private String descriFin;

	private String libroInicio;
	private String libroFin;
	
	private Long codigoInicio1;
	private Long codigoFin1;

	private Integer console;
	private Integer montoTotal;

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
		parametros.put("lector_inicio", this.descriInicio);
		parametros.put("lector_fin", this.descriFin);
		parametros.put("numero_inicio", this.codigoInicio1);
		parametros.put("numero_fin", this.codigoFin1);
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

	public ListadoDeudasBean() {
		filtro = new DeudaFilter();
	}

	/***************** PARA LISTADO DE LIBROS ********************/
	public void pesquisar() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("codigo_inicio", this.codigoInicio);
		parametros.put("codigo_fin", this.codigoFin);
		parametros.put("lector_inicio", this.descriInicio);
		parametros.put("lector_fin", this.descriFin);
		parametros.put("numero_inicio", this.codigoInicio1);
		parametros.put("numero_fin", this.codigoFin1);
		parametros.put("libro_inicio", this.libroInicio);
		parametros.put("libro_fin", this.libroFin);

		codigoInicio = this.codigoInicio;
		codigoFin = this.codigoFin;
		descriInicio = this.descriInicio;
		descriFin = this.descriFin;
		codigoInicio1 = this.codigoInicio1;
		codigoFin1 = this.codigoFin1;
		libroInicio = this.libroInicio;
		libroFin = this.libroFin;
		console = this.console;
		
		System.out.println("console desde bean : "+ console);
		deudasFiltrados = deudas.filtradosReportesDeudaTeste2(deuda,
				codigoInicio, codigoFin, descriInicio, descriFin, codigoInicio1, codigoFin1,console);
		System.out.println("paso por aca");
		if (this.deudasFiltrados.isEmpty()) {
			System.out.println("Si está vacio");
			throw new NegocioException("No hay datos en este intervalo");

		} else {
			System.out.println("entra en else");
			getDeudasFiltrados();
		}

	}
	public void pesquisaPorLector() {
		Map<String, Object> parametros = new HashMap<>();

		parametros.put("numero_inicio", this.codigoInicio);
		parametros.put("numero_fin", this.codigoFin);


		codigoInicio = this.codigoInicio;
		codigoFin = this.codigoFin;
		
		deudasFiltrados = deudas.informePorLector(deuda,codigoInicio, codigoFin);
		montoTotal = 0;
		if (this.deudasFiltrados.isEmpty()) {
			System.out.println("Si está vacio");
			throw new NegocioException("No hay datos en este intervalo");

		} else {
			for (Deuda deudass : deudasFiltrados) {
				montoTotal = montoTotal + deudass.getMonto();
			}
			System.out.println("entra en else");
			getDeudasFiltrados();
			getMontoTotal();
		}

	}
	public List<Deuda> getDeudasFiltrados() {
		return deudasFiltrados;
	}

	public DeudaFilter getFiltro() {
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

	public String getLibroInicio() {
		return libroInicio;
	}

	public void setLibroInicio(String libroInicio) {
		this.libroInicio = libroInicio;
	}
	

	public String getLibroFin() {
		return libroFin;
	}

	public void setLibroFin(String libroFin) {
		this.libroFin = libroFin;
	}
	public Long getCodigoInicio1() {
		return codigoInicio1;
	}

	public void setCodigoInicio1(Long codigoInicio1) {
		this.codigoInicio1 = codigoInicio1;
	}

	public Long getCodigoFin1() {
		return codigoFin1;
	}

	public void setCodigoFin1(Long codigoFin1) {
		this.codigoFin1 = codigoFin1;
	}
	public Integer getConsole() {
		return console;
	}

	public void setConsole(Integer console) {
		this.console = console;
	}
	public Integer getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(Integer montoTotal) {
		this.montoTotal = montoTotal;
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
