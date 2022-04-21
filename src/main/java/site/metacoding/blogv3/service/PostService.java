package site.metacoding.blogv3.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv3.domain.category.Category;
import site.metacoding.blogv3.domain.category.CategoryRepository;
import site.metacoding.blogv3.domain.post.Post;
import site.metacoding.blogv3.domain.post.PostRepository;
import site.metacoding.blogv3.domain.user.User;
import site.metacoding.blogv3.handler.ex.CustomException;
import site.metacoding.blogv3.util.UtilFileUpload;
import site.metacoding.blogv3.web.dto.post.PostRespDto;
import site.metacoding.blogv3.web.dto.post.PostWriteReqDto;

@RequiredArgsConstructor
@Service
public class PostService {

    @Value("${file.path}")
    private String uploadFolder;

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public List<Category> 게시글쓰기화면(User principal) {
        return categoryRepository.findByUserId(principal.getId());
    }

    // 하나의 서비스는 여러가지 일을 한번에 처리한다 . (여러가지 일이 하나의 트랜잭션이다.)
    @Transactional
    public void 게시글쓰기(PostWriteReqDto postWriteReqDto, User principal) {

        System.out.println(postWriteReqDto);
        String thumnail = null;
        // UUID로 파일쓰고 경로 리턴 받기.
        if (!postWriteReqDto.getThumnailFile().isEmpty()) {
            thumnail = UtilFileUpload.write(uploadFolder, postWriteReqDto.getThumnailFile());
        }
        // Category category = new Category();
        // category.setId(postWriteReqDto.getCategoryId());

        Optional<Category> categoryOp = categoryRepository.findById(postWriteReqDto.getCategoryId());
        if (categoryOp.isPresent()) {
            Post post = postWriteReqDto.toEntity(thumnail, principal, categoryOp.get());
            postRepository.save(post);
        } else {
            throw new CustomException("해당 카테고리가 존재하지 않습니다.");
        }

        // INSERT INTO post(categoryId, title, content, userId, thumnail)VALUES(?,?,?,?)
        // postRepository.mSave(postWriteReqDto.getCategoryId(), principal.getId(),
        // postWriteReqDto.getTitle(),
        // postWriteReqDto.getContent(), thumnail);

    }

    public PostRespDto 게시글목록보기(Integer userId, Pageable pageable) {
        Page<Post> postsEntity = postRepository.findByUserId(userId, pageable);
        List<Category> categorysEntity = categoryRepository.findByUserId(userId);
        PostRespDto postRespDto = new PostRespDto(
                postsEntity,
                categorysEntity);
        return postRespDto;
    }

    public PostRespDto 게시글카테고리별보기(Integer userId, Integer categoryId, Pageable pageable) {
        Page<Post> postsEntity = postRepository.findByUserIdAndCategoryId(userId, categoryId, pageable);
        List<Category> categorysEntity = categoryRepository.findByUserId(userId);
        PostRespDto postRespDto = new PostRespDto(
                postsEntity,
                categorysEntity);
        return postRespDto;
    }
}