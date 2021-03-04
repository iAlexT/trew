package me.yushust.inject.multibinding.builder;

import me.yushust.inject.Binder;
import me.yushust.inject.key.TypeReference;
import me.yushust.inject.multibinding.CollectionCreator;
import me.yushust.inject.multibinding.MapCreator;

import java.util.*;

/**
 * Represents a binding builder for collections,
 * it can be qualified.
 *
 * @param <T> The element key being bound
 */
public interface MultiBindingBuilder<T> extends Binder.Qualified<MultiBindingBuilder<T>> {

  /** Starts linking and scoping the element type as a Set */
  default CollectionMultiBindingBuilder<T> asSet() {
    return asCollection(Set.class, HashSet::new);
  }

  /** Starts linking and scoping the element type as a List */
  default CollectionMultiBindingBuilder<T> asList() {
    return asCollection(List.class, ArrayList::new);
  }

  /**
   * Starts linking and scoping the element type
   * using the collection creator returned instances
   */
  default CollectionMultiBindingBuilder<T> asCollection(CollectionCreator collectionCreator) {
    return asCollection(Collection.class, collectionCreator);
  }

  /**
   * Starts linking and scoping the element type
   * using the collection creator returned instances
   */
  CollectionMultiBindingBuilder<T> asCollection(Class<?> baseType, CollectionCreator collectionCreator);

  /**
   * Starts linking and scoping the element type as
   * a Map with the specified key type
   */
  default <K> MapMultiBindingBuilder<K, T> asMap(Class<K> keyClass) {
    return asMap(keyClass, HashMap::new);
  }

  /**
   * Starts linking and scoping the element type as
   * a Map with the specified key type and map creator
   */
  default <K> MapMultiBindingBuilder<K, T> asMap(Class<K> keyClass, MapCreator mapCreator) {
    return asMap(TypeReference.of(keyClass), mapCreator);
  }

  /**
   * Starts linking and scoping the element type as
   * a Map with the specified key type
   */
  default <K> MapMultiBindingBuilder<K, T> asMap(TypeReference<K> keyReference) {
    return asMap(keyReference, HashMap::new);
  }

  /**
   * Starts linking and scoping the element type as
   * a Map with the specified key type and map creator
   */
  <K> MapMultiBindingBuilder<K, T> asMap(TypeReference<K> keyReference, MapCreator mapCreator);

}