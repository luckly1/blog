package springboot.modal.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xj
 */
@Data
public class StaticticsBo implements Serializable{
    private Long articles;
    private Long comments;
    private Long links;
    private Long attachs;

}
