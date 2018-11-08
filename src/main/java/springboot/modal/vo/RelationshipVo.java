package springboot.modal.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xj
 */
@Data
public class RelationshipVo implements Serializable{
    /**
     * 内容主键
     */
    private Integer cid;

    /**
     * 项目主键
     */
    private Integer mid;

    private static final long serialVersionUID = 1L;

}
