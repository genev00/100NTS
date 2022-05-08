package com.a100nts.services.impl;

import com.a100nts.dto.CommentDTO;
import com.a100nts.models.Comment;
import com.a100nts.models.Site;
import com.a100nts.models.User;
import com.a100nts.repositories.SiteRepository;
import com.a100nts.repositories.UserRepository;
import com.a100nts.services.SiteService;
import com.a100nts.web.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.a100nts.utils.ListToStringConverter.DATE_TIME_FORMATTER;

@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Override
    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    @Override
    public Site getDetails(Long id) {
        return siteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Site> getAllSitesBG() {
        return siteRepository.findAll();
    }

    @Override
    public Site getDetailsBG(Long id) {
        return siteRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Site voteSite(Vote vote) {
        Site site = siteRepository.findById(vote.getSiteId()).get();
        com.a100nts.models.Vote voteDB = site.getVotes().stream()
                .filter(v -> v.getUser().getId().equals(vote.getUserId()))
                .findFirst()
                .map(v -> {
                    v.setVote(vote.getVote());
                    return v;
                })
                .orElse(null);

        List<com.a100nts.models.Vote> siteVotes = new ArrayList<>(site.getVotes());
        if (voteDB != null) {
            int voteIndex = 0;
            for (int i = 0; i < siteVotes.size(); i++) {
                if (siteVotes.get(i).getId().equals(voteDB.getId())) {
                    voteIndex = i;
                    break;
                }
            }
            siteVotes.set(voteIndex, voteDB);
        } else {
            siteVotes.add(new com.a100nts.models.Vote(
                    site, userRepository.findById(vote.getUserId()).get(), vote.getVote()
            ));
        }
        site.setVotes(siteVotes);
        return siteRepository.save(site);
    }

    @Override
    public Integer getUserVoteForSite(Long userId, Long siteId) {
        return siteRepository.findById(siteId).get().getVotes().stream()
                .filter(v -> v.getUser().getId().equals(userId))
                .map(com.a100nts.models.Vote::getVote)
                .findFirst()
                .orElse(0);
    }

    @Override
    @Transactional
    public Comment addComment(CommentDTO comment) {
        Site site = siteRepository.findById(Long.valueOf(comment.getCommenter())).get();
        User user = userRepository.findById(Long.valueOf(comment.getDateTime())).get();
        List<Comment> comments = new ArrayList<>(site.getComments());
        Comment newComment = new Comment(
            user, site, comment.getComment(), LocalDateTime.now().format(DATE_TIME_FORMATTER)
        );
        comments.add(newComment);
        site.setComments(comments);
        return newComment;
    }

}
