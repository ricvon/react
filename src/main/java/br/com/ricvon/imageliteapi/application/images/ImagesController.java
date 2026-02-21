package br.com.ricvon.imageliteapi.application.images;


import br.com.ricvon.imageliteapi.domain.entity.Image;
import br.com.ricvon.imageliteapi.domain.enums.ImageExtension;
import br.com.ricvon.imageliteapi.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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


    // http://localhost:8080/v1/images/:id
    // /v1/images/:id
    @GetMapping("{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id){
        var possibleImage = service.getById(id);
        if (possibleImage.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var image = possibleImage.get();
        HttpHeaders headers =  new HttpHeaders();
        headers.setContentType(image.getExtension().getMediaType());
        headers.setContentLength(image.getSize());
        //inline; filename="image.PNG"
        headers.setContentDispositionFormData("inline; filename=\"" + image.getFileName() + "\"", image.getFileName());
        return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);
    }

    //localhost:8080/v1/images?extension=PNG&quey=Natureza
    @GetMapping
    public ResponseEntity<List<ImageDTO>> search(
            @RequestParam(value = "extension", required = false) String extension,
            @RequestParam(value = "extension", required = false) String query){

            var result = service.search(ImageExtension.valueOf(extension), query);

            var images = result.stream().map (image -> {
                var url= buildImageURL((image));
                return mapper.imageToDTO(image, url.toString());
            }).collect(Collectors.toList());

            return ResponseEntity.ok(images);
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
