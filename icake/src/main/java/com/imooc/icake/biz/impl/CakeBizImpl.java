package com.imooc.icake.biz.impl;

import com.imooc.icake.DAO.CakeDao;
import com.imooc.icake.biz.CakeBiz;
import com.imooc.icake.entity.Cake;
import com.imooc.icake.global.DaoFactory;

import java.util.List;

public class CakeBizImpl implements CakeBiz {

    CakeDao cakeDao= DaoFactory.getInstance().getDao(CakeDao.class);
    public void add(Cake cake) {
        cakeDao.insert(cake);
    }

    public void edit(Cake cake) {
        cakeDao.update(cake);
    }

    public void remove(int id) {
        cakeDao.delete(id);
    }

    public Cake get(int id) {
        return cakeDao.Select(id);
    }

    public List<Cake> getAll() {
        return cakeDao.selectAll();
    }

    public Cake getSpecial() {
        List<Cake> list=cakeDao.selectByStatus("特卖");
        if (list.size()>0)
            return list.get(0);
        return null;
    }

    public List<Cake> getForIndex() {
        List<Cake> list=cakeDao.selectByStatus("推荐");
        return list;
    }

    public List<Cake> getForCatalog(int cid) {
        List<Cake> list=cakeDao.selectByCid(cid);
        return list;
    }
}
