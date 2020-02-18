package com.eomcs.lms.servlet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

public class LessonDetailServlet implements Servlet {

  LessonDao lessonDao;

  public LessonDetailServlet(LessonDao lessonDao) {
    this.lessonDao = lessonDao;
  }

  @Override
  public void service(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();
    Lesson lesson = lessonDao.findByNo(no);


    if (lesson != null) {
      out.writeUTF("OK");
      out.writeObject(lesson);
    } else {
      out.writeUTF("FAIL");
      out.writeUTF("해당 번호의 수업 정보가 없습니다.");
    }
  }
}