package com.eomcs.oop.ex08.test;

public class B {
  // field
  static int a; // 클래스 필드 = 스태틱 필드 (클래스가 로딩될 때 생성)
  String b;     // 인스턴스 필드 = 논스태틱 필드

  // method
  static void m1() {}  // 클래스 메소드 = 스태틱 메소드
  int m2() {return 0;} // 인스턴스 메소드 = 논스태틱 메소드

  // initializer block : 초기화 블럭
  static {} // 스태틱 블록
  {}        // 인스턴스 블록
  
  // constructor : 생성자
  B() {}
  
  // nested class
  static class B1 {} // static nested class
  class B2 {}        // non-static nested class = inner class
}
