package com.eomcs.lms.dao.json;

import java.util.List;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

public class LessonJsonFileDao extends AbstractJsonFileDao<Lesson> implements LessonDao {

  public LessonJsonFileDao(String filename) {
    super(filename);
  }

  @Override
  public int insert(Lesson lesson) throws Exception {
    if (indexOf(lesson.getNo()) > -1) {
      return 0;
    }
    list.add(lesson);
    saveData();
    return 1;
  }

  @Override
  public List<Lesson> findAll() throws Exception {
    return list;
  }

  @Override
  public Lesson findByNo(int no) throws Exception {
    int index = indexOf(no);
    if (index == -1) {
      return null;
    }
    return list.get(index);
  }

  @Override
  public int update(Lesson lesson) throws Exception {
    int index = indexOf(lesson.getNo());
    if (index == -1) {
      return 0;
    }

    list.set(index, lesson);
    saveData();
    return 1;
  }

  @Override
  public int delete(int no) throws Exception {
    int index = indexOf(no);
    if (index == -1) {
      return 0;
    }
    list.remove(index);
    saveData();
    return 1;
  }

  @Override
  protected <K> int indexOf(K key) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getNo() == (int) key) {
        return i;
      }
    }
    return -1;
  }
}
