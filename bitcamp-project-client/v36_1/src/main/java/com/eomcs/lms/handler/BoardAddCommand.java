// "/board/add" 명령어 처리
package com.eomcs.lms.handler;

import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.util.Prompt;

public class BoardAddCommand implements Command {
  Prompt prompt;
  BoardDao boardDao;

  public BoardAddCommand(BoardDao boardDao, Prompt prompt) {
    this.boardDao = boardDao;
    this.prompt = prompt;

  }

  @Override
  public void execute() {
    Board board = new Board();
    board.setTitle(prompt.inputString("내용? "));

    try {
      boardDao.insert(board);
      System.out.println("저장하였습니다.");

    } catch (Exception e) {
      System.out.println("게시글 등록 실패!");
    }
  }
}


