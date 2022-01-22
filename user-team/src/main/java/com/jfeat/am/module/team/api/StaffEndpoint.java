package com.jfeat.am.module.team.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.common.constant.tips.ErrorTip;
import com.jfeat.am.common.constant.tips.SuccessTip;
import com.jfeat.am.common.constant.tips.Tip;
import com.jfeat.am.common.controller.BaseController;
import com.jfeat.am.common.exception.BusinessCode;
import com.jfeat.am.common.exception.BusinessException;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.shiro.ShiroKit;
import com.jfeat.am.module.log.annotation.BusinessLog;
import com.jfeat.am.module.team.api.permission.TeamPermission;
import com.jfeat.am.module.team.services.common.StaffUserKit;
import com.jfeat.am.module.team.services.service.StaffService;
import com.jfeat.am.module.team.services.domain.dao.QueryStaffTeamDao;
import com.jfeat.am.module.team.services.domain.model.StaffModel;
import com.jfeat.am.module.team.services.domain.service.QueryStaffService;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.crud.plus.CRUD;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * <p>
 * 员工表 api
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-20
 */
@Api(value = "员工信息")
@RestController
@RequestMapping("/api/team/staffs")
public class StaffEndpoint extends BaseController {

    @Resource
    StaffService staffService;
    @Resource
    QueryStaffService queryStaffService;
    @Resource
    StaffUserKit staffUserKit;
    @Resource
    QueryStaffTeamDao queryStaffTeamDao;

    @ApiOperation(value = "创建新员工", response = StaffModel.class)
    @BusinessLog(name = "员工",value = "创建新员工")
    @Permission(TeamPermission.STAFF_ADD)
    @PostMapping
    public Tip createStaff(@Valid @RequestBody StaffModel entity) {
        int affect = staffService.createStaffModel(entity);
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "查看员工信息/管理员", response = StaffModel.class)
    @GetMapping("/{id}")
    public Tip getStaff(@PathVariable long id) {
        return SuccessTip.create(staffService.retrieveMaster(id));
    }

    @ApiOperation(value = "个人中心", response = StaffModel.class)
    @GetMapping("/details")
    public Tip staffCenter() {
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId(getHttpServletRequest()));
        if (staff==null){
            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
        }
        return SuccessTip.create(staffService.retrieveMaster(staff.getId()));
    }

    @ApiOperation(value = "修改员工信息", response = StaffModel.class)
    @BusinessLog(name = "员工",value = "修改员工信息")
    @Permission(TeamPermission.STAFF_EDIT)
    @PutMapping("/{id}")
    public Tip updateStaff(@PathVariable long id, @Valid @RequestBody StaffModel entity) {
        long userId = JWTKit.getUserId(getHttpServletRequest());
        Staff staff = staffService.retrieveMaster(id);
        if (staff.getUserId().compareTo(userId) == 0 || ShiroKit.hasPermission(TeamPermission.STAFF_EDIT)){
            entity.setId(id);
            entity.setUserId(staff.getUserId());
            int affect = staffService.updateStaffModel(entity);
            return SuccessTip.create(affect);
        }else{
            return ErrorTip.create(BusinessCode.NoPermission.getCode(),
                    BusinessCode.NoPermission.getMessage());
        }
    }

    @ApiOperation("删除，仅仅限于管理员")
    @DeleteMapping("/{id}")
    //@Permission(TeamPermission.TEAM_EDIT)
    public Tip deleteStaff(@PathVariable Long id) {
        return SuccessTip.create(staffService.deleteStaff(id));
    }

    @ApiOperation("模糊查询员工")
    @Permission(TeamPermission.STAFF_LIST)
    @GetMapping
    public Tip queryStaffs(Page<StaffModel> page,
                           @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                           @RequestParam(name = "firstName", required = false) String firstName,
                           @RequestParam(name = "lastName", required = false) String lastName,
                           @RequestParam(name = "sex", required = false) String sex,
                           @RequestParam(name = "careerPost", required = false) String careerPost,
                           @RequestParam(name = "phone", required = false) String phone,
                           @RequestParam(name = "email", required = false) String email,
                           @RequestParam(name = "weChat", required = false) String weChat,
                           @RequestParam(name = "microBlog", required = false) String microBlog,
                           @RequestParam(name = "twitter", required = false) String twitter) {
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Staff staff = new Staff();
        staff.setFirstName(firstName);
        staff.setLastName(lastName);
        staff.setSex(sex);
        staff.setPostPosition(careerPost);
        staff.setPhone(phone);
        staff.setEmail(email);
        staff.setWeChat(weChat);
        staff.setMicroBlog(microBlog);
        staff.setTwitter(twitter);

        page.setRecords(queryStaffService.findStaffPage(page, staff));

        return SuccessTip.create(page);
    }

    @ApiOperation(value = "获得创建人所属的所有小组")
    @GetMapping("/createPeopleTeam")
    public Tip findCreatePeopleTeam(HttpServletRequest httpServletRequest){
        Long staffId = staffUserKit.getStaffIdByUserId(JWTKit.getUserId(httpServletRequest));
        if(staffId == null )
            throw new BusinessException(BusinessCode.EmptyNotAllowed);
        return SuccessTip.create(queryStaffTeamDao.getTeamsByStaffId(staffId));
    }

    @ApiOperation(value = "获取当前登录用户的员工信息")
    @GetMapping("/staffInfo")
    public Tip getStaffInfoByUserId(){
        return SuccessTip.create(staffService.getStaffModelByuserId(JWTKit.getUserId()));
    }


    @ApiOperation(value = "获取当前登录用户")
    @GetMapping("/app/staff/profile")
    public Tip getCurrentStaff(){
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
        if(staff==null){
            throw new com.jfeat.crud.base.exception.BusinessException(com.jfeat.crud.base.exception.BusinessCode.PowerTeamLoginUserNoStaff);
        }

        StaffNameModel model = CRUD.castObject(staff, StaffNameModel.class);
        model.setStaffName(new UniversalName(staff.getFirstName(), staff.getLastName()).toString());

        return SuccessTip.create(model);
    }

    @ApiOperation(value = "个人中心")
    @GetMapping("/app/staff/center")
    public Tip center(){
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId(getHttpServletRequest()));
        if(staff==null){
            throw new com.jfeat.crud.base.exception.BusinessException(com.jfeat.crud.base.exception.BusinessCode.TeamLoginUserNoStaff);
        }
        return SuccessTip.create(queryStaffService.selfCenterRecord(staff.getId()));
    }
}
