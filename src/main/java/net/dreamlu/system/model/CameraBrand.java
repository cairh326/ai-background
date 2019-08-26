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
 * 摄像机品牌
 * </p>
 *
 * @author Administrator
 * @since 2019-08-13
 */
@Data
@TableName("t_camera_brand")
public class CameraBrand implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

	/**
	 * 品牌代码
	 */
	private String brandCode;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 主码流
     */
    private String mainStream;

    /**
     * 辅码流
     */
    private String subStream;

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
