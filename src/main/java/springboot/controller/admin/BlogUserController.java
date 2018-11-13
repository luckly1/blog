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
import springboot.service.IBlogUserService;
import springboot.util.Commons;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户管理
 *
 * @author xj
 */
@Controller
@RequestMapping("admin/bloguser")
public class BlogUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(BlogUserController.class);

    @Resource
    private IContentService contentService;
    @Resource
    private IBlogUserService blogUserService;

    @Resource
    private ILogService logService;
    @GetMapping(value = "")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "15") int limit,HttpServletRequest request) {
        BlogUserVoExample blogUserVoExample = new BlogUserVoExample();
        blogUserVoExample.setOrderByClause("created desc");
        blogUserVoExample.createCriteria().andTypeEqualTo(Types.ARTICLE.getType());
        PageInfo<BlogUserVo> bloguserVoPageInfo = blogUserService.getArticlesWithpage(blogUserVoExample,  page, limit);
        request.setAttribute("blogUsers", bloguserVoPageInfo);
        return "admin/bloguser_list";
    }

    @GetMapping(value = "new")
    public String newPage(HttpServletRequest request) {
        request.setAttribute(Types.ATTACH_URL.getType(), Commons.site_option(Types.ATTACH_URL.getType()));
        return "admin/bloguser_edit";
    }

    @GetMapping(value = "/{uid}")
    public String editPage(@PathVariable String uid, HttpServletRequest request) {
        BlogUserVo blogUserVo = blogUserService.getPoorUse(uid);
        request.setAttribute("blogUsers", blogUserVo);
        request.setAttribute(Types.ATTACH_URL.getType(), Commons.site_option(Types.ATTACH_URL.getType()));
        request.setAttribute("active", "bloguser");
        return "admin/bloguser_edit";
    }

    @PostMapping(value = "publish")
    @ResponseBody
    @Transactional(rollbackFor = TipException.class)
    public RestResponseBo publishPage(BlogUserVo blogUserVo, HttpServletRequest request) {
        UserVo users = this.user(request);
        blogUserVo.setType(Types.ARTICLE.getType());
        try {
            blogUserService.publish(blogUserVo);
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
            blogUserService.updatebloguser(blogUserVo);
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
