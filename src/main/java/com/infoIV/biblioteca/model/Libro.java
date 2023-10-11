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
@Table(name="libro")
public class Libro implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String descri;
	private Editor editor;	
	private String autor;
	private String isbn;
	private String obse;
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "libro_seq")
	@SequenceGenerator(name = "libro_seq", sequenceName = "libro_seq", allocationSize=1)	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)	
	public String getDescri() {
		return descri;
	}

	public void setDescri(String descri) {
		this.descri = descri;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "editor_codigo", nullable = false)
	public Editor getEditor() {
		return editor;
	}

	public void setEditor(Editor editor) {
		this.editor = editor;
	}

	@NotBlank
	@Size(max = 200)
	@Column(nullable = false, length = 200)	
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	@NotBlank
	@Size(max = 30)
	@Column(nullable = false, length = 30)	
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	@Size(max = 200)
	@Column(nullable = false, length = 200)
	public String getObse() {
		return obse;
	}

	public void setObse(String obse) {
		this.obse = obse;
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
		Libro other = (Libro) obj;
		
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
		return "Libro [codigo=" + codigo + ", descri=" + descri + ", editor=" + editor + ", autor=" + autor + ", isbn="
				+ isbn + ", obse=" + obse + "]";
	}

	
	
	
	
}
