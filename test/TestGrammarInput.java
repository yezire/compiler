import java.util.Map.Entry;
import java.util.Set;
import org.junit.Test;
import syntax.Grammar;

public class TestGrammarInput {
  @Test
  public void test() {
    // String path ="/Users/yezizhi/Desktop/compiler/src/syntax/grammarTest.txt";
    String path = "src/test/myTestGrammar.txt";
    // String path = "src/test/grammarTest.txt";
    Grammar preprocess =new Grammar();
    preprocess.inputGrammar(path);

    System.out.println("================Terminal===============");
    Grammar.getTerminals().forEach(System.out::println);

    System.out.println("================Non-Terminal===============");
    Grammar.getNonTerminals().forEach(System.out::println);

    System.out.println("================FirstSet===============");
    Grammar.getFirst().entrySet().forEach(this::show);

  }

  public void show(Entry<String,Set<String>>e){
    if(Grammar.getNonTerminals().contains(e.getKey())){
    System.out.print(e.getKey()+": ");
    for (String v :e.getValue()) {
      System.out.print(v+", ");
    }
    System.out.println();
  }}
}
