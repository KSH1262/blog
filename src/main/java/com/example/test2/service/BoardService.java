package com.example.test2.service;

import com.example.test2.dto.BoardResponseDto;
import com.example.test2.dto.BoardSearchDto;
import com.example.test2.dto.ReplySaveRequestDto;
import com.example.test2.entity.Board;
import com.example.test2.entity.Reply;
import com.example.test2.entity.User;
import com.example.test2.repository.BoardRepository;
import com.example.test2.repository.ReplyRepository;
import com.example.test2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void write(Board board, User user) { // title, content
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
//    public Page<Board> writeList(Pageable pageable) {
//        return boardRepository.findAll(pageable);
//    }
    public Page<BoardResponseDto> writeList(Pageable pageable) {
        return boardRepository.findAll(pageable)
                .map(BoardResponseDto::new);
    }

    @Transactional(readOnly = true)
    public Board reading(long id) {
        return boardRepository.findById((int) id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
                });
    }

    @Transactional
    public void delete(int id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void modify(int id, Board requestBoard) {
        Board board = boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
                });
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
    }

    @Transactional
    public void writeComment(ReplySaveRequestDto replySaveRequestDto) {
        User user = userRepository.findById(replySaveRequestDto.getUserId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("댓글 작성 실패 : 유저 아이디를 찾을 수 없습니다.");
                });

        Board board = boardRepository.findById(replySaveRequestDto.getBoardId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("댓글 작성 실패 : 게시글 아이디를 찾을 수 없습니다.");
                });

        Reply reply = Reply.builder()
                .user(user)
                .board(board)
                .content(replySaveRequestDto.getContent())
                .build();


        replyRepository.save(reply);
    }

    public void commentDelete(int replyId) {
        replyRepository.deleteById(replyId);
    }

    @Transactional(readOnly = true)
    public Page<BoardResponseDto> searchBoards(BoardSearchDto searchDto, Pageable pageable) {
        return boardRepository.searchByTitle(searchDto, pageable)
                .map(BoardResponseDto::fromEntity);
    }

    public List<BoardResponseDto> getRecentBoards() {
        Pageable top6 = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "id"));
        return boardRepository.findAll(top6)
                .stream()
                .map(BoardResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
