package com.jfeat.am.module.team.services.service;

import com.jfeat.am.module.team.services.domain.model.StaffModel;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.crud.plus.CRUDServiceOnly;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 员工表 service interface
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-20
 */

public interface StaffService extends CRUDServiceOnly<Staff> {
    Staff queryStaffByUserId(long userId);

    /**
     *   同时删除 user 表中对应的记录
     * */

    @Transactional
    Integer deleteStaff(long id);
    /**
     * 创建staff时同时创建sysuser
     */
    @Transactional
    Integer createStaffModel(StaffModel entity);

    /**
     * 根据userId获得StaffModel对象
     */
    StaffModel getStaffModelByuserId(Long userId);

    /**
     * 更新
     * @param entity entity
     * @return
     */
    Integer updateStaffModel(StaffModel entity);
}
