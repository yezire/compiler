package lexical_analysis;

public class Node {
	    int id;
		int isFinal,isBackOff;//ʵ��Ϊboolean
	    String tag;
		
		public Node(int id,int is_final,int is_back_off,String tag){
		// id
		this.id = id;
		//�Ƿ����ս�ڵ� 1�����ǣ�0������
		this.isFinal = is_final;
		//�Ƿ���Ҫ���� 1������Ҫ��0������Ҫ
		this.isBackOff = is_back_off;
		//ֻ���ս�ڵ���Ҫtag
		this.tag = tag;
		}
		
	}
	

