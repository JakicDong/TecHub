package com.github.jakicdong.techub.service.user.repository.entity;

import lombok.Data;

import java.io.Serializable;

/*
* @author JakicDong
* @description IP信息
* @time 2025/7/3 19:47
*/

@Data
public class IpInfo implements Serializable {
    private static final long serialVersionUID = -4612222921661930429L;

    private String firstIp;

    private String firstRegion;

    private String latestIp;

    private String latestRegion;
}