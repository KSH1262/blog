package com.example.test2.controller;

import com.example.test2.config.auth.PrincipalDetail;
import com.example.test2.dto.BoardResponseDto;
import com.example.test2.dto.BoardSearchDto;
import com.example.test2.entity.Board;
import com.example.test2.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String index(@ModelAttribute BoardSearchDto searchDto, Model model,
                        @PageableDefault(size = 8,sort = "id",direction = Sort.Direction.DESC)Pageable pageable) {
        Page<BoardResponseDto> boards = boardService.searchBoards(searchDto, pageable);
        List<BoardResponseDto> recentBoards = boardService.getRecentBoards();

        int currentPage = pageable.getPageNumber();
        int totalPages = boards.getTotalPages();
        int startPage = Math.max(0, currentPage - 2);
        int endPage = Math.min(startPage + 4, totalPages - 1);

        model.addAttribute("boards", boards);
        model.addAttribute("recentBoards", recentBoards);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchDto", searchDto);
        return "index";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model, @AuthenticationPrincipal PrincipalDetail principal){
        model.addAttribute("board", boardService.reading(id));
        model.addAttribute("principal", principal);
        model.addAttribute("recentBoards", boardService.getRecentBoards());
        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model){
        model.addAttribute("board", boardService.reading(id));
        return "board/updateForm";
    }

    @GetMapping("/board/saveForm")
    public String saveForm(){ return "board/saveForm"; }
}
