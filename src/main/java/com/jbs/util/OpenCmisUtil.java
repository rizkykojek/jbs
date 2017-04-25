package com.jbs.util;

import com.sap.ecm.api.EcmService;
import com.sap.ecm.api.RepositoryOptions;
import org.apache.chemistry.opencmis.client.api.Session;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by rizkykojek on 4/24/17.
 */
public final class OpenCmisUtil {
    public static final String REPOSITORY_NAME = "JBS_DOCUMENT";
    public static final String REPOSITORY_SECRET_KEY = "password01";

    public static final String JNDI_LOOKUP_NAME = "java:comp/env/EcmService";

    public static EcmService initEcmService() throws NamingException {
        InitialContext ctx = new InitialContext();
        EcmService ecmService = (EcmService) ctx.lookup(JNDI_LOOKUP_NAME);

        createRepository(ecmService);

        return  ecmService;
    }

    public static Session openSession(EcmService ecmService) {
        Session openCmisSession = ecmService.connect(REPOSITORY_NAME, REPOSITORY_SECRET_KEY);

        return openCmisSession;
    }

    private static void createRepository(EcmService ecmService)  {
        try {
            /** connect to my repository */
            openSession(ecmService);

        } catch (Exception e) {
            /** if repository not exist then create it */
            RepositoryOptions options = new RepositoryOptions();
            options.setUniqueName(REPOSITORY_NAME);
            options.setRepositoryKey(REPOSITORY_SECRET_KEY);
            options.setVisibility(RepositoryOptions.Visibility.PROTECTED);
            ecmService.createRepository(options);
        }
    }
}
