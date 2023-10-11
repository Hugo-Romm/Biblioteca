package com.infoIV.biblioteca.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.infoIV.biblioteca.model.Config;
import com.infoIV.biblioteca.repository.filter.ConfigFilter;
import com.infoIV.biblioteca.service.NegocioException;
import com.infoIV.biblioteca.util.jpa.Transactional;

public class Configs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	Integer monto;
	
	public Config guardar(Config cofig) {
		return manager.merge(cofig);

	}

	@Transactional
	public void remover(Config cofig) {
		try {
			cofig = porCodigo(cofig.getCodigo());
			manager.remove(cofig);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException(
					"Estas configuraciones no pueden ser eliminadas.");
		}
	}
	public Integer getCofigsMonto() {

		List<Config> config = this.manager.createQuery("from Config",
				Config.class).getResultList();
		return this.monto = config.get(0).getDeumon();
	}
	// @SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	public List<Config> filtrados(ConfigFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Config.class);

		if (filtro.getCodigo() != null) {
			criteria.add(Restrictions.ge("codigo", filtro.getCodigo()));
		}

		if (StringUtils.isNotBlank(filtro.getOrganom())) {
			criteria.add(Restrictions.ilike("nombre", filtro.getOrganom(),
					MatchMode.ANYWHERE));
		}

		// return criteria.list();

		return criteria.addOrder(Order.asc("nombre")).list();
	}

	@SuppressWarnings("unchecked")
	public List<Config> controlaSeña(Config config, String seña) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Config.class);
		System.out.println(seña);
		if (StringUtils.isNotBlank(seña)){
			criteria.add(Restrictions.ilike("seña", seña, MatchMode.ANYWHERE));
		}else{
			throw new NegocioException("La seña ingresada no es correcta"); 
		}
		return criteria.list();
	}


	public Config porCodigo(Long codigo) {
		return manager.find(Config.class, codigo);
	}

	public Config porNome(String organom) {
		try {
			return manager
					.createQuery("from Config where upper(organom) = :organom",
							Config.class)
					.setParameter("organom", organom.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Integer diasAPrestar() {
		Integer x = 0;
		System.out.println("Dias a prestar");
		Query query = manager.createNativeQuery("Select * from config",
				Config.class);
		List<Config> conf = query.getResultList();

		for (Config configs : conf) {
			x = configs.getDiapre();
			System.out.println("Codigo = " + configs.getCodigo()
					+ "Dias Prestamo =" + configs.getDiapre());

		}
		return x;

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Integer diaPrestarMasMoroso() {
		Integer x = 0;
		System.out.println("Dias a prestar");
		Query query = manager.createNativeQuery("Select * from config",
				Config.class);
		List<Config> conf = query.getResultList();

		for (Config configs : conf) {
			x = configs.getDiapre();
			System.out.println("Codigo = " + configs.getCodigo()
					+ "Dias Prestamo =" + configs.getDiapre());

		}
		return conf.get(0).getDiapre()+ conf.get(0).getDiamor();

	}

	public List<Config> porNome1() {

		try {
			System.out.println("por nome 1");
			return manager.createQuery("from Config ", Config.class)
					.setFirstResult(1).getResultList();

		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Config> cofigs() {
		// TODO filtrar apenas func (por um grupo específico)
		return this.manager.createQuery("from Config", Config.class)
				.getResultList();

	}

	public List<Config> porNomeLista(String organom) {

		return this.manager
				.createQuery(
						"from Config " + "where upper(organom) like :organom",
						Config.class)
				.setParameter("organom", organom.toUpperCase() + "%")
				.getResultList();

	}

}
