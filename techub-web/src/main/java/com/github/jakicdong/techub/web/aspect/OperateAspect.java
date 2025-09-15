package com.github.jakicdong.techub.web.aspect;

import com.alibaba.fastjson.JSON;
import com.github.jakicdong.techub.api.model.constant.KafkaTopicConstant;
import com.github.jakicdong.techub.api.model.context.ReqInfoContext;
import com.github.jakicdong.techub.api.model.enums.OperateTypeEnum;
import com.github.jakicdong.techub.api.model.vo.comment.CommentSaveReq;
import com.github.jakicdong.techub.api.model.vo.kafka.ArticleKafkaMessageDTO;
import com.github.jakicdong.techub.core.annotation.RecordOperate;
import com.github.jakicdong.techub.core.util.IpUtil;
import com.github.jakicdong.techub.core.util.ServletUtils;
import com.github.jakicdong.techub.service.article.repository.entity.ArticleDO;
import com.github.jakicdong.techub.service.article.service.ArticleReadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class OperateAspect {

    @Autowired
    ArticleReadService articleReadService;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 1、定义切入点
     * 2、横切逻辑
     * 3、织入
     */

    @Pointcut(value = "@annotation(recordOperate)")
    public void pointcut(RecordOperate recordOperate) {
    }

    /**
     * 处理完请求后执行
     */
    @AfterReturning(pointcut = "@annotation(recordOperate)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, RecordOperate recordOperate, Object jsonResult) {
        handleLog(joinPoint, recordOperate, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(recordOperate)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, RecordOperate recordOperate, Exception e) {
        handleLog(joinPoint, recordOperate, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, RecordOperate recordOperate, final Exception e,
                             Object jsonResult) {
        try {
            log.info("=== OperateAspect 开始执行 ===");
            
            // 请求的地址
            String ip = IpUtil.getLocalIp4Address();

            HttpServletRequest request = ServletUtils.getRequest();
            // URL
            String requestURI = "http://xxx.xxx.xxx.xxx/default_url";
            // 设置请求方式
            String method = "defaultMethod";
            if (ObjectUtils.isNotEmpty(request)) {
                requestURI = request.getRequestURI();
                method = request.getMethod();
            }

            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            // 处理设置注解上的参数
            // 设置action动作
            String businessType = recordOperate.businessType();
            // 设置标题
            String title = recordOperate.title();
            
            log.info("切面执行 - 类: {}, 方法: {}, 标题: {}, 业务类型: {}, URI: {}", 
                    className, methodName, title, businessType, requestURI);
            
            String paramStr = requestValue(joinPoint, request, title, requestURI);
            log.info("原始参数字符串: '{}'", paramStr);
            
            if (StringUtils.isBlank(paramStr)) {
                log.warn("参数字符串为空，跳过Kafka消息发送");
                return;
            }
            
            String[] params = paramStr.split("&");
            log.info("解析到的参数: {}", Arrays.toString(params));

            this.sendKafkaMessage(params);

        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    private void sendKafkaMessage(String[] params) {
        log.info("=== 开始发送Kafka消息 ===");
        
        try {
            // 参数验证
            if (params == null || params.length < 2) {
                log.error("参数不足，无法解析: {}", Arrays.toString(params));
                return;
            }

            // 谁向谁的那篇文章点赞了
            String sourceName = ReqInfoContext.getReqInfo().getUser().getUserName();
            String articleIdStr = params[0].split("=")[1];
            Long articleId = Long.parseLong(articleIdStr);
            String typeStr = params[1].split("=")[1];
            // 2-点赞、4-取消点赞；3-收藏、5-取消点赞；1-评论；
            int type = Integer.parseInt(typeStr);
            String typeName = OperateTypeEnum.fromCode(type).getDesc();
            
            log.info("解析参数 - 用户: {}, 文章ID: {}, 操作类型: {} ({})", 
                    sourceName, articleId, type, typeName);
            
            ArticleDO articleDO = articleReadService.queryBasicArticle(articleId);
            String articleTitle = articleDO.getTitle();
            Long targetUserId = articleDO.getUserId();

            ArticleKafkaMessageDTO articleKafkaMessageDTO = new ArticleKafkaMessageDTO();
            articleKafkaMessageDTO.setType(type);
            articleKafkaMessageDTO.setSourceUserName(sourceName);
            articleKafkaMessageDTO.setTargetUserId(targetUserId);
            articleKafkaMessageDTO.setArticleTitle(articleTitle);
            articleKafkaMessageDTO.setTypeName(typeName);
            
            String messageJson = JSON.toJSONString(articleKafkaMessageDTO);
            log.info("准备发送Kafka消息 - 主题: {}, 消息: {}", 
                    KafkaTopicConstant.ARTICLE_TOPIC, messageJson);
            
            kafkaTemplate.send(KafkaTopicConstant.ARTICLE_TOPIC, messageJson);
            
            log.info("Kafka消息发送完成");
            
        } catch (Exception e) {
            log.error("发送Kafka消息失败", e);
            throw e; // 重新抛出异常
        }
    }


    /**
     * 获取请求的参数，放到log中
     *
     * @throws Exception 异常
     */
    private String requestValue(JoinPoint joinPoint, HttpServletRequest request,
                                String title, String requestURI) throws Exception {
        String requestMethod = request.getMethod();
        String param = "";
        
        log.info("requestValue - 请求方法: {}, URI: {}, 标题: {}", requestMethod, requestURI, title);
        
        // 评论
        if ((HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod))
                && "/comment/api/post".equals(requestURI)) {
            log.info("处理评论请求");
            Object[] args = joinPoint.getArgs();
            CommentSaveReq commentSaveReq = (CommentSaveReq) args[0];
            Long parentCommentId = commentSaveReq.getParentCommentId();
            if (ObjectUtils.isEmpty(parentCommentId)) {
                Long articleId = commentSaveReq.getArticleId();
                // 评论
                param = "articleId=" + articleId + "&type=" + 6;
            } else {
                // 删除评论
                Long articleId = commentSaveReq.getArticleId();
                param = "articleId=" + articleId + "&type=" + 8;
            }
            log.info("评论参数: {}", param);

        } else {
            // 点赞 收藏
            if (ObjectUtils.isNotEmpty(request)) {
                log.info("处理点赞/收藏请求");
                Map<String, String[]> parameterMap = request.getParameterMap();
                log.info("请求参数: {}", parameterMap);
                
                if (StringUtils.equals(title, "article")) {
                    String[] articleIdArray = parameterMap.get("articleId");
                    String[] typeArray = parameterMap.get("type");
                    
                    if (articleIdArray != null && articleIdArray.length > 0 && 
                        typeArray != null && typeArray.length > 0) {
                        String articleId = articleIdArray[0];
                        String type = typeArray[0];
                        param = "articleId=" + articleId + "&type=" + type;
                        log.info("解析到参数 - articleId: {}, type: {}", articleId, type);
                    } else {
                        log.warn("缺少必要参数 - articleId: {}, type: {}", articleIdArray, typeArray);
                    }
                } else {
                    log.warn("标题不匹配 - 期望: 'article', 实际: '{}'", title);
                }
            } else {
                log.warn("请求对象为空");
            }
        }
        
        log.info("最终参数: '{}'", param);
        return param;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (ObjectUtils.isNotEmpty(o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params += jsonObj.toString() + " ";
                    } catch (Exception e) {
                        log.info(e.toString());
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}


