package de.zebrajaeger.maven.projectgenerator.opencms;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class SimpleVersionTest {
    @Test
    public void create() throws MojoExecutionException {
        assertThat(SimpleVersion.of("1").toString(), is("1"));
        assertThat(SimpleVersion.of("1-SNAPSHOT").toString(), is("1"));
        assertThat(SimpleVersion.of("1.2").toString(), is("1.2"));
        assertThat(SimpleVersion.of("1.2-SNAPSHOT").toString(), is("1.2"));
        assertThat(SimpleVersion.of("1.2.3").toString(), is("1.2.3"));
        assertThat(SimpleVersion.of("1.2.3-SNAPSHOT").toString(), is("1.2.3"));
    }

}