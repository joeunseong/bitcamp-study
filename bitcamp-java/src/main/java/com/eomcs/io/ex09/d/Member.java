package com.eomcs.io.ex09.d;

// 인스턴스의 값을 바이트 배열로 자동 변환(Serialize)할 수 있도록 허가한다.
// => java.io.Serializable 인터페이스를 구현한다.
// => Serializable 인터페이스는 아무런 메서드가 정의되어 있지 않다.
//    단지 Serialize를 활성화시키는 기능을 수행한다.
// => 이 인터페이스를 구현한 객체만 ObjectInputStream/ObjectOutputStream으로
//    serivalize/deserialize 할 수 있다.
public class Member implements java.io.Serializable {// Serializable 할 수 있다고 나타내기 위해 씀
  
  // java.io.Serializable 인터페이스를 구현하는 클래스는
  // 항상 serialVersionUID 라는 스티틱 상수를 가져야 한다.
  // 이 변수는 데이터를 저장하고 읽을 때 
  // 데이터 형식에 대한 구분자로 사용된다.
  // 이 값을 가지고 데이터를 읽을 수 있는지 없는지 판단한다.
  //
  // 다음과 같이 개발자가 버전 번호를 명시할 수 있다.
  // 필드를 추가하거나 변경하더라도 버전 번호가 같다면
  // JVM은 같은 형식으로 판단한다.
  //
  private static final long serialVersionUID = 1280L;
  //serialVersionUID의 사용법
  // => 클래스를 변경하더라도 기존 데이터를 읽는데 문제가 없다면 
  //    변경한 클래스의 버전을 이전 버전과 같게 하라!
  // => 그러나 클래스의 변경 사항이 너무 많아 이전 데이터를 읽기가 부적합하다면 
  //    변경한 클래스의 버전을 바꿔 읽을 때 오류가 나게 하라!
  // => Member의 경우 클래스를 변경할 때 tel 변수를 추가하였다.
  //    따라서 이전 데이터를 읽는데는 문제가 없다.
  //    그래서 버전 번호를 바꾸지 않은 것이다. 
  String name;
  
  // Exam0420의 세 번째 테스트를 실행할 때 주석을 풀라!
  int age;
  
  boolean gender; // true(여자), false(남자)

  // Exam0420의 두 번째 테스트를 실행할 때 주석을 풀라!
  //String tel;
  
  @Override
  public String toString() {
    return "Member [name=" + name +
        // Exam0420의 세 번째 테스트를 실행할 때 주석을 풀라!
         ", age=" + age +
        
        ", gender=" + gender +
        // Exam0420의 두 번째 테스트를 실행할 때 주석을 풀라!
        //", tel=" + tel +
        "]";

  }
}
