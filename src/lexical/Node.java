package lexical_analysis;

public class Node {
	    int id;
		int isFinal,isBackOff;//实际为boolean
	    String tag;
		
		public Node(int id,int is_final,int is_back_off,String tag){
		// id
		this.id = id;
		//是否是终结节点 1代表是，0代表不是
		this.isFinal = is_final;
		//是否需要回退 1代表需要，0代表不需要
		this.isBackOff = is_back_off;
		//只有终结节点需要tag
		this.tag = tag;
		}
		
	}
	

