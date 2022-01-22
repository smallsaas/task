package com.jfeat.am.module.team.services.service;

import com.jfeat.am.module.team.services.domain.model.AllTeamIncludeStaff;
import com.jfeat.am.module.team.services.domain.model.StaffTeamModel;
import com.jfeat.am.module.team.services.domain.model.TeamAndStaffModel;
import com.jfeat.am.module.team.services.domain.model.TeamModel;
import com.jfeat.am.module.team.services.persistence.model.StaffTeam;
import com.jfeat.am.module.team.services.persistence.model.Team;
import com.jfeat.crud.plus.CRUDServiceOnly;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 团队 service interface
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-20
 */

public interface TeamService  extends CRUDServiceOnly<Team> {


    /**
     *    判断某个 Staff 在某个 Team 中是否是该 Team 的 Leader 通过 staffId 以及 TeamId 去查找
     * */
    boolean isStaffTeamLeader(long teamId, long staffId);

    /*//判断某个Team是否有Leader
    public void  isHasTeamLeader(long teamId);*/

    // 判断某个团队是否有 Staff  如果存在 Staff 无法执行操作

    /**
     *
     * */
    void judgeWhetherStaffInTeam(long id);

    /**
     *  //  指派某个Staff为TeamLeader
     * */
    StaffTeam setStaffToTeamLeader(long teamId, Long staffId);

    /**
     *  // 批量添加Staff到某个team
     * */
    Integer batchAddStaffToTeam(long teamId, List<Long> ids);

    /**
     *  // 某个Team批量删除Staff
     * */
    Integer batchDeleteStaffToTeam(Long teamId, List<Long> ids);

    /**
     *  // 判断某个Staff是否在某个Team
     * */
    Boolean isStaffInTeam(long teamId, long staffId);

    /**
     *  // 判断某个Staff是否在某个Team
     * */
    Integer deleteStaffInTeam(long teamId,long staffId);

    /**
     *   我的所有团队
     * */
    List<StaffTeamModel> allMyTeam(long staffId);


    // 重构 新建 Team Service
    @Transactional
    Integer createTeamIncludeStaff(TeamAndStaffModel model);

    // 重构 修改 Team Service
    @Transactional
    Integer updateTeamIncludeStaff(long id,TeamAndStaffModel model);

    /**
     *  删除 该 Team的所有成员，包括 Leader  提供 给 更新 Team的staff 以及 Leader  的时候使用
     * */
    Integer deleteALlStaff(long id);

    // 查找Team   返回 Team 中所有成员的信息。
    TeamModel teamBuilding(long id);


    /*void deleteTeam(long id);*/

    /**
     *      删除某个Team  ，当 Team下面有记录的时候，不允许删除
     * */
    public Integer deleteTeam(long id,Integer version);


    /**
     *  所有的团队以及团队下面的所有的员工
     * */
    List<AllTeamIncludeStaff> allTeamAndStaff();

    /**
     *  登录用户作为Leader的所有的Team下面的所有的员工
     * */
    List<AllTeamIncludeStaff> allTeamLeaderAndStaff(long staffId);
}
