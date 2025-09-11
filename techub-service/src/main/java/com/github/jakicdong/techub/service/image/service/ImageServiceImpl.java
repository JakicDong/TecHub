package com.github.jakicdong.techub.service.image.service;


import com.github.hui.quick.plugin.base.constants.MediaType;
import com.github.jakicdong.techub.api.model.exception.ExceptionUtil;
import com.github.jakicdong.techub.api.model.vo.constants.StatusEnum;
import com.github.jakicdong.techub.service.image.oss.ImageUploader;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/*
* @author JakicDong
* @description 图片服务实现类
* @time 2025/9/10 16:30
*/
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageUploader imageUploader;
    /**
     * 外网图片转存缓存
     */
    private Cache<String, String> imgReplaceCache = CacheBuilder
            .newBuilder()
            .maximumSize(300)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    @Override
    public String saveImg(HttpServletRequest request) {
        MultipartFile file = null;
        if (request instanceof MultipartHttpServletRequest) {
            file = ((MultipartHttpServletRequest) request).getFile("image");
        }
        if (file == null) {
            throw ExceptionUtil.of(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "缺少需要上传的图片");
        }

        // 目前只支持 jpg, png, webp 等静态图片格式
        String fileType = validateStaticImg(file.getContentType());
        if (fileType == null) {
            throw ExceptionUtil.of(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "图片只支持png,jpg,gif");
        }

        try {
            // 先获取图像摘要，根据摘要确定缓存中是否已经包含图像。
            String digest = calculateSHA256(file.getInputStream());
            String ans = imgReplaceCache.getIfPresent(digest);
            if (StringUtils.isBlank(ans)) {
                ans = imageUploader.upload(file.getInputStream(), fileType);
                imgReplaceCache.put(digest, ans);
            }
            return ans;
        } catch (IOException | NoSuchAlgorithmException e) {
            log.error("Parse img from httpRequest to BufferedImage error! e:", e);
            throw ExceptionUtil.of(StatusEnum.UPLOAD_PIC_FAILED);
        }
    }


    /**
     * 图片格式校验
     *
     * @param mime
     * @return
     */
    private String validateStaticImg(String mime) {
        if ("svg".equalsIgnoreCase(mime)) {
            // fixme 上传文件保存到服务器本地时，做好安全保护, 避免上传了有攻击性的脚本
            return "svg";
        }

        if (mime.contains(MediaType.ImageJpg.getExt())) {
            mime = mime.replace("jpg", "jpeg");
        }
        for (MediaType type : ImageUploader.STATIC_IMG_TYPE) {
            if (type.getMime().equals(mime)) {
                return type.getExt();
            }
        }
        return null;
    }

    /**
     *  图片摘要生成
     *
     * @param inputStream
     * @return
     */
    private String calculateSHA256(InputStream inputStream) throws NoSuchAlgorithmException, IOException {

        inputStream = toByteArrayInputStream(inputStream);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] buffer = new byte[1024];
        int bytesRead;

        // 读取 InputStream 并更新到 MessageDigest
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            md.update(buffer, 0, bytesRead);
        }

        // 获取摘要并将其转换为十六进制字符串
        byte[] digest = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            hexString.append(String.format("%02x", b));
        }
        inputStream.reset();
        return hexString.toString();
    }

    /**
     * 转换为字节数组输入流，可以重复消费流中数据
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public ByteArrayInputStream toByteArrayInputStream(InputStream inputStream) throws IOException {
        if (inputStream instanceof ByteArrayInputStream) {
            return (ByteArrayInputStream) inputStream;
        }
        //相当于c就是缓存区的大小,然后建立了一个1kb的缓存区,然后从复制构造的br来1kb循环读取,然后写入到bos中,最后返回bos.toByteArray()
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            BufferedInputStream br = new BufferedInputStream(inputStream);
            byte[] b = new byte[1024];
            for (int c; (c = br.read(b)) != -1; ) {
                bos.write(b, 0, c);
            }
            // 主动告知回收
            b = null;
            br.close();
            inputStream.close();
            return new ByteArrayInputStream(bos.toByteArray());
        }
    }

}
