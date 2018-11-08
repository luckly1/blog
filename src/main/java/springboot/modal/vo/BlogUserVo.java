package springboot.modal.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaojun
 *
 */
@Data
public class BlogUserVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * blogusers表主键
     */
    private Integer uid;
    /**
     * 用户名
     **/
    private String username;
    /**
     * 密码
     **/
    private String password;
    /**
     * 用户手机号码
     **/
    private String mobilePhone;
    /**
     * 用户邮箱
     **/
    private String email;
    /**
     * 用户状态
     **/
    private int status;
    /**
     * 创建时间
     **/
    private Integer created;
    /**
     * 设置头像图片地址
     **/
    private String infoImage;
    /**
     * 用户上传附件的类型
     **/
    private String type;
    /**
     * 用户上传附件的类型
     **/
    private String enjoyPolicy;
}
