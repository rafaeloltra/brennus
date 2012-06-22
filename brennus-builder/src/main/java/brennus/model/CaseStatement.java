package brennus.model;

import java.util.List;

public class CaseStatement extends Statement {

  private final List<Statement> statements;

  public CaseStatement(Expression value, List<Statement> statements) {
    super(value);
    this.statements = statements;
  }

  @Override
  public void accept(StatementVisitor statementVisitor) {
    statementVisitor.visit(this);
  }

  public List<Statement> getStatements() {
    return statements;
  }

}
