import java.util.Map;
import java.util.Map.Entry;
import org.junit.Test;
import syntax.syntaxLR.Action;
import syntax.Grammar;
import syntax.syntaxLR.Group;
import syntax.syntaxLR.LR0;

public class TestCreatTable {
  @Test
  public void test(){
    Grammar preprocess =new Grammar();
    // String path ="/Users/yezizhi/Desktop/compiler/src/syntax/grammarTestLR.txt";
    String path = "src/test/grammar.txt";
    // String path = "src/test/grammarTestLR.txt";
    preprocess.inputGrammar(path);
    LR0.createTable();
    LR0.getTable().entrySet().forEach(this::show);

  }


  public void show( Entry<Group, Map<String, Action>>e ){
    System.out.println("========="+e.getKey().getId()+"=========");
    for(Entry<String, Action>action:e.getValue().entrySet()){
      System.out.print(action.getKey()+": "+action.getValue().toString()+" ");
    }
    System.out.println();
  }
}
