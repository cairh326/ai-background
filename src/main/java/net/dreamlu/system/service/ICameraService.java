package net.dreamlu.system.service;

import net.dreamlu.system.model.Camera;
import com.baomidou.mybatisplus.extension.service.IService;
import net.dreamlu.system.vo.CameraVO;

import java.util.List;

/**
 * <p>
 * 摄像机配置 服务类
 * </p>
 *
 * @author Administrator
 * @since 2019-08-13
 */
public interface ICameraService extends IService<Camera> {
	void updateHeart(String code);
	List<CameraVO> getCameraList();
}
