package site.metacoding.blogv3.web.dto.user;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileImgReqDto {
    @NotBlank
    private MultipartFile profileImg;
}
