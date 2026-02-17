package br.com.ricvon.imageliteapi.application.images;

import br.com.ricvon.imageliteapi.domain.entity.Image;
import br.com.ricvon.imageliteapi.domain.service.ImageService;
import br.com.ricvon.imageliteapi.infra.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository repository;

    @Override
    @Transactional
    public Image save(Image image){
        return repository.save(image);
    }
}
