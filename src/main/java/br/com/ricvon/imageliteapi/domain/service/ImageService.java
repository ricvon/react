package br.com.ricvon.imageliteapi.domain.service;

import br.com.ricvon.imageliteapi.domain.entity.Image;

import java.util.Optional;

public interface ImageService {
    Image save(Image image);

    Optional<Image> getById(String id);

}
