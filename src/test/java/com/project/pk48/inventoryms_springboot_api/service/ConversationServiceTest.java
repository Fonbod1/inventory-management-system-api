package com.project.pk48.inventoryms_springboot_api.service;

import com.project.pk48.inventoryms_springboot_api.models.Conversation;
import com.project.pk48.inventoryms_springboot_api.repositories.ConversationRepository;
import com.project.pk48.inventoryms_springboot_api.services.ConversationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConversationServiceTest {

    private ConversationRepository conversationRepository;
    private ConversationService conversationService;

    @BeforeEach
    void setUp() {
        conversationRepository = mock(ConversationRepository.class);
        conversationService = new ConversationService(conversationRepository);
    }

    @Test
    void testGetAllConversations() {
        when(conversationRepository.findAll()).thenReturn(Arrays.asList(new Conversation(), new Conversation()));
        assertEquals(2, conversationService.getAllConversations().size());
    }

    @Test
    void testGetConversationById() {
        Conversation conversation = new Conversation();
        conversation.setId(1);
        when(conversationRepository.findById(1L)).thenReturn(Optional.of(conversation));
        assertNotNull(conversationService.getConversationById(1L));
    }

    @Test
    void testSaveConversation() {
        Conversation conversation = new Conversation();
        when(conversationRepository.save(conversation)).thenReturn(conversation);
        assertEquals(conversation, conversationService.save(conversation));
    }

    @Test
    void testDeleteConversation() {
        doNothing().when(conversationRepository).deleteById(1L);
        conversationService.deleteConversation(1L);
        verify(conversationRepository, times(1)).deleteById(1L);
    }
}