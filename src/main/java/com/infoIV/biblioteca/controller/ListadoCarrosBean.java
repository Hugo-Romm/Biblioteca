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

import com.infoIV.biblioteca.model.Carro;
import com.infoIV.biblioteca.model.Docente;
import com.infoIV.biblioteca.repository.Carros;
import com.infoIV.biblioteca.repository.filter.CarroFilter;
import com.infoIV.biblioteca.repository.filter.PrestamoFilter;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jsf.FacesUtil;
import com.infoIV.biblioteca.util.report.ExecutorRelatorio;

@Named
@ViewScoped
public class ListadoCarrosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Carros carros;
	@Inject
	private Carro carro;

	private CarroFilter filtro;
	private List<Carro> carrosFiltrados;

	private Long codigoInicio;
	private Long codigoFin;

	private String marcaInicio;
	private String marcaFin;

	private String docentInicio;
	private String docentFin;

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
		parametros.put("marca_inicio", this.marcaInicio);
		parametros.put("marca_fin", this.marcaFin);
		parametros.put("docent_inicio", this.docentInicio);
		parametros.put("docent_fin", this.docentFin);
		ExecutorRelatorio executor = new ExecutorRelatorio(
				"/relatorios/relatorio_pedcodigoos_emitcodigoos.jasper", this.response,
				parametros, "Prestamos emitcodigoos.pdf");

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("El rango no tiene datos");
		}
	}

	/******************************************************************/

	public ListadoCarrosBean() {
		filtro = new CarroFilter();
	}

	/***************** PARA LISTADO DE LIBROS ********************/
	public void pesquisar() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("codigo_inicio", this.codigoInicio);
		parametros.put("codigo_fin", this.codigoFin);
		parametros.put("marca_inicio", this.marcaInicio);
		parametros.put("marca_fin", this.marcaFin);
		parametros.put("docent_inicio", this.docentInicio);
		parametros.put("docent_fin", this.docentFin);

		codigoInicio = this.codigoInicio;
		codigoFin = this.codigoFin;
		marcaInicio = this.marcaInicio;
		marcaFin = this.marcaFin;
		docentInicio = this.docentInicio;
		docentFin = this.docentFin;
		console = this.console;
		System.out.println("console desde bean : "+ console);
		carrosFiltrados = carros.filtradosReportesCarroTeste2(carro,
				codigoInicio, codigoFin, marcaInicio, marcaFin, docentInicio,
				docentFin,console);
		System.out.println("paso por aca");
		if (this.carrosFiltrados.isEmpty()) {
			System.out.println("Si está vacio");
			throw new NegocioException("No hay datos en este intervalo");

		} else {
			System.out.println("entra en else");
			getCarrosFiltrados();
		}

	}

	public List<Carro> getCarrosFiltrados() {
		return carrosFiltrados;
	}

	public CarroFilter getFiltro() {
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

	public String getMarcaInicio() {
		return marcaInicio;
	}

	public void setMarcaInicio(String marcaInicio) {
		this.marcaInicio = marcaInicio;
	}

	public String getMarcaFin() {
		return marcaFin;
	}

	public void setMarcaFin(String marcaFin) {
		this.marcaFin = marcaFin;
	}

	public String getDocentInicio() {
		return docentInicio;
	}

	public void setDocentInicio(String docentInicio) {
		this.docentInicio = docentInicio;
	}

	public String getDocentFin() {
		return docentFin;
	}

	public void setDocentFin(String docentFin) {
		this.docentFin = docentFin;
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
