package net.dreamlu.system.service;

import net.dreamlu.system.model.CameraBrand;
import com.baomidou.mybatisplus.extension.service.IService;
import net.dreamlu.system.vo.BrandVO;

import java.util.List;

/**
 * <p>
 * 摄像机品牌 服务类
 * </p>
 *
 * @author Administrator
 * @since 2019-08-13
 */
public interface ICameraBrandService extends IService<CameraBrand> {
	List<CameraBrand> selectTreeGrid();
	List<BrandVO> selectBrand();
}
