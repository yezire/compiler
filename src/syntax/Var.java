package syntax;

import java.util.Objects;

//符号类型，符号string,符号多次推导
public class Var {

public VarType type;
public String string;
public boolean isGreedy;//true 匹配该符号1-无穷次

  public Var(String string,boolean isGreedy,VarType type){
    this.string = string;
    this.isGreedy = isGreedy;
    this.type = type;
  }

  public Var(String string,boolean isGreedy){
    this.string = string;
    this.isGreedy = isGreedy;
  }

  public void setVarType(VarType type){
    this.type = type;
  }

  @Override
  public String toString() {
    return string;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Var var = (Var) o;
    return  Objects.equals(string,
        var.string);
  }

  @Override
  public int hashCode() {
    return Objects.hash(string);
  }
}
