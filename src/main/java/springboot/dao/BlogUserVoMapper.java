package springboot.dao;

import org.springframework.stereotype.Component;
import springboot.modal.vo.BlogUserVo;
import springboot.modal.vo.BlogUserVoExample;

import java.util.List;

@Component
public interface BlogUserVoMapper {
    List<BlogUserVo> selectByExampleWithBLOBs(BlogUserVoExample example);
    int insert(BlogUserVo record);
    BlogUserVo selectByPrimaryKey(Integer cid);
    int updateByPrimaryKeySelective(BlogUserVo record);
}