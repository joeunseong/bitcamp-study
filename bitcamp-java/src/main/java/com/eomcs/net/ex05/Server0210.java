// connectionless 클라이언트 - 연결없이 데이터 수신 - 신뢰성 보장 X => 개발자가 코딩해야함
package com.eomcs.net.ex05;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

// 비연결(connectionless)
// => 연결없이 데이터를 송수신한다.
// => 상대편이 네트워크에 연결되어 있지 않다면, 그 데이터는 버려진다.
// => 그래서 전송 여부를 신뢰할 수 없다.
// => 실생활에서 "편지"와 같다.
// => 예) ping
//
public class Server0210 {
  public static void main(String[] args) throws Exception {
    Scanner keyScan = new Scanner(System.in);
    System.out.println("서버 실행 중...");

    // 특정 포트로 들어온 데이터를 받을 서버 소켓 준비
    DatagramSocket socket = new DatagramSocket(8888);

    // 받은 데이터를 저장할 버퍼 준비
    byte[] buf = new byte[8196];

    // 빈 패킷 준비
    DatagramPacket emptyPacket = new DatagramPacket(buf, buf.length);

    System.out.print("데이터를 받기 전에 잠깐 멈춤>");
    keyScan.nextLine();

    // 빈 패킷을 사용하여 들어온 데이터를 받는다.
    // => 데이터를 받을 때까지 리턴하지 않는다.
    socket.receive(emptyPacket);
    System.out.println("데이터를 받았음!");

    socket.close();
    keyScan.close();

    // 빈 패킷에 저장된 수신 데이터를 꺼낸다.
    // 패킷에 저장된 UTF-8로 인코딩된 바이트 배열을 가지고 String 객체(UTF-16)를 만든다.
    String message = new String(emptyPacket.getData(), 0, emptyPacket.getLength(), "UTF-8");
    // emptyPacket.getLength() - 실제 저장된 개수
    System.out.println(message);

  }
}


