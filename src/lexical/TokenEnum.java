package lexical_analysis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TokenEnum {
	// 创建一个set存放所有的tags
				// tag不应该有歧义
				 public static final Set<String> tags = new HashSet<>(Arrays.asList(" ","+","-","*","/","%", "=", ">", "<", "[^=]", "[^>]","!","&","[|]",
				        "[(]", "[)]", "[{]","[}]",";",
				        "[_a-zA-Z]", "[_0-9a-zA-Z]", "[^_0-9a-zA-Z]",
				        "[1-9]", "[0-9]", "[.]", "[^.0-9]", "[^0-9]", //新增加的,
				        "[0]"
						));
				
				
				 public static final HashMap<String, Integer> TYPE_TO_CONTENT_DICT_KW = new HashMap<String, Integer>(){{
					put("int",1);
					put("void",2);
					put("return",3);
					put("const",4);
				    put("main",5);
				}};
				

				 public static final HashMap<String, Integer> TYPE_TO_CONTENT_DICT_OP = new HashMap<String, Integer>(){{
					put("+", 6);
					put("-", 7);
					put("*", 8);
					put("/", 9);
					put("%", 10);
					put("=", 11);
					put(">", 12);
					put("<", 13);
					put("==", 14);
					put("<=", 15);
					put(">=", 16);
					put("!=", 17);
					put("&&", 18);
					put("||", 19);			
				}};
				
				HashMap<String, Integer> TYPE_TO_CONTENT_DICT_SE = new HashMap<String, Integer>(){{
					put("(", 20);
					put(")", 21);
					put("{", 22);
					put("}", 23);
					put(";", 24);
					put(",", 25);		
				}};
}
