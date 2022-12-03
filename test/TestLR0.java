import static syntax.SyntaxAnalysis.tokens2string;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lexical_analysis.DFA;
import lexical_analysis.Lexer;
import lexical_analysis.NFA;
import lexical_analysis.Token;
import lexical_analysis.TokenTable;
import org.junit.Test;
import syntax.Grammar;
import syntax.syntaxLL.LL1;
import syntax.syntaxLR.LR0;

public class TestLR0 {
  @Test
  public  void test(){
    String path = "src/test/testNew.txt";
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
    //String grammarPath = "src/test/grammarTestSLR.txt";
   // String grammarPath = "src/test/grammarTestLR.txt";
    String grammarPath = "src/test/grammar.txt";
    Grammar preprocess = new Grammar();
    preprocess.inputGrammar(grammarPath);
    LR0.createTable();
    // analysis
    LR0.match(tokens2string(TokenTable.getTokens()));
    System.out.println("testLR"+path);

  }

  public static List<String> tokens2string(List<Token> tokens) {
    List<String> strings = new ArrayList<>();
    for (Token token : tokens) {
      if (token.getLexeme().equals("#")) {
        strings.add("#");
      }
      if (token.getLexeme().equals("main")) {
        strings.add("func");
        continue;
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
