package com.uiautofree.agent.domain;

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
 * @since 2023-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ActionApi implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private String name;

    private String serviceName;

    private Long serviceId;

    private String apiName;

    private String method;

    private String params;

    private String body;

    private String extension;

    private String remark;

    private Integer isDeleted;

    private String version;


}
