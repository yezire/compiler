package syntax.syntaxLR;

import syntax.Production;

/**
 * SLR1对应的动作
 * @author yezizhi
 * @date 2022/10/21
 */
public class Action {
  // 移入
  public static final String TYPE_S = "s";
  // 归约
  public static final String TYPE_R = "r";
  // 接收
  public static final String TYPE_ACC = "acc";
  public static final String TYPE_GOTO = "goto";
public static  final  String TYPE_ERROR = "error";


  private final String type;

  // 如果action是移入的时候，stateGroup代表移入状态，通过stateGroup的state表示
  private final Group group;

  // 当action是归约的时候，production就是归约的产生式
  private final Production production;

  private Action(String type, Group stateGroupSet, Production production) {
    this.type = type;
    this.group = stateGroupSet;
    this.production = production;
  }


  public static Action createAcc() {
    return new Action(TYPE_ACC, null, null);
  }

  public static Action createS(Group group) {
    return new Action(TYPE_S, group, null);
  }

  public static Action createR(Production production) {
    return new Action(TYPE_R, null, production);
  }
  public static  Action createGoto(Group group){
    return new Action(TYPE_GOTO, group, null);
  }

  public static  Action createError(){
    return new Action(TYPE_ERROR, null, null);
  }


  public boolean isAcc() {
    return TYPE_ACC.equals(type);
  }

  public boolean isS() {
    return TYPE_S.equals(type);
  }

  public boolean isR() {
    return TYPE_R.equals(type);
  }


  public boolean isGoto() {
    return TYPE_GOTO.equals(type);
  }
  public boolean isError() {
    return TYPE_ERROR.equals(type);
  }


  public String getType() {
    return type;
  }

  public Group getGroup() {
    return group;
  }

  public Production getProduction() {
    return production;
  }

  @Override
  public String toString() {
    if (isAcc()) {
      return "acc";
    }
    if (isR()) {
      return "r" + production.getId();
    }
    if (isS()) {
      return "s"+ group.getId();
    }
    if(isGoto()){
      return  "g"+group.getId();
    }
    if(isError()){
      return  "E!";
    }
    return "";
  }
}
