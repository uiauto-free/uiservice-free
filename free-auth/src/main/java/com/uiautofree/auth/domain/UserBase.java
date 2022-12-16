package com.uiautofree.auth.domain;


import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author tsbxmw
 * @since 2022-12-15
 */
@Data
public class UserBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    private LocalDateTime gmtCreat;

    private LocalDateTime gmtModified;

    /**
     * 工号
     */
    private String userCode;

    /**
     * 名称
     */
    private String userName;

    /**
     * 昵称
     */
    private String userNickName;

    /**
     * 唯一ID
     */
    private String userId;

    /**
     * 其他用户信息
     */
    private String userContent;

    /**
     * 扩展字段
     */
    private String extension;

    /**
     * 密码-加密
     */
    private String password;

    /**
     * 状态
     */
    private Integer status;


}
