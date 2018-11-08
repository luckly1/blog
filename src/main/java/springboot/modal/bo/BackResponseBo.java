package springboot.modal.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xj
 */
@Data
public class BackResponseBo implements Serializable {
    private String attachPath;
    private String themePath;
    private String sqlPath;

}