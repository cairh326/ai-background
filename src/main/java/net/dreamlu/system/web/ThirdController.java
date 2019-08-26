package net.dreamlu.system.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import net.dreamlu.system.model.Config;
import net.dreamlu.system.service.ICameraService;
import net.dreamlu.system.service.IConfigService;
import net.dreamlu.system.vo.ThirdBaseVO;
import net.dreamlu.system.vo.ThirdCameraListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import net.dreamlu.common.base.BaseController;

/**
 * <p>
 * 第三方 前端控制器
 * </p>
 *
 * @author Administrator
 * @since 2019-08-21
 */
@RestController
@RequestMapping("/third")
@AllArgsConstructor
public class ThirdController extends BaseController {
	@Autowired private final ICameraService cameraService;
	@Autowired private final IConfigService configService;

    @GetMapping("/cameraList")
	@ResponseBody
    public ThirdCameraListVO getCameraList() {
		ThirdCameraListVO vo = new ThirdCameraListVO();
    	try {
			vo.setData(cameraService.getCameraList());

			QueryWrapper<Config> qw1 = new QueryWrapper<>();
			qw1.eq("c_name", "cloudConfig");
			Config config = configService.getOne(qw1);
			if(config != null){
				String[] strList = config.getCValue().split("#");
				vo.setCloudAddress(strList[0]);
				vo.setCloudKey(strList[1]);
				vo.setCloudPs(strList[2]);
			}
		}catch (Exception ex){
			vo.setSuccess(Boolean.FALSE);
			vo.setMessage(ex.getMessage());
		}
        return vo;
    }

	@GetMapping("/updateCamera")
	@ResponseBody
    public ThirdBaseVO updateCamera(String code){
		ThirdBaseVO thirdBaseVO = new ThirdBaseVO();
		try {
			cameraService.updateHeart(code);
		}catch (Exception ex){
			thirdBaseVO.setSuccess(Boolean.FALSE);
			thirdBaseVO.setMessage(ex.getMessage());
		}
		return thirdBaseVO;
	}
}
