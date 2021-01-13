package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) { this.noteMapper = noteMapper; }

    public List<Note> getUserNotes(Integer userId) { return noteMapper.getNotes(userId); }

    public Note getUserNote(Integer noteId) { return noteMapper.getNoteById(noteId); }

    public Integer deleteUserNote(Integer noteId) { return noteMapper.deleteNote(noteId); }

    public Integer saveUserNote(Note note) { return noteMapper.saveNote(note); }

    public Integer updateUserNote(Integer noteid) { return noteMapper.updateNote(noteid); }
}
