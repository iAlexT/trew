package me.yushust.inject.multibinding.builder;

import me.yushust.inject.Binder;

/**
 * Represents a binding builder for maps,
 * binds using a key and a value. It can be
 * scoped.
 *
 * @param <K> The map key type
 * @param <V> The map value type
 */
public interface MapMultiBindingBuilder<K, V>
    extends Binder.Scoped {

  /**
   * Starts linking a key to a value
   */
  KeyBinder<K, V> bind(K key);

}
