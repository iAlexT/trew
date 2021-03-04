package me.yushust.inject.assisted;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;

public class AssistedInjectTest {

  @Test
  public void test() {

    /*Injector injector = Injector.create(binder -> {
      binder.bind(Foo.class).toFactory(FooFactory.class);
    });

    FooFactory factory = injector.getInstance(FooFactory.class);
    Foo foo = factory.create("hello", 123);

    Assertions.assertEquals("hello", foo.name);
    Assertions.assertEquals(123, foo.number);
    Assertions.assertNotNull(foo.baz);
    Assertions.assertNotNull(foo.bar);*/
  }

  public static class Bar {
  }

  public static class Foo<T> {

    private final String name;
    private final int number;
    private final Bar baz;
    @Inject private Bar bar;

    @Assisted
    public Foo(
        @Assist String name,
        @Assist int number,
        Bar baz
    ) {
      this.name = name;
      this.number = number;
      this.baz = baz;
    }

  }

  public interface FooFactory {

    Foo create(String name,int asd);

  }

}
