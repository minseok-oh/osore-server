package com.dot.osore.core.memo.service;

import com.dot.osore.core.memo.entity.Memo;
import com.dot.osore.core.memo.repository.MemoRepository;
import com.dot.osore.core.note.entity.Note;
import com.dot.osore.core.note.service.NoteService;
import jakarta.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final NoteService noteService;
    private final MemoRepository memoRepository;

    /**
     * 메모를 생성하는 메서드
     *
     * @param noteId  노트 ID
     */
    public void saveMemo(Long noteId) {
        Note note = noteService.getNoteById(noteId);
        memoRepository.save(Memo.builder().page(null).content(null).note(note).build());
    }

    /**
     * 메모를 조회하는 메서드
     *
     * @param memoId 메모 ID
     */
    public String getMemo(Long memoId) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 메모가 존재하지 않습니다."));
        return memo.getContent();
    }

    /**
     * 메모를 수정하는 메서드
     *
     * @param memoId  메모 ID
     * @param content 메모 내용
     */
    @Transactional
    public void updateMemo(Long memoId, String content) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 메모가 존재하지 않습니다."));
        memo.setContent(content);
    }

    /**
     * 메모를 삭제하는 메서드
     *
     * @param memoId 메모 ID
     */
    @Transactional
    public void deleteMemo(Long memoId) {
        memoRepository.deleteById(memoId);
    }

    /**
     * 메모 리스트를 가져오는 메서드
     *
     * @param noteId 노트 ID
     */
    public List<Long> getMemoList(Long noteId) {
        List<Memo> memoList = memoRepository.findByNoteIdOrderById(noteId);
        return memoList.stream().map(Memo::getId).toList();
    }

    /**
     * 노트 ID로 메모를 삭제하는 메서드
     *
     * @param noteId 노트 ID
     */
    @Transactional
    public void deleteByNoteId(Long noteId) {
        memoRepository.deleteByNoteId(noteId);
    }
}
