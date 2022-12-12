package tutoring.Project.permission.policy.boardLike;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tutoring.Project.BoardLike.entity.BoardLike;
import tutoring.Project.BoardLike.service.BoardLikeService;
import tutoring.Project.board.entity.Board;
import tutoring.Project.member.entity.Member;
import tutoring.Project.permission.policy.Policy;

@Component
@RequiredArgsConstructor
@Slf4j
public class BoardLikePolicy implements Policy {

    private final BoardLikeService boardLikedService;

    @Override
    public Class<?> getTargetClass() {

        return Board.class;
    }

    public boolean update(Member member, Long boardId) {
        Optional<BoardLike> boardLIke = boardLikedService.findById(boardId);

        if (boardLIke.isEmpty()) {
            return false;
        }

        return Objects.equals(boardLIke.get().getMember().getId(), member.getId());
    }
}
