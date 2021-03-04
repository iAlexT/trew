package me.yushust.inject.multibinding.builder;

import me.yushust.inject.Binder;

/**
 * Represents a map key that's being bound
 * to a value. It can be linked to a provider
 *
 * @param <K> The map key type
 * @param <V> The map value type
 */
public interface KeyBinder<K, V>
    extends Binder.Linked<MapMultiBindingBuilder<K, V>, V> {

  /**
   * Adds an instance of the specific value type to the map
   */
  MapMultiBindingBuilder<K, V> toInstance(V instance);

}
