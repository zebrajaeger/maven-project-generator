package ${package}.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author schrader
 */
public class OpenCmsRuntimeConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(OpenCmsRuntimeConfiguration.class);

  private Class<?> clusterManagerClass = null;

  private Object clusterManager = null;

  private Map<String, Method> methods = new HashMap<>();

  public OpenCmsRuntimeConfiguration() {
    initialize();
  }

  protected void initialize() {
    try {
      clusterManagerClass = Class.forName("org.opencms.ocee.cluster.CmsClusterManager");
    } catch (ClassNotFoundException e) {
      LOG.info("Cluster module not installed: '" + e.getMessage() + "', assume no clustered environment");
      return;
    }

    try {
      clusterManager = getMethod("getInstance").invoke(clusterManagerClass);
      if (clusterManager == null) {
        LOG.info("No instance of cluster module found, assume no clustered environment");
      }
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      LOG.warn("Unable to get cluster manager instance, API might have changed", e);
    }
  }

  public boolean isClusteredEnvironment() {
    if (clusterManagerClass == null || clusterManager == null) {
      return false;
    }

    try {
      Boolean isConfigured = Boolean.class.cast(getMethod("isConfigured").invoke(clusterManager));
      Boolean isInitialized = Boolean.class.cast(getMethod("isInitialized").invoke(clusterManager));
      return isConfigured && isInitialized;

    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      LOG.warn("Unable to get cluster info, API might have changed", e);
    }

    return false;
  }

  public boolean isThisWorkplaceServer() {
    if (clusterManagerClass == null || clusterManager == null) {
      return true;
    }

    try {
      Boolean isInitialized = Boolean.class.cast(getMethod("isInitialized").invoke(clusterManager));
      if (!isInitialized) {
        getMethod("reInitializeCluster").invoke(clusterManager);
      }

      // public org.opencms.ocee.cluster.CmsClusterServer CmsClusterManager#getThisServer()
      Object thisServer = getMethod("getThisServer").invoke(clusterManager);

      // public boolean CmsClusterServer#isWpServer()
      return Boolean.class.cast(thisServer.getClass().getMethod("isWpServer").invoke(thisServer));
    } catch (Exception e) {
      LOG.warn("Unable to get workplace-server info, API might have changed", e);
    }

    return true;
  }

  protected Method getMethod(String name) throws NoSuchMethodException {
    if (methods.containsKey(name)) {
      return methods.get(name);
    }

    Method method = clusterManagerClass.getMethod(name);
    methods.put(name, method);
    return method;
  }

}
