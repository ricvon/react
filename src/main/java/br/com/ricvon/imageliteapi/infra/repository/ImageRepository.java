package br.com.ricvon.imageliteapi.infra.repository;

import br.com.ricvon.imageliteapi.domain.entity.Image;
import br.com.ricvon.imageliteapi.domain.enums.ImageExtension;
import br.com.ricvon.imageliteapi.infra.repository.specs.GenericSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.List;

import static br.com.ricvon.imageliteapi.infra.repository.specs.GenericSpecs.*;
import static br.com.ricvon.imageliteapi.infra.repository.specs.ImageSpecs.*;
import static org.springframework.data.jpa.domain.Specification.*;

public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {
    /**
     *
     * @param extension
     * @param query
     * @return
     *  Select * from Image where 1=1 AND EXTENSION='PNG' AND (NAME LIKE 'QUERY' OR TAGS LIKE 'QUERY')
     */

    default List<Image> findByExtensionAndNameorTagsLike(ImageExtension extension, String query){
        //Specification<Image> conjunction = (root, q, criteriaBuilder) -> criteriaBuilder.conjunction();
        //Specification<Image> spec = Specification.where(conjunction);

        Specification<Image> spec = where(conjunction());

        //AND EXTENSION='PNG'
        if (extension != null){
            //Specification<Image> extensionEqual = (root, q, cb) -> cb.equal(root.get("extension"), extension);
            //spec = spec.and(extensionEqual);
            spec = spec.and(extensionEquals(extension));
        }

        //AND (NAME LIKE 'QUERY' OR TAGS LIKE 'QUERY')
        if (StringUtils.hasText(query)){
            //Specification<Image> nameLike = (root, q, cb) -> cb.like(cb.upper(root.get("name")), "%" + query.toUpperCase() + "%");
            //Specification<Image> tagsLike = (root, q, cb) -> cb.like(cb.upper(root.get("tags")), "%" + query.toUpperCase() + "%");

            //Specification<Image> nameOrTagsLike = Specification.anyOf(nameLike, tagsLike);
            //Specification<Image> nameOrTagsLike = Specification.anyOf(nameLike(query), tagsLike(query));

            //spec = spec.and(nameOrTagsLike);
            spec = spec.and(anyOf(nameLike(query), tagsLike(query)));
        }

        return findAll(spec);
    }
}
