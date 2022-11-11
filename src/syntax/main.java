package syntax;

import static syntax.SyntaxAnalysis.tokens2string;

import java.io.IOException;
import lexical_analysis.DFA;
import lexical_analysis.Lexer;
import lexical_analysis.NFA;
import lexical_analysis.TokenTable;
import syntax.syntaxLL.LL1;

public class main {


  public static void main(String[] args){
    //syntax
    String grammarPath = "src/test/grammar.txt";
    Grammar preprocess = new Grammar();
    preprocess.inputGrammar(grammarPath);
    LL1 ll=new LL1();
    ll.initProductions();
    ll.extract();
    ll.isLL1();
    ll.createTable();
    String path = "src/test/test1.txt";
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
    ll.analysis(tokens2string(TokenTable.getTokens()));
    System.out.println("test1");

  }

}

