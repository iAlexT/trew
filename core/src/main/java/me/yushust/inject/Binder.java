package me.yushust.inject;

import me.yushust.inject.error.ErrorAttachable;
import me.yushust.inject.key.Key;
import me.yushust.inject.key.TypeReference;
import me.yushust.inject.provision.std.generic.GenericProvider;
import me.yushust.inject.scope.Scope;
import me.yushust.inject.scope.Scopes;

import javax.inject.Provider;
import java.lang.annotation.Annotation;
import java.util.*;

// TODO: Add the EDSL specification
public interface Binder extends ErrorAttachable {

  /**
   * @deprecated Unsafe operation, the method is here
   * to avoid the usage of raw-types in some cases.
   * If you misuse it, everything will end up bugged.
   *
   * <p>The method directly puts the key with the
   * specified provider to the map, it's not checked
   * so it's unsafe.</p>
   */
  @Deprecated
  void $unsafeBind(Key<?> key, Provider<?> provider);

  default <T> QualifiedBindingBuilder<T> bind(Class<T> keyType) {
    return bind(TypeReference.of(keyType));
  }

  <T> QualifiedBindingBuilder<T> bind(TypeReference<T> keyType);

  default void install(Module... modules) {
    install(Arrays.asList(modules));
  }

  void install(Iterable<? extends Module> modules);

  /**
   * Represents a binding builder that can be
   * scoped. This interface marks the end of
   * the configuration of a binding
   */
  interface Scoped {

    /** Scopes the binding being built */
    void in(Scope scope);

    /** Alias method for in(Scopes.SINGLETON) */
    default void singleton() {
      in(Scopes.SINGLETON);
    }

  }

  /**
   * Represents a binding builder that can be
   * qualified, for example with an annotation,
   * an annotation type, etc.
   * @param <R> The return type for all the
   *           qualify methods
   */
  interface Qualified<R> {

    /** Qualifies the key with the specified annotation type */
    R markedWith(Class<? extends Annotation> qualifierType);

    /** Qualifies the key with the specific annotation instance */
    R qualified(Annotation annotation);

    /** Qualifies the key with the specific name */
    R named(String name);

  }

  /**
   * Represents a binding builder that can be
   * linked to another key (or the same key)
   * @param <R> The return type for the
   *           link creation methods
   * @param <T> The key being bound
   */
  interface Linked<R, T> {

    /** Links the key to a class */
    default R to(Class<? extends T> targetType) {
      return to(TypeReference.of(targetType));
    }

    /** Links the key to a (possible) generic type */
    R to(TypeReference<? extends T> targetType);

    /** Links the key to a specific provider */
    R toProvider(Provider<? extends T> provider);

    /** Links the key to a generic provider */
    R toGenericProvider(GenericProvider<? extends T> provider);

    /** Links the key to a specific provider type */
    default <P extends Provider<? extends T>> R toProvider(Class<P> providerClass) {
      return toProvider(TypeReference.of(providerClass));
    }

    /** Links the key to a specific provider (possible) generic type */
    <P extends Provider<? extends T>> R toProvider(TypeReference<P> providerClass);

  }

  /**
   * Represents a binding builder that can be qualified,
   * linked and scoped. This is the principal binding
   * builder.
   * @param <T> The key being bound
   */
  interface QualifiedBindingBuilder<T> extends Qualified<QualifiedBindingBuilder<T>>, Linked<Scoped, T>, Scoped {

    /** Binds the key to a specific instance */
    void toInstance(T instance);

  }

}
