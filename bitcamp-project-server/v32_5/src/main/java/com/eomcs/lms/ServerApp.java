package com.eomcs.lms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.eomcs.lms.context.ApplicationContextListener;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.Member;

public class ServerApp {

  // 옵저버 관련 코드
  Set<ApplicationContextListener> listeners = new HashSet<>();
  Map<String, Object> context = new HashMap<>();
  List<Board> boards;
  List<Lesson> lessons;
  List<Member> members;

  public void addApplicationContextListener(ApplicationContextListener listener) {
    listeners.add(listener);
  }

  public void removeApplicationContextListener(ApplicationContextListener listener) {
    listeners.remove(listener);
  }

  private void notifyApplicationInitialized() {
    for (ApplicationContextListener listener : listeners) {
      listener.contextInitialized(context);
    }
  }

  private void notifyApplicationDestroyed() {
    for (ApplicationContextListener listener : listeners) {
      listener.contextDestroyed(context);
    }
  }

  // 옵저버 관련 코드 끝

  @SuppressWarnings("unchecked")
  public void service() {

    notifyApplicationInitialized();

    // DataLoaderListener가 준비한 데이터를 꺼내 인스턴스 필드에 저장한다.
    boards = (List<Board>) context.get("boardList");
    lessons = (List<Lesson>) context.get("lessonList");
    members = (List<Member>) context.get("memberList");

    try (
        // 서버쪽 연결 준비
        // => 클라이언트의 연결을 9999번 포트에서 기다린다.
        ServerSocket serverSocket = new ServerSocket(9999)) {
      System.out.println("클라이언트 연결 대기중...");

      while (true) {
        // 서버에 대기하고 있는 클라이언트와 연결
        // => 대기하고 있는 클라이언트와 연결될 때까지 리턴하지 않는다.
        Socket socket = serverSocket.accept(); // 승인은 접속 순서
        System.out.println("클라이언트와 연결되었음!");

        // 클라이언트에 요청 처리
        if (processRequest(socket) == 9) {
          break;
        }
        System.out.println("-------------------------------------");
      }

    } catch (Exception e) {
      System.out.println("서버 준비 중 오류 발생");
    }

    notifyApplicationDestroyed();

  } // service()

  int processRequest(Socket clientSocket) {
    try (Socket socket = clientSocket;

        // 클라이언트의 메시지를 수신하고 클라이언트로 전송할 입출력 도구 준비
        // 객체라서 Scanner가 아니라 ObjectInputStream을 써야함
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

      System.out.println("통신을 위한 입출력 스트림을 준비하였음!");

      while (true) {
        // 클라이언트가 보낸 메시지를 수신한다.
        // => 한 줄의 메시지를 읽을 때까지 리턴하지 않는다.
        String request = in.readUTF();
        System.out.println("클라이언트가 보낸 메시지를 수신하였음");

        switch(request) {
          case "quit": quit(out); return 0; // 클라이언트와 연결을 끊는다.
          case "/server/stop": quit(out); return 9; // 서버를 종료한다.
          
          case "/board/list": listBoard(out); break;
          case "/board/add": addBoard(in, out); break;
          case "/board/detail": detailBoard(in, out); break;
          case "/board/update": updateBoard(in, out); break;
          case "/board/delete": deleteBoard(in, out); break;

          case "/lesson/list": listLesson(out); break;
          case "/lesson/add": addLesson(in, out); break;
          case "/lesson/detail": detailLesson(in, out); break;
          case "/lesson/update": updateLesson(in, out); break;
          case "/lesson/delete": deleteLesson(in, out); break;

          case "/member/list": listMember(out); break;
          case "/member/add": addMember(in, out); break;
          case "/member/detail": detailMember(in, out); break;
          case "/member/update": updateMember(in, out); break;
          case "/member/delete": deleteMember(in, out); break;
          default:  notFound(out);
        }
        out.flush();
        System.out.println("클라이언트에게 응답하였음!");
      }
    } catch (Exception e) {
      System.out.println("예외발생:");
      e.printStackTrace();
      return -1;
    }
  }

  private void notFound(ObjectOutputStream out) throws IOException {
    out.writeUTF("FAIL");
    out.writeUTF("요청한 명령을 처리할 수 없습니다.");
  }

  private void quit(ObjectOutputStream out) throws IOException {
    out.writeUTF("OK");
    out.flush();
  }

  private void deleteMember(ObjectInputStream in, ObjectOutputStream out) throws IOException {
    try {
      int no = in.readInt();
      int index = -1;
      for (int i = 0; i < members.size(); i++) {
        if (members.get(i).getNo() == no) {
          index = i;
        }
      }

      if (index != -1) {
        members.remove(index);
        out.writeUTF("OK");
      } else {
        out.writeUTF("FAIL");
        out.writeUTF("해당 번호의 게시물이 없습니다.");
      }
    } catch (Exception e) {
      out.writeUTF(e.getMessage());
    }
  }

  private void updateMember(ObjectInputStream in, ObjectOutputStream out) throws IOException {
    try {
      Member member = (Member) in.readObject();
      int index = -1;
      for (int i = 0; i < members.size(); i++) {
        if (members.get(i).getNo() == member.getNo()) {
          index = i;
        }
      }

      if (index != -1) {
        members.set(index, member);
        out.writeUTF("OK");
      } else {
        out.writeUTF("FAIL");
        out.writeUTF("같은 번호의 게시물이 없습니다.");
      }
    } catch (Exception e) {
      out.writeUTF("FAIL");
      out.writeUTF(e.getMessage());
    }
  }

  private void detailMember(ObjectInputStream in, ObjectOutputStream out) throws IOException {
    try {
      int no = in.readInt();
      Member member = null;
      for (Member m : members) {
        if (m.getNo() == no) {
          member = m;
        }
      }

      if (member != null) {
        out.writeUTF("OK");
        out.writeObject(member);
      } else {
        out.writeUTF("FAIL");
        out.writeUTF("해당 번호의 회원 정보가 없습니다.");
      }
    } catch (Exception e) {
      out.writeUTF("FAIL");
      out.writeUTF(e.getMessage());
    }
  }

  private void addMember(ObjectInputStream in, ObjectOutputStream out) throws IOException {
    try {
      Member member = (Member) in.readObject();
      int i = 0;
      for (; i < members.size(); i++) {
        if (members.get(i).getNo() == member.getNo()) {
          break;
        }
      }

      if (i == members.size()) {
        members.add(member);
        out.writeUTF("OK");
      } else {
        out.writeUTF("FAIL");
        out.writeUTF("같은 번호의 회원 정보가 있습니다.");
      }
    } catch (Exception e) {
      out.writeUTF("FAIL");
      out.writeUTF(e.getMessage());

    }
  }

  private void listMember(ObjectOutputStream out) throws IOException {
    out.writeUTF("OK");
    out.reset();
    out.writeObject(members);
  }

  private void deleteLesson(ObjectInputStream in, ObjectOutputStream out) throws IOException {
    try {
      int no = in.readInt();

      int index = -1;
      for (int i = 0; i < lessons.size(); i++) {
        if (lessons.get(i).getNo() == no) {
          index = i;
        }
      }

      if (index != -1) {
        lessons.remove(index);
        out.writeUTF("OK");
      } else {
        out.writeUTF("FAIL");
        out.writeUTF("같은 번호의 게시물이 없습니다.");
      }
    } catch (Exception e) {
      out.writeUTF("FAIL");
      out.writeUTF(e.getMessage());
    }
  }

  private void updateLesson(ObjectInputStream in, ObjectOutputStream out) throws IOException {
    try {
      Lesson lesson = (Lesson) in.readObject();

      int index = -1;
      for (int i = 0; i < lessons.size(); i++) {
        if (lessons.get(i).getNo() == lesson.getNo()) {
          index = i;
        }
      }

      if (index != -1) {
        lessons.set(index, lesson);
        out.writeUTF("OK");
      } else {
        out.writeUTF("FAIL");
        out.writeUTF("같은 번호의 수업 정보가 있습니다.");
      }
    } catch (Exception e) {
      out.writeUTF("FAIL");
      out.writeUTF(e.getMessage());
    }
  }

  private void detailLesson(ObjectInputStream in, ObjectOutputStream out) throws IOException {
    try {
      int no = in.readInt();
      Lesson lesson = null;
      for (Lesson l : lessons) {
        if (l.getNo() == no) {
          lesson = l;
        }
      }

      if (lesson != null) {
        out.writeUTF("OK");
        out.writeObject(lesson);
      } else {
        out.writeUTF("FAIL");
        out.writeUTF("해당 번호의 수업 정보가 없습니다.");
      }
    } catch (Exception e) {
      out.writeUTF("FAIL");
      out.writeUTF(e.getMessage());
    }
  }

  private void addLesson(ObjectInputStream in, ObjectOutputStream out) throws IOException {
    try {
      Lesson lesson = (Lesson) in.readObject();

      int i = 0;
      for (; i < lessons.size(); i++) {
        if (lessons.get(i).getNo() == lesson.getNo()) {
          break;
        }
      }

      if (i == lessons.size()) {
        lessons.add(lesson);
        out.writeUTF("OK");
      } else {
        out.writeUTF("FAIL");
        out.writeUTF("같은 번호의 게시물이 있습니다.");
      }
    } catch (Exception e) {
      out.writeUTF("FAIL");
      out.writeUTF(e.getMessage());
    }
  }

  private void listLesson(ObjectOutputStream out) throws IOException {
    out.writeUTF("OK");
    out.reset();
    out.writeObject(lessons);
  }

  private void deleteBoard(ObjectInputStream in, ObjectOutputStream out) throws IOException {
    try {
      int no = in.readInt();

      int index = -1;
      for (int i = 0; i < boards.size(); i++) {
        if (boards.get(i).getNo() == no) {
          index = i;
          break;
        }
      }

      if (index != -1) { // 삭제하려는 번호의 게시물을 찾았다면
        boards.remove(index);
        out.writeUTF("OK");

      } else {
        out.writeUTF("FAIL");
        out.writeUTF("해당 번호의 게시물이 없습니다.");
      }
    } catch (Exception e) {
      out.writeUTF("FAIL");
      out.writeUTF(e.getMessage());
    }
  }

  private void updateBoard(ObjectInputStream in, ObjectOutputStream out) throws IOException {
    try {
      Board board = (Board) in.readObject();
      int index = -1;

      for (int i = 0; i < boards.size(); i++) {
        if (boards.get(i).getNo() == board.getNo()) {
          index = i;
          break;
        }
      }

      if (index != -1) {
        boards.set(index, board);
        out.writeUTF("OK");
      } else {
        out.writeUTF("FAIL");
        out.writeUTF("해당 번호의 게시물이 없습니다.");
      }

    } catch (Exception e) {
      out.writeUTF("FAIL");
      out.writeUTF(e.getMessage());
    }
  }

  private void detailBoard(ObjectInputStream in, ObjectOutputStream out) throws IOException {
    try {
      int no = in.readInt();
      Board board = null;
      for (Board b : boards) {
        if (b.getNo() == no) {
          board = b;
          break;
        }
      }

      if (board != null) {
        out.writeUTF("OK");
        out.writeObject(board);

      } else {
        out.writeUTF("FAIL");
        out.writeUTF("해당 번호의 게시물이 없습니다.");
      }
    } catch (Exception e) {
      out.writeUTF("FAIL");
      out.writeUTF(e.getMessage());
    }
  }

  private void addBoard(ObjectInputStream in, ObjectOutputStream out) throws IOException {
    try {
      Board board = (Board) in.readObject();

      int i = 0;
      for (; i < boards.size(); i++) {
        if (boards.get(i).getNo() == board.getNo()) {
          break;
        }
      }

      if (i == boards.size()) {
        boards.add(board);
        out.writeUTF("OK");

      } else {
        out.writeUTF("FAIL");
        out.writeUTF("같은 번호의 게시물이 있습니다.");
      }

    } catch (Exception e) {
      out.writeUTF("FAIL");
      out.writeUTF(e.getMessage());
    }
  }

  private void listBoard(ObjectOutputStream out) throws IOException {
    out.writeUTF("OK");
    out.reset();
    out.writeObject(boards);
  }

  public static void main(String[] args) {

    System.out.println("서버 수업관리 시스템");
    ServerApp app = new ServerApp();

    // 애플리케이션의 상태를 정보를 받을 옵저버를 등록한다.
    app.addApplicationContextListener(new DataLoaderListener());

    app.service();
  }
}
