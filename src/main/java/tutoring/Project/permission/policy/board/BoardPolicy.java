package tutoring.Project.permission.policy.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import tutoring.Project.board.entity.Board;
import tutoring.Project.board.service.BoardService;
import tutoring.Project.member.entity.Member;
import tutoring.Project.permission.policy.Policy;

@Component
@RequiredArgsConstructor
@Slf4j
public class BoardPolicy implements Policy {

    private final BoardService boardService;

    @Override
    public Class<?> getTargetClass() {

        return Board.class;
    }

    public boolean update(Authentication authentication, Long boardId) {
        Member member = (Member) authentication.getPrincipal();
        Board board = boardService.findOne(boardId);

        return board.getMember().getId().equals(member.getId());
    }
}
