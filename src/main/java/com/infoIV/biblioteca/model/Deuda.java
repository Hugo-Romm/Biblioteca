package com.infoIV.biblioteca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "deuda")
public class Deuda implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long num;
	private Lector lector;
	private Prestamo prestamo;
	private Integer monto;
	private Integer pagado;

	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deuda_seq")
	@SequenceGenerator(name = "deuda_seq", sequenceName = "deuda_seq", allocationSize = 1)
	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
	
	@ManyToOne
	@JoinColumn(name = "lector_codigo")
	public Lector getLector() {
		return lector;
	}

	public void setLector(Lector lector) {
		this.lector = lector;
	}

	
	@ManyToOne
	@JoinColumn(name = "prestamo_numero")
	public Prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	
	@Min(0)
	@Max(value = 999999999)
	@Column(nullable = false)
	public Integer getMonto() {
		return monto;
	}

	public void setMonto(Integer monto) {
		this.monto = monto;
	}

	
	@Min(0)
	@Max(value = 999999999)
	@Column(nullable = false)
	public Integer getPagado() {
		return pagado;
	}

	public void setPagado(Integer pagado) {
		this.pagado = pagado;
	}

	@Transient
	public boolean isNovo() {
		return getNum() == null;
	}

	@Transient
	public boolean isExistente() {
		return !isNovo();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((num == null) ? 0 : num.hashCode());
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
		Deuda other = (Deuda) obj;

		if (num == null) {
			if (other.num != null)
				return false;
		} else if (!num.equals(other.num))
			return false;

		return true;
	}


}
