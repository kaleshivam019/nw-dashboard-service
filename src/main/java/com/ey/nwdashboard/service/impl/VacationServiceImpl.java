package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.entity.VacationEntity;
import com.ey.nwdashboard.model.VacationModel;
import com.ey.nwdashboard.model.VacationResponse;
import com.ey.nwdashboard.service.UserDBService;
import com.ey.nwdashboard.service.VacationDBService;
import com.ey.nwdashboard.service.VacationService;
import com.ey.nwdashboard.utils.DashboardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacationServiceImpl implements VacationService {

    @Autowired
    UserDBService userDBService;

    @Autowired
    VacationDBService vacationDBService;

    /**
     * This method is responsible to fetch all the vacations of a user
     * @param userGPN
     * @return
     */
    @Override
    public VacationResponse getUserVacations(String userGPN) {
        VacationResponse vacationResponse = new VacationResponse();

        if(userDBService.isExistingUser(userGPN)){
            List<VacationEntity> vacationEntityList = vacationDBService.getVacations(userGPN);
            if(null != vacationEntityList && !vacationEntityList.isEmpty()){
                List<VacationModel> vacationModelList = DashboardUtils.convertVacationEntityToModel(vacationEntityList);

                if(null != vacationModelList && !vacationModelList.isEmpty()){
                    vacationResponse.setVacations(vacationModelList);
                }
            }
        }
        return vacationResponse;
    }
}
