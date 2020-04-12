package hdd.flowable.dao;

import hdd.flowable.entity.Holiday;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayMapper {
    int insert(Holiday holiday);
    int deleteByPrimaryKey(String id);
    int update(Holiday holiday);
    Holiday selectByPrimaryKey(String id);
}
