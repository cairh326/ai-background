package net.dreamlu.system.web;

import lombok.AllArgsConstructor;
import net.dreamlu.system.service.IBoxService;
import net.dreamlu.system.service.IDeviceService;
import net.dreamlu.system.vo.BoxVO;
import net.dreamlu.system.vo.ThirdBaseVO;
import net.dreamlu.system.vo.ThirdConfigVO;
import org.springframework.beans.BeanUtils;
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
	@Autowired private final IDeviceService deviceService;
	@Autowired private final IBoxService boxService;

    @GetMapping("/config")
	@ResponseBody
    public ThirdConfigVO getCameraList() {
		ThirdConfigVO vo = new ThirdConfigVO();
    	try {
			vo.setData(deviceService.getDeviceList());
			BoxVO boxVO = new BoxVO();
			BeanUtils.copyProperties(boxService.getCurrentBox(),boxVO);
			vo.setBox(boxVO);
		}catch (Exception ex){
			vo.setSuccess(Boolean.FALSE);
			vo.setMessage(ex.getMessage());
		}
        return vo;
    }

	@GetMapping("/keepLive")
	@ResponseBody
    public ThirdBaseVO updateDevice(String serialNo){
		ThirdBaseVO thirdBaseVO = new ThirdBaseVO();
		try {
			deviceService.updateHeart(serialNo);
		}catch (Exception ex){
			thirdBaseVO.setSuccess(Boolean.FALSE);
			thirdBaseVO.setMessage(ex.getMessage());
		}
		return thirdBaseVO;
	}
}
