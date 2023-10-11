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
import javax.persistence.JoinColumn;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.infoIV.biblioteca.model.Deuda;
import com.infoIV.biblioteca.model.Lector;
import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.repository.Deudas;
import com.infoIV.biblioteca.repository.Prestamos;
import com.infoIV.biblioteca.repository.filter.PrestamoFilter;
import com.infoIV.biblioteca.util.jpa.Transactional;
import com.infoIV.biblioteca.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaPrestamosBean implements Serializable {
	Date diapre;
	Date diaDev;
	Date diaRecup;
	Lector xlector;
	Prestamo xprestamo;
	private static final long serialVersionUID = 1L;

	@Inject
	private Prestamos prestamos;
	
	@Inject
	private Prestamo prestamo;
	@Inject
	private Deudas deudas;
	@Inject
	private Deuda deuda;
	
	private Date fecrec ;
	private Date otrofecrec ;
	private PrestamoFilter filtro;
	private List<Prestamo> prestamosFiltrados;
	private Integer deudaMonto;
	private Prestamo prestamoSelecionado;
	private Deuda deudaSeleccionada;
	private List<Deuda> deudasFiltradas;
	
	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;
	@Inject
	private EntityManager manager;

	public PesquisaPrestamosBean() {
		filtro = new PrestamoFilter();
	}

	@JoinColumn
	@Transactional
	public void devolver() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("fecha_rec", this.fecrec);
		
		otrofecrec = this.fecrec;
		System.out.println("Entrando a Devolver"+ otrofecrec);
		prestamoSelecionado.setFecrec(fecrec);
		System.out.println("REcuperacion desde devolver  =" +prestamoSelecionado.getFecrec());
		System.out.println(prestamoSelecionado.getNumero());

		prestamoSelecionado = this.prestamos.guardar(prestamoSelecionado);

		/************* AGARRO LOS DATOS QUE VOY A NECESITAR ******************/
		this.diapre = prestamoSelecionado.getFecpre();
		this.diaDev = prestamoSelecionado.getFecdev();
		this.diaRecup = prestamoSelecionado.getFecrec();
		this.xlector = prestamoSelecionado.getLector();
		this.xprestamo = prestamoSelecionado;
		/* Y envío esos datos a deudas */
		 this.deudas.recalcularValorTotal(deuda, diapre,
				diaDev, diaRecup, xlector, xprestamo);
		System.out.println("vuelve pio aca");
		

	}

	public void excluir() {
		prestamos.remover(prestamoSelecionado);
		prestamosFiltrados.remove(prestamoSelecionado);

		FacesUtil.addInfoMessage("Prestamo " + prestamoSelecionado.getNumero()
				+ " eliminado con éxito.");
	}
	
	
	public Date getFechaHoy() {
		return new Date();
	}
	
	public void pesquisar() {
		prestamosFiltrados = prestamos.filtrados(filtro);
	}

	public List<Prestamo> getPrestamosFiltrados() {
		return prestamosFiltrados;
	}

	public PrestamoFilter getFiltro() {
		return filtro;
	}

	public Prestamo getPrestamoSelecionado() {
		return prestamoSelecionado;
	}

	public void setPrestamoSelecionado(Prestamo prestamoSelecionado) {
		this.prestamoSelecionado = prestamoSelecionado;
		
	}


	public List<Deuda> getDeudasFiltradas() {
		return deudasFiltradas;
	}

	public void setDeudasFiltradas(List<Deuda> deudasFiltradas) {
		this.deudasFiltradas = deudasFiltradas;
	}
	public Date getFecrec() {
		return fecrec;
	}

	public void setFecrec(Date fecrec) {
		this.fecrec = fecrec;
	}
	
	
	public Date getOtrofecrec() {
		return otrofecrec;
	}

	public void setOtrofecrec(Date otrofecrec) {
		this.otrofecrec = otrofecrec;
	}

	public Deuda getDeuda() {
		return deuda;
	}

	public void setDeuda(Deuda deuda) {
		this.deuda = (Deuda) deudasFiltradas;
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
