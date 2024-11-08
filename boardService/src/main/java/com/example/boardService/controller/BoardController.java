package com.example.boardService.controller;

import com.example.boardService.domain.Board;
import com.example.boardService.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/boards")
    public String board(Model model) {
        model.addAttribute("boardList", boardService.findAllBoards());
        return "boardList";
    }

    @GetMapping("/boards/new")
    public String newBoard() {
        return "newBoard";
    }

    @PostMapping("/boards/new")
    public String newBoard(@ModelAttribute Board board) {
        boardService.writeBoard(board);
        return "redirect:/boards";
    }

    @GetMapping("/boards/{boardId}")
    public String detailBoard(@PathVariable Long boardId, Model model) {
        Board board = boardService.findBoardById(boardId);
        model.addAttribute("board", board);
        return "board-detail";
    }

    @GetMapping("/boards/{boardId}/edit")
    public String editBoard(@PathVariable Long boardId, Model model) {
        Board board = boardService.findBoardById(boardId);
        model.addAttribute("board", board);
        return "editBoard";
    }

    @PostMapping("/boards/{boardId}/edit")
    public String editBoard(@PathVariable Long boardId, @ModelAttribute Board board) {
        Board oldBoard = boardService.findBoardById(boardId);
        boardService.updateBoard(board, oldBoard);
        return "redirect:/boards";
    }

    @GetMapping("/boards/{boardId}/delete")
    public String deleteBoard(@PathVariable Long boardId) {
        Board board = boardService.findBoardById(boardId);
        boardService.deleteBoard(board);
        return "redirect:/boards";
    }
}
