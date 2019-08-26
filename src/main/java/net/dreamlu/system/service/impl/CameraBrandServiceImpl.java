package net.dreamlu.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import net.dreamlu.system.mapper.CameraBrandMapper;
import net.dreamlu.system.model.CameraBrand;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamlu.system.service.ICameraBrandService;
import net.dreamlu.system.vo.BrandVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 摄像机品牌 服务实现类
 * </p>
 *
 * @author Administrator
 * @since 2019-08-13
 */
@Service
public class CameraBrandServiceImpl extends ServiceImpl<CameraBrandMapper, CameraBrand> implements ICameraBrandService {

	@Override
	public List<CameraBrand> selectTreeGrid() {
		LambdaQueryWrapper<CameraBrand> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByAsc(CameraBrand::getCreateTime);
		return super.list(wrapper);
	}

	@Override
	public List<BrandVO> selectBrand() {
		List<BrandVO> brandVOList = new ArrayList<>();
		for (CameraBrand brand : super.list()){
			BrandVO brandVO = new BrandVO();
			brandVO.setBrandId(brand.getId());
			brandVO.setBrandName(brand.getBrandName());
			brandVOList.add(brandVO);
		}
		return brandVOList;
	}
}
