package com.infoIV.biblioteca.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.hibernate.Session;

import com.infoIV.biblioteca.model.Deuda;
import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.repository.Prestamos;
import com.infoIV.biblioteca.repository.filter.PrestamoFilter;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jsf.FacesUtil;
import com.infoIV.biblioteca.util.report.ExecutorRelatorio;

@Named
@ViewScoped
public class PesquisaPrestamosBean1 implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Prestamos prestamos;
	@Inject
	private Prestamo prestamo;
	@Inject
	private Deuda deuda;
	
	private PrestamoFilter filtro;
	private List<Prestamo> prestamosFiltrados;
	
	
	private Date dataInicio;
	private Date dataFim;
	
	private Date fechaInicio;
	private Date fechaFin;
	private String lectorInicio;
	private String lectorFin;
	
	private String libroInicio;
	private String libroFin;
	private Integer numeroInicio;
	private Integer numeroFin;
	
	private Integer console;
	
	
	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;
/***********************   INFORME DETALLADO PRESTAMO.PDF   ****************************/
	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_fin", this.dataFim);
		parametros.put("numero_inicio", this.numeroInicio);
		parametros.put("numero_fin", this.numeroFin);
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/relatorio_pedidos_emitidos.jasper",
				this.response, parametros, "Prestamos emitidos.pdf");
		
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("El rango no tiene datos");
		}
	}
	public PesquisaPrestamosBean1() {
		filtro = new PrestamoFilter();
	}
	/******************************************************************/
	
	
	/*****************   PARA LISTADO DE PRESTAMO ********************/
	public void pesquisar() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_fin", this.dataFim);
		parametros.put("lector_inicio", this.lectorInicio);
		parametros.put("lector_fin", this.lectorFin);
		parametros.put("libro_inicio", this.libroInicio);
		parametros.put("libro_fin", this.libroFin);
				
		fechaInicio = this.dataInicio;
		fechaFin = this.dataFim;
		lectorInicio = this.lectorInicio;
		lectorFin = this.lectorFin;
		libroInicio = this.libroInicio;
		libroFin = this.libroFin;
		console = this.console;
		prestamosFiltrados = prestamos.filtradosReportesPrestamoTeste2(prestamo, fechaInicio, fechaFin, lectorInicio, lectorFin, libroInicio, libroFin, console);
		if (this.prestamosFiltrados.isEmpty() ){
		       System.out.println("Si está vacio");
		       throw new NegocioException("No hay datos en este intervalo");  
		      
		}else {
			getPrestamosFiltrados();
		}
		
	}
	
	public List<Prestamo> getPrestamosFiltrados() {
		return prestamosFiltrados;
	}
	
	public PrestamoFilter getFiltro() {
		return filtro;
	}
/**************   SETTERS AND GETTERS   *************************/
	
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getLectorInicio() {
		return lectorInicio;
	}

	public void setLectorInicio(String lectorInicio) {
		this.lectorInicio = lectorInicio;
	}

	public String getLectorFin() {
		return lectorFin;
	}

	public void setLectorFin(String lectorFin) {
		this.lectorFin = lectorFin;
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

	
	public Integer getNumeroInicio() {
		return numeroInicio;
	}

	public void setNumeroInicio(Integer numeroInicio) {
		this.numeroInicio = numeroInicio;
	}

	public Integer getNumeroFin() {
		return numeroFin;
	}

	public void setNumeroFin(Integer numeroFin) {
		this.numeroFin = numeroFin;
	}
	
	public Integer getConsole() {
		return console;
	}

	public void setConsole(Integer console) {
		this.console = console;
	}
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
	
	
