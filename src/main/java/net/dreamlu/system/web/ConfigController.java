package net.dreamlu.system.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.dreamlu.common.result.EasyPage;
import net.dreamlu.common.result.PageVO;
import net.dreamlu.system.model.Config;
import net.dreamlu.system.service.IConfigService;
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
 * @since 2019-08-19
 */
@Controller
@RequestMapping("/config")
public class ConfigController extends BaseController {

    @Autowired private IConfigService configService;

    @GetMapping("/manager")
    @PreAuthorize("@sec.hasPermission('config:manager')")
    public String manager() {
        return "system/config/configList";
    }

    @PostMapping("/dataGrid")
    @PreAuthorize("@sec.hasPermission('config:dataGrid')")
    @ResponseBody
    public EasyPage<Config> dataGrid(Config config, PageVO pageVO) {
        QueryWrapper<Config> ew = new QueryWrapper<>(config);
        Page<Config> pages = pageVO.toPage();
        configService.page(pages, ew);
        return EasyPage.of(pages);
    }

    /**
     * 添加页面-摄像机配置
     */
    @GetMapping("/addPage")
    public String addPage() {
        return "system/config/configAdd";
    }

    /**
     * 添加页面-摄像机配置
     */
    @PostMapping("/add")
    @PreAuthorize("@sec.hasPermission('config:add')")
    @ResponseBody
    public Object add(@Valid Config config) {
        return status(configService.save(config));
    }

    /**
     * 删除-摄像机配置
     */
    @PostMapping("/delete")
    @PreAuthorize("@sec.hasPermission('config:delete')")
    @ResponseBody
    public Object delete(Config config) {
        return status(configService.removeById(config));
    }

    /**
     * 编辑-摄像机配置
     */
    @GetMapping("/editPage")
    public String editPage(Model model, Integer id) {
        Config config = configService.getById(id);
        model.addAttribute("config", config);
        return "system/config/configEdit";
    }

    /**
     * 编辑-摄像机配置
     */
    @PostMapping("/edit")
    @PreAuthorize("@sec.hasPermission('config:edit')")
    @ResponseBody
    public Object edit(@Valid Config config) {
        return status(configService.updateById(config));
    }
}
