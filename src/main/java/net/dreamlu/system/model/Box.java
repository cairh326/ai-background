package net.dreamlu.system.model;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.dreamlu.mica.core.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 本机配置
 * </p>
 *
 * @author Administrator
 * @since 2019-09-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_box")
public class Box implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 序列号
     */
    private String serialNo;

    /**
     * 路数上线
     */
    private Integer upperLimit;

    /**
     * 经度
     */
    private String lng;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 平台地址
     */
    private String platformAddress;

    /**
     * 平台端口
     */
    private String platformPort;

    /**
     * 平台用户
     */
    private String platformUser;

    /**
     * 平台密码
     */
    private String platformPwd;

    /**
     * MQ地址
     */
    private String mqAddress;

    /**
     * MQ端口
     */
    private String mqPort;

    /**
     * MQ用户
     */
    private String mqUser;

    /**
     * MQ密码
     */
    private String mqPwd;

    /**
     * 系统版本
     */
    private String version;

    /**
     * 绑定状态
     */
    private Integer bindingStatus;

    /**
     * 绑定时间
     */
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
    private LocalDateTime bingdingTime;


}
