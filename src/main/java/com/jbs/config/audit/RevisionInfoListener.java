package com.jbs.config.audit;

import com.jbs.config.security.UserApplication;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by rizkykojek on 5/1/17.
 */
@Component
public class RevisionInfoListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        Optional<Authentication> authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
        if (authentication.isPresent()) {
            Long employeeId = ((UserApplication) authentication.get().getPrincipal()).getEmployeeId();
            RevisionInfo audit = (RevisionInfo) revisionEntity;
            audit.setUpdatedBy(employeeId);
        }
    }
}
