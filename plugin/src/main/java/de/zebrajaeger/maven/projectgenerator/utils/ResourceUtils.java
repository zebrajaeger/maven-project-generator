package de.zebrajaeger.maven.projectgenerator.utils;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class ResourceUtils {

//    public static final ResourcePath PROJECT_TEMPLATE = ResourcePath.of("project_template");
//    public static final ResourcePath META_INF = ResourcePath.of("META_INF");
//
//    private ResourceManager resourceManager;
//
//    public ResourceUtils(ResourceManager resourceManager) {
//        this.resourceManager = resourceManager;
//    }
//
//    public void copy(ResourcePath path, File targetDirectory, boolean recursive) throws IOException, TemplateEngineException {
//        copy(path, null, null, targetDirectory, recursive);
//    }
//
//    public void copy(ResourcePath path, File targetDirectory, TemplateProcessor templateProcessor, boolean recursive) throws IOException, TemplateEngineException {
//        copy(path, null, templateProcessor, targetDirectory, recursive);
//    }
//
//    public void copy(ResourcePath path, File targetDirectory, ResourceFilter resourceFilter, boolean recursive) throws IOException, TemplateEngineException {
//        copy(path, resourceFilter, null, targetDirectory, recursive);
//    }
//
//    public void copy(ResourcePath path, ResourceFilter filter, TemplateProcessor templateProcessor, File targetDirectory, boolean recursive) throws IOException, TemplateEngineException {
//        if (filter == null) {
//            filter = new AcceptAllResourceFilter();
//        }
//
//        List<Item> items = resourceManager.getItems(path, recursive)
//                .stream()
//                .filter(filter)
//                .collect(Collectors.toList());
//
//        // for exception handling: oldschool iterations
//        for (Item i : items) {
//            ResourcePath resourcePath = i.getPath().removeParent(path);
//            if (resourcePath.isEmpty()) {
//                continue;
//            }
//
//            File f = new File(targetDirectory, resourcePath.toString());
//            if (i.isNode()) {
//                f.mkdirs();
//            } else if (i.isResource()) {
//                Resource resource = (Resource) i;
//                if (templateProcessor != null) {
//                    String content = new String(resource.getContent(), StandardCharsets.UTF_8);
//                    content = templateProcessor.convert(content);
//                    FileUtils.write(f, content, StandardCharsets.UTF_8);
//                } else {
//                    FileUtils.writeByteArrayToFile(f, resource.getContent());
//                }
//            }
//        }
//    }
}
