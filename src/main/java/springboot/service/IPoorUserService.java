package springboot.service;

import com.github.pagehelper.PageInfo;
import springboot.modal.vo.BlogUserVo;
import springboot.modal.vo.PoorUserVoExample;

/**
 * @author tangj
 * @date 2018/1/24 21:04
 */
public interface IPoorUserService {

    /**
     * @param page
     * @param limit
     * @return
     */
    PageInfo<BlogUserVo> getArticlesWithpage(PoorUserVoExample poorUserVoExample, Integer page, Integer limit);
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
    void updatePoorUser(BlogUserVo blogUserVo);
}
