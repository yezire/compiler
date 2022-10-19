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

public Production(String left,List<String>right){
  this.left = left;
  this.right = right;
}
  public List<String> getRights(){
    return right;
  }

  public String getLeft(){
    return left;
  }

}
