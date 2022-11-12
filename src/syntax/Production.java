package syntax;

import java.util.ArrayList;
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

  public static List<Production> getProductions() {
    return productions;
  }

  private static List<Production>productions= new ArrayList<>();



  public Production(String left,List<String>right){
  this.left = left;
  this.right = right;
  this.id = idPool++;
  emptyRight= right.get(0).equals("$");
  productions.add(this);
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
