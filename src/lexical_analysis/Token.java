package lexical_analysis;

public class Token {
   public String lexeme;
  public String tokenType;
 public String tokenNum;
    public Token(String lexeme,String token_type,String token_num) {
		this.lexeme = lexeme;
		this.tokenType = token_type;
		//说是num，其实不是全是num，所以还是用str类型
		this.tokenNum = token_num;
    }
    public Token(String lexeme){
      this.lexeme = lexeme;
    }


  public String getLexeme() {
    return lexeme;
  }

  public String getTokenType() {
    return tokenType;
  }

  public String getTokenNum() {
    return tokenNum;
  }

}
