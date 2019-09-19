package net.dreamlu.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.dreamlu.system.mapper.ConfigMapper;
import net.dreamlu.system.model.Config;
import net.dreamlu.system.service.IConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamlu.system.util.RestConstant;
import net.dreamlu.system.util.StringUtils;
import net.dreamlu.system.util.WebHttpUtil;
import net.dreamlu.system.vo.NetworkResponse;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 参数配置 服务实现类
 * </p>
 *
 * @author Administrator
 * @since 2019-08-19
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {
	/**
	 * 根据名称获取参数配置
	 * @param name 名称
	 * @return config
	 */
	@Override
	public Config getByName(String name) {
		if(StringUtils.isBlank(name)){
			return null;
		}
		QueryWrapper<Config> qw = new QueryWrapper<>();
		qw.eq("c_name",name);
		return super.getOne(qw);
	}
}
