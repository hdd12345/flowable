package hdd.flowable.service;

import hdd.flowable.entity.Holiday;

public interface HolidayService {
    Holiday insert(Holiday holiday);
    int deleteByPrimaryKey(String id);
    Holiday update(Holiday holiday);
    Holiday selectByPrimaryKey(String id);
}
