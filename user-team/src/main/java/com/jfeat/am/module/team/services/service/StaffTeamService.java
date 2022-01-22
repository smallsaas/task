package com.jfeat.am.module.team.services.service;

import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.StaffTeam;
import com.jfeat.am.module.team.services.persistence.model.Team;
import com.jfeat.crud.plus.CRUDServicePeer;

import java.util.List;


/**
 * <p>
 * 团队 service interface
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-20
 */

public interface StaffTeamService extends CRUDServicePeer<Staff,Team,StaffTeam> {
    // judge login user is team leader or not
    Boolean checkIsTeamLeader(long teamId, long staffId);

    Integer setStaffToTeamLeader(long teamId,List<Long> ids);

    Integer addStaffToTeam(long teamId, List<Long> ids);

    /**
     *   删除 批量删除 Staff 可以 复用于  批量删除 Leader  并且 从该组中删除
     * */

    Integer deleteStaffTeamLeader(long teamId, List<Long> ids);

    /**
     *      执行 删除操作的时候  会把所有的关于该 Team 的记录全部删除
     */
    Integer deleteTeam(long id);
}
