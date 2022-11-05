package lexical_analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//输出符号表，写入到文件里，部署时这里需要更改path
public class TokenTable {
		ArrayList<Token> tokens =new ArrayList<Token>() ;

		public void print_token_table() {
		for (Token token : this.tokens) {
		System.out.println(token.lexeme+" <"+token.tokenType+","+token.tokenNum+">\n");
		}
		}

		public void push_token(Token token) {
		this.tokens.add(token);
		}


		public void save_token_table(String path,String filename) throws IOException {
		File file=new File(path+filename);
		FileWriter fw = new FileWriter(file,true);    //创建FileWriter类对象
        BufferedWriter bufw=new BufferedWriter(fw);   //创建BufferedWriter对象

        for(Token token : this.tokens) {           //循环遍历数组

				bufw.write(token.lexeme+" <"+token.tokenType+","+token.tokenNum+">\n");

			                   //将字符串数组中的元素写入到文件中
            //bufw.newLine();                           //换行
        }
        bufw.close();                                 //关闭BufferedWriter流
        fw.close();


		}
}

