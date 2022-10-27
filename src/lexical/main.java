package lexical_analysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

public class main {

	
	
	public static void main(String[] args) throws IOException {
		// 读取的sql文件路径
		String path = "C:\\Users\\Administrator\\Desktop\\test.txt";
		String text = "";
		// 主函数
	   NFA nfa = new NFA();
	   DFA dfa = new DFA();
	    dfa.determine(nfa);
	    DFAtoMFA mfa =new DFAtoMFA(dfa);
	   // for i in mfa.nodes:
	   //    print('id: ' + str(i.id) + ' isFinal: ' + str(i.isFinal) + ' isBackOff: ' + str(i.isBackOff) + ' tag: ' + str(i.tag))
	     //for i in mfa.edges:
	    //     print('fromId:'+str(i.fromNodeId)+' tag:'+str(i.tag)+' toNodeId: '+str(i.toNodeIds))
	    TokenTable token_table = new TokenTable();
	    Lexer lexer = new Lexer(path, token_table, dfa);
	    lexer.run();
	    lexer.tokenTable.print_token_table();
	    lexer.tokenTable.save_token_table("C:\\Users\\Administrator\\Desktop\\");
}

	


	// 读取sql文件返回字符串
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
		


	
}
