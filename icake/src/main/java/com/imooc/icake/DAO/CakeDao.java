package com.imooc.icake.DAO;

import com.imooc.icake.entity.Cake;

import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CakeDao {

    @Insert("INSERT INTO cake(title,cid,image_path,price,taste,sweetness,weight,size,metarial,status)" +
            "VALUES(#{title},#{cid},#{image_path},#{price},#{taste},#{sweetness},#{weight},#{size},#{metarial},#{status})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insert(Cake cake);

    @Update("UPDATE cake SET title=#{title},cid=#{cid},image_path=#{image_path},price=#{price},taste=#{taste},sweetness=#{sweetness},weight=#{weight},size=#{size},metarial=#{metarial},status=#{status}" +
            "WHERE id=#{id}")
    void update(Cake cake);

    @Delete("DELETE FROM cake WHERE id=#{id}")
    void delete(Integer id);

    @Select("SELECT c.*,ca.title ctitle FROM cake c LEFT JOIN catalog ca ON c.cid=ca.id WHERE c.id=#{id}")
    @Results(id = "all",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "title",property = "title"),
            @Result(column = "cid",property = "cid"),
            @Result(column = "image_path",property = "image_path"),
            @Result(column = "price",property = "price"),
            @Result(column = "taste",property = "taste"),
            @Result(column = "sweetness",property = "sweetness"),
            @Result(column = "weight",property = "weight"),
            @Result(column = "size",property = "size"),
            @Result(column = "metarial",property = "metarial"),
            @Result(column = "status",property = "status"),
            @Result(column = "ctitle",property = "catalog.title")
    })
    Cake Select(Integer id);

    @Select("SELECT c.*,ca.title ctitle FROM cake c LEFT JOIN catalog ca ON c.cid=ca.id ORDER BY c.id DESC")
    @ResultMap("all")
    List<Cake> selectAll();

    @Select("SELECT c.*,ca.title ctitle FROM cake c LEFT JOIN catalog ca ON c.cid=ca.id WHERE c.status=#{status} ORDER BY c.id DESC" )
    @ResultMap("all")
    List<Cake> selectByStatus(String status);

    @Select("SELECT c.*,ca.title ctitle FROM cake c LEFT JOIN catalog ca ON c.cid=ca.id WHERE c.cid=#{cid}")
    @ResultMap("all")
    List<Cake> selectByCid(int cid);
}
