package com.eomcs.io.ex07;

import java.io.ByteArrayInputStream;

public class ByteArrayDataInputStream extends ByteArrayInputStream {

  public ByteArrayDataInputStream(byte[] buf) {
    super(buf);
  }

  public String readUTF() throws Exception {
    // 상속 받은 read() 메서드를 사용하여 문자열 출력
    int size = this.read(); // 문자열의 바이트 개수를 알아내고
    byte[] bytes = new byte[size]; // 그 갯수만큼 바이트 배열을 만들어서
    this.read(bytes); // 이름 배열 개수 만큼 바이트를 읽어 배열에 저장한다.
    return new String(bytes, "UTF-8"); // 그 배열을 가지고 String 객체를 만들어 리턴
  }

  public int readInt() throws Exception {
    // 상속 받은 read() 메서드를 사용하여 int 값 출력
    int value = 0;

    value = this.read() << 24;
    value += this.read() << 16;
    value += this.read() << 8;
    value += this.read();
    return value;
  }

  public long readLong() throws Exception {
    // 상속 받은 read() 메서드를 사용하여 long 값 출력
    long value = 0;
    value += (long)this.read() << 56;
    value += (long)this.read() << 48;
    value += (long)this.read() << 40;
    value += (long)this.read() << 32;
    value += (long)this.read() << 24;
    value += (long)this.read() << 16;
    value += (long)this.read() << 8;
    value += (long)this.read();
    return value;
  }

  public boolean readBoolean() throws Exception {
    // 상속 받은 read() 메서드를 사용하여 boolean 값 출력
    if (this.read()==1) 
      return true;
    else 
      return false;
  }    

}
