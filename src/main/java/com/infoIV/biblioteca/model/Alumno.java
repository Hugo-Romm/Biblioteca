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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="alumno")
public class Alumno implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String nombre;	
	private int edad;
	private String fechanac;
	private String materia;
	private String curso;


	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alumno_seq")
	@SequenceGenerator(name = "alumno_seq", sequenceName = "alumno_seq", allocationSize=1)	
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
	
	
	
	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	@NotBlank
	@Size(max = 80)
	@Column(nullable = false, length = 80)	
	
	public String getFechanac() {
		return fechanac;
	}

	public void setFechanac(String fechanac) {
		this.fechanac = fechanac;
	}
	
	@NotBlank
	@Size(max = 80)
	@Column(nullable = false, length = 80)	

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}
	
	@NotBlank
	@Size(max = 80)
	@Column(nullable = false, length = 80)	

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
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
		Alumno other = (Alumno) obj;
		
		if (codigo == null)
		{ if (other.codigo != null)
				return false;
		} 
		else 
			if (!codigo.equals(other.codigo))
			return false;
		
		
		return true;
	}

	
	
	
	
}
