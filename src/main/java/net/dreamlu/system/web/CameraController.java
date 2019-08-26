package net.dreamlu.system.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.dreamlu.common.result.EasyPage;
import net.dreamlu.common.result.PageVO;
import net.dreamlu.system.model.Camera;
import net.dreamlu.system.service.ICameraService;
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

/**
 * <p>
 * 摄像机配置 前端控制器
 * </p>
 *
 * @author Administrator
 * @since 2019-08-13
 */
@Controller
@RequestMapping("/camera")
public class CameraController extends BaseController {

    @Autowired private ICameraService cameraService;

    @GetMapping("/manager")
    @PreAuthorize("@sec.hasPermission('camera:manager')")
    public String manager() {
        return "system/camera/cameraList";
    }

    @PostMapping("/dataGrid")
    @PreAuthorize("@sec.hasPermission('camera:dataGrid')")
    @ResponseBody
    public EasyPage<Camera> dataGrid(Camera camera, PageVO pageVO) {
        QueryWrapper<Camera> ew = new QueryWrapper<Camera>(camera);
        Page<Camera> pages = pageVO.toPage();
        cameraService.page(pages, ew);
        return EasyPage.of(pages);
    }

    /**
     * 添加页面-摄像机配置
     */
    @GetMapping("/addPage")
    public String addPage() {
        return "system/camera/cameraAdd";
    }

    /**
     * 添加页面-摄像机配置
     */
    @PostMapping("/add")
    @PreAuthorize("@sec.hasPermission('camera:add')")
    @ResponseBody
    public Object add(@Valid Camera camera) {
    	camera.setIsOnline(1);
        return status(cameraService.save(camera));
    }

    /**
     * 删除-摄像机配置
     */
    @PostMapping("/delete")
    @PreAuthorize("@sec.hasPermission('camera:delete')")
    @ResponseBody
    public Object delete(Camera camera) {
        return status(cameraService.removeById(camera));
    }

    /**
     * 编辑-摄像机配置
     */
    @GetMapping("/editPage")
    public String editPage(Model model, Integer id) {
        Camera camera = cameraService.getById(id);
        model.addAttribute("camera", camera);
        return "system/camera/cameraEdit";
    }

    /**
     * 编辑-摄像机配置
     */
    @PostMapping("/edit")
    @PreAuthorize("@sec.hasPermission('camera:edit')")
    @ResponseBody
    public Object edit(@Valid Camera camera) {
        return status(cameraService.updateById(camera));
    }
}
