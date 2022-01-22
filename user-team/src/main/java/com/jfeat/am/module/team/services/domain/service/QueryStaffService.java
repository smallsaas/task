package com.jfeat.am.module.team.services.domain.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.team.services.domain.model.NameModel;
import com.jfeat.am.module.team.services.domain.model.SelfCenterRecord;
import com.jfeat.am.module.team.services.domain.model.StaffModel;
import com.jfeat.am.module.team.services.persistence.model.Staff;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface QueryStaffService {
    List<StaffModel> findStaffPage(Page<StaffModel> page, Staff staff );

    /**
     *  查找某个组中的所有成员
     *  @param teamId
     * */
    List<NameModel> searchStaffInTeam(long teamId);

    // ID 返回 staff 的信息

    SelfCenterRecord selfCenterRecord(long id);
}