package net.dreamlu.system.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.dreamlu.common.result.EasyPage;
import net.dreamlu.common.result.PageVO;
import net.dreamlu.system.model.Config;
import net.dreamlu.system.model.NetCard;
import net.dreamlu.system.service.IConfigService;
import net.dreamlu.system.service.INetCardService;
import net.dreamlu.system.util.*;
import net.dreamlu.system.vo.CardVO;
import net.dreamlu.system.vo.NetworkResponse;
import org.apache.http.entity.ContentType;
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
 * 网卡信息 前端控制器
 * </p>
 *
 * @author Administrator
 * @since 2019-08-21
 */
@Controller
@RequestMapping("/netCard")
public class NetCardController extends BaseController {

    @Autowired private INetCardService netCardService;
    @Autowired private IConfigService configService;

    @GetMapping("/manager")
    @PreAuthorize("@sec.hasPermission('netCard:manager')")
    public String manager() {
        return "system/netCard/netCardList";
    }

    @PostMapping("/dataGrid")
    @PreAuthorize("@sec.hasPermission('netCard:dataGrid')")
    @ResponseBody
    public EasyPage<NetCard> dataGrid(NetCard netCard, PageVO pageVO) {
		Config config = configService.getByName("netCardPort");
		String url = NetCardConstant.NET_CARD_GET.replace(NetCardConstant.PLACE_HOLDER,config.getCValue());
		NetworkResponse response = WebHttpUtil.invokeAPI(url,RestConstant.HTTPGET
			,null, null, null, null, null
			, ContentType.APPLICATION_JSON, null, null, 2000, NetworkResponse.class);
		for(CardVO vo : response.getData()){
			if(StringUtils.isBlank(vo.getMethod())){
				continue;
			}
			String mac = vo.getMac();
			boolean isNew = false;
			QueryWrapper<NetCard> qw = new QueryWrapper<>();
			qw.eq("mac",mac);
			NetCard card = netCardService.getOne(qw);
			if(card == null){
				card = new NetCard();
				isNew = true;
			}
			BeanUtils.copyProperties(vo,card);
			card.setCardName(vo.getName());
			card.setIsAuto(vo.getIs_auto() ? 1 : 0);
			if(isNew){
				netCardService.save(card);
			}else{
				netCardService.updateById(card);
			}
		}
        QueryWrapper<NetCard> ew = new QueryWrapper<>();
        Page<NetCard> pages = pageVO.toPage();
        netCardService.page(pages, ew);
        return EasyPage.of(pages);
    }

    /**
     * 编辑-网卡信息
     */
    @GetMapping("/editPage")
    public String editPage(Model model, Integer id) {
        NetCard netCard = netCardService.getById(id);
        model.addAttribute("netCard", netCard);
        return "system/netCard/netCardEdit";
    }

    /**
     * 编辑-网卡信息
     */
    @PostMapping("/edit")
    @PreAuthorize("@sec.hasPermission('netCard:edit')")
    @ResponseBody
    public Object edit(@Valid NetCard netCard) {
		CardVO vo = new CardVO();
		BeanUtils.copyProperties(netCard,vo);
		vo.setIs_auto(netCard.getIsAuto() == 1 ? true : false);
		vo.setName(netCard.getCardName());
		boolean isUpdate = netCardService.updateById(netCard);
		if(isUpdate){
			new Thread() {
				@Override
				public void run() {
					Config config = configService.getByName("netCardPort");
					String url = NetCardConstant.NET_CARD_SET.replace(NetCardConstant.PLACE_HOLDER,config.getCValue());
					System.out.println(url);
					System.out.println(JsonUtils.toJson(vo));
					String responce = WebHttpUtil.invokeAPI(url,RestConstant.HTTPPOST
						,null, JsonUtils.toJson(vo), null, null, null
						, ContentType.APPLICATION_JSON, null, null, 2000, String.class);
					System.out.println(responce);
				}
			}.start();
		}
        return status(isUpdate);
    }
}
