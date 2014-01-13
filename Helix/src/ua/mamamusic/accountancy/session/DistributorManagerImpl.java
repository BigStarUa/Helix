package ua.mamamusic.accountancy.session;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;

import ua.mamamusic.accountancy.HibernateUtil;
import ua.mamamusic.accountancy.dao.DistributorDAO;
import ua.mamamusic.accountancy.dao.DistributorDAOimpl;
import ua.mamamusic.accountancy.model.Distributor;

public class DistributorManagerImpl implements DistributorManager{

	private DistributorDAO productDAO = new DistributorDAOimpl();
	
	@Override
	public Distributor findProductById(long id) {
		Distributor product = null;
		try{
			HibernateUtil.beginTransaction();
			product = productDAO.findById(Distributor.class, id);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return product;
	}

	@Override
	public void saveNewProduct(Distributor product) {
		try{
			HibernateUtil.beginTransaction();
			productDAO.save(product);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
	}

	@Override
	public void deleteProduct(Distributor product) {
		try{
			HibernateUtil.beginTransaction();
			productDAO.delete(product);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
		
	}

	@Override
	public List<Distributor> loadAllProducts() {
		List<Distributor> list = null;
		try{
			HibernateUtil.beginTransaction();
			list = productDAO.findAll(Distributor.class);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return list;
	}
	
	@Override
	public List<Distributor> loadAllProductsOrderedBy(String criteria) {
		List<Distributor> list = null;
		try{
			HibernateUtil.beginTransaction();
			Criteria c = HibernateUtil.getSession().createCriteria(Distributor.class);
			c.addOrder(Order.asc(criteria));
			list = c.list();
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return list;
	}

	@Override
	public void mergeProduct(Distributor product) {
		try{
			HibernateUtil.beginTransaction();
			productDAO.merge(product);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
	}

	@Override
	public void updateProduct(Distributor product) {
		try{
			HibernateUtil.beginTransaction();
			productDAO.update(product);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}		
	}

	@Override
	public List<Distributor> loadProducts(Query query) {
		List<Distributor> list = null;
		try{
			HibernateUtil.beginTransaction();
			list = productDAO.findMany(query);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return list;
	}

	@Override
	public List<Distributor> loadProductsNotInList(List<Long> l) {
		List<Distributor> list = null;
		try{
			HibernateUtil.beginTransaction();
			list = productDAO.findAllNotInList(l);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return list;
	}
	
	@Override
	public List<Distributor> loadDependentProductsList(Long id) {
		List<Distributor> list = null;
		try{
			HibernateUtil.beginTransaction();
			list = productDAO.findAllDependentProducts(id);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return list;
	}

}
