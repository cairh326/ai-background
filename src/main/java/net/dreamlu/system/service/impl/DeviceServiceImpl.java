package net.dreamlu.system.service.impl;

import net.dreamlu.system.mapper.DeviceMapper;
import net.dreamlu.system.model.Device;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamlu.system.model.CameraBrand;
import net.dreamlu.system.service.ICameraBrandService;
import net.dreamlu.system.service.IDeviceService;
import net.dreamlu.system.util.CommonConstant;
import net.dreamlu.system.util.StringUtils;
import net.dreamlu.system.vo.DeviceVO;
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
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device>
	implements IDeviceService {
	@Autowired
	private ICameraBrandService brandService;

	/**
	 * 设置主、辅码流
	 * @param entity 设备
	 */
	private void setStream(Device entity){
		if(entity.getType().equals(CommonConstant.DeviceType.CAMERA)) {
			CameraBrand cameraBrand = brandService.getById(entity.getBrandId());
			entity.setMainStream(String.format(cameraBrand.getMainStream(), entity.getUser(), entity.getPasswd(), entity.getIp()));
			entity.setSubStream(String.format(cameraBrand.getSubStream(), entity.getUser(), entity.getPasswd(), entity.getIp()));
			if(entity.getOpenFlag() == 1){
				if(StringUtils.isEmpty(entity.getOpenCode())){
					entity.setOpenCode(entity.getCode());
				}
			}else{
				entity.setOpenCode("");
				entity.setOpenChannel("");
			}
		}else{
			entity.setBrandId(null);
			entity.setMainStream("");
			entity.setSubStream("");
			entity.setOpenCode("");
			entity.setOpenChannel("");
		}

	}

	/**
	 * 重写插入方法
	 * @param entity 设备
	 * @return true or false
	 */
	@Override
	public boolean save(Device entity) {
		entity.setIsOnline(1);
		this.setStream(entity);
		return super.save(entity);
	}

	/**
	 * 重写更新方法
	 * @param entity 设备
	 * @return true or false
	 */
	@Override
	public boolean updateById(Device entity) {
		this.setStream(entity);
		return super.updateById(entity);
	}

	/**
	 * 更新设备心跳
	 * @param code 设备序列号
	 */
	@Override
	public void updateHeart(String code) {
		baseMapper.updateHeart(code);
	}

	/**
	 * 获取设备列表
	 * @return deviceVO list
	 */
	@Override
	public List<DeviceVO> getDeviceList() {
		return baseMapper.getDeviceList();
	}
}
