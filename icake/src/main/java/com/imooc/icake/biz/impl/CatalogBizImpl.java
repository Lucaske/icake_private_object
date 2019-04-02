package com.imooc.icake.biz.impl;

import com.imooc.icake.DAO.CatalogDao;
import com.imooc.icake.biz.CatalogBiz;
import com.imooc.icake.entity.Catalog;
import com.imooc.icake.global.DaoFactory;

import java.util.List;

public class CatalogBizImpl implements CatalogBiz {

    private CatalogDao catalogDao=DaoFactory.getInstance().getDao(CatalogDao.class);
    public void add(List<Catalog> list) {
        catalogDao.batchInsert(list);
    }

    public void remove(int id) {
        catalogDao.delete(id);
    }

    public Catalog getRoot() {
        return catalogDao.select(10000);
    }
}
