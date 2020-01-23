package com.eomcs.lms.handler;

import java.sql.Date;
import java.util.List;
import com.eomcs.lms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberUpdateCommand implements Command {
  public Prompt prompt;

  List<Member> memberList;

  public MemberUpdateCommand(Prompt prompt, List<Member> list) {
    this.prompt = prompt;
    memberList = list;
  }

  @Override
  public void excute() {
    int index = indexOfMember(prompt.inputInt("번호? "));
    if (index == -1) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    Member oldMember = this.memberList.get(index);

    boolean changed = false;
    Member newMember = new Member();
    String inputStr = null;
    newMember.setNo(oldMember.getNo());


    newMember.setName(
        prompt.inputString(String.format("이름(%s)? ", oldMember.getName()), oldMember.getName()));

    newMember.setEmail(
        prompt.inputString(String.format("이메일(%s)? ", oldMember.getEmail()), oldMember.getEmail()));

    newMember.setPassword(prompt.inputString(String.format("암호(%s)? ", oldMember.getPassword()),
        oldMember.getPassword()));

    newMember.setPhoto(
        prompt.inputString(String.format("사진(%s)? ", oldMember.getPhoto()), oldMember.getPhoto()));


    newMember.setTel(
        prompt.inputString(String.format("전화(%s)? ", oldMember.getTel()), oldMember.getTel()));

    newMember.setRegisteredDate(new Date(System.currentTimeMillis()));

    if (oldMember.equals(newMember)) {
      System.out.println("멤버 변경을 취소하였습니다.");
      return;
    }
    this.memberList.set(index, newMember);
    System.out.println("멤버 정보를 변경하였습니다.");

  }

  private int indexOfMember(int no) {
    for (int i = 0; i < this.memberList.size(); i++) {
      if (this.memberList.get(i).getNo() == no) {
        return i;
      }
    }
    return -1;
  }

}