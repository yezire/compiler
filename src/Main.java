
import syntax.Grammar;

public class Main {
  public static void main(String[] args)  {
Grammar preprocess =new Grammar();
preprocess.inputGrammar();
preprocess.generateFirstCollection();
preprocess.showMap(Grammar.getFirst());

  }

}
