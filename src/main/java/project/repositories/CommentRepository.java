package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import project.models.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	@Query("SELECT count(c) > 0 FROM Comment c WHERE c.room.id = :roomId AND c.author.id = :authorId")
	public boolean hasComment(@Param("roomId") int roomId, @Param("authorId") int authorId);
}
