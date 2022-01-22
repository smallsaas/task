package com.jfeat.am.module.team.services.common;

import com.jfeat.am.module.team.services.service.StaffService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class StaffUserKit {
    @Resource
    StaffService staffService;

    public Long getStaffIdByUserId(Long userId){
        return staffService.getStaffModelByuserId(userId).getId();
    }
    public Long getUserIdByStaffId(Long staffId){
        return staffService.retrieveMaster(staffId).getUserId();
    }

}
