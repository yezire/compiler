package syntax;

import java.util.List;

/**
 * 产生式
 * @author yezizhi
 * @date 2022/10/19
 */
public class Production {
private String left;
private List<String> right;
private int id;
private  static int idPool=0;
private boolean emptyRight;//compUnit -> $
  private boolean isSynch=false;



  public Production(String left,List<String>right){
  this.left = left;
  this.right = right;
  this.id = idPool++;
  emptyRight= right.get(0).equals("$");
}
  public List<String> getRight(){
    return right;
  }

  public String getLeft(){
    return left;
  }

  public  int getId(){return id;}


  public boolean isEmptyRight() {
    return emptyRight;
  }

public Production(String synch){
    //LL1文法中同步符号
    isSynch=true;
}

  public boolean isSynch() {
    return isSynch;
  }
}
