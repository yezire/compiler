package lexical_analysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;
import java.util.regex.Pattern;

public class test {

	
	
	public static void main(String[] args) throws IOException {
		
		
		String path = "C:\\Users\\Administrator\\Desktop\\test.txt";
		String text = "";
		// 主函数

	   NFA nfa = new NFA();
	   DFA dfa = new DFA();
	   dfa.determine(nfa);
	   DFAtoMFA mfa = new DFAtoMFA(dfa);
	
		   //System.out.println(nfa.nodes.size());
		   //System.out.println(nfa.edges.size());
		   //System.out.println(nfa.edges.get(0).fromNodeId);
		   
		   //测试epsilon_closure
			//Node start_node = nfa.nodes.get(nfa.startId);
			//Set<Node> new_start_node_set =new HashSet<Node>();
			//new_start_node_set.add(start_node);
		    //  dfa.epsilon_closure(new_start_node_set, nfa);//s
		   
		   //测试determine
	
		  
		  //测试
		 // DFAtoMFA dfatomfa=new DFAtoMFA(dfa);
		//  mfa =dfatomfa.buildMFA（dfa）;


		//System.out.println(nfa.nodes.get(0));
		//System.out.println(start_node);
	   
	  // String ch="n";
	  // Pattern p = Pattern.compile( "[_0-9a-zA-Z]");//java.util.regex.Pattern;
		//Matcher matcher = p.matcher(ch);//java.util.regex.Pattern;
		//System.out.println(matcher.find());
	
}
	
}



