package me.yushust.inject.multibinding.builder;

import me.yushust.inject.Binder;
import me.yushust.inject.key.TypeReference;

/**
 * Extension of {@link Binder} for adding
 * multi-binding methods
 */
public interface MultiBinder extends Binder {

  /**
   * Creates a new {@link MultiBindingBuilder} to
   * bind the specified {@code keyType}
   */
  default <T> MultiBindingBuilder<T> multibind(Class<T> keyType) {
    return multibind(TypeReference.of(keyType));
  }

  /**
   * Creates a new {@link MultiBindingBuilder} to
   * bind the specified {@code keyType}
   */
  <T> MultiBindingBuilder<T> multibind(TypeReference<T> keyType);

}
