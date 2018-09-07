package {{package}}.common;

import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lars Brandt, brandt@silpion.de
 */
public class BuildVersion {

    private static final Logger LOG = LoggerFactory.getLogger(BuildVersion.class);

    private static final BuildVersion INSTANCE = new BuildVersion();

    public static BuildVersion getInstance() {
        return INSTANCE;
    }

    public enum VALUE {
        TAGS("git.tags"),
        COMMIT_ID_ABBREV("git.commit.id.abbrev"),
        COMMIT_USER_EMAIL("git.commit.user.email"),
        COMMIT_MESSAGE_FULL("git.commit.message.full"),
        COMMIT_ID("git.commit.id"),
        COMMIT_ID_DESCRIBE_SHORT("git.commit.id.describe-short"),
        COMMIT_MESSAGE_SHORT("git.commit.message.short"),
        COMMIT_USER_NAME("git.commit.user.name"),
        BUILD_USER_NAME("git.build.user.name"),
        COMMIT_ID_DESCRIBE("git.commit.id.describe"),
        BUILD_USER_EMAIL("git.build.user.email"),
        BRANCH("git.branch"),
        COMMIT_TIME("git.commit.time"),
        DIRTY("git.dirty"),
        BUILD_TIME("git.build.time"),
        REMOTE_ORIGIN_URL("git.remote.origin.url");
        private String mName;

        VALUE(String pName) {
            mName = pName;
        }

        public String getName() {
            return mName;
        }
    }

    /**
     * Content from git.properties file
     */
    private Properties gitProperties;

    public BuildVersion() {
        gitProperties = new Properties();

        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("git.properties")) {
            gitProperties.load(is);
        } catch (Exception e) {
            LOG.error("Error load git.properties for version info. (File is missing?)", e);
        }
    }

    /**
     * get a git-value
     *
     * @param pValue
     * @return
     */
    public String get(VALUE pValue) {
        Object o = gitProperties.get(pValue.getName());
        return (o != null) ? o.toString() : null;
    }
}
