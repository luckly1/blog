package springboot.modal.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xj
 */
@Data
public class OptionVo implements Serializable {
    /**
     * 配置名称
     */
    private String name;

    /**
     * 配置值
     */
    private String value;

    /**
     * 描述
     */
    private String description;

    private static final long serialVersionUID = 1L;

}