package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.entity.PublicHolidayEntity;
import com.ey.nwdashboard.entity.VacationEntity;
import com.ey.nwdashboard.repository.PublicHolidayRepository;
import com.ey.nwdashboard.repository.VacationRepository;
import com.ey.nwdashboard.service.VacationDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VacationDBServiceImpl implements VacationDBService {

    @Autowired
    VacationRepository vacationRepository;

    @Autowired
    PublicHolidayRepository publicHolidayRepository;

    @Override
    public List<VacationEntity> getVacations(String userGPN) {
        List<VacationEntity> vacationEntityList = vacationRepository.findAllByvacationUserGPN(userGPN);
        if(null != vacationEntityList && !vacationEntityList.isEmpty()){
            return vacationEntityList;
        }
        return null;
    }

    @Override
    public VacationEntity insertOrUpdateVacation(VacationEntity vacationEntity) {
        VacationEntity vacation = vacationRepository.save(vacationEntity);
        if(null != vacation){
            return vacation;
        }
        return null;
    }

    @Override
    public PublicHolidayEntity insertOrUpdatePublicHoliday(PublicHolidayEntity publicHolidayEntity) {
        if(null != publicHolidayEntity){
            publicHolidayEntity = publicHolidayRepository.save(publicHolidayEntity);
            if(null != publicHolidayEntity){
                return  publicHolidayEntity;
            }
        }
        return null;
    }

    @Override
    public List<PublicHolidayEntity> getPublicHolidays(LocalDate date) {
        if(null != date){
            List<PublicHolidayEntity> publicHolidayEntityList = publicHolidayRepository.findBypublicHolidayDate(date);
            if(null != publicHolidayEntityList){
                return publicHolidayEntityList;
            }
        }
        return null;
    }

    @Override
    public void deleteVacations(List<VacationEntity> existingVacations) {
        if(null != existingVacations && !existingVacations.isEmpty()){
            vacationRepository.deleteAll(existingVacations);
        }
    }
}
