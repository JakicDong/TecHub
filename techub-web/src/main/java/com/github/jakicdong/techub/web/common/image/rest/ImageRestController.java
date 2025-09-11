package com.github.jakicdong.techub.web.common.image.rest;

import com.github.jakicdong.techub.api.model.vo.ResVo;
import com.github.jakicdong.techub.api.model.vo.constants.StatusEnum;
import com.github.jakicdong.techub.core.permission.Permission;
import com.github.jakicdong.techub.core.permission.UserRole;
import com.github.jakicdong.techub.service.image.service.ImageService;
import com.github.jakicdong.techub.web.common.image.vo.ImageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/*
* @author JakicDong
* @description 图片上传接口,要求登录之后才允许操作
* @time 2025/9/10 16:10
*/
@Permission(role = UserRole.LOGIN)
@RequestMapping(path = {"image/", "admin/image/", "api/admin/image/",})
@RestController
@Slf4j
public class ImageRestController {
    @Autowired
    private ImageService imageService;

    /**
     * 图片上传
     *
     * @return
     */

    @RequestMapping(path = "upload")
    public ResVo<ImageVo> upload(HttpServletRequest request) {
        ImageVo imageVo = new ImageVo();
        try {
            String imagePath = imageService.saveImg(request);
            imageVo.setImagePath(imagePath);
        } catch (Exception e) {
            log.error("save upload file error!", e);
            return ResVo.fail(StatusEnum.UPLOAD_PIC_FAILED);
        }
        return ResVo.ok(imageVo);
    }




}
