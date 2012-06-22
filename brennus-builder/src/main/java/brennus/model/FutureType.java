package brennus.model;

import java.util.Collections;
import java.util.List;

public final class FutureType extends Type {

  private final String name;
  private final Type extending;
  private final List<Field> fields;
  private final List<Field> staticFields;
  private final List<Method> methods;
  private final List<Method> staticMethods;

  public FutureType(String name, Type extending, List<Field> fields, List<Field> staticFields, List<Method> methods, List<Method> staticMethods) {
    this.name = name;
    this.extending = extending;
    this.fields = Collections.unmodifiableList(fields);
    this.staticFields = Collections.unmodifiableList(staticFields);
    this.methods = Collections.unmodifiableList(methods);
    this.staticMethods = Collections.unmodifiableList(staticMethods);
  }

  @Override
  public void accept(TypeVisitor typeVisitor) {
    typeVisitor.visit(this);
  }

  public String getName() {
    return name;
  }

  public Type getExtending() {
    return extending;
  }

  public List<Field> getFields() {
    return fields;
  }

  public List<Field> getStaticFields() {
    return staticFields;
  }

  public List<Method> getMethods() {
    return methods;
  }

  public List<Method> getStaticMethods() {
    return staticMethods;
  }

  @Override
  public String getClassIdentifier() {
    return getName().replace('.', '/');
  }

  @Override
  public String getSignature() {
    return "L" + getClassIdentifier() + ";";
  }

  @Override
  public boolean isPrimitive() {
    return false;
  }
// TODO add parameters
  // TODO add static methods
  public Method getMethod(String methodName) {
    for (Method method : methods) {
      if (method.getName().equals(methodName)) {
        return method;
      }
    }
    if (extending!=null) {
      return extending.getMethod(methodName);
    }
    return null;
  }

}
