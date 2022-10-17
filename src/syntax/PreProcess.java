package syntax;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*  preprocess
     * 输入文法，将文法中"|"展开，存放在map中，从1开始，key:产生式左边，value:list 产生式右
     * 在0新增一条，形成拓广文法
     * 标记终结符，非终结符

     * 求first集:非终结符，终结符就是它本身
     * first(A)={a| A ==>+ B}
     * - if (A ==>+ ε) then add ε to first(A)
     * - 有产生式A ==> αβ if (α ∈ VT) then add α to first(A)
     * - 有产生式A ==> Bβ add first(B) to first(A)

 */

public class PreProcess {
  //k:产生式序号 v:产生式，index=0存放 产生式左边
  private static Map<Integer, List<String>> productions = new HashMap<>();
  private static Set<String> terminalSet = new HashSet<>();
  private static Set<String> nonTerminalSet = new HashSet<>();
  private static Map<String,Set<String>>  first = new HashMap<>();
  private static Map<String,Set<String>>  follow = new HashMap<>();

  public void expand(){
    //展开文法，可以用程序读入也可以自己手动添加
    //拓广文法 存入productions，terminalSet，nonTerminalSet
  }

  public void generateFirstCollection(){

  }

  public void generateFollowCollection(){

  }

  public Map<Integer, List<String>> getProductions(){return productions;}
  public Set<String> getTerminals(){return terminalSet;}
  public Set<String>getNonTerminals(){return nonTerminalSet;}
  public Map<String,Set<String>> getFirst(){return  first;}
  public Map<String,Set<String>> getFollow(){return  follow;}


}
