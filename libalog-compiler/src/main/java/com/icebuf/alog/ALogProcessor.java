package com.icebuf.alog;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.FileObject;


/**
 *
 * <p>
 * ALogProcessor
 * </p>
 * Description:
 * ALogProcessor
 *
 * @author tangjie
 * @version 1.0 on 2020/12/17.
 */
@AutoService(Processor.class)  //auto-service
@SupportedOptions("moduleName")
@SupportedSourceVersion(SourceVersion.RELEASE_8)  //源码类型 1.8
public class ALogProcessor extends AbstractProcessor  {

    private static final String KEY_MODULE_NAME = "moduleName";

    private Messager messager; //使用日志打印

    private Filer filer;  //用于文件处理

    private Elements elements;

    private final Map<String, String> logTagMap = new HashMap<>();

    private String moduleName;

    private final List<String> helperList = new ArrayList<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        elements = processingEnv.getElementUtils();
        moduleName = ProcessorUtils.getModuleName(processingEnv, KEY_MODULE_NAME);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(ALogTag.class.getName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        //获取注解
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ALogTag.class);
        if(elements.isEmpty()) {
            return true;
        }
        //获取应用包名
        String packageName = ProcessorUtils.getEnvPackageName(roundEnv);
        printNote(packageName);
        //创建类名-tag的映射表
        for (Element element : elements) {
            if(element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                String qualifiedName = typeElement.getQualifiedName().toString();
                ALogTag logTag = element.getAnnotation(ALogTag.class);
                logTagMap.put(qualifiedName, logTag.value());
            }
        }
//        if(logTagMap != null) {
//            //写到源码文件
//            writeFile(packageName, logTagMap);
//        }

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("generate")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TagMapper.class);

        methodBuilder.addCode("\nTagMapper mapper = new TagMapper();");
        for (Map.Entry<String, String> entry : logTagMap.entrySet()) {
            String code = String.format(Locale.getDefault(),
                    "\nmapper.putTag(\"%s\",\"%s\");",
                    entry.getKey(), entry.getValue());
            methodBuilder.addCode(code);
        }
        methodBuilder.addCode("\nreturn mapper;");

        MethodSpec methodSpec = methodBuilder.build();
        TypeSpec typeSpec = TypeSpec.classBuilder(getGeneratorClassName())
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(TagMapperGenerator.class)
                .addMethod(methodSpec)
                .build();
        JavaFile javaFile = JavaFile.builder(packageName, typeSpec).indent("\t").build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            printNote(e.getMessage());
        }
        printNote(typeSpec.toString());
        return true;
    }

    private void writeFile(String packageName, Map<String, String> logTagMap) {
        String className = "";
        if (!Utils.isEmpty(moduleName)) {
            className = ProcessorUtils.firstCharUpper(moduleName);
        }
        className += TagMapper.class.getSimpleName() + "Impl";
        Writer writer = null;
        //创建源码文件
        try {
            FileObject fileObject = filer.createSourceFile(packageName + "." + className);
            writer = fileObject.openWriter();
            String source = new MapperSourceBuilder()
                    .setPackage(packageName)
                    .setImports(TagMapper.class, Map.class)
                    .setExtendName(TagMapper.class.getSimpleName())
                    .setClassName(className)
                    .setTagMap(logTagMap)
                    .build();
            writer.write(source);
            writer.flush();
        } catch (IOException e) {
            printNote(e.getMessage());
        } finally {
            ProcessorUtils.close(writer);
        }
    }

    public String getGeneratorClassName() {
        return ProcessorUtils.firstCharUpper(moduleName) + "TagMapperGenerator";
    }

    public void printNote(String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

    public void printError(String msg) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg);
    }
}
