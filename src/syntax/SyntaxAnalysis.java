package syntax;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lexical_analysis.DFA;
import lexical_analysis.Lexer;
import lexical_analysis.NFA;
import lexical_analysis.Token;
import lexical_analysis.TokenTable;
import syntax.Grammar;
import syntax.syntaxLR.LR0;

public class SyntaxAnalysis {

  //得到输入串
//String input="i * i + i";
  public static void main(String[] args) {
    //输入文件
    String path = "src/test/test1.txt";
  String grammarPath = "src/test/grammar.txt";
   // String grammarPath = "src/test/grammar.txt";

    //lex
    NFA nfa = new NFA();
    DFA dfa = new DFA();
    dfa.determine(nfa);
    TokenTable token_table = new TokenTable();
    Lexer lexer;
    try {
      lexer = new Lexer(path, token_table, dfa);
      lexer.run();
    } catch (IOException e) {
      e.printStackTrace();
    }

    //syntax
    Grammar preprocess = new Grammar();
    preprocess.inputGrammar(grammarPath);

    preprocess.generateFirstCollection();

   // LR0.createTable();

    //analysis
//List<String> testString= Arrays.asList("i", "*", "i","+","i");
    //#####!
   // List<String> testString= Arrays.asList("a", "c", "d","#");
//LR0.match(testString);
//    LR0.match(tokens2string(TokenTable.getTokens()));

  }

  public static List<String> tokens2string(List<Token> tokens) {
    List<String> strings = new ArrayList<>();
    for (Token token : tokens) {
      if (token.getLexeme().equals("#")) {
        strings.add("#");
      }
      String tokenType = token.getTokenType();
      switch (tokenType) {
        case "SE":
        case "KW":
        case "OP":
          strings.add(token.getLexeme());
          break;
        case "IDN":
          strings.add("Ident");
          break;
        case "INT":
          strings.add("INT");
          break;
      }
    }
    strings.add("#");
    return strings;
  }


}

