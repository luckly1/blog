package springboot.controller.admin;

import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springboot.controller.AbstractController;
import springboot.controller.helper.ExceptionHelper;
import springboot.dto.LogActions;
import springboot.dto.Types;
import springboot.exception.TipException;
import springboot.modal.bo.RestResponseBo;
import springboot.modal.vo.*;
import springboot.service.IContentService;
import springboot.service.ILogService;
import springboot.service.IPoorUserService;
import springboot.util.Commons;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户管理
 *
 * @author tangj
 * @date 2018/1/27 14:43
 */
@Controller
@RequestMapping("admin/pooruser")
public class PoorUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(PoorUserController.class);

    @Resource
    private IContentService contentService;
    @Resource
    private IPoorUserService poorUserService;

    @Resource
    private ILogService logService;
    @GetMapping(value = "")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "15") int limit,HttpServletRequest request) {
        PoorUserVoExample poorUserVoExample = new PoorUserVoExample();
        poorUserVoExample.setOrderByClause("created desc");
        poorUserVoExample.createCriteria().andTypeEqualTo(Types.ARTICLE.getType());
        PageInfo<BlogUserVo> poorUserVoPageInfo = poorUserService.getArticlesWithpage(poorUserVoExample,  page, limit);
        request.setAttribute("poorUsers", poorUserVoPageInfo);
        return "admin/pooruser_list";
    }

    @GetMapping(value = "new")
    public String newPage(HttpServletRequest request) {
        request.setAttribute(Types.ATTACH_URL.getType(), Commons.site_option(Types.ATTACH_URL.getType()));
        return "admin/pooruser_edit";
    }

    @GetMapping(value = "/{uid}")
    public String editPage(@PathVariable String uid, HttpServletRequest request) {
        BlogUserVo blogUserVo = poorUserService.getPoorUse(uid);
        request.setAttribute("poorUsers", blogUserVo);
        request.setAttribute(Types.ATTACH_URL.getType(), Commons.site_option(Types.ATTACH_URL.getType()));
        request.setAttribute("active", "pooruser");
        return "admin/pooruser_edit";
    }

    @PostMapping(value = "publish")
    @ResponseBody
    @Transactional(rollbackFor = TipException.class)
    public RestResponseBo publishPage(BlogUserVo blogUserVo, HttpServletRequest request) {
        UserVo users = this.user(request);
        blogUserVo.setType(Types.ARTICLE.getType());
        try {
            poorUserService.publish(blogUserVo);
        } catch (Exception e) {
            String msg = "用户发布失败";
            return ExceptionHelper.handlerException(logger, msg, e);
        }
        return RestResponseBo.ok();
    }

    @PostMapping(value = "modify")
    @ResponseBody
    @Transactional(rollbackFor = TipException.class)
    public RestResponseBo modifyArticle(BlogUserVo blogUserVo, HttpServletRequest request) {
        blogUserVo.setType(Types.ARTICLE.getType());
        try {
            poorUserService.updatePoorUser(blogUserVo);
        } catch (Exception e) {
            String msg = "用户编辑失败";
            return ExceptionHelper.handlerException(logger, msg, e);
        }
        return RestResponseBo.ok();
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    @Transactional(rollbackFor = TipException.class)
    public RestResponseBo delete(@RequestParam int cid, HttpServletRequest request) {
        try {
            contentService.deleteByCid(cid);
            logService.insertLog(LogActions.DEL_PAGE.getAction(), cid + "", request.getRemoteAddr(), this.getUid(request));
        } catch (Exception e) {
            String msg = "页面删除失败";
            return ExceptionHelper.handlerException(logger, msg, e);
        }
        return RestResponseBo.ok();
    }


}
