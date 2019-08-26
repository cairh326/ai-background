package net.dreamlu.system.mapper;

import net.dreamlu.system.model.Camera;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.dreamlu.system.vo.CameraVO;

import java.util.List;

/**
 * <p>
 * 摄像机配置 Mapper 接口
 * </p>
 *
 * @author Administrator
 * @since 2019-08-13
 */
public interface CameraMapper extends BaseMapper<Camera> {
	void updateHeart(String code);
	List<CameraVO> getCameraList();
}
