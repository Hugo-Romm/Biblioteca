package com.infoIV.biblioteca.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Persistence;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/*import com.infoIV.biblioteca.model.ItemPrestamo;*/

@Entity
@Table(name = "prestamo")
public class Prestamo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long numero;
	private Date fecpre;
	private Lector lector;
	private Libro libro;
	private Integer nroeje;
	private Date fecdev;
	private Date fecrec;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prestamo_seq")
	@SequenceGenerator(name = "prestamo_seq", sequenceName = "prestamo_seq", allocationSize = 1)
	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	@Column(nullable = false)
	public Date getFecpre() {
		return fecpre;
	}

	public void setFecpre(Date fecpre) {
		this.fecpre = fecpre;
	}

	@Column
	public Date getFecrec() {
		return fecrec;
	}

	public void setFecrec(Date fecrec) {
		this.fecrec = fecrec;
	}
    
	@Column(nullable = false)
	public Date getFecdev() {
		return fecdev;
	}

	public void setFecdev(Date fecdev) {
		this.fecdev = fecdev;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "lector_codigo")
	public Lector getLector() {
		return lector;
	}

	public void setLector(Lector lector) {
		this.lector = lector;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "libro_codigo")
	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	@NotNull
	@Min(0)
	@Max(value = 999999999, message = "El valor es muy alto. Verifique!")
	@Column(name = "nroejem", nullable = false, length = 3)
	public Integer getNroeje() {
		return nroeje;
	}

	public void setNroeje(Integer nroeje) {
		this.nroeje = nroeje;
	}

	@Transient
	public boolean isNuevo() {
		return getNumero() == null;
	}

	@Transient
	public boolean isExistente() {
		return !isNuevo();
	}

	@Transient
	public void calcularFechaDevolucion() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("PedidoPU");
		EntityManager em = emf.createEntityManager();
		List<Config> config = em.createQuery("from Config ", Config.class)
				.getResultList();
		int i = config.get(0).getDiapre(); ;
		Date xfp = this.getFecpre();
		SimpleDateFormat formateador = new SimpleDateFormat();
		System.out.println(formateador.format(xfp));

		Calendar c = GregorianCalendar.getInstance();
		c.setTimeInMillis(xfp.getTime());
		c.add(Calendar.DAY_OF_MONTH, i);
		this.setFecdev(c.getTime());

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prestamo other = (Prestamo) obj;

		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;

		return true;
	}

}
