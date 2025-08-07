package com.saracco.buenasLecturas.Service;

import com.saracco.buenasLecturas.model.Reader;
import com.saracco.buenasLecturas.repository.ReaderRepository;
import com.saracco.buenasLecturas.service.ReaderService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReaderServiceTest {

    @Test
    void register_NewReader_SavesWithEncryptedPassword() {
        ReaderRepository mockRepo = mock(ReaderRepository.class);
        ReaderService service = new ReaderService(mockRepo);

        Reader reader = new Reader();
        reader.setEmail("juan@mail.com");
        reader.setPassword("clave123");

        when(mockRepo.findByEmail("juan@mail.com")).thenReturn(null);
        when(mockRepo.save(any(Reader.class))).thenAnswer(inv -> inv.getArgument(0));

        service.register(reader);

        assertNotEquals("clave123", reader.getPassword());
        assertTrue(reader.getPassword().startsWith("$2a$"));
        verify(mockRepo, times(1)).save(reader);
    }

    @Test
    void register_ExistingEmail_ThrowsException() {

        ReaderRepository mockRepo = mock(ReaderRepository.class);
        ReaderService service = new ReaderService(mockRepo);

        Reader existing = new Reader();
        existing.setEmail("juan@mail.com");

        when(mockRepo.findByEmail("juan@mail.com")).thenReturn(existing);

        Reader nuevo = new Reader();
        nuevo.setEmail("juan@mail.com");
        nuevo.setPassword("clave123");

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            service.register(nuevo);
        });

        assertEquals("El email ya está registrado", thrown.getMessage());
        verify(mockRepo, never()).save(any());
    }
}
