package me.yushust.inject.multibinding;

import me.yushust.inject.impl.BinderImpl;
import me.yushust.inject.impl.LinkedBuilder;
import me.yushust.inject.key.Key;
import me.yushust.inject.multibinding.builder.KeyBinder;
import me.yushust.inject.multibinding.builder.MapMultiBindingBuilder;
import me.yushust.inject.provision.Providers;
import me.yushust.inject.provision.StdProvider;
import me.yushust.inject.scope.Scope;
import me.yushust.inject.util.Validate;

import javax.inject.Provider;
import java.util.Map;

class MapMultiBindingBuilderImpl<K, V>
    implements MapMultiBindingBuilder<K, V> {

  private final BinderImpl binder;
  private final MapCreator mapCreator;
  private final Key<Map<K, V>> mapKey;
  private final Key<V> valueKey;

  MapMultiBindingBuilderImpl(BinderImpl binder, MapCreator mapCreator, Key<Map<K, V>> mapKey, Key<V> valueKey) {
    this.binder = binder;
    this.mapCreator = mapCreator;
    this.mapKey = mapKey;
    this.valueKey = valueKey;
  }

  @Override
  public void in(Scope scope) {
    Validate.notNull(scope, "scope");
    Provider<? extends Map<K, V>> provider = Providers.unwrap(binder.getProvider(mapKey));
    if (provider != null) {
      binder.$unsafeBind(mapKey, Providers.scope(mapKey, provider, scope));
    }
  }

  @Override
  public KeyBinder<K, V> bind(K key) {
    return new KeyBinderImpl(key);
  }

  class KeyBinderImpl implements
      KeyBinder<K, V>,
      LinkedBuilder<MapMultiBindingBuilder<K, V>, V> {

    private final K key;

    private KeyBinderImpl(K key) {
      this.key = key;
    }

    @Override
    public Key<V> key() {
      return valueKey;
    }

    @Override
    public MapMultiBindingBuilder<K, V> toProvider(Provider<? extends V> provider) {
      Validate.notNull(provider, "provider");
      StdProvider<? extends Map<K, V>> mapProvider = binder.getProvider(mapKey);

      if (mapProvider == null) {
        mapProvider = new MapBoundProvider<>(mapCreator);
        binder.$unsafeBind(mapKey, mapProvider);
      }

      Provider<? extends Map<K, V>> delegate = Providers.unwrap(mapProvider);
      if (!(delegate instanceof MapBoundProvider)) {
        throw new IllegalStateException("The key '" + mapKey
            + "' is already bound and it isn't a multibinding!");
      }
      @SuppressWarnings("unchecked")
      MapBoundProvider<K, V> collectionDelegate =
          (MapBoundProvider<K, V>) delegate;
      collectionDelegate.getModifiableProviderMap().put(key, provider);
      return MapMultiBindingBuilderImpl.this;
    }

    @Override
    public MapMultiBindingBuilder<K, V> toInstance(V instance) {
      return toProvider(Providers.instanceProvider(key(), instance));
    }
  }
}
