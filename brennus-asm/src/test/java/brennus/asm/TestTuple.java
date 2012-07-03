package brennus.asm;

import static brennus.ClassBuilder.startClass;
import static brennus.model.ExistingType.INT;
import static brennus.model.ExistingType.OBJECT;
import static brennus.model.ExistingType.STRING;
import static brennus.model.ExistingType.VOID;
import static brennus.model.ExistingType.existing;
import static brennus.model.Protection.PRIVATE;
import static brennus.model.Protection.PUBLIC;
import junit.framework.Assert;
import brennus.BaseTestClass;
import brennus.TestGeneration.DynamicClassLoader;
import brennus.asm.ref.ReferenceClass;
import brennus.model.FutureType;
import brennus.printer.TypePrinter;

import org.junit.Test;

public class TestTuple {

  @Test
  public void testTuple() throws Exception {
    Class<? extends BaseClass> c = ReferenceClass.class;
    testSetGet(c);

    FutureType testClass =
        startClass("brennus.asm.TestTuple$TestClass").extendsType(existing(BaseClass.class))
          .field(STRING, "a", PRIVATE)
          .field(INT, "b", PRIVATE)

          .startMethod(OBJECT, "get", PUBLIC).param(INT, "i")
            .call("println").withParam().literal("get").end()
            .call("println").withParam().get("i").end()
            .switchOn().get("i").end()
              .caseBlock(0)
                .returnExp().get("a").end()
              .endCase()
              .caseBlock(1)
                .returnExp().get("b").end()
              .endCase()
              .defaultCase()
                .throwExp().call("error").end()
              .endCase()
            .endSwitch()
          .endMethod()

          .startMethod(VOID, "set", PUBLIC).param(INT, "i").param(OBJECT, "o")
            .call("println").withParam().literal("set").end()
            .call("println").withParam().get("i").end()
            .switchOn().get("i").end()
              .caseBlock(0)
                .set("a").get("o").end()
              .breakCase()
              .caseBlock(1)
                .set("b").get("o").end()
              .breakCase()
              .defaultCase()
                .throwExp().call("error").end()
              .breakCase()
            .endSwitch()
          .endMethod()

        .endClass();

//    new TypePrinter().print(testClass);

    DynamicClassLoader cl = new DynamicClassLoader();
    cl.define(testClass);
    testSetGet((Class<? extends BaseClass>)cl.loadClass("brennus.asm.TestTuple$TestClass"));
  }

  private void testSetGet(Class<? extends BaseClass> c) throws Exception {
    BaseClass t = c.newInstance();
    t.set(0, "test");
    Assert.assertEquals("test", t.get(0));
    t.set(1, 12);
    Assert.assertEquals(12, t.get(1));
    try {
      t.set(123, null);
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
  }
}