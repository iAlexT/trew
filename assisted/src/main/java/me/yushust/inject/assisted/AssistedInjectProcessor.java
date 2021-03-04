package me.yushust.inject.assisted;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AssistedInjectProcessor
    extends AbstractProcessor
    implements Processor {

  @Override
  public Set<String> getSupportedOptions() {
    return Collections.emptySet();
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    Set<String> supportedAnnotations = new HashSet<>();
    supportedAnnotations.add(Assist.class.getName());
    supportedAnnotations.add(Assisted.class.getName());
    return supportedAnnotations;
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.RELEASE_8;
  }

  @Override
  public boolean process(
      Set<? extends TypeElement> annotations,
      RoundEnvironment roundEnv
  ) {
    if (annotations.isEmpty()) {
      return false;
    }

    TypeElement assistedAnnotation = null;
    TypeElement assistAnnotation = null;

    for (TypeElement annotation : annotations) {
      String simpleName = annotation.getSimpleName().toString();
      if (Assist.class.getSimpleName().equals(simpleName)) {
        assistAnnotation = annotation;
      } else {
        assistedAnnotation = annotation;
      }
    }

    for (Element element : roundEnv.getElementsAnnotatedWith(assistedAnnotation)) {
      ExecutableElement constructor = (ExecutableElement) element;
      TypeElement holder = (TypeElement) constructor.getEnclosingElement();
      System.out.println("========================");
      System.out.println(holder.getQualifiedName().toString());
      System.out.println("type parameters: " + holder.getTypeParameters().get(0));
      System.out.println("========================");
    }

    return false;
  }

}
