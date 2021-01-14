package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) { this.noteMapper = noteMapper; }

    public List<Note> getUserNotes(Integer userId) { return noteMapper.getNotes(userId); }

    public Note getUserNote(Integer noteId) { return noteMapper.getNoteById(noteId); }

    public Integer deleteUserNote(Integer noteId) { return noteMapper.deleteNote(noteId); }

    public Integer saveUserNote(Note note, User user) {

        return noteMapper.saveNote(new Note(
                null,
                note.getNotetitle(),
                note.getNotedescription(),
                user.getUserId()
        ));
    }

    public Integer updateUserNote(Note note) {
        Note noteToUpdate = getUserNote(note.getNoteid());
        noteToUpdate.setNotetitle(note.getNotetitle());
        noteToUpdate.setNotedescription(note.getNotedescription());

        return noteMapper.updateNote(noteToUpdate);
    }
}
