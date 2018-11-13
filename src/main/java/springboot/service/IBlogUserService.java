package springboot.service;

import com.github.pagehelper.PageInfo;
import springboot.modal.vo.BlogUserVo;
import springboot.modal.vo.BlogUserVoExample;

/**
 * @author xj
 */
public interface IBlogUserService {

    /**
     * @param page
     * @param limit
     * @return
     */
    PageInfo<BlogUserVo> getArticlesWithpage(BlogUserVoExample blogUserVoExample, Integer page, Integer limit);
    /**
     * 添加用户信息
     *
     */
    void publish(BlogUserVo blogUserVo);
    /**
     * 根据id获取用户信息
     *
     * @param id id
     * @return ContentVo
     */
    BlogUserVo getPoorUse(String id);
    /**
     * 编辑用户信息
     *
     */
    void updatebloguser(BlogUserVo blogUserVo);
}
