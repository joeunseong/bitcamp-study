package com.eomcs.basic.ex07.assignment;

public class Test02 {
  public static void main(String[] args) {
    //배열에 들어 있는 값을 오름차순으로 정렬을 수행
    int[] values = {34, 4, -3, 78, 12, 22, 45, 0, -22};
    sort(values);

    for (int value : values) {
      System.out.println(value);
    }
    //출력 결과
    // => -22, -3, 0, 4, 12, 22, 34, 45, 78
  }


// 5 4 3 2 1
// 1  2     5
}
