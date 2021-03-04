package me.yushust.inject.multibinding.builder;

import me.yushust.inject.Binder;

/**
 * Represents a binding builder for collections,
 * it can be linked and scoped, it's qualified
 * using {@link MultiBindingBuilder}
 *
 * @param <T> The collection element type
 */
public interface CollectionMultiBindingBuilder<T>
    extends Binder.Linked<CollectionMultiBindingBuilder<T>, T>, Binder.Scoped {

  /** Adds an instance of the specific element type to the collection */
  CollectionMultiBindingBuilder<T> toInstance(T instance);

}
