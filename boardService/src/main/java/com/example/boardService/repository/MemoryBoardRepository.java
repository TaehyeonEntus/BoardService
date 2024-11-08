package com.example.boardService.repository;

import com.example.boardService.domain.Board;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryBoardRepository implements BoardRepository{
    private static List<Board> boardList = new ArrayList<>();
    private static Long boardId = 1L;

    @PostConstruct
    public void init() {
        // 예시 데이터 추가
        save(new Board("제목1", "내용1"));
        save(new Board("제목2", "내용2"));
    }


    @Override
    public Long save(Board board) {
        board.setBoardId(boardId);
        boardList.add(board);
        boardId++;
        return board.getBoardId();
    }

    @Override
    public void delete(Board board) {
        boardList.remove(board);
    }

    @Override
    public Long update(Board newBoard, Board oldBoard) {
        int index = boardList.indexOf(oldBoard);
        boardList.set(index, newBoard);
        return newBoard.getBoardId();
    }

    @Override
    public Board findById(Long boardId) {
        return boardList.stream()
                .filter(item -> item.getBoardId().equals(boardId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Board> findAll() {
        return boardList;
    }
}
