package com.itheima.service.impl;

import com.itheima.mapper.BrandMapper;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.service.BrandService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class BrandServiceImpl implements BrandService {

    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();
    @Override
    public List<Brand> selectAll() {

        SqlSession session=factory.openSession();
        BrandMapper mapper=session.getMapper(BrandMapper.class);
        List<Brand> brands=mapper.selectAll();

        //千万不要忘记要释放资源
        session.close();

        return brands;
    }

    @Override
    public void add(Brand brand) {
        SqlSession session=factory.openSession();
        BrandMapper mapper=session.getMapper(BrandMapper.class);
        mapper.add(brand);

        //提交事务
        session.commit();
        session.close();

    }

    @Override
    public void deleteByIds(int[] ids) {
        SqlSession session=factory.openSession();
        BrandMapper mapper=session.getMapper(BrandMapper.class);
        mapper.deleteByIds(ids);

        session.commit();
        session.close();

    }

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean<Brand> selectByPageAndCondition(int currentPage, int pageSize,Brand brand) {
        SqlSession session=factory.openSession();
        BrandMapper mapper=session.getMapper(BrandMapper.class);

        int begin=(currentPage-1)*pageSize;
        int size=pageSize;

        String brandName = brand.getBrandName();
        if (brandName != null && brandName.length() > 0) {
            brand.setBrandName("%" + brandName + "%");
        }

        String companyName = brand.getCompanyName();
        if (companyName != null && companyName.length() > 0) {
            brand.setCompanyName("%" + companyName + "%");
        }

        List<Brand> rows = mapper.selectByPageAndCondition(begin,size,brand);

        int totalCount=mapper.selectTotalCountByCondition(brand);

        PageBean<Brand> pageBean=new PageBean<>();

        pageBean.setRow(rows);
        pageBean.setTotalCount(totalCount);

        session.close();

        return pageBean;
    }

    @Override
    public Brand selectById(int id) {
        SqlSession session=factory.openSession();
        BrandMapper mapper=session.getMapper(BrandMapper.class);

        Brand brand = mapper.selectById(id);

        session.close();

        return brand;
    }

    @Override
    public void update(Brand brand) {
        SqlSession session=factory.openSession();
        BrandMapper mapper=session.getMapper(BrandMapper.class);

        mapper.update(brand);

        session.commit();
        session.close();

    }
}
