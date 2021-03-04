package me.yushust.inject.multibinding;

import me.yushust.inject.impl.BinderImpl;
import me.yushust.inject.impl.KeyBuilder;
import me.yushust.inject.key.Key;
import me.yushust.inject.key.TypeReference;
import me.yushust.inject.multibinding.builder.CollectionMultiBindingBuilder;
import me.yushust.inject.multibinding.builder.MapMultiBindingBuilder;
import me.yushust.inject.multibinding.builder.MultiBindingBuilder;

import java.util.List;
import java.util.Map;

public class MultiBindingBuilderImpl<T> implements
    MultiBindingBuilder<T>,
    KeyBuilder<MultiBindingBuilder<T>, T> {

  private Key<T> key;
  private final BinderImpl binder;

  public MultiBindingBuilderImpl(BinderImpl binder, TypeReference<T> key) {
    this.key = Key.of(key);
    this.binder = binder;
  }

  /** Starts building a binding using the given collection creator */
  @Override
  public CollectionMultiBindingBuilder<T> asCollection(Class<?> baseType, CollectionCreator collectionCreator) {
    Key<List<T>> listKey = Key.of(TypeReference.of(baseType, key.getType().getType()));
    return new CollectionMultiBindingBuilderImpl<>(binder, listKey, key, collectionCreator);
  }

  @Override
  public <K> MapMultiBindingBuilder<K, T> asMap(TypeReference<K> keyReference, MapCreator mapCreator) {
    Key<Map<K, T>> mapKey = Key.of(TypeReference.mapTypeOf(keyReference, key.getType()));
    return new MapMultiBindingBuilderImpl<>(binder, mapCreator, mapKey, key);
  }

  @Override
  public Key<T> key() {
    return key;
  }

  @Override
  public void setKey(Key<T> key) {
    this.key = key;
  }

  @Override
  public MultiBindingBuilder<T> getReturnValue() {
    return this;
  }

}
