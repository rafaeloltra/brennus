package brennus;

import java.util.ArrayList;
import java.util.List;

import brennus.MethodBuilder.MethodHandler;
import brennus.model.ExistingType;
import brennus.model.Field;
import brennus.model.FutureType;
import brennus.model.MemberFlags;
import brennus.model.Method;
import brennus.model.Protection;
import brennus.model.Type;

/**
 * builds a class
 *
 * @author Julien Le Dem
 *
 */
public class ClassBuilder {

  private final String name;
  private Type extending;
  private final List<Field> fields = new ArrayList<Field>();
  private final List<Field> staticFields = new ArrayList<Field>();
  private final List<Method> methods = new ArrayList<Method>();
  private final List<Method> staticMethods = new ArrayList<Method>();
  private final List<Method> constructors = new ArrayList<Method>();
  private final String sourceFile;
  private final Builder builder;

  ClassBuilder(String name, Type extending, Builder builder) {
    this.name = name;
    this.extending = extending;
    this.builder = builder;
    StackTraceElement creatingStackFrame = builder.getCreatingStackFrame();
    if (creatingStackFrame == null) {
      sourceFile = "generated";
    } else {
      sourceFile = creatingStackFrame.getFileName();
    }
  }

  // builder methods

  /**
   * @param protection public/package/protected/private
   * @param type
   * @param name
   * @return this
   */
  public ClassBuilder field(Protection protection, Type type, String name) {
    field(protection, type, name, false);
    return this;
  }

  /**
   * @param protection public/package/protected/private
   * @param type
   * @param name
   * @return this
   */
  public ClassBuilder staticField(Protection protection, Type type, String name) {
    field(protection, type, name, true);
    return this;
  }

  /**
   * .startStaticMethod(protection, return, name){statements}.endMethod()
   *
   * @param protection public/package/protected/private
   * @param returnType
   * @param methodName
   * @return a MethodDeclarationBuilder
   */
  public MethodDeclarationBuilder startStaticMethod(Protection protection, Type returnType, String methodName) {
    return startMethod(protection, returnType, methodName, true);
  }

  /**
   * .startMethod(protection, return, name){statements}.endMethod()
   *
   * @param protection public/package/protected/private
   * @param returnType
   * @param methodName
   * @return a MethodDeclarationBuilder
   */
  public MethodDeclarationBuilder startMethod(Protection protection, Type returnType, String methodName) {
    return startMethod(protection, returnType, methodName, false);
  }

  /**
   * @return the resulting class
   */
  public FutureType endClass() {
    FutureType futureType = new FutureType(name, extending == null ? ExistingType.OBJECT : extending, fields, staticFields, methods, staticMethods, constructors, sourceFile);
    new ClassValidator().validate(futureType);
    return futureType;
  }


  public ConstructorDeclarationBuilder startConstructor(Protection protection) {
    return new ConstructorDeclarationBuilder(this.name.replace(".", "/"), protection, new MethodHandler() {
      public ClassBuilder handleMethod(Method method) {
        constructors.add(method);
        return ClassBuilder.this;
      }
    }, builder);
  }

  // internals

  private MethodDeclarationBuilder startMethod(Protection protection, Type returnType, String methodName, final boolean isStatic) {
    // TODO: allow final
    return new MethodDeclarationBuilder(this.name.replace(".", "/"), new MemberFlags(isStatic, false, protection), returnType, methodName, new MethodHandler() {
      public ClassBuilder handleMethod(Method method) {
        (isStatic ? staticMethods : methods).add(method);
        return ClassBuilder.this;
      }
    }, builder);
  }

  private void field(Protection protection, Type type, String name, boolean isStatic) {
    // TODO: allow final
    (isStatic ? staticFields : fields).add(new Field(new MemberFlags(isStatic, false, protection), type, name));
  }

}
