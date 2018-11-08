package springboot.modal.bo;


import lombok.Data;
import springboot.modal.vo.ContentVo;

import java.io.Serializable;
import java.util.List;

/**
 * 文章
 * @author xj
 */
@Data
public class ArchiveBo implements Serializable {

    private String date;
    private String count;
    private List<ContentVo> articles;

}
