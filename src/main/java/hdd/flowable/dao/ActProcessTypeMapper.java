package hdd.flowable.dao;

import hdd.flowable.entity.ActProcessType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActProcessTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActProcessType record);
    List<ActProcessType> selectAll();
    int insertSelective(ActProcessType record);

    ActProcessType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActProcessType record);

    int updateByPrimaryKey(ActProcessType record);
}