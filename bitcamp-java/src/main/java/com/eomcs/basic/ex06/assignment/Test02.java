package com.eomcs.basic.ex06.assignment;

public class Test02 {
  // 현재 과제와 유사한 결과를 내는 기존 소스를 가져온다.

  public static void main(String[] args) {
    int width = Console.inputInt();
    int line = 0;
    while (line++ < width) {// 0부터 시작하면 <= 틀림 / <가 맞음
      Graphic.drawLine(line);
      System.out.println();
    }
    line--;
    while (--line > 0) {// 0부터 시작하면 <= 틀림 / <가 맞음
      Graphic.drawLine(line);
      System.out.println();
    }
  }

}
