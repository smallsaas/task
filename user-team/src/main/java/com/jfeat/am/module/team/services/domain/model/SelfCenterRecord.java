package com.jfeat.am.module.team.services.domain.model;

import com.jfeat.am.module.team.services.persistence.model.Staff;

import java.util.List;

public class SelfCenterRecord extends Staff{
    List<String> teamLeader;
    String loginAccount;
    String loginName;
    List<String> roles;
}
