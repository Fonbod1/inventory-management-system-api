package com.project.pk48.inventoryms_springboot_api.service.CommentSrvice;

import com.project.pk48.inventoryms_springboot_api.models.Comment;
import com.project.pk48.inventoryms_springboot_api.repositories.CommentRepository;
import com.project.pk48.inventoryms_springboot_api.services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    private CommentRepository commentRepository;
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        commentRepository = mock(CommentRepository.class);
        commentService = new CommentService(commentRepository);
    }

    @Test
    void testGetAllComments() {
        when(commentRepository.findAll()).thenReturn(Arrays.asList(new Comment(), new Comment()));
        assertEquals(2, commentService.getAllComments().size());
    }

    @Test
    void testGetCommentById() {
        Comment comment = new Comment();
        comment.setId(1L);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        assertNotNull(commentService.getCommentById(1L));
    }

    @Test
    void testSaveComment() {
        Comment comment = new Comment();
        when(commentRepository.save(comment)).thenReturn(comment);
        assertEquals(comment, commentService.save(comment));
    }

    @Test
    void testDeleteComment() {
        doNothing().when(commentRepository).deleteById(1L);
        commentService.deleteComment(1L);
        verify(commentRepository, times(1)).deleteById(1L);
    }
}