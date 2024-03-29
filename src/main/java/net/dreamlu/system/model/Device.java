package net.dreamlu.system.model;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.dreamlu.mica.core.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 前端设备配置
 * </p>
 *
 * @author Administrator
 * @since 2019-08-13
 */
@Data
@TableName("t_device")
public class Device implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备序列号
     */
    private String code;

	/**
	 * 设备类型
	 */
	private String type;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备品牌
     */
    private Integer brandId;

    /**
     * 设备ip
     */
    private String ip;

    /**
     * 用户名
     */
    private String user;

    /**
     * 密码
     */
    private String passwd;
	/**
	 * 开门标志
	 */
	private Integer openFlag;
	/**
	 * 开门设备序列号
	 */
	private String openCode;
	/**
	 * 开门通道
	 */
	private String openChannel;

    /**
     * 主码流
     */
    private String mainStream;

    /**
     * 辅码流
     */
    private String subStream;

	/**
	 * 人脸宽度
	 */
	private Integer faceWidth;

	/**
	 * 人脸高度
	 */
	private Integer faceHeight;

	/**
	 * 经度
	 */
	private String lng;

	/**
	 * 纬度
	 */
	private String lat;

	/**
	 * 是否在线[0:不在线,1:在线]
	 */
    private Integer isOnline;

    /**
     * 最后心跳
     */
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
    private LocalDateTime heartTime;

	/**
	 * 创建人
	 */
	@TableField(value = "create_by",fill = FieldFill.INSERT)
	private String createBy;

	/**
	 * 创建时间
	 */
	@TableField(value = "create_time",fill = FieldFill.INSERT)
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	@TableField(value = "update_by",fill = FieldFill.UPDATE)
	private String updateBy;

	/**
	 * 修改时间
	 */
	@TableField(value = "update_time",fill = FieldFill.UPDATE)
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	private LocalDateTime updateTime;


}
