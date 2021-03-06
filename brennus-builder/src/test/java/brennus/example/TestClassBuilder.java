package brennus.example;

import static brennus.model.ExistingType.INT;
import static brennus.model.ExistingType.OBJECT;
import static brennus.model.ExistingType.STRING;
import static brennus.model.ExistingType.existing;
import static brennus.model.Protection.PRIVATE;
import static brennus.model.Protection.PUBLIC;
import brennus.Builder;
import brennus.model.Type;
import brennus.printer.TypePrinter;

import org.junit.Test;

public class TestClassBuilder {

  @Test
  public void testBuilder() {
    Type testClass = new Builder()
        .startClass("test.TestClass", existing(TestClassBuilder.class))
          .field(PRIVATE, STRING, "foo")
          .field(PRIVATE, INT, "bar")
          .startMethod(PUBLIC, STRING, "getFoo")
            .returnExp().get("foo").endReturn()
          .endMethod()
          .startMethod(PUBLIC, INT, "getBar")
            .returnExp().get("bar").endReturn()
          .endMethod()
          .startMethod(PUBLIC, OBJECT, "get").param(INT, "i")
            .switchOn().get("i").switchBlock()
              .caseBlock(0)
                .returnExp().callOnThisNoParam("getFoo").endReturn()
              .endCase()
              .caseBlock(1)
                .returnExp().callOnThisNoParam("getBar").endReturn()
              .endCase()
              .defaultCase()
                .throwExp().callOnThisNoParam("error").endThrow()
              .endCase()
            .endSwitch()
          .endMethod()
        .endClass();
    new TypePrinter().print(testClass);
  }

//  @Test
//  public void testBuilder2() {
//    ClassBuilder classBuilder = startClass("test.TestClass").extendsType(existing(TestClassBuilder.class));
//
//    classBuilder.field(STRING, "foo", PRIVATE);
//    classBuilder.field(INT, "bar", PRIVATE);
//
//    MethodBuilder methodBuilder = classBuilder.startMethod(STRING, "getFoo", PUBLIC);
//    methodBuilder.expression().get("foo").returnExp().endMethod();
//
//    methodBuilder = classBuilder.startMethod(INT, "getBar", PUBLIC);
//    methodBuilder.expression().get("bar").returnExp().endMethod();
//
//    methodBuilder = classBuilder.startMethod(OBJECT, "get", PUBLIC).withParameter(INT, "i");
//    SwitchBuilder<MethodBuilder> switchBuilder = methodBuilder.expression().get("i").switchOn();
//    switchBuilder.caseBlock(0).expression().call("getFoo").returnExp().endCase();
//    switchBuilder.caseBlock(1).expression().call("getBar").returnExp().endCase();
//    switchBuilder.defaultCase().expression().call("error").throwException().endCase();
//    switchBuilder.endSwitch();
//    methodBuilder.endMethod();
//
//
//    Type testClass = classBuilder.endClass();
//    new TypePrinter().print(testClass);
//  }

}
