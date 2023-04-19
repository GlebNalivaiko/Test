package by.sunshine.converter;

import by.sunshine.dto.CommentDto;
import by.sunshine.dto.UserDto;
import by.sunshine.entity.Comment;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class CommentConverter {
    public Comment convert(UserDto userDto, CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setOpinion(commentDto.getOpinion().trim());
        comment.setNameOfUser(userDto.getName());
        comment.setDate(LocalDate.now());
        int stars = commentDto.getStars();
        if (commentDto.getStars() > 5) stars = 5;
        else if (commentDto.getStars() < 1) stars = 1;
        comment.setStars(stars);
        return comment;
    }
}
