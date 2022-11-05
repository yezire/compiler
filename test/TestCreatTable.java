import java.util.Map;
import java.util.Map.Entry;
import org.junit.Test;
import syntax.Action;
import syntax.Grammar;
import syntax.Group;
import syntax.LR0;

public class TestCreatTable {
  @Test
  public void test(){
    Grammar preprocess =new Grammar();
    preprocess.inputGrammar();
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