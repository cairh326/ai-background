package net.dreamlu.system.service.impl;

import net.dreamlu.system.mapper.CameraMapper;
import net.dreamlu.system.model.Camera;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamlu.system.model.CameraBrand;
import net.dreamlu.system.service.ICameraBrandService;
import net.dreamlu.system.service.ICameraService;
import net.dreamlu.system.vo.CameraVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 摄像机配置 服务实现类
 * </p>
 *
 * @author Administrator
 * @since 2019-08-13
 */
@Service
public class CameraServiceImpl extends ServiceImpl<CameraMapper, Camera> implements ICameraService {

	@Autowired
	private ICameraBrandService brandService;

	private void setStream(Camera entity){
		CameraBrand cameraBrand = brandService.getById(entity.getBrandId());
		entity.setMainStream(String.format(cameraBrand.getMainStream(), entity.getUser(),
			entity.getPasswd(), entity.getIp()));
		entity.setSubStream(String.format(cameraBrand.getSubStream(), entity.getUser(),
			entity.getPasswd(), entity.getIp()));
	}

	@Override
	public boolean save(Camera entity) {
		this.setStream(entity);
		return super.save(entity);
	}

	@Override
	public boolean updateById(Camera entity) {
		this.setStream(entity);
		return super.updateById(entity);
	}

	@Override
	public void updateHeart(String code) {
		baseMapper.updateHeart(code);
	}

	@Override
	public List<CameraVO> getCameraList() {
		return baseMapper.getCameraList();
	}
}
