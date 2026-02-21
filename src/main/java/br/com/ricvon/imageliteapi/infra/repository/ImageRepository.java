package br.com.ricvon.imageliteapi.infra.repository;

import br.com.ricvon.imageliteapi.domain.entity.Image;
import br.com.ricvon.imageliteapi.domain.enums.ImageExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {
    default List<Image> findByExtensionAndNameorTagsLike(ImageExtension extension, String query){
        return findAll();
    }
}
