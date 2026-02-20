package br.com.ricvon.imageliteapi.application.images;


import br.com.ricvon.imageliteapi.domain.entity.Image;
import br.com.ricvon.imageliteapi.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/images")
@Slf4j
@RequiredArgsConstructor
public class ImagesController {
    private final ImageService service;
    private final ImageMapper mapper;
    //{"name": "", "size":100}//formato application/json

    //para receber arquivo de upload é necessário usar
    // o formato mult-part/formdata

    @PostMapping
    public ResponseEntity save(
        @RequestParam(value = "file", required = false) MultipartFile file,
        @RequestParam("name") String name,
        @RequestParam("tags") List<String> tags
        ) throws IOException {
            log.info("imagem recebida: {}, size {}", file.getOriginalFilename(), file.getSize());
            /*log.info("Content Type: {}", file.getContentType());
            log.info("Media Type: {}", MediaType.valueOf(file.getContentType()));
            log.info("Nome definido por a imagem: {}", name);
            log.info("Tags> {}", tags);

        Image image = Image.builder()
                .name(name)
                .tags(String.join(",",tags))
                .size(file.getSize())
                .extension(ImageExtension.valueOf(MediaType.valueOf(file.getContentType())))
                .file(file.getBytes())
                .build();*/

        Image image = mapper.mapToImage(file, name, tags);

        Image sevedImage = service.save(image);

        URI imageUri = buildImageURL(sevedImage);

         return ResponseEntity.created(imageUri).build();
    }

    private URI buildImageURL(Image image){
        String imagePath = "/" + image.getId();
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(imagePath)
                .build()
                .toUri();
    }
}
