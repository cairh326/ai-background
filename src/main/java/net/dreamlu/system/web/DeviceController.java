package net.dreamlu.system.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.dreamlu.common.result.EasyPage;
import net.dreamlu.common.result.PageVO;
import net.dreamlu.system.model.Box;
import net.dreamlu.system.model.Device;
import net.dreamlu.system.service.IBoxService;
import net.dreamlu.system.service.IConfigService;
import net.dreamlu.system.service.IDeviceService;
import net.dreamlu.system.util.CommonConstant;
import net.dreamlu.system.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.validation.Valid;
import net.dreamlu.common.base.BaseController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 摄像机配置 前端控制器
 * </p>
 *
 * @author Administrator
 * @since 2019-08-13
 */
@Controller
@RequestMapping("/device")
public class DeviceController extends BaseController {
    @Autowired private IDeviceService deviceService;
	@Autowired private IBoxService boxService;
	@Autowired private IConfigService configService;

    @GetMapping("/manager")
    @PreAuthorize("@sec.hasPermission('device:manager')")
    public String manager() {
        return "system/device/deviceList";
    }

    @PostMapping("/dataGrid")
    @PreAuthorize("@sec.hasPermission('device:dataGrid')")
    @ResponseBody
    public EasyPage<Device> dataGrid(Device device, PageVO pageVO) {
        QueryWrapper<Device> ew = new QueryWrapper<Device>(device);
        Page<Device> pages = pageVO.toPage();
		deviceService.page(pages, ew);
        return EasyPage.of(pages);
    }

    /**
     * 添加页面-摄像机配置
     */
    @GetMapping("/addPage")
    public String addPage() {
        return "system/device/deviceAdd";
    }

	/**
	 * 事前校验
	 * @param device 设备
	 * @param isNew 是否新增
	 * @return string
	 */
	private String check(Device device,Boolean isNew){
		if(StringUtils.isEmpty(device.getType())){
			return "请选择设备类型";
		}
		if(device.getType().equals(CommonConstant.DeviceType.CAMERA)){
			if(device.getBrandId() == null){
				return "设备类型为摄像头,请选择设备品牌";
			}
			if(StringUtils.isEmpty(device.getUser())
			   	||StringUtils.isEmpty(device.getPasswd())){
				return "设备类型为摄像头,用户名及密码非空";
			}
			if(device.getOpenFlag() == 1 && StringUtils.isEmpty(device.getOpenChannel())){
				return "设备类型为摄像头并启用开门,开门通道非空";
			}
		}
		if(isNew){
			Integer upperLimit = Integer.parseInt(configService.getByName("MaxDeviceCount").getCValue());
			Box box = boxService.getCurrentBox();
			if(box.getBindingStatus() ==1){
				upperLimit = box.getUpperLimit();
			}
			Integer count = deviceService.list().size();
			if(count >= upperLimit){
				return "已达路数上限,请联系管理员";
			}
		}
    	return "";
	}

    /**
     * 添加页面-摄像机配置
     */
    @PostMapping("/add")
    @PreAuthorize("@sec.hasPermission('device:add')")
    @ResponseBody
    public Object add(@Valid Device device) {
		String msg = this.check(device, true);
		if (StringUtils.isNotEmpty(msg)) {
			return status(false, msg);
		}
		return status(deviceService.save(device));
    }

    /**
     * 删除-摄像机配置
     */
    @PostMapping("/delete")
    @PreAuthorize("@sec.hasPermission('device:delete')")
    @ResponseBody
    public Object delete(Device device) {
        return status(deviceService.removeById(device));
    }

    /**
     * 编辑-摄像机配置
     */
    @GetMapping("/editPage")
    public String editPage(Model model, Integer id) {
        Device device = deviceService.getById(id);
        model.addAttribute("device", device);
        return "system/device/deviceEdit";
    }

    /**
     * 编辑-摄像机配置
     */
    @PostMapping("/edit")
    @PreAuthorize("@sec.hasPermission('device:edit')")
    @ResponseBody
    public Object edit(@Valid Device device) {
		String msg = this.check(device,false);
		if(StringUtils.isNotEmpty(msg)){
			return status(false,msg);
		}
        return status(deviceService.updateById(device));
    }

	/**
	 * 摄像机品牌选择
	 */
	@PostMapping("/select")
	@ResponseBody
	public List<Device> select(){
		List<Device> deviceList = new ArrayList<>();
		Device nullDevice = new Device();
		nullDevice.setCode("");
		nullDevice.setName("请选择开门设备");
		deviceList.add(nullDevice);
		deviceList.addAll(deviceService.list());
		return deviceList;
	}
}
