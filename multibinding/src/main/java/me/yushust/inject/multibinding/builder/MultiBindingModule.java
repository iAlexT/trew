package me.yushust.inject.multibinding.builder;

import me.yushust.inject.Binder;
import me.yushust.inject.Module;

public interface MultiBindingModule extends Module {

  @Override
  default void configure(Binder binder) {
    configure(null);
  }

  void configure(MultiBinder binder);

}
