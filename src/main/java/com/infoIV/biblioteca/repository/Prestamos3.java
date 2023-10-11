package com.infoIV.biblioteca.repository;
/*
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.infoIV.biblioteca.model.Prestamo;
import com.infoIV.biblioteca.repository.filter.PrestamoFilter;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class Prestamos3 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Prestamo guardar(Prestamo prestamo) {
		return manager.merge(prestamo);
	}

	@Transactional
	public void remover(Prestamo prestamo) {
		try {
			prestamo = porId(prestamo.getNumero());
			manager.remove(prestamo);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Prestamo no puede ser excluído.");
		}
	}

	// @SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	public List<Prestamo> filtrados(PrestamoFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Prestamo.class);

		if (StringUtils.isNotBlank(filtro.getCodigo())) {
			criteria.add(Restrictions.eq("id", filtro.getCodigo()));
		}

		

		// return criteria.list();

		return criteria.addOrder(Order.asc("descri")).list();
	}

	public Prestamo porId(Long numero) {
		System.out.println("Por id ");
		System.out.println(this.porId(numero));
		return manager.find(Prestamo.class, numero);
		
	}
	
	
	public List<Prestamo> porNomeLista(String descri) {
		return this.manager.createQuery("from Prestamo " +
				"where upper(descri) like :descri", Prestamo.class)
				.setParameter("descri", descri.toUpperCase() + "%")
				.getResultList();
	}
	
		public Prestamo porNome(String descri) {
		try {
			return manager
					.createQuery(
							"from Prestamo where upper(descri) = :descri",
							Prestamo.class)
					.setParameter("descri", descri.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
*/