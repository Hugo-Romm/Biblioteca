/*package com.infoIV.biblioteca.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "item_pedido")
public class ItemPrestamo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Integer cantidad = 1;
	private BigDecimal valorUnitario = BigDecimal.ZERO;
	private Producto producto;
	private Pedido pedido;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false, length = 3)
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	@ManyToOne
	@JoinColumn(name = "producto_id", nullable = false)
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@ManyToOne
	@JoinColumn(name = "pedido_id", nullable = false)
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ItemPrestamo other = (ItemPrestamo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Transient
	public BigDecimal getValorTotal() {
		return this.getValorUnitario().multiply(new BigDecimal(this.getCantidad()));
	}
	
	@Transient
	public boolean isProductoAssociado() {
		return this.getProducto() != null && this.getProducto().getId() != null;
	}
	
	@Transient
	public boolean isStockSuficiente() {
		return this.getPedido().isEmitido() || this.getProducto().getId() == null 
			|| this.getProducto().getCantidadStock() >= this.getCantidad(); 
	}
	
	@Transient
	public boolean isStockInsuficiente() {
		return !this.isStockSuficiente();
	}

}
*/