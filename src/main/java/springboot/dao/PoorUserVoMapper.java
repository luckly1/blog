package springboot.dao;

import org.springframework.stereotype.Component;
import springboot.modal.vo.BlogUserVo;
import springboot.modal.vo.PoorUserVoExample;

import java.util.List;

@Component
public interface PoorUserVoMapper {
    List<BlogUserVo> selectByExampleWithBLOBs(PoorUserVoExample example);
    int insert(BlogUserVo record);
    BlogUserVo selectByPrimaryKey(Integer cid);
    int updateByPrimaryKeySelective(BlogUserVo record);
}