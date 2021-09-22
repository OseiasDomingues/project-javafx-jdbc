package model.services;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

import java.util.List;

public class SellerService {

    private SellerDao SellerDao = DaoFactory.createSellerDao();

    public List<Seller> findAll() {
        return SellerDao.findAll();
    }

    public void saveOrUpdate(Seller seller) {
        if (seller.getId() == null) {
            SellerDao.insert(seller);
        }else{
            SellerDao.update(seller);
        }
    }
    public void remove(Seller seller){
        SellerDao.deleteById(seller.getId());
    }
}
