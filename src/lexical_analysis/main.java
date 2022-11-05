package lexical_analysis;

import java.io.IOException;

public class main {



	public static void main(String[] args) throws IOException {
		// 读取的txt文件路径
		String path = "src/test/test2.txt";
		String text = "";
		// 主函数

	   NFA nfa = new NFA();
	   DFA dfa = new DFA();
	   MFA mfa= new MFA();
	   dfa.determine(nfa);
	   DFAtoMFA to = new DFAtoMFA(dfa);
	   mfa=to.buildMFA(dfa);
		 
	    TokenTable token_table = new TokenTable();
	    Lexer lexer = new Lexer(path, token_table, dfa);
	    lexer.run();
	    lexer.tokenTable.print_token_table();
	    lexer.tokenTable.save_token_table("src/result","/test2.txt");
}




}
