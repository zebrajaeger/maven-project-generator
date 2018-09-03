package ${package};

import org.opencms.configuration.CmsConfigurationManager;
import org.opencms.file.CmsObject;
import org.opencms.module.CmsModule;
import org.opencms.main.CmsEvent;
import org.opencms.module.A_CmsModuleAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionClass extends A_CmsModuleAction {
    private static final Logger LOG = LoggerFactory.getLogger(ActionClass.class);

    private static CmsObject adminCms;

    public static CmsObject getAdminCms() {
        return adminCms;
    }

    @Override
    public void initialize(CmsObject pAdminCms, CmsConfigurationManager configurationManager, CmsModule module) {
        adminCms = pAdminCms;
    }

    @Override
    public void moduleUninstall(CmsModule module) {
    }

    @Override
    public void cmsEvent(CmsEvent event) {
    }
}
