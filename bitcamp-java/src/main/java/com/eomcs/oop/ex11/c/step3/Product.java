package com.eomcs.oop.ex11.c.step3;

public class Product {
  // 카테고리 값을 잘못 입력한 경우를 방지하기 위해
  // 문자열을 상수로 정의한다.
  public static final String apppliance_tv = "appliancee/tv";
  public static final String computer_ram = "computer/ram";
  public static final String book_novel = "book/novel";

  String category;
  String name;
  String maker;
  int price;
}
