package {{package}}.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Locale;

/**
 * <h3>Staging</h3> To configure the staging set the system property with key: {@link #STAGING_KEY}
 * and a value from {@link ${package}.config.Environment.Staging}. <p/> e.LoggingUtils. <code>export
 * JAVA_OPTS="-Dstaging=TEST"</code>
 *
 * @author brandt
 */
public class Environment {

    private static final Logger LOG = LoggerFactory.getLogger(Environment.class);

  /**
   * The property key to lookup the staging.
   */
  public static final String STAGING_KEY = "staging";

    public enum Staging {
        DEV, TEST, QA, STAGE, LIVE, UNKNOWN
    }

    private static Environment environment = null;

    public static Environment instance() {
        if (environment == null) {
            environment = new Environment();
        }
        return environment;
    }

    private Staging staging;
    private OpenCmsRuntimeConfiguration runtimeConfiguration;

    protected Environment() {

        runtimeConfiguration = new OpenCmsRuntimeConfiguration();

        // read staging config
        String stage = System.getProperty(STAGING_KEY, Staging.UNKNOWN.name());

        try {
            staging = Staging.valueOf(stage.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            LOG.error(String.format("Getting Environment failed, stage value is: '%s'", stage));
            throw e;
        }
        LOG.info(String.format("Environment stage: '%s', property: '%s'", staging, stage));
    }

    /**
     * Returns the configured Staging, identified by {@link #STAGING_KEY}.
     *
     * @return The value is <em>UNKNOWN</em> if no staging was configured.
     */
    public Staging getStaging() {
        return staging;
    }

    public boolean isThisWorkplaceServer() {
        return runtimeConfiguration.isThisWorkplaceServer();
    }

    public boolean isClusteredEnvironment() {
        return runtimeConfiguration.isClusteredEnvironment();
    }
}
