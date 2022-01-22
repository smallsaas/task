package com.jfeat.am.power.base;

/**
 * Created by vincent on 2017/11/29.
 */
public enum PowerBusinessCode {
    PowerBusinessCodeBase(PowerBusinessException.PowerBusinessCodeBase, "业务错误"),

    /// 4001
    PowerBusinessNotImplement(PowerBusinessCodeBase.friendlyCode + 1, "服务未实现"),
    /// 4002
    PowerBusinessNotSupport(PowerBusinessCodeBase.friendlyCode + 2, "服务不支持"),
    /// 4003
    PowerBusinessDatabaseConnectFailure(PowerBusinessCodeBase.friendlyCode + 3, "数据库连接异常"),
    /// 4004
    PowerBusinessPartiallyAffectedError(PowerBusinessCodeBase.friendlyCode + 4, "部分操作有效"),
    /// 4005
    PowerBusinessErrorStatus(PowerBusinessCodeBase.friendlyCode + 5, "状态错误"),
    /// 4006
    PowerBusinessInvalidKey(PowerBusinessCodeBase.friendlyCode + 6, "非法键值"),
    /// 4007
    PowerBusinessDuplicateKey(PowerBusinessCodeBase.friendlyCode + 7, "键值重复"),
    /// 4008
    PowerBusinessDeleteAssociatedOne(PowerBusinessCodeBase.friendlyCode + 8, "尝试删除关联项"),
    /// 4009
    PowerBusinessDeleteNotEmptyOne(PowerBusinessCodeBase.friendlyCode + 9, "尝试删除非空项"),
    /// 4010
    PowerBusinessReservedCode10(PowerBusinessCodeBase.friendlyCode + 10, "预留业务错误"),
    /// 4011
    PowerBusinessDatabaseInsertError(PowerBusinessCodeBase.friendlyCode + 11, "数据库插入错误"),
    /// 4012
    PowerBusinessDatabaseUpdateError(PowerBusinessCodeBase.friendlyCode + 12, "数据库更新错误"),
    /// 4013
    PowerBusinessDatabaseDeleteError(PowerBusinessCodeBase.friendlyCode + 13, "数据库删除错误"),


    /// 4020
    PowerBusinessBadRequest(PowerBusinessCodeBase.friendlyCode + 20, "请求参数错误"),
    /// 4021
    PowerBusinessOutOfRange(PowerBusinessCodeBase.friendlyCode + 21, "超出范围"),
    /// 4022
    PowerBusinessEmptyNotAllowed(PowerBusinessCodeBase.friendlyCode + 22, "不允许空值"),
    /// 4023
    PowerBusinessReserved(PowerBusinessCodeBase.friendlyCode + 23, "预留"),
    /// 4024
    PowerBusinessReserved1(PowerBusinessCodeBase.friendlyCode + 24, "预留"),
    /// 4025
    PowerBusinessSyntaxError(PowerBusinessCodeBase.friendlyCode + 25, "语法错误"),
    /// 4025
    PowerBusinessJsonSyntaxError(PowerBusinessCodeBase.friendlyCode + 26, "JSON语法错误"),


    // 权限以及用户类
    /// 4040
    PowerBusinessAuthorizationError(PowerBusinessCodeBase.friendlyCode + 40, "权限异常"),
    /// 4041
    PowerBusinessNoPermission(PowerBusinessCodeBase.friendlyCode + 41, "没有权限"),
    /// 4042
    PowerBusinessUserAlreadyReg(PowerBusinessCodeBase.friendlyCode + 42, "该用户已经注册"),
    /// 4043
    PowerBusinessUserNotExisted(PowerBusinessCodeBase.friendlyCode + 43, "没有此用户"),
    /// 4044
    PowerBusinessPasswordIncorrect(PowerBusinessCodeBase.friendlyCode + 44, "密码不正确"),
    /// 4045
    PowerBusinessPasswordMismatch(PowerBusinessCodeBase.friendlyCode + 45, "两次输入密码不一致"),
    /// 4046
    PowerBusinessJsonLoginFailure(PowerBusinessCodeBase.friendlyCode + 46, "登录失败"),
    /// 4047
    PowerBusinessInvalidToken(PowerBusinessCodeBase.friendlyCode + 47, "非法验证码"),


    /// 文件操作异常类
    /// 4060
    PowerBusinessGeneralIOError(PowerBusinessCodeBase.friendlyCode + 60, "文件操作错误"),
    /// 4061
    PowerBusinessUploadFileError(PowerBusinessCodeBase.friendlyCode + 61, "上传文件出错"),
    /// 4062
    PowerBusinessFileReadingError(PowerBusinessCodeBase.friendlyCode + 62, "读取文件出错"),
    /// 4063
    PowerBusinessFileNotFound(PowerBusinessCodeBase.friendlyCode + 63, "找不到文件"),


    /// 数据格式错误
    /// 4100
    PowerBusinessDateError(PowerBusinessCodeBase.friendlyCode + 100, "日期错误"),
    /// 4101
    PowerBusinessNameRepetione(PowerBusinessCodeBase.friendlyCode + 101, "名字重复"),
    /// 4102
    PowerBusinessConnectDataError(PowerBusinessCodeBase.friendlyCode + 102, "关联数据出错"),
    /// 4103
    PowerBusinessIllegalSources(PowerBusinessCodeBase.friendlyCode + 103, "非法来源"),
    /// 4104
    PowerBusinessInvalidStatus(PowerBusinessCodeBase.friendlyCode + 104, "无效的状态"),


    //TODO, 添加业务错误编码

    /// 联系人业务错误
    /// 4100
    PowerContactBusinessCodeBase(PowerBusinessCodeBase.friendlyCode + 100, "联系人业务错误"),
    /// 4101
    PowerContactRelationSelf(PowerContactBusinessCodeBase.friendlyCode + 101, "不允许同一人建立联系人关系"),
    /// 4102
    PowerContactLimitedQueryCriteria(PowerContactBusinessCodeBase.friendlyCode + 102, "查询条件受限"),
    PowerDateVersionError(PowerContactBusinessCodeBase.friendlyCode + 103, "数据版本不一致"),

    /// 组与员工业务错误
    /// 4200
    PowerTeamBusinessCodeBase(PowerBusinessCodeBase.friendlyCode + 200, "组业务错误"),
    PowerTeamStaffNotInTeam(PowerTeamBusinessCodeBase.friendlyCode + 201, "组员不在组内"),
    PowerTeamLoginUserNoStaff(PowerTeamBusinessCodeBase.friendlyCode + 202, "登录用户未绑定相关组员"),
    PowerTeamDeleteSuperStaffNotSupport(PowerTeamBusinessCodeBase.friendlyCode + 203, "不支持删除超级用户"),
    PowerTeamStaffNotTheTeamLeader(PowerTeamBusinessCodeBase.friendlyCode + 204, "非该组Leader"),
    PowerTeamStaffNotExists(PowerTeamBusinessCodeBase.friendlyCode + 204, "此组员不存在"),
    PowerTeamStaffLeaderOnly(PowerTeamBusinessCodeBase.friendlyCode + 205, "非法执行Leader权限"),
    contactNumberNotExist(PowerTeamBusinessCodeBase.friendlyCode + 206, "联系人编号不存在"),


    //// 4300
    PowerEndBusinessCodeBase(PowerBusinessCodeBase.friendlyCode + 300, "系统业务错误"),

    // 4400 邮箱类
    PowerEmailNotExisted(PowerBusinessCodeBase.friendlyCode + 401, "邮箱不存在"),

    // 4500 webservice类
    WebServiceConfigIncomplete(PowerBusinessCodeBase.friendlyCode + 500,"WebService配置不完整"),
    WebServiceConnectError(PowerBusinessCodeBase.friendlyCode + 501,"WebService连接失败"),

    //4600 读数估算类
    EstimateError(PowerBusinessCodeBase.friendlyCode + 600,"无法进行估算"),
    ProfileNotExist(PowerBusinessCodeBase.friendlyCode + 601,"profile不存在或缺失"),
    IcpNotExist(PowerBusinessCodeBase.friendlyCode + 602,"icp不存在");


    /**
     *  enum PowerBusinessCode
     */
    private int friendlyCode;
    private String friendlyMsg;

    private PowerBusinessCode(int code, String message) {
        this.friendlyCode = code;
        this.friendlyMsg = message;
    }

    public int getCode() {
        return this.friendlyCode;
    }

    public void setCode(int code) {
        this.friendlyCode = code;
    }

    public String getMessage() {
        return this.friendlyMsg;
    }

    public void setMessage(String message) {
        this.friendlyMsg = message;
    }
}
