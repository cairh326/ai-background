package net.dreamlu.system.service;

import net.dreamlu.system.model.Device;
import com.baomidou.mybatisplus.extension.service.IService;
import net.dreamlu.system.vo.DeviceVO;

import java.util.List;

/**
 * <p>
 * 摄像机配置 服务类
 * </p>
 *
 * @author Administrator
 * @since 2019-08-13
 */
public interface IDeviceService extends IService<Device> {
	/**
	 * 更新设备心跳
	 * @param code 设备序列号
	 */
	void updateHeart(String code);
	/**
	 * 获取设备列表
	 * @return deviceVO list
	 */
	List<DeviceVO> getDeviceList();
}
