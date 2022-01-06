package com.app.libraryeventsproducer.controller;

import com.app.libraryeventsproducer.domain.Book;
import com.app.libraryeventsproducer.domain.LibraryEvent;
import com.app.libraryeventsproducer.producer.LibraryEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(LibraryEventsController.class)
@AutoConfigureMockMvc
public class LibraryEventControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    LibraryEventProducer libraryEventProducer;

    @Test
    void postLibraryEvent() throws Exception {
        // given
        Book book = Book.builder().bookId(123).bookName("Kafka using spring boot")
                .bookAuthor("Stephen").build();

        LibraryEvent libraryEvent = LibraryEvent.builder().libraryEventId(null).book(book).build();

        String value = objectMapper.writeValueAsString(libraryEvent);
        Mockito.doNothing().when(libraryEventProducer).sendLibraryEvent_Approach2(Mockito.isA(LibraryEvent.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/libraryevent").content(value)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}
