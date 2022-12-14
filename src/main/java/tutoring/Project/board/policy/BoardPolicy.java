package tutoring.Project.board.policy;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public boolean update(Member member, Long boardId) {
        Board board = boardService.findOne(boardId);

        return Objects.equals(board.getMember().getId(), member.getId());
    }
}
