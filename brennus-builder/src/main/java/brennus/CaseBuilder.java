package brennus;

import brennus.model.CaseBlockStatement;
import brennus.model.LiteralExpression;
import brennus.model.Statement;

/**
 * builds a case
 *
 * @author Julien Le Dem
 *
 * @param <T> the type of the parent of the switch to return on completion
 */
public final class CaseBuilder<T> extends StatementBuilder<CaseBuilder<T>> {

  public interface CaseStatementHandler<T> {
    SwitchBuilder<T> handleStatement(CaseBlockStatement caseStatement);
  }

  private final LiteralExpression literalExpression;
  private final CaseStatementHandler<T> statementHandler;
  private final ImmutableList<Statement> statements;
  private final int line;

  CaseBuilder(LiteralExpression literalExpression, CaseStatementHandler<T> statementHandler, Builder builder) {
    this(
        literalExpression,
        statementHandler,
        builder,
        ImmutableList.<Statement>empty(),
        builder.getSourceLineNumber());
  }

  private CaseBuilder(
      LiteralExpression literalExpression,
      CaseStatementHandler<T> statementHandler,
      Builder builder,
      ImmutableList<Statement> statements,
      int line) {
    super(builder);
    this.literalExpression = literalExpression;
    this.statementHandler = statementHandler;
    this.statements = statements;
    this.line = line;
  }

  private CaseBuilder<T> addStatement(Statement statement) {
    return new CaseBuilder<T>(literalExpression, statementHandler, builder, statements.append(statement), line);
  }

  public SwitchBuilder<T> endCase() {
    return statementHandler.handleStatement(new CaseBlockStatement(line, literalExpression, statements, false));
  }

  protected StatementHandler<CaseBuilder<T>> statementHandler() {
    return new StatementHandler<CaseBuilder<T>>() {
      public CaseBuilder<T> handleStatement(Statement statement) {
        return addStatement(statement);
      }
    };
  }

  public SwitchBuilder<T> breakCase() {
    return statementHandler.handleStatement(new CaseBlockStatement(line, literalExpression, statements, true));
  }

  public <S> S transform(Function<CaseBuilder<T>, S> function) {
    return function.apply(this);
  }
}
