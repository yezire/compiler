package lexical_analysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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



	  // for (Node node : dfa.nodes) {

		//	System.out.println(node.id+","+node.isFinal+","+node.isBackOff+","+node.tag);
		//	}
	 //  for (DFAEdge edge : dfa.edges) {
		//	System.out.println(edge.fromNodeId+","+edge.toNodeId+","+edge.tag);
		//	}

	    TokenTable token_table = new TokenTable();
	    Lexer lexer = new Lexer(path, token_table, dfa);
	    lexer.run();
	    lexer.tokenTable.print_token_table();
	    lexer.tokenTable.save_token_table("src/result","/test2.txt");
}



/*
	// 读取txt文件返回字符串
    public String read_file(String path) throws IOException {
    	  StringBuilder sb = new StringBuilder();
    	  String s ="";
    	  BufferedReader br = new BufferedReader(new FileReader(path));
    	  while( (s = br.readLine()) != null) {
    	  sb.append(s + "\n");
    	  }
    	  br.close();
    	  String str = sb.toString();

    	  return str;
    	 }
    	 */




}
