import org.junit.Test;
import syntax.Grammar;
import syntax.syntaxLR.Group;
import syntax.syntaxLR.Item;
import syntax.syntaxLR.LR0;

public class TestRemoveEpsilon {
@Test
  public  void test(){
  String grammarPath = "src/test/grammar.txt";
  Grammar preprocess = new Grammar();
  preprocess.inputGrammar(grammarPath);
LR0.createTable();
    Group group=Group.getGroupById(121);
  for(Item item : group.getItemSet()){
   Item newItem= Item.removeEpsilon(item);
   group.getItemSet().add(newItem);
  }

  }

}
