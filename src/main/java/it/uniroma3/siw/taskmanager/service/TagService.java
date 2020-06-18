package it.uniroma3.siw.taskmanager.service;

import it.uniroma3.siw.taskmanager.model.Tag;
import it.uniroma3.siw.taskmanager.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    @Transactional
    public Tag getTag(Long id){
        Optional<Tag> result = this.tagRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Tag saveTag(Tag tag){
        return this.tagRepository.save(tag);
    }

    @Transactional
    public List<Tag> getAllTags(){
        List<Tag> tags = new ArrayList<Tag>();
        Iterable<Tag> iterable = this.tagRepository.findAll();
        for (Tag t :
                iterable) {
            tags.add(t);
        }
        return tags;
    }

    @Transactional
    public void deleteTag(Tag tag){
        this.tagRepository.delete(tag);
    }



}
