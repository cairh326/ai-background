package net.dreamlu.system.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.system.model.Device;
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
@Slf4j
public class ThirdController extends BaseController {
	@Autowired private final IDeviceService deviceService;
	@Autowired private final IBoxService boxService;

    @GetMapping("/config")
	@ResponseBody
    public ThirdConfigVO getConfig() {
		ThirdConfigVO vo = new ThirdConfigVO();
    	try {
			vo.setData(deviceService.getDeviceList());
			BoxVO boxVO = new BoxVO();
			BeanUtils.copyProperties(boxService.getCurrentBox(),boxVO);
			vo.setBox(boxVO);
		}catch (Exception ex){
			vo.setSuccess(Boolean.FALSE);
			vo.setMessage(ex.getMessage());
			log.error("third->config->error:" + ex.getMessage());
		}
        return vo;
    }

	@GetMapping("/keepLive")
	@ResponseBody
    public ThirdBaseVO updateDevice(String serialNo){
		ThirdBaseVO thirdBaseVO = new ThirdBaseVO();
		try {
			QueryWrapper<Device> ew = new QueryWrapper<>();
			ew.eq("code",serialNo);
			Device device = deviceService.getOne(ew);
			if(device == null){
				throw new Exception(serialNo + "该设备不存在");
			}
			deviceService.updateHeart(serialNo);
		}catch (Exception ex){
			thirdBaseVO.setSuccess(Boolean.FALSE);
			thirdBaseVO.setMessage(ex.getMessage());
			log.error("third->keepLive->error:" + ex.getMessage());
		}
		return thirdBaseVO;
	}
}
