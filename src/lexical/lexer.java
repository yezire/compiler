package lexical_analysis;

public class lexer {
	def __init__(self, lexeme: str, token_type: str, token_num: str):
		self.lexeme = lexeme
		self.tokenType = token_type
		# 说是num，其实不是全是num，所以还是用str类型
		self.tokenNum = token_num

}
