package com.example.boardService.service;

import com.example.boardService.domain.Board;
import com.example.boardService.repository.MemoryBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final MemoryBoardRepository memoryBoardRepository;

    public Long writeBoard(Board board) {
        return memoryBoardRepository.save(board);
    }

    public void deleteBoard(Board board) {
        memoryBoardRepository.delete(board);
    }

    public Long updateBoard(Board newBoard, Board oldBoard) {
        return memoryBoardRepository.update(newBoard, oldBoard);
    }

    public Board findBoardById(Long boardId) {
        return memoryBoardRepository.findById(boardId);
    }

    public List<Board> findAllBoards() {
        return memoryBoardRepository.findAll();
    }
}
