package net.dreamlu.system.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import net.dreamlu.common.result.PageVO;
import net.dreamlu.common.result.Tree;
import net.dreamlu.mica.core.result.R;
import net.dreamlu.system.model.Camera;
import net.dreamlu.system.model.CameraBrand;
import net.dreamlu.system.service.ICameraBrandService;
import net.dreamlu.system.service.ICameraService;
import net.dreamlu.system.vo.BrandVO;
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

import java.util.List;

/**
 * <p>
 * 摄像机品牌 前端控制器
 * </p>
 *
 * @author Administrator
 * @since 2019-08-13
 */
@Controller
@RequestMapping("/cameraBrand")
public class CameraBrandController extends BaseController {
    @Autowired private ICameraBrandService cameraBrandService;
    @Autowired private ICameraService cameraService;

    @GetMapping("/manager")
    @PreAuthorize("@sec.hasPermission('cameraBrand:manager')")
    public String manager() {
        return "system/cameraBrand/cameraBrandList";
    }

	@PostMapping("/dataGrid")
	@ResponseBody
    public Object dataGrid(CameraBrand cameraBrand, PageVO pageVO) {
		return cameraBrandService.selectTreeGrid();
    }

    /**
     * 添加页面-摄像机品牌
     */
    @GetMapping("/addPage")
    public String addPage() {
        return "system/cameraBrand/cameraBrandAdd";
    }

    /**
     * 添加页面-摄像机品牌
     */
    @PostMapping("/add")
    @PreAuthorize("@sec.hasPermission('cameraBrand:add')")
    @ResponseBody
    public R<Object> add(@Valid CameraBrand cameraBrand) {
		LambdaQueryWrapper<CameraBrand> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CameraBrand::getBrandName, cameraBrand.getBrandName());
		int count = cameraBrandService.list(wrapper).size();
		if(count > 0){
			return status(false,"该品牌已存在");
		}
        return status(cameraBrandService.save(cameraBrand));
    }

    /**
     * 删除-摄像机品牌
     */
    @PostMapping("/delete")
    @PreAuthorize("@sec.hasPermission('cameraBrand:delete')")
    @ResponseBody
    public R<Object> delete(CameraBrand cameraBrand) {
		LambdaQueryWrapper<Camera> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Camera::getBrandId,cameraBrand.getId());
		int count = cameraService.list(wrapper).size();
		if(count > 0){
			return status(false,"存在引用该配置的摄像头,不允许删除");
		}
        return status(cameraBrandService.removeById(cameraBrand));
    }

    /**
     * 编辑-摄像机品牌
     */
    @GetMapping("/editPage")
    public String editPage(Model model, Integer id) {
        CameraBrand cameraBrand = cameraBrandService.getById(id);
        model.addAttribute("cameraBrand", cameraBrand);
        return "system/cameraBrand/cameraBrandEdit";
    }

    /**
     * 编辑-摄像机品牌
     */
    @PostMapping("/edit")
    @PreAuthorize("@sec.hasPermission('cameraBrand:edit')")
    @ResponseBody
    public R<Object> edit(@Valid CameraBrand cameraBrand) {
        return status(cameraBrandService.updateById(cameraBrand));
    }

	/**
	 * 摄像机品牌选择
	 */
	@PostMapping("/select")
	@ResponseBody
	public List<BrandVO> selectBrand() {
		return cameraBrandService.selectBrand();
	}
}
