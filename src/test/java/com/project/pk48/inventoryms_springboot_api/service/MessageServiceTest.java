package com.project.pk48.inventoryms_springboot_api.service;

import com.project.pk48.inventoryms_springboot_api.models.Message;
import com.project.pk48.inventoryms_springboot_api.repositories.MessageRepository;
import com.project.pk48.inventoryms_springboot_api.services.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    private MessageRepository messageRepository;
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        messageRepository = mock(MessageRepository.class);
        messageService = new MessageService(messageRepository);
    }

    @Test
    void testGetAllMessages() {
        when(messageRepository.findAll()).thenReturn(Arrays.asList(new Message(), new Message()));
        assertEquals(2, messageService.getAllMessages().size());
    }

    @Test
    void testGetMessageById() {
        Message message = new Message();
        message.setId(1);
        when(messageRepository.findById(1L)).thenReturn(Optional.of(message));
        assertNotNull(messageService.getMessageById(1L));
    }

    @Test
    void testSave() {
        Message message = new Message();
        when(messageRepository.save(message)).thenReturn(message);
        assertEquals(message, messageService.save(message));
    }

    @Test
    void testDeleteMessage() {
        doNothing().when(messageRepository).deleteById(1L);
        messageService.deleteMessage(1L);
        verify(messageRepository, times(1)).deleteById(1L);
    }
}