package springboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import springboot.dao.ContentVoMapper;
import springboot.dao.BlogUserVoMapper;
import springboot.exception.TipException;
import springboot.modal.redisKey.BlogUserKey;
import springboot.modal.vo.BlogUserVo;
import springboot.modal.vo.BlogUserVoExample;
import springboot.service.IBlogUserService;
import springboot.util.DateKit;
import springboot.util.RedisKeyUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xj
 */
@Service
public class BlogUserServcieImpl implements IBlogUserService {

    @Resource
    private ContentVoMapper contentDao;
    @Resource
    private BlogUserVoMapper blogUserVoMapper;


    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ValueOperations<String,Object> valueOperations;
    @Override
    public PageInfo<BlogUserVo> getArticlesWithpage(BlogUserVoExample blogUserVoExample, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<BlogUserVo> blogUserVos = blogUserVoMapper.selectByExampleWithBLOBs(blogUserVoExample);
        return new PageInfo<>(blogUserVos);
    }

    @Override
    public void publish(BlogUserVo blogUserVo) {
        checkContent(blogUserVo);

        // 去除表情
        blogUserVo.setEnjoyPolicy(EmojiParser.parseToAliases(blogUserVo.getEnjoyPolicy()));

        int time = DateKit.getCurrentUnixTime();
        blogUserVo.setCreated(time);
//        if(blogUserVo.getOutpoorDate()!=null){
//            blogUserVo.setOutpoorDate(time);
//        }
        blogUserVoMapper.insert(blogUserVo);
    }

    @Override
    public BlogUserVo getPoorUse(String id) {
        // 先从redis中读取用户信息
        String poorUserKey = RedisKeyUtil.getKey(BlogUserKey.TABLE_NAME, BlogUserKey.MAJOR_KEY, id);
        BlogUserVo blogUserVo = (BlogUserVo) valueOperations.get(poorUserKey);
        if (blogUserVo == null){
            if (StringUtils.isNotBlank(id)) {
                    blogUserVo = blogUserVoMapper.selectByPrimaryKey(Integer.valueOf(id));
                    valueOperations.set(poorUserKey, blogUserVo);
                    redisService.expireKey(poorUserKey, BlogUserKey.LIVE_TIME, TimeUnit.HOURS);
                    return blogUserVo;
            }
        }
        return blogUserVo;
    }

    @Override
    public void updatebloguser(BlogUserVo blogUserVo) {
        String poorUserKey = RedisKeyUtil.getKey(BlogUserKey.TABLE_NAME, BlogUserKey.MAJOR_KEY, Integer.toString(blogUserVo.getUid()));
        // 检查用户输入
        checkContent(blogUserVo);
        if (StringUtils.isBlank(blogUserVo.getEmail())) {
            blogUserVo.setEmail(null);
        }
        int time = DateKit.getCurrentUnixTime();
        blogUserVo.setEnjoyPolicy(EmojiParser.parseToAliases(blogUserVo.getEnjoyPolicy()));
        blogUserVoMapper.updateByPrimaryKeySelective(blogUserVo);
        valueOperations.set(poorUserKey, blogUserVo);
        redisService.expireKey(poorUserKey, BlogUserKey.LIVE_TIME, TimeUnit.HOURS);

    }

    private void  checkContent(BlogUserVo blogUserVo) throws TipException{
        if (null == blogUserVo) {
            throw new TipException("用户对象不能为空");
        }
        if (StringUtils.isBlank(blogUserVo.getUsername())) {
            throw new TipException("用户姓名不能为空");
        }
        if (StringUtils.isBlank(blogUserVo.getEnjoyPolicy())) {
            throw new TipException("用户享受政策不能为空");
        }
        if (blogUserVo.getUsername().length() > 200) {
            throw new TipException("用户姓名过长");
        }
        if (blogUserVo.getEnjoyPolicy().length() > 65000) {
            throw new TipException("用户享受政策过长");
        }
      /*  if (null == contents.getAuthorId()) {
            throw new TipException("请登录后发布文章");
        }*/
    }
}
