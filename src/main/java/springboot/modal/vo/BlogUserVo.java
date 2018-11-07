package springboot.modal.vo;

import java.io.Serializable;

/**
 * @author xiaojun
 *
 */
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

    public Integer getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public int getStatus() {
        return status;
    }

    public Integer getCreated() {
        return created;
    }

    public String getInfoImage() {
        return infoImage;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public void setInfoImage(String infoImage) {
        this.infoImage = infoImage;
    }

    @Override
    public String toString() {
        return "BlogUserVo{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", created=" + created +
                ", infoImage='" + infoImage + '\'' +
                '}';
    }
}
