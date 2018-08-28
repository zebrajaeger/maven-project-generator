package de.zebrajaeger.maven.projectgenerator;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.io.IOUtils;
import org.apache.maven.archetype.ui.generation.ArchetypeGenerationConfigurator;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.DefaultProjectBuildingRequest;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.shared.artifact.DefaultArtifactCoordinate;
import org.apache.maven.shared.artifact.resolve.ArtifactResolver;
import org.apache.maven.shared.artifact.resolve.ArtifactResolverException;
import org.apache.maven.shared.artifact.resolve.ArtifactResult;
import org.apache.maven.shared.dependencies.DefaultDependableCoordinate;
import org.apache.maven.shared.dependencies.resolve.DependencyResolver;
import org.apache.maven.shared.dependencies.resolve.DependencyResolverException;
import org.eclipse.aether.RepositorySystemSession;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.PROCESS_SOURCES, requiresProject = false)
public class GenerateMojo extends AbstractMojo {

    @Component
    private ArchetypeGenerationConfigurator configurator;

    @Parameter(property = "archetypeGroupId", defaultValue = "de.zebrajaeger")
    private String archetypeGroupId;
    @Parameter(property = "archetypeArtifactId", defaultValue = "project-generator-testproject")
    private String archetypeArtifactId;
    @Parameter(property = "archetypeVersion", defaultValue = "0.0.1-SNAPSHOT")
    private String archetypeVersion;

    @Parameter(defaultValue = "${localRepository}", readonly = true, required = true)
    private ArtifactRepository localRepository;
    @Parameter(defaultValue = "${project.remoteArtifactRepositories}", readonly = true, required = true)
    private List<ArtifactRepository> remoteArtifactRepositories;

    @Parameter(defaultValue = "${repositorySystemSession}", readonly = true, required = true)
    private RepositorySystemSession repoSession;

    @Parameter(defaultValue = "${session}", required = true, readonly = true)
    private MavenSession session;

    @Component
    private ArtifactResolver artifactResolver;

    @Component
    private DependencyResolver dependencyResolver;

    public void execute() throws MojoExecutionException, MojoFailureException {
        File file;
        try {
            file = findProjectJar();

            DefaultDependableCoordinate coordinate = new DefaultDependableCoordinate();
            coordinate.setGroupId(archetypeGroupId);
            coordinate.setArtifactId(archetypeArtifactId);
            coordinate.setVersion(archetypeVersion);

            ProjectBuildingRequest buildingRequest =
                    new DefaultProjectBuildingRequest(session.getProjectBuildingRequest());

            getLog().info("Resolving " + coordinate + " with transitive dependencies");
            List<URL> cp = new LinkedList<>();
            try {
                Iterable<ArtifactResult> artifactResults = dependencyResolver.resolveDependencies(buildingRequest, coordinate, null);
                Iterator<ArtifactResult> iterator = artifactResults.iterator();
                while(iterator.hasNext()){
                    ArtifactResult r = iterator.next();
                    System.out.println(r.getArtifact());
                    cp.add(r.getArtifact().getFile().toURI().toURL());
                }
            } catch (DependencyResolverException e) {
                throw new MojoExecutionException("Couldn't download artifact: " + e.getMessage(), e);
            } catch (MalformedURLException e) {
                throw new MojoExecutionException("Couldn't get URI of artifact: " + e.getMessage(), e);
            }

            URLClassLoader urlClassLoader = URLClassLoader.newInstance(cp.toArray(new URL[cp.size()]));
            Class<?> executorClazz = urlClassLoader.loadClass(Executor.class.getName());
            Object executor = executorClazz.newInstance();
            Method exec = executorClazz.getMethod("exec", String.class);
            exec.invoke(executor, file.getAbsolutePath());

            //processJarResources(file);
            System.out.println("YO!!!" + file.getAbsolutePath());
        } catch (ArtifactResolverException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private File findProjectJar() throws ArtifactResolverException {
        File file;
        DefaultArtifactCoordinate c = new DefaultArtifactCoordinate();
        c.setGroupId(archetypeGroupId);
        c.setArtifactId(archetypeArtifactId);
        c.setVersion(archetypeVersion);

        DefaultProjectBuildingRequest r = new DefaultProjectBuildingRequest();
        r.setLocalRepository(localRepository);
        r.setRemoteRepositories(remoteArtifactRepositories);
        r.setRepositorySession(repoSession);

        ArtifactResult artifactResult = artifactResolver.resolveArtifact(r, c);
        Artifact artifact = artifactResult.getArtifact();
        file = artifact.getFile();
        return file;
    }
}
