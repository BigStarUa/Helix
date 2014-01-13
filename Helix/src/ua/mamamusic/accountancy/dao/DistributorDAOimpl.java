package ua.mamamusic.accountancy.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ua.mamamusic.accountancy.model.Distributor;

public class DistributorDAOimpl extends AbstractGenericDAO<Distributor, Long> implements DistributorDAO{

	@Override
	public List<Distributor> findAllNotInList(List<Long> list) {
		List<Distributor> l = null;
		Session session = this.getSession();
		Query q = session.createQuery("FROM Product WHERE id NOT IN (:list) ORDER BY name");
		q.setParameterList("list", list);
		l = q.list();
		return l;
	}

	@Override
	public List<Distributor> findAllDependentProducts(Long id) {
		List<Distributor> l = new ArrayList<>();
		Session session = this.getSession();
		Query q = session.createQuery("SELECT p FROM Product p LEFT JOIN p.productsList pe WHERE pe.product.id = (:id) group by p");
		q.setParameter("id", id);
		l = q.list();
		return l;
	}
}
