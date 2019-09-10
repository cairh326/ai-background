package net.dreamlu.system.service;

import net.dreamlu.system.model.Box;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 本机配置 服务类
 * </p>
 *
 * @author Administrator
 * @since 2019-09-09
 */
public interface IBoxService extends IService<Box> {
	/**
	 * 获取本地配置
	 * @return box
	 */
	Box getCurrentBox();
}
