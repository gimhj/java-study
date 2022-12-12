package tutoring.Project.BoardLike.controller;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tutoring.Project.BoardLike.entity.BoardLike;
import tutoring.Project.BoardLike.service.BoardLikeService;
import tutoring.Project.board.entity.Board;
import tutoring.Project.board.service.BoardService;
import tutoring.Project.member.entity.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
@Slf4j
public class BoardLikeController {

    private final BoardLikeService boardLikeService;
    private final BoardService boardService;

    @PostMapping("")
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity store(@RequestParam Long boardId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();
        Board board = boardService.findOne(boardId);

        boardLikeService.addBoardLike(member, board);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{boardLikeId}")
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity update(@PathVariable("boardLikeId") Long boardLikeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();
        Optional<BoardLike> boardLike = boardLikeService.findById(boardLikeId);

        if (boardLike.isEmpty()) {
            throw new IllegalStateException("좋아요 이력이 없습니다.");
        }

        boardLikeService.updatedDeletedAt(boardLike.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
