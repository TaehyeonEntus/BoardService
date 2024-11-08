package com.example.boardService.repository;

import com.example.boardService.domain.Board;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository {
    public Long save(Board board);
    public void delete(Board board);
    public Long update(Board newBoard, Board oldBoard);
    public Board findById(Long boardId);
    public List<Board> findAll();
}
