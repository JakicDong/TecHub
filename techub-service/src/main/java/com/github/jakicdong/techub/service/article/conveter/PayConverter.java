package com.github.jakicdong.techub.service.article.conveter;


import com.fasterxml.jackson.databind.JsonNode;
import com.github.hui.quick.plugin.base.Base64Util;
import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeGenV3;
import com.github.jakicdong.techub.api.model.enums.pay.ThirdPayWayEnum;
import com.github.jakicdong.techub.api.model.vo.article.dto.ArticlePayInfoDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.UserPayCodeDTO;
import com.github.jakicdong.techub.core.util.JsonUtil;
import com.github.jakicdong.techub.core.util.PriceUtil;
import com.github.jakicdong.techub.service.article.repository.entity.ArticlePayRecordDO;
import org.apache.commons.lang3.StringUtils;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
* @author JakicDong
* @description
* @time 2025/8/11 19:36
*/
public class PayConverter {

    public static ArticlePayInfoDTO toPay(ArticlePayRecordDO record) {
        ArticlePayInfoDTO info = new ArticlePayInfoDTO();
        info.setPayId(record.getId());
        info.setPayUserId(record.getPayUserId());
        info.setPayStatus(record.getPayStatus());
        info.setReceiveUserId(record.getReceiveUserId());
        info.setArticleId(record.getArticleId());
        info.setPayAmount(PriceUtil.toYuanPrice(record.getPayAmount()));
        ThirdPayWayEnum payWay = ThirdPayWayEnum.ofPay(record.getPayWay());
        if (payWay != null) {
            info.setPayWay(payWay.getPay());
            info.setPrePayExpireTime(record.getPrePayExpireTime() == null ? null : record.getPrePayExpireTime().getTime());
            info.setPrePayId(genQrCode(record.getPrePayId()));
        }
        return info;
    }


    /**
     * 格式化收款码
     *
     * @return key: 渠道   value: 收款二维码base64格式
     */
    public static Map<String, String> formatPayCode(String dbCode) {
        if (StringUtils.isBlank(dbCode)) {
            return Collections.emptyMap();
        }

        JsonNode node = JsonUtil.toNode(dbCode);
        Map<String, String> result = new HashMap<>();
        node.fields().forEachRemaining(kv -> {
            String key = kv.getKey();
            String value = kv.getValue().asText();
            result.put(key, genQrCode(value));
        });
        return result;
    }

    public static Map<String, UserPayCodeDTO> formatPayCodeInfo(String dbCode) {
        if (StringUtils.isBlank(dbCode)) {
            return Collections.emptyMap();
        }

        JsonNode node = JsonUtil.toNode(dbCode);
        Map<String, UserPayCodeDTO> result = new HashMap<>();
        node.fields().forEachRemaining(kv -> {
            String key = kv.getKey();
            String value = kv.getValue().asText();
            result.put(key, new UserPayCodeDTO(genQrCode(value), value));
        });
        return result;
    }


    public static String genQrCode(String txt) {
        try {
            BufferedImage img = QrCodeGenV3.of(txt).setSize(500).asImg();
            return Base64Util.encode(img, "png");
        } catch (Exception e) {
            return txt;
        }
    }
}

