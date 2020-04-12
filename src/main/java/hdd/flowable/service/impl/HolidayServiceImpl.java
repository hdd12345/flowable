package hdd.flowable.service.impl;

import hdd.flowable.dao.HolidayMapper;
import hdd.flowable.entity.Holiday;
import hdd.flowable.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("holidayService")
public class HolidayServiceImpl implements HolidayService {
    @Autowired
    private HolidayMapper holidayMapper;
    @Override
    public Holiday insert(Holiday holiday) {
        holiday.setId(UUID.randomUUID().toString().replace("-",""));
        if(holidayMapper.insert(holiday)>0){
            return holidayMapper.selectByPrimaryKey(holiday.getId());
        }
        return null;
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return holidayMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Holiday update(Holiday holiday) {
        if(holidayMapper.update(holiday)>0){
            return holiday;
        }
        return null;
    }

    @Override
    public Holiday selectByPrimaryKey(String id) {
        return holidayMapper.selectByPrimaryKey(id);
    }
}
