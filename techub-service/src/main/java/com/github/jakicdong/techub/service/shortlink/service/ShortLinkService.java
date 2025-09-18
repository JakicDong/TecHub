package com.github.jakicdong.techub.service.shortlink.service;

import com.github.jakicdong.techub.api.model.vo.shortlink.ShortLinkVO;
import com.github.jakicdong.techub.api.model.vo.shortlink.dto.ShortLinkDTO;

import java.security.NoSuchAlgorithmException;

/*
* @author JakicDong
* @description 短链接服务接口
* @time 2025/9/18 11:25
*/
public interface ShortLinkService {


    /**
     * 创建短链接
     *
     * @param shortLinkDTO 包含原始URL和用户信息的数据传输对象
     * @return 包含短链接和原始URL的ShortLinkVO对象
     * @throws NoSuchAlgorithmException 如果生成短码时发生错误
     */
    ShortLinkVO createShortLink(ShortLinkDTO shortLinkDTO) throws NoSuchAlgorithmException;

    /**
     * 获取原始URL
     *
     * @param shortCode 短码
     * @return 包含原始URL的ShortLinkVO对象
     */
    ShortLinkVO getOriginalLink(String shortCode);
}