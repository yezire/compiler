import org.junit.Test;
import syntax.Grammar;
import syntax.LR0;

public class TestGroup {
  @Test
  public void test() {
    Grammar preprocess =new Grammar();
    preprocess.inputGrammar();
    preprocess.generateFirstCollection();
    preprocess.generateProductionsByLeft();
    LR0.createTable();
    LR0.showTable();
  }

}
