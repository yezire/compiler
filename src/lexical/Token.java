package lexical_analysis;

public class Token {
    String lexeme;
    String tokenType;
    String tokenNum;
    public Token(String lexeme,String token_type,String token_num) {
		this.lexeme = lexeme;
		this.tokenType = token_type;
		//说是num，其实不是全是num，所以还是用str类型
		this.tokenNum = token_num;
    }
}
