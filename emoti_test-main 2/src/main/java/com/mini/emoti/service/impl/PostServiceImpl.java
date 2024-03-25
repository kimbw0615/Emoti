package com.mini.emoti.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.emoti.model.dao.PostDao;
import com.mini.emoti.model.dao.UserDao;
import com.mini.emoti.model.dto.PostDto;
import com.mini.emoti.model.entity.PostEntity;
import com.mini.emoti.service.PostService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @Override
    public void deletePost(Long postId) {
        postDao.deletePost(postId);
        
        // TODO Auto-generated method stub

    }

    @Override
    public List<Map<String,Object>> getAllPost() {
        // TODO Auto-generated method stub
        List<PostEntity> postEntityList = postDao.getAllPost();
        List<PostDto> postDtoList = new ArrayList<>();
        for (int i = 0; i < postEntityList.size(); i++) {
            PostDto dto = new PostDto();
            dto.setPostId(postEntityList.get(i).getPostId());
            dto.setCreatedDate(postEntityList.get(i).getCreatedDate());
            dto.setContent(postEntityList.get(i).getContent());
            dto.setHateCnt(postEntityList.get(i).getHateCount());
            dto.setLikeCnt(postEntityList.get(i).getLikeCount());
            dto.setEmail(postEntityList.get(i).getUsers().getEmail());
            postDtoList.add(dto);

        }

        List<Map<String, Object>> posts = new ArrayList<>();

        for (PostDto post : postDtoList) {
            String email = post.getEmail(); // 작성자의 이메일 가져오기
            String nickname;
            Long postId = post.getPostId();
            try {
                nickname = userDao.findByEmail(post.getEmail()).getNickname();
            } catch (Exception e) {
                nickname = null;
                e.printStackTrace();
            }
            String content = post.getContent(); // 내용 가져오기
            
            // 댓글 데이터를 Map 형태로 생성하여 리스트에 추가
            Map<String, Object> postData = new HashMap<>();
            postData.put("author", nickname); // 닉네임으로 대체
            postData.put("content", content);
            postData.put("postId", postId);
            postData.put("email", email);


            posts.add(postData);


    }

    return posts;

}


    @Override
    public void updatePost(PostDto dto, Long postId) {
        // TODO Auto-generated method stub
        PostEntity postEntity = postDao.findById(postId);
        postEntity.setContent(dto.getContent());
        postEntity.setHateCount(dto.getHateCnt());
        postEntity.setLikeCount(dto.getLikeCnt());
        postEntity.setUsers(userDao.findByEmail(dto.getEmail()));

        postDao.updatePost(postEntity);

    }

    @Override
    public List<Map<String, Object>> writePost(PostDto dto) {
        // TODO Auto-generated method stub
        if (dto != null) {
            PostEntity entity = new PostEntity();
            entity.setContent(dto.getContent());
            entity.setHateCount(dto.getHateCnt());
            entity.setLikeCount(dto.getLikeCnt());
            entity.setUsers(userDao.findByEmail(dto.getEmail()));

            Long postId = postDao.writePost(entity);

            List<Map<String, Object>> responseData = new ArrayList<>();

            Map<String, Object> data = new HashMap<>();
            try {
                String nickname = userDao.findByEmail(dto.getEmail()).getNickname();
                data.put("nickname", nickname);
            } catch (Exception e) {
                data.put("nickname", null);
                e.printStackTrace();
            }
            data.put("content", dto.getContent());
            data.put("postId", postId);
            data.put("email", dto.getEmail());

            responseData.add(data);

            return responseData;
        } else {
            return null;
        }

    }

    @Override
    public PostDto findById(Long postId) {
        PostEntity postEntity = postDao.findById(postId);
        PostDto postDto = new PostDto();
        postDto.setContent(postEntity.getContent());
        postDto.setCreatedDate(postDto.getCreatedDate());
        postDto.setEmail(postEntity.getUsers().getEmail());
        // TODO Auto-generated method stub
        return postDto;
    }

}
