package lexical_analysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Lexer {
	String path;
	TokenTable token_table;
	DFA dfa;
	String source;
	public TokenTable tokenTable;
	public  Lexer(String path,TokenTable token_table,DFA dfa) throws IOException{
		this.source =  new String(Files.readAllBytes(Paths.get(path)));
		this.source+=" ";
		this.tokenTable = token_table;
		this.dfa = dfa;
	}
		// 执行词法分析
		public void run() {
		// 流程：
		// token_now = "";
		// 1.读取字符
		// 2.查找对应的状态转换
		// 2.1 如果找不到则说明错误的词法，报错
		// 2.2 如果找到了则状态转换到新的状态
		//2.2.1 非终结态则继续读取
		// 2.2.2 终结态判断is_back_off
		//2.2.2.1 true：从token_now退出一个ch,将生成的token_now加入tokentable
	    // 2.2.2.2 false: 将token_now加入token list中
		// 2.2.3 修改DFA的指针指向初始位置，token_now = ""
		// 初始化
		String text = this.source;
		//System.out.println(text);
		String token_now = "";
		this.dfa.get_start();
		int ID = 0;
		int i = 0;

		// System.out.println(this.dfa.next_id(Character.toString(ch)));
		//for (DFAEdge edge : this.dfa.edges) {
		//	System.out.println(edge.fromNodeId+","+edge.toNodeId+","+edge.tag);
		//	}
		// System.out.println(this.dfa.edges);

		while (i < text.length()) {
			// 需要跳过的情况
			char ch = text.charAt(i);
			if (token_now == "" && (ch == '\n' || ch == ' '||ch=='\r'||ch=='\t')){
			i += 1;
			continue;
	         }
			// GROUP BY和ORDER BY特殊处理
		    //if (token_now == "GROUP" || token_now == "ORDER") {
			//i += 1;
			//token_now += " ";
			//}
			token_now += ch;


			// 匹配成功到下一个节点
			//System.out.println(this.dfa.next_id(Character.toString(ch)));


		    if (this.dfa.next_id(Character.toString(ch))) {
			   ID = this.dfa.nowId;
			// 判断is_final
			   if (this.dfa.is_final(ID)==1) {
			// 判断is_back_off
			     if (this.dfa.is_back_off(ID)==1){
			  //指针回退一个
			     token_now = token_now.substring(0, token_now.length()-1);
			     i -= 1;
			     }

			     // 获得最终节点的tag
			     String node_tag = this.dfa.get_tag(ID);
			     // 这个判断应该是dfa提供的
			     //System.out.println(node_tag);
			     String token_type = this.dfa.get_token_type(token_now,node_tag);
			     String token_num = this.dfa.get_token_num(token_now,token_type);

			   //  System.out.println("node_tag:"+node_tag+"  token_now:"+token_now+"  token_type:"+token_type+"  token_num:"+token_num);

			     this.tokenTable.push_token(new Token(token_now, token_type,token_num));
			     token_now = "";
			     this.dfa.get_start();


			   }

			   //else if (this.dfa.is_final(ID)==0) {
			    i += 1;
			      // continue;
			       //}

		    }

			// 匹配失败，则抛出异常
			else {
			System.out.println("Lexical error: 不符合词法！");
			break;
			}

			// 如果最后一个词是属于IDNorKWorOP那么也要加入token_list中

		}//while结束



		if (this.dfa.is_final(ID)==0) {
			System.out.println("Lexical error: 最终一个词不是完整的token");
			}





	}

		}



