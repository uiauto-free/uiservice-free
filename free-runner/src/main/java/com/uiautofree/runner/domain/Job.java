package com.uiautofree.runner.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * @description job
 * @author tsbxmw
 * @date 2023-07-06
 */
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * gmt_create
     */
    private Date gmtCreate;

    /**
     * gmt_modified
     */
    private Date gmtModified;

    /**
     * batch_id
     */
    private Long batchId;

    /**
     * case_id
     */
    private Long caseId;

    /**
     * case_record_id
     */
    private Long caseRecordId;

    /**
     * resource_id
     */
    private Long resourceId;

    /**
     * status
     */
    private Integer status;

    /**
     * is_deleted
     */
    private Integer isDeleted;

    /**
     * extension
     */
    private String extension;


    public Job() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getCaseRecordId() {
        return caseRecordId;
    }

    public void setCaseRecordId(Long caseRecordId) {
        this.caseRecordId = caseRecordId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

}