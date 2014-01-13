package ua.mamamusic.accountancy.dao;


import java.util.List;

import ua.mamamusic.accountancy.model.Distributor;

public interface DistributorDAO extends GenericDAO<Distributor, Long> {

	public List<Distributor> findAllNotInList(List<Long> list);
	
	public List<Distributor> findAllDependentProducts(Long id);
}
