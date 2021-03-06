// 메소드 레퍼런스 - 생성자 레퍼런스
package com.eomcs.oop.ex12;

public class Exam0740 {

  static class Message {
    String name;

    public Message() {
      this.name = "이름없음";
    }

    public Message(String name) {
      this.name = name;
    }

    public void print() {
      System.out.printf("%s님 반갑습니다.\n", this.name);
    }
  }

  static interface Factory1 {
    Message get();
  }

  static interface Factory2 {
    Message get(String name);
  }

  static interface Factory3 {
    Message get(String name, int age);
  }

  public static void main(String[] args) {
    // 생성자 레퍼런스를 지정할 때,
    // 인터페이스 메소드의 파라미터에 따라 호출할 생성자가 결정되나.

    Factory1 f1 = Message::new; // Factory() 생성자를 가리킨다.
    Factory2 f2 = Message::new; // Factory(String) 생성자를 가리킨다.

    // Factory(String, int) 생성자가 없기 때문에 컴파일 오류!
    // Factory3 f3 = Message::new;

  }
}

