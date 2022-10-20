
import syntax.Generate;

public class Main {
  public static void main(String[] args)  {
Generate preprocess =new Generate();
preprocess.inputGrammar();
preprocess.generateFirstCollection();
preprocess.showMap(Generate.first);
  }

}
