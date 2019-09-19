package net.dreamlu.system.service;

import net.dreamlu.system.model.Config;
import com.baomidou.mybatisplus.extension.service.IService;
import net.dreamlu.system.vo.NetworkResponse;

import java.io.Serializable;

/**
 * <p>
 * 参数配置 服务类
 * </p>
 *
 * @author Administrator
 * @since 2019-08-19
 */
public interface IConfigService extends IService<Config> {
	/**
	 * 根据名称获取参数配置
	 * @param name 名称
	 * @return config
	 */
	Config getByName(String name);
}
