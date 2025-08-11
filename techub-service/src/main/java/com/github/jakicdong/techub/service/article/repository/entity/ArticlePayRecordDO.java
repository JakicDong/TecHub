package com.github.jakicdong.techub.service.article.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jakicdong.techub.api.model.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.github.jakicdong.techub.api.model.enums.pay.ThirdPayWayEnum;

import java.util.Date;
/*
* @author JakicDong
* @description 记录支付状态
* @time 2025/8/11 17:33
*/

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("article_pay_record")
public class ArticlePayRecordDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /**
     * 支付用户
     */
    private Long payUserId;

    /**
     * 收款用户
     */
    private Long receiveUserId;

    /**
     * 文章
     */
    private Long articleId;

    /**
     * 支付状态
     */
    private Integer payStatus;

    /**
     * 邮件通知用户的时间
     */
    private Date notifyTime;

    /**
     * 通知确认次数
     */
    private Integer notifyCnt;

    /**
     * 备注信息
     */
    private String notes;

    /**
     * - 个人收款码场景： 用于验证合法性的code
     * - 微信支付场景： 这里是传递给第三方系统的唯一外部订单号
     */
    private String verifyCode;

    /**
     * 支付金额
     * 说明：对人个人收款码场景，无法知道具体的收款金额
     */
    private Integer payAmount;

    /**
     * 微信支付回传的关键参数
     * h5支付： 返回的是支付中间页地址
     * jspai支付：返回的是唤起支付的prePayId
     * native支付：返回的是用于生成微信支付二维码的字符串
     */
    private String prePayId;

    /**
     * prePayId的有效截止时间
     */
    private Date prePayExpireTime;

    /**
     * 支付方式
     *
     * @see ThirdPayWayEnum#getPay()
     */
    private String payWay;

    /**
     * 记录的是三方交易单号
     */
    private String thirdTransCode;

    /**
     * 回调的支付成功/失败时间
     */
    private Date payCallbackTime;

}
