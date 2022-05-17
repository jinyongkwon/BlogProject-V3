package site.metacoding.blogv3.web.dto.love;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoveRespDto {
    private Integer loveId;
    // 클래스가 필요하면 내부클래스를 만들어서 해야함.
    private PostDto post;

    @Data
    public class PostDto {
        private Integer id;
        private String title;
    }

}
