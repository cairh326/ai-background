package net.dreamlu.system.web;

import net.dreamlu.system.model.Box;
import net.dreamlu.system.service.IBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.validation.Valid;
import net.dreamlu.common.base.BaseController;

/**
 * <p>
 * 本机配置 前端控制器
 * </p>
 *
 * @author Administrator
 * @since 2019-09-09
 */
@Controller
@RequestMapping("/box")
public class BoxController extends BaseController {

    @Autowired private IBoxService boxService;

    @GetMapping("get")
	@ResponseBody
	public Box getBox(){
    	return boxService.getCurrentBox();
	}

	@PostMapping("/edit")
	@ResponseBody
	public Object edit(@Valid Box box) {
		return status(boxService.updateById(box));
	}

}
