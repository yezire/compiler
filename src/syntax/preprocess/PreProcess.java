package syntax.preprocess;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import syntax.Var;
import syntax.VarType;

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
  private static Map<Integer, List<Var>> productions = new HashMap<>();
  private static Set<Var> terminalSet = new HashSet<>();
  private static Set<Var> nonTerminalSet = new HashSet<>();
  private static Map<String,Set<Var>>  first = new HashMap<>();
  private static Map<String,Set<Var>>  follow = new HashMap<>();

  public void init(){
    String path="/Users/yezizhi/Desktop/compiler/src/syntax/preprocess/grammar1.txt";
    int index=0;
    try {
      List<Var>res=new ArrayList<>();
      String ori=readFromTxt(path);
      for(String string:ori.split("\n")){
        Var left= new Var(string.split(" -> ")[0],false, VarType.nonTer);
        res.add(left);
        String preRight=string.split(" -> ")[1];
        for(String s:preRight.split(" ")){
          if(s.endsWith("+")){
            Var v =new Var(s,true);
            res.add(v);
          }else{
            Var v =new Var(s,false);
            res.add(v);
          }
        }
        nonTerminalSet.add(left);
        productions.put(index++,res);
        terminalSet.addAll(res);
      }
      terminalSet.removeIf(v -> nonTerminalSet.contains(v));
      //terminalSet.removeIf(v -> v.endsWith("*")||v.equals("+"));
   //showMap(productions);
      System.out.println("================Terminal===============");
      for(Var s:terminalSet){
        System.out.println(s.toString());
      }
      System.out.println("================Non-Terminal===============");
      for(Var s:nonTerminalSet){
        System.out.println(s.toString());
      }



    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void generateFirstCollection(){

  }

  public void generateFollowCollection(){

  }

//  public Map<Integer, List<String>> getProductions(){return productions;}
//  public Set<String> getTerminals(){return terminalSet;}
//  public Set<String>getNonTerminals(){return nonTerminalSet;}
//  public Map<String,Set<String>> getFirst(){return  first;}
//  public Map<String,Set<String>> getFollow(){return  follow;}

  private String readFromTxt(String filename) throws Exception {
    Reader reader = null;
    try {
      StringBuffer buf = new StringBuffer();
      char[] chars = new char[1024];
      // InputStream in=new FileInputStream(filename);

      reader = new InputStreamReader(new FileInputStream(filename), "UTF-8");
      int readed = reader.read(chars);
      while (readed != -1) {
        buf.append(chars, 0, readed);
        readed = reader.read(chars);
      }
      return buf.toString();
    } finally {
      close(reader);
    }
  }

  /**
   * 关闭输入输入流
   *
   * @param inout
   */
  private void close(Closeable inout) {
    if (inout != null) {
      try {
        inout.close();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  private void showMap( Map<Integer, List<Var>> map){
    for (Map.Entry<Integer, List<Var>> entry : map.entrySet()){
      System.out.print(entry.getKey()+"\t:");
      for (Var v: entry.getValue()){
        System.out.print(v.toString()+" ");
      }
      System.out.println();
    }

  }
}
