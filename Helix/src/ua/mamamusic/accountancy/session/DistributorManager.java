package ua.mamamusic.accountancy.session;


import java.util.List;

import org.hibernate.Query;

import ua.mamamusic.accountancy.model.Distributor;

public interface DistributorManager {

	public Distributor findProductById(long id);
	
	public void saveNewProduct(Distributor product);
	
	public void mergeProduct(Distributor product);
	
	public void updateProduct(Distributor product);
	
	public void deleteProduct(Distributor product);
	
	public List<Distributor> loadAllProducts();
	
	public List<Distributor> loadProducts(Query query);
	
	public List<Distributor> loadProductsNotInList(List<Long> list);
	
	public List<Distributor> loadAllProductsOrderedBy(String criteria);
	
	public List<Distributor> loadDependentProductsList(Long id);
}
