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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name="carro")
public class Carro implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String marca;
	private Docente docente;	
	private String modelo;
	private String ano;
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carro_seq")
	@SequenceGenerator(name = "carro_seq", sequenceName = "carro_seq", allocationSize=1)	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)	
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "docente_codigo", nullable = false)
	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	@NotBlank
	@Size(max = 200)
	@Column(nullable = false, length = 200)	
	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	@NotBlank
	@Size(max = 30)
	@Column(nullable = false, length = 30)	
	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}
	
	
	/***********************/

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
		Carro other = (Carro) obj;
		
		if (codigo == null)
		{ if (other.codigo != null)
				return false;
		} 
		else 
			if (!codigo.equals(other.codigo))
			return false;
		
		
		return true;
	}

	@Override
	public String toString() {
		return "Carro [codigo=" + codigo + ", marca=" + marca + ", docente=" + docente + ", modelo=" + modelo + ", ano="
				+ ano + "]";
	}

	
	
	
	
}
