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
 * 参数配置
 * </p>
 *
 * @author Administrator
 * @since 2019-08-19
 */
@Data
@TableName("t_config")
public class Config implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String cName;

    /**
     * 值
     */
    private String cValue;

    /**
     * 描述
     */
    private String cDesc;

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
