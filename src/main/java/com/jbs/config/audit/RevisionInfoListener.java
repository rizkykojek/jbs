package com.jbs.config.audit;

import com.jbs.util.UserSessionUtil;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;

/**
 * Created by rizkykojek on 5/1/17.
 */
@Component
public class RevisionInfoListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        RevisionInfo audit = (RevisionInfo) revisionEntity;
        audit.setUpdatedBy(UserSessionUtil.getEmployeeId());
    }
}
