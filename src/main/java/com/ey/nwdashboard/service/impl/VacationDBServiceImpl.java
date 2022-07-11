package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.entity.VacationEntity;
import com.ey.nwdashboard.repository.VacationRepository;
import com.ey.nwdashboard.service.VacationDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacationDBServiceImpl implements VacationDBService {

    @Autowired
    VacationRepository vacationRepository;

    @Override
    public List<VacationEntity> getVacations(String userGPN) {
        List<VacationEntity> vacationEntityList = vacationRepository.findAllByvacationUserGPN(userGPN);
        if(null != vacationEntityList && !vacationEntityList.isEmpty()){
            return vacationEntityList;
        }
        return null;
    }
}
