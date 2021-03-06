package brennus;

import java.util.List;

import brennus.model.CallConstructorStatement;
import brennus.model.CaseBlockStatement;
import brennus.model.DefineVarStatement;
import brennus.model.ExpressionStatement;
import brennus.model.FieldAccessType;
import brennus.model.FutureType;
import brennus.model.GotoCaseStatement;
import brennus.model.GotoStatement;
import brennus.model.IfStatement;
import brennus.model.LabelStatement;
import brennus.model.LocalVariableAccessType;
import brennus.model.Method;
import brennus.model.ParameterAccessType;
import brennus.model.ReturnStatement;
import brennus.model.SetStatement;
import brennus.model.Statement;
import brennus.model.StatementVisitor;
import brennus.model.SwitchStatement;
import brennus.model.ThrowStatement;
import brennus.model.VarAccessType;
import brennus.model.VarAccessTypeVisitor;

public class ClassValidator {

  public void validate(FutureType type) {
    for (Method method : type.getMethods()) {
      validate(type, method);
    }
    for (Method method : type.getStaticMethods()) {
      validate(type, method);
    }
  }

  private void validate(FutureType type, Method method) {
    MethodContext context = new MethodContext(type, method);
    Iterable<Statement> statements = method.getStatements();
    for (Statement statement : statements) {
      validate(context, statement);
    }

  }

  private void validate(final MethodContext context, Statement statement) {
    try {
    StatementVisitor statementVisitor = new StatementVisitor() {

      @Override
      public void visit(SetStatement setStatement) {
//        VarAccessType varTypeToSet = context.getVarAccessType(setStatement.getTo());
//        varTypeToSet.accept(new VarAccessTypeVisitor() {
//          public void visit(ParameterAccessType parameterAccessType) {
//            // TODO
//          }
//          public void visit(FieldAccessType fieldAccessType) {
//            // TODO
//          }
//          public void visit(LocalVariableAccessType localVariableAccessType) {
//            // TODO
//          }
//        });
      }

      public void visit(ThrowStatement throwStatement) {
        // TODO

      }

      public void visit(CaseBlockStatement caseStatement) {
        // TODO Auto-generated method stub

      }

      public void visit(SwitchStatement switchStatement) {
        // TODO Auto-generated method stub

      }

      public void visit(ExpressionStatement methodCallStatement) {
        // TODO Auto-generated method stub

      }

      public void visit(ReturnStatement returnStatement) {
        // TODO Auto-generated method stub

      }

      public void visit(IfStatement ifStatement) {
        // TODO Auto-generated method stub

      }

      @Override
      public void visit(LabelStatement labelStatement) {
        // TODO Auto-generated method stub

      }

      @Override
      public void visit(GotoStatement gotoStatement) {
        // TODO Auto-generated method stub

      }

      @Override
      public void visit(CallConstructorStatement callConstructorStatement) {
        // TODO Auto-generated method stub

      }

      @Override
      public void visit(DefineVarStatement defineVarStatement) {
        // TODO Auto-generated method stub

      }

      @Override
      public void visit(GotoCaseStatement gotoCaseStatement) {
        // TODO Auto-generated method stub

      }
    };
    statement.accept(statementVisitor);
    } catch (RuntimeException e) {
      throw new RuntimeException("error at line " + statement.getLine() + " of method "+context.getClassIdentifier()+"."+context.getMethod().getName()+context.getMethod().getSignature(), e);
    }
  }

}