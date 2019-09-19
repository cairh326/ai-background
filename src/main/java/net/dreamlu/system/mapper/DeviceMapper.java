package net.dreamlu.system.mapper;

import net.dreamlu.system.model.Device;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.dreamlu.system.vo.DeviceVO;

import java.util.List;

/**
 * <p>
 * 摄像机配置 Mapper 接口
 * </p>
 *
 * @author Administrator
 * @since 2019-08-13
 */
public interface DeviceMapper extends BaseMapper<Device> {
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
