package com.eomcs.basic.ex03;

public class Exam24 {  //공개 클래스인 경우 클래스명과 파일명은 꼭 같아야 함
  public static void main(final String[] args) {
    //(23) 0000 0000 0000 0000 0000 0000 0001  0111
    
    System.out.println(23);

    //-23
    // 1) Sign-magnitude :  맨 앞에를 1로 바꿔주면 음수
      //    => 1000 0000 0000 0000 0000 0000 0001  0111 (-23)
      //23 +(-23) = 0
      //  ->     //=> 0000 0000 0000 0000 0000 0000 0001 0111 (23)
                 //=> 1000 0000 0000 0000 0000 0000 0001 1000 (-23)
                 //-------------------------------------------------------
                 //   1000 0000 0000 0000 0000 0000 0010 1110 = -46   ->옳은 결과 X

    // 2) 1's complement(1의 보수)
    // 모든 비트를 1이 되는 수로 바꾼다. 즉 0을 1로, 1을 0으로 바꾼다
    // => 1111 1111 1111 1111 1111 1111 1110  1000 (-23)
     //23 +(-23) = 0
      //  ->     //=> 0000 0000 0000 0000 0000 0000 0001 0111 (23)
                 //=> 1111 1111 1111 1111 1111 1111 1110 1000(-23)
                 //   +                                     1
                 //-------------------------------------------------------
                 //   1000 0000 0000 0000 0000 0000 0000 0000 = 0

    // 3) 2's complement(2의 보수)
    // -> 1의 보수로 저장된 음수 값을 더할 때 마다
    //    계산 결과에 1을 추가하는 번거로움을 없애기 위해
    //    음수를 저장할 때 미리 1을 추가해 두는 방법
    //23 +(-23) = 0
    //=> 0000 0000 0000 0000 0000 0001 0111 (23)
    //=> 1111 1111 1111 1111 1111 1110 1001 (-23)
     //-------------------------------------------------------
    //10000 0000 0000 0000 0000 0000 0000 0000 = 0
                 //그래서 컴퓨터에서 음수를 메모리에 저장할 때는 
                 //양수와 음수를 더할 때 정상적인 값이 나오도록
                 //2의 보수 방법으로 음수를 저장한다.

    // 4) 2의 보수를 만드는 방법 2:
    //  - 오른쪽에서부터 1을 찾는다.
    //  - 찾은 1의 왼쪽편에 있는 모든 비트를 반대 값으로 바꾼다.
    //    예) 0010 1001(41) => 1101 0111(-41)
    //                ^                ^
    //    예) 0010 1100(44) => 1101 0100(-44)
    //              ^                ^
    // 5) - 2의 보수를 만드는 방법 3:
    //  - 2^n(8비트일 경우 2^8 = 256)에서 음수 값 만큼 뺀다.
    //    예) -41 => 256 - 41 = 215 = 1101 0111
    //    예) -44 => 256 - 44 = 212 = 1101 0100

    System.out.println(-23);




  }
}