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

import org.apache.commons.lang3.StringUtils;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;

/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.PROCESS_SOURCES, requiresProject = false)
@SuppressWarnings("unused")
public class GenerateMojo extends AbstractMojo {

    @Component
    @SuppressWarnings("unused")
    private ArchetypeGenerationConfigurator configurator;

    @Parameter(property = "template", defaultValue = "de.zebrajaeger:project-generator-testproject:0.0.1-SNAPSHOT")
    private String template;

    @Parameter(defaultValue = "${localRepository}", readonly = true, required = true)
    @SuppressWarnings("unused")
    private ArtifactRepository localRepository;
    @Parameter(defaultValue = "${project.remoteArtifactRepositories}", readonly = true, required = true)
    @SuppressWarnings("unused")
    private List<ArtifactRepository> remoteArtifactRepositories;

    @Parameter(defaultValue = "${repositorySystemSession}", readonly = true, required = true)
    @SuppressWarnings("unused")
    private RepositorySystemSession repoSession;

    @Parameter(defaultValue = "${session}", required = true, readonly = true)
    @SuppressWarnings("unused")
    private MavenSession session;

    @Component
    @SuppressWarnings("unused")
    private ArtifactResolver artifactResolver;

    @Component
    @SuppressWarnings("unused")
    private DependencyResolver dependencyResolver;

    public void execute() throws MojoExecutionException, MojoFailureException {
        // Coordinate of Project Template resources
        DefaultDependableCoordinate coordinate = new DefaultDependableCoordinate();
        if (StringUtils.isBlank(template)) {
            throw new MojoFailureException("You must specify an template coordinate, "
                    + "e.g. -Dtemplate=de.zebrajaeger:project-generator-testproject:0.0.1-SNAPSHOT");
        }

        String[] tokens = template.split(":");
        if (tokens.length < 3 || tokens.length > 5) {
            throw new MojoFailureException("Invalid template, you must specify "
                    + "groupId:artifactId:version[:packaging[:classifier]] " + template);
        }
        coordinate.setGroupId(tokens[0]);
        coordinate.setArtifactId(tokens[1]);
        coordinate.setVersion(tokens[2]);
        if (tokens.length >= 4) {
            coordinate.setType(tokens[3]);
        }
        if (tokens.length == 5) {
            coordinate.setClassifier(tokens[4]);
        }

        File file;
        try {
            file = findProjectJar(coordinate);
        } catch (ArtifactResolverException e) {
            throw new MojoExecutionException("Couldn't resolve template artifact: " + e.getMessage(), e);
        }

        // Collect template resources, dependency jars and transitive dependency jars
        List<URL> classpath = new LinkedList<>();
        ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest(session.getProjectBuildingRequest());
        getLog().info("Resolving " + coordinate + " with transitive dependencies");
        try {
            Iterable<ArtifactResult> artifactResults = dependencyResolver.resolveDependencies(buildingRequest, coordinate, null);
            for (ArtifactResult artifactResult : artifactResults) {
                System.out.println(artifactResult.getArtifact());
                classpath.add(artifactResult.getArtifact().getFile().toURI().toURL());
            }
        } catch (DependencyResolverException e) {
            throw new MojoExecutionException("Couldn't download artifact: " + e.getMessage(), e);
        } catch (MalformedURLException e) {
            throw new MojoExecutionException("Couldn't get URI of artifact: " + e.getMessage(), e);
        }

        try {
            // Start Executor into dedicated classloader with all the collected dependencies
            URLClassLoader urlClassLoader = URLClassLoader.newInstance(classpath.toArray(new URL[0]));
            Class<?> executorClazz = urlClassLoader.loadClass(Executor.class.getName());
            Object executor = executorClazz.newInstance();
            Method exec = executorClazz.getMethod("exec", String.class);
            exec.invoke(executor, file.getAbsolutePath());

            System.out.println("YO!!!" + file.getAbsolutePath());
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            throw new MojoExecutionException("Couldn't start executor: " + e.getMessage(), e);
        }
    }

    private File findProjectJar(DefaultDependableCoordinate templateCoordinate) throws ArtifactResolverException {
        DefaultArtifactCoordinate coordinate = new DefaultArtifactCoordinate();
        coordinate.setGroupId(templateCoordinate.getGroupId());
        coordinate.setArtifactId(templateCoordinate.getArtifactId());
        coordinate.setVersion(templateCoordinate.getVersion());
        coordinate.setExtension(templateCoordinate.getType());
        coordinate.setClassifier(templateCoordinate.getClassifier());

        DefaultProjectBuildingRequest projectBuildingRequest = new DefaultProjectBuildingRequest();
        projectBuildingRequest.setLocalRepository(localRepository);
        projectBuildingRequest.setRemoteRepositories(remoteArtifactRepositories);
        projectBuildingRequest.setRepositorySession(repoSession);

        ArtifactResult artifactResult = artifactResolver.resolveArtifact(projectBuildingRequest, coordinate);
        Artifact artifact = artifactResult.getArtifact();
        return artifact.getFile();
    }
}
