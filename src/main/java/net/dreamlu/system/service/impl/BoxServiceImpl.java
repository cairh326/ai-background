package net.dreamlu.system.service.impl;

import net.dreamlu.system.mapper.BoxMapper;
import net.dreamlu.system.model.Box;
import net.dreamlu.system.service.IBoxService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 本机配置 服务实现类
 * </p>
 *
 * @author Administrator
 * @since 2019-09-09
 */
@Service
public class BoxServiceImpl extends ServiceImpl<BoxMapper, Box> implements IBoxService {

	/**
	 * 获取本地配置
	 * @return box
	 */
	@Override
	public Box getCurrentBox() {
		return super.list().get(0);
	}
}
