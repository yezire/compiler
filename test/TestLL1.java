import java.util.Set;
import org.junit.Test;
import syntax.Grammar;
import syntax.syntaxLL.LL1;

public class TestLL1 {
  @Test
 public void test(){
    //syntax
    String grammarPath = "src/test/grammar.txt";
    Grammar preprocess = new Grammar();
    preprocess.inputGrammar(grammarPath);
    LL1 ll=new LL1();
    ll.initProductions();
   ll.extract();
   ll.isLL1();

    System.out.println("test1");

  }

}
