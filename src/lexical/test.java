package lexical_analysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

public class test {

	
	
	public static void main(String[] args) throws IOException {
		
	 	    NFA nfa=new NFA();
	 	   nfa.add_node(0, 0, 0, "");
		   nfa.add_node(1, 1, 0, "OP");
		   Node start_node = nfa.nodes.get(0);
		System.out.println(nfa.nodes.get(0));
		System.out.println(start_node);
			
		
	}
}

