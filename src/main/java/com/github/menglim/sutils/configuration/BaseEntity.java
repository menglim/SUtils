package com.github.menglim.sutils.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.menglim.sutils.SUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

//    @Transient
//    @Getter
//    @Setter
//    @JsonIgnore
//    private String url;

    @Column(length = 1)
    @Getter
    @Setter
    @JsonIgnore
    private CoreConstants.Status status;

    @Transient
    @JsonIgnore
    public boolean canUpdate() {
        switch (this.status) {
            case Enabled:
            case Disabled:
                return true;
            case Pending:
            case Deleted:
                return false;
        }
        return false;
    }

    @JsonIgnore
    public boolean isEnabled() {
        switch (this.status) {
            case Enabled:
                return true;
            case Deleted:
            case Disabled:
            case Pending:
                return false;
        }
        return false;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    @JsonIgnore
    private Date createdAt;

    @Getter
    @Setter
    @JsonIgnore
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    @JsonIgnore
    private Date updatedAt;

    @Getter
    @Setter
    @JsonIgnore
    private String updatedBy;

    @PrePersist
    public void initTimeStamps() {
        if (createdAt == null) {
            createdAt = new Date();
        }
        if (updatedAt == null) {
            updatedAt = new Date();
        }
        if (this.status == null) {
            this.status = CoreConstants.Status.Enabled;
        }
        if (SUtils.getInstance().isUserAuthenticated()) {
            this.createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
            this.updatedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        }
    }

    @PreUpdate
    public void updateTimeStamp() {
        updatedAt = new Date();
        if (SUtils.getInstance().isUserAuthenticated()) {
            this.updatedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        }
    }
}
