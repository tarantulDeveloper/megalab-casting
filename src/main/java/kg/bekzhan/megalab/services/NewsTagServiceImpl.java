package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.ENewsTags;
import kg.bekzhan.megalab.entities.NewsTag;
import kg.bekzhan.megalab.repo.NewsTagRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsTagServiceImpl implements NewsTagService{
    private final NewsTagRepo newsTagRepo;
    @Override
    public NewsTag createTag(String newsTag) {
        NewsTag tag = new NewsTag(ENewsTags.valueOf(newsTag));
        return newsTagRepo.save(tag);
    }
}
