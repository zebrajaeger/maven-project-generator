package de.zebrajaeger.maven.projectgenerator;

public class ProjectResource {
    private String path;
    private byte[] content;

    public ProjectResource(String path, byte[] content) {
        this.path = path;
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public byte[] getContent() {
        return content;
    }
}
