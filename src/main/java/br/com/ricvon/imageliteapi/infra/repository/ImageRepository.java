package br.com.ricvon.imageliteapi.infra.repository;

import br.com.ricvon.imageliteapi.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}
