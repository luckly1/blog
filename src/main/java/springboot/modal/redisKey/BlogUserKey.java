package springboot.modal.redisKey;

/**
 * @author xj
 */
public class BlogUserKey {
    // 表名
    public static final String TABLE_NAME = "t_blogusers";

    // 主键名
    public static final String MAJOR_KEY = "uid";

    // 默认主键值
    public static final String DEFAULT_VALUE = "all";

    // 生存周期
    public static final int LIVE_TIME = 6;
}
