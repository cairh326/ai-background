package net.dreamlu.system.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 网卡信息
 * </p>
 *
 * @author Administrator
 * @since 2019-08-21
 */
@Data
@TableName("t_net_card")
public class NetCard implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String cardName;

    /**
     * ip获取方式
     */
    private String method;

    /**
     * 首选DNS
     */
    private String dns;

    /**
     * 备用DNS
     */
    private String dns2;

    /**
     * 默认网关
     */
    private String gateway;

    /**
     * IPV4地址
     */
    private String ipv4;

    /**
     * 开机自启动
     */
    private Integer isAuto;

    /**
     * mac地址
     */
    private String mac;

    /**
     * 子网掩码
     */
    private String mask;


}
