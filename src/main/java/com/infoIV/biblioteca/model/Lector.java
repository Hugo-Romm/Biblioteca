package com.infoIV.biblioteca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
@Entity
@Table(name="lector")
public class Lector implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String nombre;
	private Integer cedula;
	private String direc;
	private String telef;


	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lector_seq")
	@SequenceGenerator(name = "lector_seq", sequenceName = "lector_seq", allocationSize=1)	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	@NotBlank
	@Size(max = 80)
	@Column(nullable = false, length = 80)	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	@NotNull @Min(0) @Max(value = 99999999)
	@Column(name="cedula", nullable = false, length = 10)
	
	public Integer getCedula() {
		return cedula;
	}

	public void setCedula(Integer cedula) {
		this.cedula = cedula;
	}
	
	@NotBlank
	@Size(max = 80)
	@Column(nullable = false, length = 80)
	
	public String getDirec() {
		return direc;
	}

	public void setDirec(String direc) {
		this.direc = direc;
	}
	
	@NotBlank
	@Size(max = 60)
	@Column(nullable = false, length = 60)
	
	public String getTelef() {
		return telef;
	}

	public void setTelef(String telef) {
		this.telef = telef;
	}

	@Transient
	public boolean isNovo() {
		return getCodigo() == null;
	}
	
	@Transient
	public boolean isExistente() {
		return !isNovo();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Lector other = (Lector) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lector [codigo=" + codigo + ", nombre=" + nombre + ", cedula=" + cedula + ", direc=" + direc
				+ ", telef=" + telef + "]";
	}
	
	
	
	
}